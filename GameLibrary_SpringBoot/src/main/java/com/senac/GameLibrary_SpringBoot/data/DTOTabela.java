package com.senac.GameLibrary_SpringBoot.data;

public class DTOTabela {
    private Usuario usuario;
    private JogoJogado jogosJogados;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public JogoJogado getJogosJogados() {
        return jogosJogados;
    }

    public void setJogosJogados(JogoJogado jogosJogados) {
        this.jogosJogados = jogosJogados;
    }
    
}
