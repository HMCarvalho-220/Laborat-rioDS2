package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.Automovel;
import com.labds2.AlugeulCarro.Repository.AutomovelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomovelService {

    private final AutomovelRepository automovelRepository;

    public AutomovelService(AutomovelRepository automovelRepository) {
        this.automovelRepository = automovelRepository;
    }


    public Automovel salvar(Automovel automovel) {
        return automovelRepository.save(automovel);
    }

    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }

    public List<Automovel> listarDisponiveis() {
        return automovelRepository.findByDisponivelTrue();
    }

    public Automovel buscarPorId(Long id) {
        return automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
    }

    public boolean existeAutomovel(Long id) {
        return automovelRepository.existsById(id);
    }

    public Double buscarPrecoDiaria(Long id) {
        return automovelRepository.findById(id)
                .map(Automovel::getPrecoDiaria)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
    }

    public void remover(Long id) {
        automovelRepository.deleteById(id);
    }
}