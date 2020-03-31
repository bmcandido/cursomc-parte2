package com.brunocandido.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.brunocandido.cursomc.domain.Categoria;
import com.brunocandido.cursomc.exceptions.DataIntegrityException;
import com.brunocandido.cursomc.exceptions.ObjectNotFoundException;
import com.brunocandido.cursomc.repositories.CategoriaRepository;

//2º Camada - Chama Repository

@Service
public class CategoriaService {

	@Autowired // Anotação do Spring

	CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) { // Metodo vinculado a CategoriaResource
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) { // Metodo vinculado a CategoriaResource

		find(obj.getId()); // Busca o metodo find e devolve a excessao caso ele nao encontre public
							// Categoria find(Integer id) {
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException("Exclusão não realizada! Não é possivel excluir uma categoria "
					+ "que possui produtos vinculados a ela!");
		}
	}

}
