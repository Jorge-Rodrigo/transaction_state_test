package br.com.jorgerodrigo.transactionstate.models;



public class EmpresaModel {
    private Long id;
    private String nome;
    private String cnpj;
    private String areaAtuacao;
    private double saldo;

    public EmpresaModel(long id,String nome, String cnpj, String areaAtuacao, double saldo){
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.areaAtuacao = areaAtuacao;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
    
}
