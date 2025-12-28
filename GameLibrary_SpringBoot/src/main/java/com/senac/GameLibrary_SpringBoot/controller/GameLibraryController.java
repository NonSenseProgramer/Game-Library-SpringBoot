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
import com.senac.GameLibrary_SpringBoot.data.Jogo;
import com.senac.GameLibrary_SpringBoot.data.JogosJogadosId;
import com.senac.GameLibrary_SpringBoot.data.RegistroJogoDTO;
import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.data.jogoJogado;
import com.senac.GameLibrary_SpringBoot.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GameLibraryController {
    @Autowired
    AuthTokenService authTokenService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    JogoService jogoService;
    @Autowired
    JogoJogadoService jogoJogadoService;

    @GetMapping("/pagina-inicial")
    public String mostraPaginaInicial(
            Model model) {
        model.addAttribute("usuario", new Usuario());
        return "index";
    }

    @GetMapping("/cadastroUsuario")
    public String mostraPaginaCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastroUsuario";
    }

    @GetMapping("/paginaServicos")
    public String mostraPaginaServicos(Model model, @CookieValue(value = "AUTH", required = true) String token) {
        Usuario usuario = authTokenService.getUsuarioPorCookie(token);
        if (usuario == null) {
            return "redirect:/pagina-inicial";
        }
        model.addAttribute("usuario", usuario);
        return "paginaServicos";
    }

    // --------------- GET PÁGINAS DE SERVIÇO ---------------
    @GetMapping("/registroJogo")
    public String mostraPaginaRegistroJogo(Model model) {
        model.addAttribute("registro", new RegistroJogoDTO());
        return "registrarJogo";
    }

    // --------------- POST PÁGINAS DE SERVIÇO ---------------
    @PostMapping("/registroJogo")
    public String fazRegistroJogoJogado(@ModelAttribute RegistroJogoDTO registro, Model model,
            @CookieValue(value = "AUTH", required = true) String token) {
        jogoJogado jogoJogado = new jogoJogado();
        Jogo jogo = jogoService.retornaJogoPorNome(registro.nomeJogo);
        if (jogo == null) {
            model.addAttribute("jogoInexistente", true);
           
            return "registrarJogo";
        }

        jogoJogado.setJogo(jogo);
        jogoJogado.setStatus(registro.status);
        Usuario usuario = authTokenService.getUsuarioPorCookie(token);
        JogosJogadosId jogadoId = new JogosJogadosId();

        jogadoId.setUserId(usuario.getId());
        jogadoId.setJogoId(jogo.getId());

        jogoJogado.setId(jogadoId);
        jogoJogado.setUsuario(usuario);
        jogoJogadoService.salvarJogoJogado(jogoJogado);
        model.addAttribute("sucesso", true);
         model.addAttribute("registro", registro);
        return "registrarJogo";
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
            model.addAttribute("usuarioJaExiste", true);
            return "cadastroUsuario";
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
