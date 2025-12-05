package com.controleestoque.api_estoque.controllers;

import com.controleestoque.api_estoque.models.Categoria;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

	private final CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public List<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaByID(@PathVariable Long id) {

		return categoriaRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
		Categoria savedCategoria = categoriaRepository.save(categoria);
		return ResponseEntity.ok(savedCategoria);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(
			@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
		return categoriaRepository.findById(id)
				.map(categoria -> {
					categoria.setNome(categoriaDetails.getNome());
					Categoria updateCategoria = categoriaRepository.save(categoria);
					return ResponseEntity.ok(updateCategoria);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {

		if (!categoriaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		categoriaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
