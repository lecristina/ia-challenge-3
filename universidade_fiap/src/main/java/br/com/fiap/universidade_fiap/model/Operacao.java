package br.com.fiap.universidade_fiap.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "operacao")
public class Operacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "moto_id")
	private Moto moto;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao", nullable = false)
	private EnumTipoOperacao tipoOperacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
	private String descricao;
	
	@Column(name = "data_operacao")
	private LocalDateTime dataOperacao;

	public Operacao() {
		this.dataOperacao = LocalDateTime.now();
	}

	public Operacao(Long id, Moto moto, EnumTipoOperacao tipoOperacao, Usuario usuario, String descricao) {
		super();
		this.id = id;
		this.moto = moto;
		this.tipoOperacao = tipoOperacao;
		this.usuario = usuario;
		this.descricao = descricao;
		this.dataOperacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Moto getMoto() {
		return moto;
	}

	public void setMoto(Moto moto) {
		this.moto = moto;
	}

	public EnumTipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(EnumTipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(LocalDateTime dataOperacao) {
		this.dataOperacao = dataOperacao;
	}
}
