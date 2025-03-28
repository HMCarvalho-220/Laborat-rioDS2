package com.labds2.AlugeulCarro.Controllers;

import com.labds2.AlugeulCarro.Model.Automovel;
import com.labds2.AlugeulCarro.Service.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automoveis")
public class AutomovelController {
    @Autowired
    private AutomovelService service;

    @GetMapping("/disponiveis")
    public List<Automovel> listarDisponiveis() {
        return service.listarDisponiveis();
    }
}