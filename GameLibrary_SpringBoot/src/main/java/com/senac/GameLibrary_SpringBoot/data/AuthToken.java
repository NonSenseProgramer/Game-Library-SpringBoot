package com.senac.GameLibrary_SpringBoot.data;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tokens_autorizacao")
public class AuthToken {

    @Id
    private String token;  // Chave primária da tabela

    @ManyToOne
    private Usuario usuario;  // Relacionamento com o usuário

    private LocalDateTime expiracao;  // Data de expiração
    public LocalDateTime getExpiracao()
    {
        return this.expiracao;
    }
}