package com.senac.GameLibrary_SpringBoot.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.senac.GameLibrary_SpringBoot.data.AuthToken;
import com.senac.GameLibrary_SpringBoot.data.DTOAddJogo;
import com.senac.GameLibrary_SpringBoot.data.DTOEditarRegistro;
import com.senac.GameLibrary_SpringBoot.data.DTOTabela;
import com.senac.GameLibrary_SpringBoot.data.Jogo;
import com.senac.GameLibrary_SpringBoot.data.JogosJogadosId;
import com.senac.GameLibrary_SpringBoot.data.RegistroJogoDTO;
import com.senac.GameLibrary_SpringBoot.data.Usuario;
import com.senac.GameLibrary_SpringBoot.data.JogoJogado;
import com.senac.GameLibrary_SpringBoot.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/registroJogos")
    public String mostraRegistroJogos(Model model, @CookieValue(value = "AUTH", required = true) String token) {
        Usuario usuario = authTokenService.getUsuarioPorCookie(token);
        if (usuario == null) {
            return "redirect:/pagina-inicial";
        }
        List<DTOTabela> listaTabela = new ArrayList<>();
        List<JogoJogado> jogosJogados = jogoJogadoService.retornaJogosUsuario(usuario);
        if (jogosJogados.isEmpty()) {
            model.addAttribute("nenhumJogo", true);
            return "verJogosRegistrados";
        }
        for (int i = 0; i < jogosJogados.size(); i++) {
            DTOTabela dto = new DTOTabela();
            dto.setUsuario(usuario);
            dto.setJogosJogados(jogosJogados.get(i));
            listaTabela.add(dto);

        }
        model.addAttribute("listDTO", listaTabela);
        return "verJogosRegistrados";

    }

    @GetMapping("/cadastrarJogo")
    public String mostraTelaADDJogo(@CookieValue(value = "AUTH", required = true) String token, Model model) {
        Usuario usuario = authTokenService.getUsuarioPorCookie(token);
        if (!usuario.getTipo_user().equals("ADM")) {

            return null;
        }
        List<String> generos = List.of(
                "Ação",
                "Aventura",
                "RPG",
                "Tiro",
                "Estratégia",
                "Esportes",
                "Corrida",
                "Simulação",
                "Luta",
                "Plataforma",
                "Puzzle",
                "Survival",
                "Horror",
                "MMORPG",
                "Battle Royale",
                "Música/Ritmo",
                "Sandbox",
                "Stealth",
                "Visual Novel",
                "MOBA");
        DTOAddJogo DTO = new DTOAddJogo();
        DTO.setGeneros(generos);
        model.addAttribute("DTO", DTO);

        return "cadastrarNovoJogo";

    }

    // --------------- POST PÁGINAS DE SERVIÇO ---------------
    @PostMapping("/registroJogo")
    public String fazRegistroJogoJogado(@ModelAttribute RegistroJogoDTO registro, Model model,
            @CookieValue(value = "AUTH", required = true) String token) {
        JogoJogado jogoJogado = new JogoJogado();
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
        jogoJogado.setDataJogada(LocalDate.now());
        jogoJogadoService.salvarJogoJogado(jogoJogado);
        model.addAttribute("sucesso", true);
        model.addAttribute("registro", registro);
        return "registrarJogo";
    }

    @PostMapping("/cadastrarJogo")
    public String processaNovoCadastro(@ModelAttribute DTOAddJogo DTO, Model model) {
        Jogo jogo = jogoService.criaJogo(DTO.getJogo(), DTO.getGenero(), DTO.getDataLancamento());
        List<String> generos = List.of(
                "Ação",
                "Aventura",
                "RPG",
                "Tiro",
                "Estratégia",
                "Esportes",
                "Corrida",
                "Simulação",
                "Luta",
                "Plataforma",
                "Puzzle",
                "Survival",
                "Horror",
                "MMORPG",
                "Battle Royale",
                "Música/Ritmo",
                "Sandbox",
                "Stealth",
                "Visual Novel",
                "MOBA");
        if (jogo == null) {
            model.addAttribute("jogoExistente", true);
            DTO.setGeneros(generos);
            model.addAttribute("DTO", DTO);
            return "cadastrarNovoJogo";
        }
        model.addAttribute("sucesso", true);
        DTO.setGeneros(generos);
        model.addAttribute("DTO", DTO);

        return "cadastrarNovoJogo";
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

    // --------------- UPDATE ----------------

    @PutMapping("/atualizarRegistro")
    public ResponseEntity<?> atualizarRegistro(@RequestBody DTOEditarRegistro dto,
            @CookieValue(value = "AUTH", required = true) String token) {

        Jogo jogo = jogoService.retornaJogoPorNome(dto.getJogoNovo());
        if (jogo == null) {
            System.out.println("Parou no primeiro " + dto.getJogoNovo());
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = authTokenService.getUsuarioPorCookie(token);
        JogoJogado jogoJogado = jogoJogadoService.retornaJogoJogadoUsuarioJogo(usuario, jogo);
        if (jogoJogado == null) {
            System.out.println("Parou no segundo");

            return ResponseEntity.notFound().build();
        }
        jogoJogado.setStatus(dto.getNovoStatus());
        jogoJogado = jogoJogadoService.salvarJogoJogado(jogoJogado);
        System.out.println("" + jogoJogado.getStatus() + jogoJogado.getDataJogada() + jogoJogado.getId()
                + jogoJogado.getJogo().getJogo() + jogoJogado.getUsuario().getNome() + "");
        return ResponseEntity.ok(jogoJogado);
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
