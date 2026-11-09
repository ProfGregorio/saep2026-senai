package senai499.com.br.saep.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import senai499.com.br.saep.model.*;
import senai499.com.br.saep.repository.*;

@Component
public class LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final UsuariosRepository usuariosRepository;
    private final ClientesRepository clientesRepository;

    public LoginSuccessHandler(
            UsuariosRepository usuariosRepository,
            ClientesRepository clientesRepository) {

        this.usuariosRepository = usuariosRepository;
        this.clientesRepository = clientesRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        String email = authentication.getName();

        UsuariosModel usuario = usuariosRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow();

        HttpSession session = request.getSession();

        session.setAttribute("autenticado", true);
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("nomeUsuario", usuario.getEmail());
        session.setAttribute("perfil", usuario.getPerfil());

        session.setMaxInactiveInterval(30 * 60);

        String destino;

        switch (usuario.getPerfil()) {

            case ADMIN:
                destino = "/dashboard";
                break;

            case CLIENTE:

                ClientesModel cliente = clientesRepository
                        .findByUsuarioId(usuario.getId())
                        .orElse(null);

                if (cliente == null) {
                    request.getSession().invalidate();
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/login?erro"
                    );
                    return;
                }

                session.setAttribute(
                        "clienteId",
                        cliente.getId()
                );

                session.setAttribute(
                        "nomeUsuario",
                        cliente.getNome()
                );

                destino = "/cliente/painel";
                break;

            case FUNCIONARIO:
                destino = "/admin/funcionarios/";
                break;

            default:
                request.getSession().invalidate();
                response.sendRedirect(
                        request.getContextPath()
                                + "/login?erro"
                );
                return;
        }

        response.sendRedirect(
                request.getContextPath() + destino
        );
    }
}