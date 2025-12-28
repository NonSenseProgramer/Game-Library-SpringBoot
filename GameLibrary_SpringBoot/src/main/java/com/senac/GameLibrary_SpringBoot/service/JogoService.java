package com.senac.GameLibrary_SpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.Jogo;
import com.senac.GameLibrary_SpringBoot.data.jogoRepository;
@Service
public class JogoService {
    @Autowired
    jogoRepository jogoRepo;
    public Jogo retornaJogoPorNome(String nomeJogo)
    {
        Jogo jogo = jogoRepo.findByJogoIgnoreCase(nomeJogo);
        if(jogo == null)
            {
                return null;
            }
        return jogo;
    }
}
