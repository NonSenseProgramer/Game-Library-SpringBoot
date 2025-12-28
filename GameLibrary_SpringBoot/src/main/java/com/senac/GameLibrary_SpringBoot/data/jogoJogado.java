package com.senac.GameLibrary_SpringBoot.data;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "jogos_jogados")
public class jogoJogado {
    @EmbeddedId
    private JogosJogadosId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("jogoId")
    @JoinColumn(name = "id_jogo")
    private Jogo jogo;

    private String status;

    public JogosJogadosId getId() {
        return id;
    }

    public void setId(JogosJogadosId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
