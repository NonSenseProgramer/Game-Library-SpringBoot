package com.senac.GameLibrary_SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.Jogo;
import com.senac.GameLibrary_SpringBoot.data.JogoJogadoRepository;
import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.data.JogoJogado;
@Service
public class JogoJogadoService {
    @Autowired
    JogoService jogoService;
    @Autowired
    JogoJogadoRepository jogoJogadoRepo;
    public JogoJogado salvarJogoJogado(JogoJogado jogoJogado) 
    {
    jogoJogado = jogoJogadoRepo.save(jogoJogado);

return jogoJogado;
    }
    public List<JogoJogado> retornaJogosUsuario(Usuario usuario)
    {
        return jogoJogadoRepo.findByUsuario(usuario);
    }
    public JogoJogado retornaJogoJogadoUsuarioJogo(Usuario usuario, Jogo jogo)
    {
        return jogoJogadoRepo.findByUsuarioAndJogo(usuario, jogo);
    }
    
}
