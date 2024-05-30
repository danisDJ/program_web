package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> usuario = repository.findById(id);
		return usuario.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User usuario) {
		return repository.save(usuario);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			
		}
	}
	
	public User update(Long id, User usuario) {
		try {
			User userSelect = repository.getReferenceById(id);
			updateDados(userSelect, usuario);
			return repository.save(userSelect);			
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateDados(User userSelect, User usuario) {
		
		userSelect.setNome(usuario.getNome());
		userSelect.setEmail(usuario.getEmail());
		userSelect.setTelefone(usuario.getTelefone());
	}

}
