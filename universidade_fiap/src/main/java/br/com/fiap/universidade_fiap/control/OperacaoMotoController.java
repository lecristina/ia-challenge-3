package br.com.fiap.universidade_fiap.control;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.EnumStatus;
import br.com.fiap.universidade_fiap.model.EnumTipoOperacao;
import br.com.fiap.universidade_fiap.model.Moto;
import br.com.fiap.universidade_fiap.model.Operacao;
import br.com.fiap.universidade_fiap.model.StatusMoto;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.OperacaoRepository;
import br.com.fiap.universidade_fiap.repository.StatusMotoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class OperacaoMotoController {

    private final MotoRepository motoRepository;
    private final OperacaoRepository operacaoRepository;
    private final StatusMotoRepository statusMotoRepository;
    private final UsuarioRepository usuarioRepository;

    public OperacaoMotoController(MotoRepository motoRepository, 
                                 OperacaoRepository operacaoRepository,
                                 StatusMotoRepository statusMotoRepository,
                                 UsuarioRepository usuarioRepository) {
        this.motoRepository = motoRepository;
        this.operacaoRepository = operacaoRepository;
        this.statusMotoRepository = statusMotoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    /**
     * FLUXO COMPLETO 1: Operação de Moto
     * Este fluxo permite iniciar uma operação (entrega/coleta) com uma moto
     */
    @GetMapping("/motos/{id}/operacao")
    public ModelAndView iniciarOperacao(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("motos/operacao");
        
        try {
            // Buscar moto
            Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
            
            // Verificar se moto está disponível
            Optional<StatusMoto> statusAtual = statusMotoRepository.findByMotoIdOrderByDataStatusDesc(id)
                .stream().findFirst();
            
            if (statusAtual.isPresent() && statusAtual.get().getStatus() != EnumStatus.DISPONIVEL) {
                mv.setViewName("redirect:/motos?erro=Moto não está disponível para operação");
                return mv;
            }
            
            // Adicionar usuário logado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usuarioRepository.findByEmail(auth.getName())
                .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
            
            mv.addObject("moto", moto);
            mv.addObject("tiposOperacao", EnumTipoOperacao.values());
            mv.addObject("operacao", new Operacao());
            
        } catch (Exception e) {
            mv.setViewName("redirect:/motos?erro=Erro ao carregar operação: " + e.getMessage());
        }
        
        return mv;
    }

    @PostMapping("/motos/{id}/operacao")
    public ModelAndView processarOperacao(@PathVariable Long id, 
                                         @RequestParam String tipoOperacao,
                                         @RequestParam String descricao) {
        ModelAndView mv = new ModelAndView();
        
        try {
            // Buscar moto
            Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
            
            // Buscar usuário logado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            // Criar operação
            Operacao operacao = new Operacao();
            operacao.setTipoOperacao(EnumTipoOperacao.valueOf(tipoOperacao));
            operacao.setDescricao(descricao);
            operacao.setMoto(moto);
            operacao.setUsuario(usuario);
            operacao.setDataOperacao(LocalDateTime.now());
            
            // Salvar operação
            operacaoRepository.save(operacao);
            
            // Atualizar status da moto para EM_USO
            StatusMoto statusMoto = new StatusMoto();
            statusMoto.setStatus(EnumStatus.EM_USO);
            statusMoto.setDescricao("Moto em operação: " + tipoOperacao);
            statusMoto.setMoto(moto);
            statusMoto.setDataStatus(LocalDateTime.now());
            statusMotoRepository.save(statusMoto);
            
            mv.setViewName("redirect:/motos?sucesso=Operação iniciada com sucesso");
            
        } catch (Exception e) {
            mv.setViewName("redirect:/motos?erro=Erro ao processar operação: " + e.getMessage());
        }
        
        return mv;
    }

    /**
     * Finalizar operação e retornar moto para disponível
     */
    @PostMapping("/motos/{id}/finalizar-operacao")
    public ModelAndView finalizarOperacao(@PathVariable Long id, 
                                         @RequestParam String observacoes) {
        ModelAndView mv = new ModelAndView();
        
        try {
            // Buscar moto
            Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
            
            // Buscar usuário logado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            // Criar operação de finalização
            Operacao operacao = new Operacao();
            operacao.setTipoOperacao(EnumTipoOperacao.ENTREGA); // Assumindo que é entrega
            operacao.setDescricao("Operação finalizada. Observações: " + observacoes);
            operacao.setMoto(moto);
            operacao.setUsuario(usuario);
            operacao.setDataOperacao(LocalDateTime.now());
            operacaoRepository.save(operacao);
            
            // Atualizar status da moto para DISPONIVEL
            StatusMoto statusMoto = new StatusMoto();
            statusMoto.setStatus(EnumStatus.DISPONIVEL);
            statusMoto.setDescricao("Moto disponível após operação");
            statusMoto.setMoto(moto);
            statusMoto.setDataStatus(LocalDateTime.now());
            statusMotoRepository.save(statusMoto);
            
            mv.setViewName("redirect:/motos?sucesso=Operação finalizada com sucesso");
            
        } catch (Exception e) {
            mv.setViewName("redirect:/motos?erro=Erro ao finalizar operação: " + e.getMessage());
        }
        
        return mv;
    }

    /**
     * FLUXO COMPLETO 2: Relatório de Operações por Período
     * Este fluxo gera relatórios detalhados de operações
     */
    @GetMapping("/relatorios/operacoes-periodo")
    public ModelAndView relatorioOperacoesPeriodo(@RequestParam(required = false) String dataInicio,
                                                 @RequestParam(required = false) String dataFim) {
        ModelAndView mv = new ModelAndView("relatorios/operacoes-periodo");
        
        try {
            // Adicionar usuário logado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usuarioRepository.findByEmail(auth.getName())
                .ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
            
            List<Operacao> operacoes;
            
            if (dataInicio != null && dataFim != null) {
                // Buscar operações no período
                LocalDateTime inicio = LocalDateTime.parse(dataInicio + "T00:00:00");
                LocalDateTime fim = LocalDateTime.parse(dataFim + "T23:59:59");
                operacoes = operacaoRepository.findByDataOperacaoBetween(inicio, fim);
            } else {
                // Buscar operações dos últimos 30 dias
                LocalDateTime fim = LocalDateTime.now();
                LocalDateTime inicio = fim.minusDays(30);
                operacoes = operacaoRepository.findByDataOperacaoBetween(inicio, fim);
            }
            
            // Calcular estatísticas
            long totalOperacoes = operacoes.size();
            long entregas = operacoes.stream()
                .filter(op -> op.getTipoOperacao() == EnumTipoOperacao.ENTREGA)
                .count();
            long coletas = operacoes.stream()
                .filter(op -> op.getTipoOperacao() == EnumTipoOperacao.COLETA)
                .count();
            long manutencoes = operacoes.stream()
                .filter(op -> op.getTipoOperacao() == EnumTipoOperacao.MANUTENCAO)
                .count();
            
            mv.addObject("operacoes", operacoes);
            mv.addObject("totalOperacoes", totalOperacoes);
            mv.addObject("entregas", entregas);
            mv.addObject("coletas", coletas);
            mv.addObject("manutencoes", manutencoes);
            mv.addObject("dataInicio", dataInicio);
            mv.addObject("dataFim", dataFim);
            
        } catch (Exception e) {
            mv.addObject("erro", "Erro ao gerar relatório: " + e.getMessage());
        }
        
        return mv;
    }

    /**
     * Exportar relatório para CSV
     */
    @GetMapping("/relatorios/operacoes-periodo/exportar")
    public ModelAndView exportarRelatorio(@RequestParam String dataInicio,
                                        @RequestParam String dataFim) {
        ModelAndView mv = new ModelAndView("relatorios/operacoes-csv");
        
        try {
            LocalDateTime inicio = LocalDateTime.parse(dataInicio + "T00:00:00");
            LocalDateTime fim = LocalDateTime.parse(dataFim + "T23:59:59");
            List<Operacao> operacoes = operacaoRepository.findByDataOperacaoBetween(inicio, fim);
            
            mv.addObject("operacoes", operacoes);
            mv.addObject("dataInicio", dataInicio);
            mv.addObject("dataFim", dataFim);
            
        } catch (Exception e) {
            mv.setViewName("redirect:/relatorios/operacoes-periodo?erro=Erro ao exportar: " + e.getMessage());
        }
        
        return mv;
    }
}
