package com.senac.GameLibrary_SpringBoot.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.senac.GameLibrary_SpringBoot.data.AuthToken;
import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameLibraryController {
    @Autowired
    AuthTokenService authTokenService;
    @Autowired
    UsuarioService usuarioService;

    

    @GetMapping("/pagina-inicial")
    public String mostraPaginaInicial(
            Model model) 
            {
    model.addAttribute("usuario", new Usuario());
    return "index";}

    @GetMapping("/cadastroUsuario")
    public String mostraPaginaCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastroUsuario";
    }

    @GetMapping("/paginaServicos")
    public String mostraPaginaServicos() {
        return "paginaServicos";
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    // --------------- LOGIN E CADASTRO ---------------
    @PostMapping("/login")
    public String autenticaLogin(@ModelAttribute Usuario usuario, Model model, HttpServletResponse response) {
        usuario = usuarioService.autenticaUsuario(usuario.getNome(), usuario.getSenha());
        if (usuario == null) {
            model.addAttribute("loginIncorreto", true);
            return "index";
        }

        Cookie cookieAutenticacao = criaCookie(usuario);
        response.addCookie(cookieAutenticacao);

        return "redirect:/paginaServicos";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario, Model model, HttpServletResponse response) {
        usuario = usuarioService.cadastraUsuario(usuario);
        if (usuario == null) {
            return null;
        }
        Cookie cookie = criaCookie(usuario);
        response.addCookie(cookie);

        return "redirect:/paginaServicos";
    }

    public Cookie criaCookie(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        Cookie cookieAutenticacao = new Cookie("AUTH", token);
        cookieAutenticacao.setHttpOnly(true);
        cookieAutenticacao.setMaxAge(60 * 60 * 24 * 7); // 7 dias

        AuthToken authToken = new AuthToken();
        authToken.setExpiracao(LocalDateTime.now().plusDays(30));
        authToken.setToken(token);
        authToken.setUsuario(usuario);
        authTokenService.salvarAuthToken(authToken);
        return cookieAutenticacao;
    }
}

