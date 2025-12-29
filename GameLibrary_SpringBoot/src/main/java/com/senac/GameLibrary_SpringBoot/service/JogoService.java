package com.senac.GameLibrary_SpringBoot.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.Jogo;
import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.data.jogoRepository;

@Service
public class JogoService {
    @Autowired
    jogoRepository jogoRepo;

    public Jogo retornaJogoPorNome(String nomeJogo) {
        Jogo jogo = jogoRepo.findByJogoIgnoreCase(nomeJogo);
        if (jogo == null) {
            return null;
        }
        return jogo;
    }

    public Jogo criaJogo(String nomeJogo, String genero, LocalDate dataLancamento) {
        Jogo jogo = retornaJogoPorNome(nomeJogo);
        if (jogo != null) {
            return null;
        }
        jogo = new Jogo();
        jogo.setJogo(nomeJogo);
        jogo.setGenero(genero);
        jogo.setDataLancamento(dataLancamento);
        return jogoRepo.save(jogo);

    }
}
