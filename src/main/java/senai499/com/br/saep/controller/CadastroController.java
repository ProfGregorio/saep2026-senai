package senai499.com.br.saep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import senai499.com.br.saep.dto.CadastroClienteForm;
import senai499.com.br.saep.service.ClientesService;

@Controller
public class CadastroController {

    private final ClientesService clientesService;

    public CadastroController(
            ClientesService clientesService) {

        this.clientesService = clientesService;
    }

    // ==========================================
    // PROCESSAR CADASTRO
    // ==========================================

    @PostMapping("/cadastro")
    public String cadastrar(
            @ModelAttribute("form") CadastroClienteForm form,
            Model model) {

        try {

            // Chamar a camada de serviço
            clientesService.cadastrar(form);

            // Cadastro realizado com sucesso
            return "redirect:/login?cadastro";

        } catch (IllegalArgumentException e) {

            // Mensagem de erro
            model.addAttribute("erro", e.getMessage());

            // Não devolver senhas ao formulário
            form.setSenha(null);
            form.setConfirmarSenha(null);

            return "cadastro";
        }
    }
}