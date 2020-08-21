package br.guiga.projects.lembretes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.guiga.projects.lembretes.model.Estado;
import br.guiga.projects.lembretes.model.Lembrete;

public interface LembreteRepository extends JpaRepository<Lembrete, Long>{

	List<Lembrete> findByTitulo(String titulo);

	List<Lembrete> findByCategoria_Id(Long id);

	List<Lembrete> findByEstado(Estado estado);

}
