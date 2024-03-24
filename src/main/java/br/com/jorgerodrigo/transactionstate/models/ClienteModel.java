package br.com.jorgerodrigo.transactionstate.models;

public class ClienteModel {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String profissao;
    private Double saldo;

    public ClienteModel(long id,String nome, String cpf, String email, String profissao, double saldo){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.profissao = profissao;
        this.saldo = saldo;
    }   

    public Long getId() {
      return id;
    } 

    public void setId(Long id) {
      this.id = id;
    }
  
    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfissao() {
        return profissao;
    }
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
