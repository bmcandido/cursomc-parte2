package com.brunocandido.cursomc.resoucers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brunocandido.cursomc.domain.Categoria;
import com.brunocandido.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired // Faz o instanciamento automaticamente dentro do objeto
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // Depois de Criada a Classe CategoriaResourc
																	// acrescenta value="/{id}",

	/// {id} vai concatenar o Id buscado na URL

	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // Também, é necessario acrescentar @PathVariable que
																// Vincula o id do RequestingMaping ao objeto que busca
																// o ID "FIND"
																// ResponseEntity<?> encapsula a resposta para o REST

		Categoria obj = service.find(id); // Acessa o Objeto de Repositorio CategoriaRepository

		return ResponseEntity.ok().body(obj); // Retornando o Objeto Encontrado na CAtegoria Repository

//    	Categoria cat1 = new Categoria(1, "Informática");
//		Categoria cat2 = new Categoria(2, "Escritório");
//		List<Categoria> lista = new ArrayList<>();
//		lista.add(cat1);
//		lista.add(cat2);
//		return lista;

	}

	//Serve para acrescentar/inserir a URL de coneção classe Categoria exemplo localhost:8580/categoria/1, insere dentro do post
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri(); 
		return ResponseEntity.created(uri).build();

	}
	
	//Serve para alterar/update uma url já existente (Dados que estao nela)
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT) 
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj=service.update(obj);
		return ResponseEntity.noContent().build();
		
	}

}
