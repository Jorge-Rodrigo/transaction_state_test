package br.com.jorgerodrigo.transactionstate.models;

public class ClienteModel {
    private Long id;
    private String nome;
    private String cpf;
    private int idade;
    private String profissao;

    public ClienteModel(long id,String nome, String cpf, int idade, String profissao){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.profissao = profissao;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getProfissao() {
        return profissao;
    }
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
