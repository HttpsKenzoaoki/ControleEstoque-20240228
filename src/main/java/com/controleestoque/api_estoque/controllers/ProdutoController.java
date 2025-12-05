package com.controleestoque.api_estoque.controllers;

import com.controleestoque.api_estoque.models.Fornecedor;
import com.controleestoque.api_estoque.models.Produto;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private final ProdutoRepository produtoRepository;
	private final FornecedorRepository fornecedorRepository;

	public ProdutoController(ProdutoRepository produtoRepository, 
							 FornecedorRepository fornecedorRepository) {
		this.produtoRepository = produtoRepository;
		this.fornecedorRepository = fornecedorRepository;
	}

	@GetMapping
	public List<Produto> getAllProdutos() {
		return produtoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
		return produtoRepository.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createProduto(@RequestBody Produto produto) {
		if (produto.getEstoque() == null || produto.getCategoria() == null) {
			return ResponseEntity.badRequest().build();
		}

		produto.getEstoque().setProduto(produto);

		if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {
			produto.getFornecedores().clear();
			for (Fornecedor fornecedor : produto.getFornecedores()) {
				fornecedorRepository.findById(fornecedor.getId())
					.ifPresent(produto.getFornecedores()::add);
			}
		}

		Produto savedProduto = produtoRepository.save(produto);
		return ResponseEntity.ok(savedProduto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
		return produtoRepository.findById(id)
			.map(produto -> {
				produto.setNome(produtoDetails.getNome());
				produto.setPreco(produtoDetails.getPreco());
				Produto updatedProduto = produtoRepository.save(produto);
				return ResponseEntity.ok(updatedProduto);
			})
			.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
		if (!produtoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		produtoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}


