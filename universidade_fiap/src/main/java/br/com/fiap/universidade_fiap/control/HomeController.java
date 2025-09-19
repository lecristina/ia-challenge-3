package br.com.fiap.universidade_fiap.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping({"/", "/index"})
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("home/index");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        usuarioOpt.ifPresent(u -> mv.addObject("usuario_logado", u));

        mv.addObject("motos", motoRepository.findAll());

        return mv;
    }

    @GetMapping("/cadastrar-moto")
    public String cadastrarMoto() {
        return "redirect:/motos/novo";
    }
}
