package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}