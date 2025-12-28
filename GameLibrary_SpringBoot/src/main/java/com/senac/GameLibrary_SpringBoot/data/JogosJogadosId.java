package com.senac.GameLibrary_SpringBoot.data;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class JogosJogadosId implements Serializable {

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setJogoId(Long jogoId) {
        this.jogoId = jogoId;
    }

    private Long userId;
    private Long jogoId;

    public JogosJogadosId() {}

    public JogosJogadosId(Long userId, Long jogoId) {
        this.userId = userId;
        this.jogoId = jogoId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getJogoId() {
        return jogoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JogosJogadosId that = (JogosJogadosId) o;

        return Objects.equals(userId, that.userId)
            && Objects.equals(jogoId, that.jogoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, jogoId);
    }
}


