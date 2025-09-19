package br.com.fiap.universidade_fiap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.universidade_fiap.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long>{

    Optional<Moto> findByPlaca(String placa);
    Optional<Moto> findByChassi(String chassi);

}