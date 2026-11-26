
package cl.duoc.notificaciones.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .addFilterBefore(
                        new CesfamAuthorizationFilter(),
                        BasicAuthenticationFilter.class
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    private static class CesfamAuthorizationFilter
            extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain)
                throws ServletException, IOException {

            String cesfamAuth =
                    request.getHeader("X-Cesfam-Authorization");

            if (cesfamAuth == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String originalAuth =
                    request.getHeader("Authorization");

            if (!cesfamAuth.startsWith("Basic ")
                    || (originalAuth != null
                    && originalAuth.regionMatches(
                    true, 0, "Basic ", 0, 6))) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            HttpServletRequestWrapper wrappedRequest =
                    new HttpServletRequestWrapper(request) {

                        @Override
                        public String getHeader(String name) {
                            if ("Authorization".equalsIgnoreCase(name)) {
                                return cesfamAuth;
                            }
                            return super.getHeader(name);
                        }

                        @Override
                        public Enumeration<String> getHeaders(String name) {
                            if ("Authorization".equalsIgnoreCase(name)) {
                                return Collections.enumeration(
                                        Collections.singletonList(cesfamAuth));
                            }
                            return super.getHeaders(name);
                        }
                    };

            filterChain.doFilter(wrappedRequest, response);
        }
    }
}
