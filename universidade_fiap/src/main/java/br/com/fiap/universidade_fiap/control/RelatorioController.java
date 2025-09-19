package br.com.fiap.universidade_fiap.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.Moto;
import br.com.fiap.universidade_fiap.model.Operacao;
import br.com.fiap.universidade_fiap.model.StatusMoto;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.OperacaoRepository;
import br.com.fiap.universidade_fiap.repository.StatusMotoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Controller
public class RelatorioController {

    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private StatusMotoRepository statusMotoRepository;
    
    @Autowired
    private OperacaoRepository operacaoRepository;
    
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/relatorios")
    public ModelAndView relatorios() {
        ModelAndView mv = new ModelAndView("/relatorios/lista");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        return mv;
    }

    @GetMapping("/relatorios/motos")
    public ModelAndView relatorioMotos() {
        ModelAndView mv = new ModelAndView("/relatorios/motos");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        List<Moto> motos = motoRepository.findAll();
        mv.addObject("motos", motos);
        
        return mv;
    }

    @GetMapping("/relatorios/status")
    public ModelAndView relatorioStatus() {
        ModelAndView mv = new ModelAndView("/relatorios/status");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        List<StatusMoto> statusMotos = statusMotoRepository.findAll();
        mv.addObject("statusMotos", statusMotos);
        
        return mv;
    }

    @GetMapping("/relatorios/operacoes")
    public ModelAndView relatorioOperacoes() {
        ModelAndView mv = new ModelAndView("/relatorios/operacoes");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        List<Operacao> operacoes = operacaoRepository.findAll();
        mv.addObject("operacoes", operacoes);
        
        return mv;
    }

}
