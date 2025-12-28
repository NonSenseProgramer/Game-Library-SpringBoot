package com.senac.GameLibrary_SpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.JogoJogadoRepository;
import com.senac.GameLibrary_SpringBoot.data.jogoJogado;
@Service
public class JogoJogadoService {
    @Autowired
    JogoService jogoService;
    @Autowired
    JogoJogadoRepository jogoJogadoRepo;
    public jogoJogado salvarJogoJogado(jogoJogado jogoJogado) 
    {
    jogoJogado = jogoJogadoRepo.save(jogoJogado);

return jogoJogado;
    }
}
