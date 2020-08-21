package br.guiga.projects.lembretes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.guiga.projects.lembretes.model.Categoria;

public interface CategoriaRepositoy extends JpaRepository<Categoria, Long>{

	Categoria findByNome(String nome);

}
