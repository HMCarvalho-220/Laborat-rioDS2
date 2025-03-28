package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.Automovel;
import com.labds2.AlugeulCarro.Repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutomovelService {
    @Autowired
    private AutomovelRepository repository;

    public List<Automovel> listarDisponiveis() {
        return repository.findByDisponivelTrue();
    }
}