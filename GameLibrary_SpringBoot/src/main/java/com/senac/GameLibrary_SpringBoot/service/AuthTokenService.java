package com.senac.GameLibrary_SpringBoot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.GameLibrary_SpringBoot.data.AuthToken;
import com.senac.GameLibrary_SpringBoot.data.AuthTokenRepository;
import com.senac.GameLibrary_SpringBoot.data.Usuario;

import jakarta.servlet.http.Cookie;

@Service
public class AuthTokenService {
    @Autowired
    AuthTokenRepository authTokenRepo;

    public Cookie renovaCookie(String token)

    {
        AuthToken authToken = authTokenRepo.findById(token).orElse(null);
        if (authToken == null) {
            return null;
        }
        if (authToken.getExpiracao().isBefore(LocalDateTime.now())) {
            Cookie apagar = new Cookie("AUTH", "");
            apagar.setMaxAge(0);
            apagar.setPath("/");
            authTokenRepo.delete(authToken);
            return apagar;
        }
        Cookie cookie = new Cookie("AUTH", token);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7 dias
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public void salvarAuthToken(AuthToken authToken) {
        System.out.println("Salvando");
        authTokenRepo.save(authToken);
    }

    public Usuario getUsuarioPorCookie(String token) {
        Optional<AuthToken> auth = authTokenRepo.findById(token);
        if (auth.isEmpty()) {
            return null;
        }
        AuthToken authUser = auth.get();
        return authUser.getUsuario();

    }

}
