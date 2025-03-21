package com.labds2.AlugeulCarro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.labds2.AlugeulCarro.Model.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    Optional<Cliente> findByRg(String rg);
    
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    List<Cliente> findByProfissaoContainingIgnoreCase(String profissao);
    
    boolean existsByCpf(String cpf);
    
    @Query("SELECT c FROM Cliente c JOIN c.empregos e GROUP BY c HAVING SUM(e.rendimento) >= :valorMinimo")
    List<Cliente> findClientesComRendaAcimaDe(@Param("valorMinimo") double valorMinimo);
    
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p WHERE p.cancelado = false")
    List<Cliente> findClientesComPedidosAtivos();
    
    List<Cliente> findByEnderecoContainingIgnoreCase(String parteEndereco);
    
    @Query("SELECT c.profissao, COUNT(c) FROM Cliente c GROUP BY c.profissao")
    List<Object[]> countClientesByProfissao();
    
    Optional<Cliente> findByLogin(String login);
}