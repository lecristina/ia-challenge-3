package br.com.fiap.universidade_fiap.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.universidade_fiap.model.Operacao;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Long>{

    /**
     * Busca operações por período de data
     */
    List<Operacao> findByDataOperacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    /**
     * Busca operações por tipo
     */
    List<Operacao> findByTipoOperacao(br.com.fiap.universidade_fiap.model.EnumTipoOperacao tipoOperacao);
    
    /**
     * Busca operações por moto
     */
    List<Operacao> findByMotoIdOrderByDataOperacaoDesc(Long motoId);
}

