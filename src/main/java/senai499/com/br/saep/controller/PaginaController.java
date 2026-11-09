package senai499.com.br.saep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }
    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/servicos")
    public String servicos() {
        return "servicos";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }
}