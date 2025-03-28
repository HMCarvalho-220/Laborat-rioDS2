package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    List<Automovel> findByDisponivelTrue();
}