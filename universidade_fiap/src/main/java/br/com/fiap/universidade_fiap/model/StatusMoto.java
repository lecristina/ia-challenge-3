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
@Table(name = "status_moto")
public class StatusMoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "moto_id")
	private Moto moto;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnumStatus status;
	
	@Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
	private String descricao;
	
	@NotEmpty(message = "Área é obrigatória")
	@Size(max = 50, message = "Área deve ter no máximo 50 caracteres")
	private String area;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column(name = "data_status")
	private LocalDateTime dataStatus;

	public StatusMoto() {
		this.dataStatus = LocalDateTime.now();
	}

	public StatusMoto(Long id, Moto moto, EnumStatus status, String descricao, String area, Usuario usuario) {
		super();
		this.id = id;
		this.moto = moto;
		this.status = status;
		this.descricao = descricao;
		this.area = area;
		this.usuario = usuario;
		this.dataStatus = LocalDateTime.now();
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

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(LocalDateTime dataStatus) {
		this.dataStatus = dataStatus;
	}
}
