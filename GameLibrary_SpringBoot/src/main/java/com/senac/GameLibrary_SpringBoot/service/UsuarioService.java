package com.senac.GameLibrary_SpringBoot.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.data.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario autenticaUsuario(String nome, String senha) {
        Usuario usuario = usuarioRepo.findByNome(nome);
        if (usuario == null) {
            return null;
        }

        else if (BCrypt.checkpw(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    public Usuario cadastraUsuario(Usuario usuario) {

        if (usuario.getNome().isBlank() || usuario.getSenha().isBlank()) {
            return null;
        }

        if (usuarioRepo.findByNome(usuario.getNome()) != null) {
            return null;
        }

        usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
        usuario.setTipo_user("user");
        return usuarioRepo.save(usuario);
    }

}
