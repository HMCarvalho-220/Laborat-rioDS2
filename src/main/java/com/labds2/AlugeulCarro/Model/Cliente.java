package com.labds2.AlugeulCarro.Model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente") 
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "rg", nullable = false, length = 20) 
    private String rg;

    @Column(name = "cpf", nullable = false, unique = true, length = 14) 
    private String cpf;

    @Column(name = "nome", nullable = false, length = 100) 
    private String nome;

    @Column(name = "endereco", length = 200)
    private String endereco;

    @Column(name = "profissao", length = 100) 
    private String profissao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Emprego> empregos = new ArrayList<>();

    @Column(name = "login", length = 50) 
    private String login;

    @Column(name = "senha", length = 50)
    private String senha;

    public Cliente() {
    }

    public Cliente(String rg, String cpf, String nome, String endereco, String profissao) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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

    public void setEmpregos(List<Emprego> empregos) {
        this.empregos = empregos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public void adicionarEmprego(Emprego emprego) {
        emprego.setCliente(this); 
        empregos.add(emprego);
    }

    public void removerEmprego(Emprego emprego) {
        empregos.remove(emprego); 
        emprego.setCliente(null);
    }

    public double getRendaTotal() {
        return empregos.stream()
                .mapToDouble(Emprego::getRendimento)
                .sum();
    }

    public boolean validarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }
}