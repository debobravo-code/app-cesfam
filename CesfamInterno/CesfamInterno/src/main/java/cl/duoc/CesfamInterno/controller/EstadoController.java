package cl.duoc.CesfamInterno.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoController {

    @GetMapping("/api/estado")
    public String verificarEstado() {
        return "Microservicio Funcionarios CESFAM funcionando correctamente";
    }
}