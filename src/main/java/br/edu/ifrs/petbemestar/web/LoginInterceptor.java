package br.edu.ifrs.petbemestar.web;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("usuarioLogado") != null) {
            return true;
        }

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // As rotas REST respondem com 401, em vez de redirecionar para uma página HTML.
        if (uri.startsWith(contextPath + "/tutores")
                || uri.startsWith(contextPath + "/animais")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"erro\":\"Autenticação necessária.\"}");
            return false;
        }

        response.sendRedirect(contextPath + "/login");
        return false;
    }
}
