package com.senac.GameLibrary_SpringBoot.data;

public class RegistroJogoDTO {

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String nomeJogo;
    public String status;
}
