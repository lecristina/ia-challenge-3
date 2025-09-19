package br.com.fiap.universidade_fiap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.universidade_fiap.model.StatusMoto;

@Repository
public interface StatusMotoRepository extends JpaRepository<StatusMoto, Long>{

    List<StatusMoto> findByMotoId(Long motoId);
    
    /**
     * Busca status de moto ordenados por data (mais recente primeiro)
     */
    List<StatusMoto> findByMotoIdOrderByDataStatusDesc(Long motoId);

}

