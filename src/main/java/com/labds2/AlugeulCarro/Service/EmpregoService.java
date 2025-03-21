package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.Emprego;
import com.labds2.AlugeulCarro.Repository.EmpregoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpregoService {

    @Autowired
    private EmpregoRepository empregoRepository;

    public List<Emprego> listarTodos() {
        return empregoRepository.findAll();
    }

    public Optional<Emprego> buscarPorId(Long id) {
        return empregoRepository.findById(id);
    }

    public Emprego salvar(Emprego emprego) {
        return empregoRepository.save(emprego);
    }

    public void deletar(Long id) {
        empregoRepository.deleteById(id);
    }

    public Emprego atualizar(Long id, Emprego empregoAtualizado) {
        return empregoRepository.findById(id)
                .map(emprego -> {
                    emprego.setEmpresa(empregoAtualizado.getEmpresa());
                    emprego.setCargo(empregoAtualizado.getCargo());
                    emprego.setRendimento(empregoAtualizado.getRendimento());
                    emprego.setDataInicio(empregoAtualizado.getDataInicio());
                    return empregoRepository.save(emprego);
                })
                .orElseGet(() -> {
                    empregoAtualizado.setId(id);
                    return empregoRepository.save(empregoAtualizado);
                });
    }
}