package br.guiga.projects.lembretes.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.guiga.projects.lembretes.model.Estado;
import br.guiga.projects.lembretes.model.Lembrete;
import br.guiga.projects.lembretes.repository.LembreteRepository;

public class EditaLembreteForm {
	//Os atributos que podem ser editados de um lembrete
	private String lembreteTitulo;
	@NotBlank
	@Size(min = 1)
	private String lembreteConteudo;
	private String categoriaNome;
	private String categoriaCor;
	private Estado estado;
	
	public Lembrete editar(Long id, LembreteRepository lemrep) {
		Lembrete editar = lemrep.getOne(id);
		editar.setTitulo(this.lembreteTitulo);
		editar.setEstado(this.estado);
		editar.getCategoria().setNome(this.categoriaNome);
		editar.getCategoria().setCor(this.categoriaCor);
		editar.setConteudo(this.lembreteConteudo);
		return editar;
	}

	public String getLembreteTitulo() {
		return lembreteTitulo;
	}

	public void setLembreteTitulo(String lembreteTitulo) {
		this.lembreteTitulo = lembreteTitulo;
	}

	public String getLembreteConteudo() {
		return lembreteConteudo;
	}

	public void setLembreteConteudo(String lembreteConteudo) {
		this.lembreteConteudo = lembreteConteudo;
	}

	public String getCategoriaNome() {
		return categoriaNome;
	}

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	public String getCategoriaCor() {
		return categoriaCor;
	}

	public void setCategoriaCor(String categoriaCor) {
		this.categoriaCor = categoriaCor;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
