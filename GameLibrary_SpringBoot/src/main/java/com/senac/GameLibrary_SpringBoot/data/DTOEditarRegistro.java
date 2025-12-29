package com.senac.GameLibrary_SpringBoot.data;

public class DTOEditarRegistro {

    public String getJogoVelho() {
        return jogoVelho;
    }

    public void setJogoVelho(String jogoVelho) {
        this.jogoVelho = jogoVelho;
    }

    public String getJogoNovo() {
        return jogoNovo;
    }

    public void setJogoNovo(String jogoNovo) {
        this.jogoNovo = jogoNovo;
    }
    private String jogoVelho;
    private String jogoNovo;

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }
    private String novoStatus;


    
}
