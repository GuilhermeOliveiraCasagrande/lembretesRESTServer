package br.guiga.projects.lembretes.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.guiga.projects.lembretes.controller.form.EditaLembreteForm;
import br.guiga.projects.lembretes.controller.form.LembreteForm;
import br.guiga.projects.lembretes.dto.DetalheLembreteDTO;
import br.guiga.projects.lembretes.dto.LembreteDTO;
import br.guiga.projects.lembretes.model.Estado;
import br.guiga.projects.lembretes.model.Lembrete;
import br.guiga.projects.lembretes.repository.CategoriaRepositoy;
import br.guiga.projects.lembretes.repository.LembreteRepository;

@RestController
@RequestMapping("/lembretes")
public class LembretesRESTController {

	@Autowired
	private LembreteRepository lemrep; // Repositório de lembretes

	@Autowired
	private CategoriaRepositoy catrep; // Repositório de categorias

	@GetMapping // Mapeia para get direto
	@ResponseBody
	public List<LembreteDTO> notasDto(String titulo, String categoriaNome, Estado estado) { // DTO vai

		List<Lembrete> dados = null;
		// PRIMEIRA BUSCA INÍCIO
		if (titulo != null) { // Vê se forneceu título
			boolean bTitulo = (lemrep.findByTitulo(titulo) != null); // Vê se tem algum lembrete om esse título
			if (bTitulo) { // Se tiver
				dados = lemrep.findByTitulo(titulo); // Coloca o resultado da primeira busca
														// (Se achar nada retorna uma lista vazia)
			}
		}
		// PRIMEIRA BUSCA FIM

		// SEGUNDA BUSCA INÍCIO
		if (categoriaNome != null) { // Vê se forneceu nome da categoria
			boolean bCat = (lemrep.findByCategoria_Id(catrep.findByNome(categoriaNome).getId()) != null);
			// Vê se acha alguma categoria com o nome informado e vê se tem algum lembrete
			// com essa categoria
			if (bCat) { // Se sim
				if (dados == null) { // Vê se a busca anterior não teve resultado
					dados = lemrep.findByCategoria_Id(catrep.findByNome(categoriaNome).getId());// Coloca o resultado da
																								// busca nos dados
																								// (Se achar nada
																								// retorna uma lista
																								// vazia)
				} else { // Se teve
					for (Lembrete lembrete : dados) {// Olha o que achou
						if (!lembrete.getCategoria().getNome().contentEquals(categoriaNome)) {
							// Tira o que tiver categoria com nome diferente
							dados.remove(lembrete);
						}
					}
				}
			}
		}
		// SEGUNDA BUSCA FIM

		// TERCEITA BUSCA INÍCIO
		if (estado != null) { // Vê se forneceu um estado
			boolean bEst = (lemrep.findByEstado(estado) != null); // Vê se acha algo com esse estado
			if (bEst) {
				if (dados == null) { // Vê se a busca anterior não teve resultado
					dados = lemrep.findByEstado(estado); // Coloca o resultado dessa busca nos dados
															// (Se achar nada retorna uma lista vazia)
				} else {// Se teve resultado
					for (Lembrete lembrete : dados) {// Olha cada lembrete que achou
						if (!lembrete.getEstado().equals(estado)) {
							dados.remove(lembrete);// Remove os que tem estado diferente
						}
					}
				}
			}
		}
		// TERCEITA BUSCA FIM

		if (dados == null) { // Se nenhuma das buscas achou algo é porque é sem parâmetros
			dados = lemrep.findAll(); // Então coloca nos dados todos os lembretes guardados
		}
		return LembreteDTO.converter(dados); // Converte pra DTO e manda

//		if (titulo != null && categoriaNome == null) { // Se ter só titúlo
//			return NotaDTO.converter(nrep.findByTitulo(titulo));
//		} else if (categoriaNome != null && titulo == null) { // Se tiver só categoria
//			Categoria cat = catrep.findByNome(categoriaNome); // Pega a categoria pelo nome no db
//			List<Lembrete> notas = new ArrayList<Lembrete>(); // Cria uma lista de notas
//			for (Lembrete n : cat.getNotas()) {// Para cada nota com a categoria
//				notas.add(n); // coloca na lista
//			}
//			return NotaDTO.converter(notas); // converte a lista e manda a lista com dtos
//
//		} else if (titulo != null && categoriaNome != null) { // Se tiver os dois
//			Categoria cat = catrep.findByNome(categoriaNome); // Pega a categoria pelo nome no db
//			if (cat == null) {
//				return Arrays.asList(new NotaDTO());
//			}
//			List<Lembrete> notasBruto = nrep.findByCategoria_Id(cat.getId()); // Acha as notas com a mesma categoria
//			List<Lembrete> notasLimpo = new ArrayList<Lembrete>(); // Cria uma lista
//			for (Lembrete n : notasBruto) { // Para cada lista que achou
//				if (n.getTitulo().contains(titulo)) { // Vê se tem o mesmo título informado
//					notasLimpo.add(n); // Coloca na lista
//				}
//			}
//			return NotaDTO.converter(notasLimpo);// converte e manda a lista
//
//		} else { // Se tiver nenhum dos dois
//			return NotaDTO.converter(nrep.findAll()); // Se não estiver procurando por título ou categoria
//		}
	}

	@PostMapping // Mapeia para post direto
	public ResponseEntity<LembreteDTO> postNota(@RequestBody @Valid LembreteForm form,
			UriComponentsBuilder uriBuilder) { // Form
		// vem
		System.out.println(form.toString());
		// Classe que recebe o corpo do request do form
		Lembrete nota = form.converter(lemrep, catrep); // Converte o form em um novo objeto
		lemrep.save(nota); // Salva ele
		URI uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.getId()).toUri(); // Retorna o código 201 e não 200
		// 200 -> Deu tudo certo
		// 201 -> Criou o objeto e deu tudo certo

		return ResponseEntity.created(uri).body(LembreteDTO.converter(nota)); // Retorna o endereço de detalhe da nota
	}

	@GetMapping("/{id}") // Pega a variável do caminho do endereço
	public ResponseEntity<DetalheLembreteDTO> detalhamento(@PathVariable(name = "id") Long id) { // Coloca como long
		Optional<Lembrete> teste = lemrep.findById(id);
		if (!teste.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Lembrete lem = lemrep.getOne(id); // Acha o lembrete
		return ResponseEntity.ok(new DetalheLembreteDTO(lem)); // Retorna com o detalhamento
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<LembreteDTO> editar(@PathVariable(name = "id") Long id, @RequestBody EditaLembreteForm form) {
		Optional<Lembrete> teste = lemrep.findById(id);
		if (!teste.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Lembrete editado = form.editar(id, lemrep);
		return ResponseEntity.ok(LembreteDTO.converter(editado));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable(value = "id") Long id) {
		Optional<Lembrete> teste = lemrep.findById(id);
		if (!teste.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		lemrep.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
