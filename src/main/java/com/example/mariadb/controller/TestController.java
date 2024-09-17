package com.example.mariadb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mariadb.service.TestService;

@RestController
public class TestController {
    
    @Autowired
    private TestService service;

    @GetMapping("/CountProduto")
    public void countProduto(){
        service.countProduto();
    }
    @GetMapping("/getAllProducts")
	public List<Map<String, Object>> getAllProducts() {
		return service.getAllProducts();
	}

	@GetMapping("/getAllClients")
	public List<Map<String, Object>> getAllClients() {
		return service.getAllClients();
	}

	// 1. Consulta para retornar produtos de fornecedores do Japão
    @GetMapping("/getProductsFromJapan")
	public List<Map<String, Object>> getProductsFromJapan() {
		return service.getProductsFromJapan();
	}

	// 4. Método para retornar dados de armazéns e estoques com soma de quantidade
	// >= 200
    @GetMapping("/getWarehouseStockSummary") 
	public List<Map<String, Object>> getWarehouseStockSummary() {
		return service.getWarehouseStockSummary();
	}
}
