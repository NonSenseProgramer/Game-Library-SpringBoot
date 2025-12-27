package com.senac.GameLibrary_SpringBoot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.senac.GameLibrary_SpringBoot.service.AuthTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        if (request.getCookies() == null) {
            return true;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("AUTH".equals(cookie.getName())) {
                Cookie novoCookie = authTokenService.renovaCookie(cookie.getValue());

                if (novoCookie != null) {
                    response.addCookie(novoCookie);
                }
                break;
            }
        }
        return true; // deixa a request continuar
    }
}
