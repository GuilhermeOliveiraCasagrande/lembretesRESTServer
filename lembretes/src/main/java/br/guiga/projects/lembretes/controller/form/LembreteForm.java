package br.guiga.projects.lembretes.controller.form;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.guiga.projects.lembretes.model.Categoria;
import br.guiga.projects.lembretes.model.Lembrete;
import br.guiga.projects.lembretes.repository.CategoriaRepositoy;
import br.guiga.projects.lembretes.repository.LembreteRepository;

public class LembreteForm {
	private String lembreteTitulo; //Um lembrete não precisa necessáriamente de um título
	@NotBlank @Size(min = 1)
	private String lembreteConteudo; //Mas precisa de pelo menos 1 caractere de conteúdo, se não não vale a pena guardar
	private String categoriaNome; //Também não precisa de uma categoria explícita
	private String categoriaCor; //Se não tiver categoria, não precisa da cor dela
	
	
	public Lembrete converter(LembreteRepository nrep, CategoriaRepositoy catrep) {
		Lembrete lembreteNovo = new Lembrete(lembreteTitulo, lembreteConteudo); //Cria uma nova nota com os dados do form
		if (catrep.findByNome(categoriaNome) == null) { //Se não achar uma categoria com o nome dado
			 Categoria catNova = new Categoria(); //Cria uma nova
			 catNova.setNome(categoriaNome); //Coloca o nome
			 if(categoriaCor == null) { //Se não informar uma cor
				 catNova.setCor("nenhuma"); //Coloca com nenhuma
			 } else {
				 catNova.setCor(categoriaCor); //Senão coloca a cor informada
			 }
			 
			 catNova.setNotas(Arrays.asList(lembreteNovo));//Coloca nas notas com a categoria nova a nota criada agora 
			 catrep.save(catNova); //salva a categoria no DB
		}
		Categoria cat = catrep.findByNome(categoriaNome); //Pega a categoria com o nome certo no db
		lembreteNovo.setCategoria(cat); //deixa a categoria da nota nova como a achada no db
		return lembreteNovo; //Devolve uma nota nova criada
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

	@Override
	public String toString() {
		return "[titulo: " + lembreteTitulo + " conteudo: " + lembreteConteudo + " categoriaNome: " + categoriaNome + " categoriaCor: " + categoriaCor + "]";
	}
	
}
