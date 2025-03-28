package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    boolean existsById(Long id); 
    
    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.pedidos")
    List<Cliente> findAllWithPedidos();
    
    Optional<Cliente> findByCpf(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findByRg(String rg);
    boolean existsByCpf(String cpf);

    @Query("SELECT c FROM Cliente c WHERE SIZE(c.pedidos) > :minPedidos")
    List<Cliente> findClientesComMaisDeXPedidos(@Param("minPedidos") int minPedidos);
}