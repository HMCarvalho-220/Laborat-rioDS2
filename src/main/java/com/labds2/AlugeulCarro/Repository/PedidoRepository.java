package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Pedido;
import com.labds2.AlugeulCarro.Model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    @Query("SELECT p FROM Pedido p WHERE " +
           "p.automovel.id = :automovelId AND " +
           "p.status = :status AND " +
           "((p.dataInicio BETWEEN :inicio AND :fim) OR " +
           "(p.dataFim BETWEEN :inicio AND :fim) OR " +
           "(:inicio BETWEEN p.dataInicio AND p.dataFim))")
    List<Pedido> findPedidosSobrepostos(
        @Param("automovelId") Long automovelId,
        @Param("status") StatusPedido status,
        @Param("inicio") LocalDate inicio,
        @Param("fim") LocalDate fim);
    
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByClienteId(Long clienteId);
}