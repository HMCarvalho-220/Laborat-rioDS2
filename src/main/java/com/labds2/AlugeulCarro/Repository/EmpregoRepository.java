package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Emprego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregoRepository extends JpaRepository<Emprego, Long> {
}