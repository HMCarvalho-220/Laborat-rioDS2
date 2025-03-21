package com.labds2.AlugeulCarro.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    
    private String profissao;
    
    private List<Emprego> empregos;
    
 //   private List<Pedido> pedidos;
 //   private List<Contrato> contratos;
 //   private List<Automovel> automoveisAlugados;
    
    private String login;
    private String senha;
    
    public Cliente(String rg, String cpf, String nome, String endereco, String profissao) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.empregos = new ArrayList<>(3); 
 //       this.pedidos = new ArrayList<>();
 //       this.contratos = new ArrayList<>();
//        this.automoveisAlugados = new ArrayList<>();
    }
    
    public boolean adicionarEmprego(Emprego emprego) {
        if (empregos.size() < 3) {
            return empregos.add(emprego);
        }
        return false;
    }
    
    public boolean removerEmprego(Emprego emprego) {
        return empregos.remove(emprego);
    }
    
   /*  public Pedido criarPedido() {
        Pedido novoPedido = new Pedido(this);
        pedidos.add(novoPedido);
        return novoPedido;
    }
    
    public boolean modificarPedido(Pedido pedido) {
        return true;
    }
    
    public boolean cancelarPedido(Pedido pedido) {
        pedido.setCancelado(true);
        return true;
    }
    
   public List<Pedido> consultarPedidos() {
       return new ArrayList<>(pedidos);
   } */
    
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(Long id) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public List<Emprego> getEmpregos() {
        return empregos;
    }

    public double getRendaTotal() {
        return empregos.stream()
                .mapToDouble(Emprego::getRendimento)
                .sum();
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean validarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }
}