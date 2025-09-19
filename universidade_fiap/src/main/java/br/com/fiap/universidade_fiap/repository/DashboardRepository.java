package br.com.fiap.universidade_fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.universidade_fiap.model.Dashboard;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long>{

}

