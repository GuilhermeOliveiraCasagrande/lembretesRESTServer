package br.guiga.projects.lembretes.dto;

import java.util.ArrayList;
import java.util.List;

import br.guiga.projects.lembretes.model.Estado;
import br.guiga.projects.lembretes.model.Lembrete;

public class LembreteDTO {

	private Long id;
	private String titulo;
	private Long categoriaId;
	private Estado estado;

	static public List<LembreteDTO> converter(List<Lembrete> lembretes) { 
		List<LembreteDTO> enviar = new ArrayList<LembreteDTO>();
		for (Lembrete lembrete : lembretes) { //pega cada nota da lista
			LembreteDTO lemdto = converter(lembrete); //faz a conversão de cada nota
			enviar.add(lemdto); //adiciona na lista de enviar
		}
		return enviar; //devolve a lista de enviar
	}

	static public LembreteDTO converter(Lembrete lembrete) {
		LembreteDTO lemdto = new LembreteDTO(); //Cria um dto
		lemdto.id = lembrete.getId(); //Coloca com o mesmo id
		lemdto.categoriaId = lembrete.getCategoria().getId(); //Pega o id da categoria
		lemdto.setEstado(lembrete.getEstado()); //Pega o estado dele
		lemdto.setTitulo(lembrete.getTitulo()); //Pega o título dele
		return lemdto; //Devolve o dto correto
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
