package br.com.fiap.universidade_fiap.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.EnumTipoOperacao;
import br.com.fiap.universidade_fiap.model.Moto;
import br.com.fiap.universidade_fiap.model.Operacao;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.OperacaoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Controller
public class OperacaoController {

    @Autowired
    private OperacaoRepository operacaoRepository;
    
    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/operacoes")
    public ModelAndView operacoes() {
        ModelAndView mv = new ModelAndView("/operacoes/lista");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        List<Operacao> operacoes = operacaoRepository.findAll();
        mv.addObject("operacoes", operacoes);
        
        return mv;
    }

    @GetMapping("/operacoes/nova")
    public ModelAndView novaOperacao() {
        ModelAndView mv = new ModelAndView("/operacoes/cadastro");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
        
        mv.addObject("operacao", new Operacao());
        mv.addObject("motos", motoRepository.findAll());
        mv.addObject("tiposOperacao", EnumTipoOperacao.values());
        
        return mv;
    }

    @PostMapping("/operacoes/salvar")
    public ModelAndView salvarOperacao(Operacao operacao) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuarioRepository.findByEmail(auth.getName())
            .ifPresent(usuario -> {
                // Buscar a moto pelo ID
                Long motoId = operacao.getMoto().getId();
                if (motoId != null) {
                    motoRepository.findById(motoId).ifPresent(moto -> {
                        operacao.setMoto(moto);
                        operacao.setUsuario(usuario);
                        operacaoRepository.save(operacao);
                    });
                }
            });
        
        return new ModelAndView("redirect:/operacoes");
    }
}
