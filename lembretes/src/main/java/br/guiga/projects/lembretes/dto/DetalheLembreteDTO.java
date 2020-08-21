package br.guiga.projects.lembretes.dto;

import br.guiga.projects.lembretes.model.Estado;
import br.guiga.projects.lembretes.model.Lembrete;

public class DetalheLembreteDTO {
	private Long id;
	private String titulo;
	private CategoriaDTO categoria;
	private Estado estado;

	public DetalheLembreteDTO(Lembrete lembrete) {
		this.id = lembrete.getId();
		this.titulo = lembrete.getTitulo();
		this.setCategoria(new CategoriaDTO(lembrete.getCategoria()));
		this.estado = lembrete.getEstado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

}
