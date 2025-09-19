package br.com.fiap.universidade_fiap.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dashboard")
public class Dashboard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total_motos")
	private Integer totalMotos;
	
	@Column(name = "motos_disponiveis")
	private Integer motosDisponiveis;
	
	@Column(name = "motos_alugadas")
	private Integer motosAlugadas;
	
	@Column(name = "motos_em_manutencao")
	private Integer motosEmManutencao;
	
	@Column(name = "total_operacoes")
	private Integer totalOperacoes;
	
	@Column(name = "total_check_in")
	private Integer totalCheckIn;
	
	@Column(name = "total_check_out")
	private Integer totalCheckOut;
	
	@Column(name = "ultima_atualizacao")
	private LocalDateTime ultimaAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "atualizado_por")
	private Usuario atualizadoPor;

	public Dashboard() {
		this.ultimaAtualizacao = LocalDateTime.now();
	}

	public Dashboard(Long id, Integer totalMotos, Integer motosDisponiveis, Integer motosAlugadas, 
			Integer motosEmManutencao, Integer totalOperacoes, Integer totalCheckIn, 
			Integer totalCheckOut, Usuario atualizadoPor) {
		super();
		this.id = id;
		this.totalMotos = totalMotos;
		this.motosDisponiveis = motosDisponiveis;
		this.motosAlugadas = motosAlugadas;
		this.motosEmManutencao = motosEmManutencao;
		this.totalOperacoes = totalOperacoes;
		this.totalCheckIn = totalCheckIn;
		this.totalCheckOut = totalCheckOut;
		this.atualizadoPor = atualizadoPor;
		this.ultimaAtualizacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalMotos() {
		return totalMotos;
	}

	public void setTotalMotos(Integer totalMotos) {
		this.totalMotos = totalMotos;
	}

	public Integer getMotosDisponiveis() {
		return motosDisponiveis;
	}

	public void setMotosDisponiveis(Integer motosDisponiveis) {
		this.motosDisponiveis = motosDisponiveis;
	}

	public Integer getMotosAlugadas() {
		return motosAlugadas;
	}

	public void setMotosAlugadas(Integer motosAlugadas) {
		this.motosAlugadas = motosAlugadas;
	}

	public Integer getMotosEmManutencao() {
		return motosEmManutencao;
	}

	public void setMotosEmManutencao(Integer motosEmManutencao) {
		this.motosEmManutencao = motosEmManutencao;
	}

	public Integer getTotalOperacoes() {
		return totalOperacoes;
	}

	public void setTotalOperacoes(Integer totalOperacoes) {
		this.totalOperacoes = totalOperacoes;
	}

	public Integer getTotalCheckIn() {
		return totalCheckIn;
	}

	public void setTotalCheckIn(Integer totalCheckIn) {
		this.totalCheckIn = totalCheckIn;
	}

	public Integer getTotalCheckOut() {
		return totalCheckOut;
	}

	public void setTotalCheckOut(Integer totalCheckOut) {
		this.totalCheckOut = totalCheckOut;
	}

	public LocalDateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public Usuario getAtualizadoPor() {
		return atualizadoPor;
	}

	public void setAtualizadoPor(Usuario atualizadoPor) {
		this.atualizadoPor = atualizadoPor;
	}
}
