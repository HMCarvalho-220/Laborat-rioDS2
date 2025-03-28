package com.labds2.AlugeulCarro.Controllers;

import com.labds2.AlugeulCarro.Model.Automovel;
import com.labds2.AlugeulCarro.Service.AutomovelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {

    private final AutomovelService automovelService;


    public AutomovelController(AutomovelService automovelService) {
        this.automovelService = automovelService;
    }

    @PostMapping
    public ResponseEntity<Automovel> criarAutomovel(@RequestBody Automovel automovel) {
        Automovel novoAutomovel = automovelService.salvar(automovel);
        return ResponseEntity.ok(novoAutomovel);
    }

    @GetMapping
    public ResponseEntity<List<Automovel>> listarTodos() {
        return ResponseEntity.ok(automovelService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Automovel>> listarDisponiveis() {
        return ResponseEntity.ok(automovelService.listarDisponiveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Automovel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(automovelService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        automovelService.remover(id);
        return ResponseEntity.noContent().build();
    }
}