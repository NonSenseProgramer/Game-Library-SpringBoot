package com.senac.GameLibrary_SpringBoot.service;
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
        if(usuario == null)
            {
                return null;
            }
           
            else if(BCrypt.checkpw(senha, usuario.getSenha()))
                {
                   return usuario;
                }
        return null;


    }

}
