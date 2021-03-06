package com.brunocandido.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brunocandido.cursomc.domain.Cidades;
import com.brunocandido.cursomc.domain.Cliente;
import com.brunocandido.cursomc.domain.Enderecos;
import com.brunocandido.cursomc.dto.ClienteDTO;
import com.brunocandido.cursomc.dto.ClienteNewDTO;
import com.brunocandido.cursomc.enuns.TipoCliente;
import com.brunocandido.cursomc.exceptions.DataIntegrityException;
import com.brunocandido.cursomc.exceptions.ObjectNotFoundException;
import com.brunocandido.cursomc.repositories.ClienteRepository;
import com.brunocandido.cursomc.repositories.EnderecosRepository;

//2º Camada - Chama Repository

@Service
public class ClienteServices {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecosRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfCgc(), TipoCliente.toEnum(objDto.getTipoCliente()));
		Cidades cid = new Cidades(objDto.getCidadeId(), null, null);
		Enderecos end = new Enderecos(null, objDto.getLogadouro(), objDto.getNumero(), objDto.getComplemento(), 
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefone().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefone().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefone().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}