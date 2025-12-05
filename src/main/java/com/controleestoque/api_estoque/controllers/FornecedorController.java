package com.controleestoque.api_estoque.controllers;

import com.controleestoque.api_estoque.models.Fornecedor;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

	private final FornecedorRepository fornecedorRepository;

	public FornecedorController(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}

	@GetMapping
	public List<Fornecedor> getAllFornecedores() {
		return fornecedorRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
		return fornecedorRepository.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Fornecedor> createFornecedor(@RequestBody Fornecedor fornecedor) {
		Fornecedor savedFornecedor = fornecedorRepository.save(fornecedor);
		return ResponseEntity.ok(savedFornecedor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorDetails) {
		return fornecedorRepository.findById(id)
			.map(fornecedor -> {
				fornecedor.setNome(fornecedorDetails.getNome());
				Fornecedor updatedFornecedor = fornecedorRepository.save(fornecedor);
				return ResponseEntity.ok(updatedFornecedor);
			})
			.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
		if (!fornecedorRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		fornecedorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
