package br.com.fiap.universidade_fiap.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapaController {
    
    @GetMapping("/mapa.html")
    public String mapa() {
        return "forward:/static/mapa.html";
    }
    
    @GetMapping("/mapa_simples.html")
    public String mapaSimples() {
        return "forward:/static/mapa_simples.html";
    }
    
    @GetMapping("/mapa_inteligente.html")
    public String mapaInteligente() {
        return "forward:/static/mapa_inteligente.html";
    }
    
    @GetMapping("/mapa_deteccao.html")
    public String mapaDeteccao() {
        return "forward:/static/mapa_deteccao.html";
    }
}
