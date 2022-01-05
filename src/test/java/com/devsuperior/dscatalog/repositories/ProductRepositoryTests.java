package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	private long existingId;
	private long notExistingId;
	private long countTotalProduct;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		notExistingId = 1000L;
		countTotalProduct = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProduct + 1, product.getId());
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		// action
		repository.deleteById(existingId);

		// assert
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		// assert
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(notExistingId);
		});

	}
	
	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
		repository.findById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}
	 
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
		repository.findById(existingId);
		
		Optional<Product> result = repository.findById(notExistingId);
		Assertions.assertTrue(result.isEmpty());
	}

}













