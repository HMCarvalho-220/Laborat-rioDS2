package com.labds2.AlugeulCarro.Controllers;

import com.labds2.AlugeulCarro.Model.Emprego;
import com.labds2.AlugeulCarro.Service.EmpregoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empregos")
public class EmpregoController {

    @Autowired
    private EmpregoService empregoService;

    @GetMapping
    public List<Emprego> listarTodos() {
        return empregoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprego> buscarPorId(@PathVariable Long id) {
        return empregoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Emprego salvar(@RequestBody Emprego emprego) {
        return empregoService.salvar(emprego);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprego> atualizar(@PathVariable Long id, @RequestBody Emprego empregoAtualizado) {
        return ResponseEntity.ok(empregoService.atualizar(id, empregoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        empregoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}