package br.com.fiap.universidade_fiap.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@RestController
public class TesteController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/teste-banco")
    public String testeBanco() {
        try {
            long count = usuarioRepository.count();
            return "Conexão OK! Total de usuários: " + count;
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }
}
