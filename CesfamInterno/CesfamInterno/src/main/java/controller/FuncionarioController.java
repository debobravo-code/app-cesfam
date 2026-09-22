package cl.duoc.CesfamInterno.controller;

import cl.duoc.CesfamInterno.model.Funcionario;
import cl.duoc.CesfamInterno.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Funcionario crearFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.guardarFuncionario(funcionario);
    }

    @PutMapping("/{id}")
    public Funcionario actualizarFuncionario(
            @PathVariable Long id,
            @RequestBody Funcionario funcionario) {

        return funcionarioService.actualizarFuncionario(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFuncionario(@PathVariable Long id) {
        funcionarioService.eliminarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}