package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Automovel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    List<Automovel> findByDisponivelTrue();  
}