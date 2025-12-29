package com.senac.GameLibrary_SpringBoot.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JogoJogadoRepository extends JpaRepository <JogoJogado, JogosJogadosId> {
    List<JogoJogado> findByUsuario(Usuario usuario);
    JogoJogado findByUsuarioAndJogo(Usuario usuario, Jogo jogo);
}
