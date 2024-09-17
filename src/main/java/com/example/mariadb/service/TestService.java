package com.example.mariadb.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mariadb.resource.TestResource;

@Service
public class TestService {

    @Autowired
    private TestResource resourse;

	public Integer countProduto() {
		return resourse.countProduto();
	}

	public List<Map<String, Object>> getAllProducts() {
		return resourse.getAllProducts();
	}

	public List<Map<String, Object>> getAllClients() {
		return resourse.getAllClients();
	}

//	 1. Consulta para retornar produtos de fornecedores do Japão
	public List<Map<String, Object>> getProductsFromJapan() {
		return resourse.getProductsFromJapan();
	}

	// 4. Método para retornar dados de armazéns e estoques com soma de quantidade >= 200
	public List<Map<String, Object>> getWarehouseStockSummary(){
		return resourse.getWarehouseStockSummary();
	}

	//3. nome e endereço dos armazéns com pelo menos 200 estoques de produtos eletrodomésticos e < 1000 total
	public List<Map<String, Object>> getWarehouseWithElectrodomestics() {
		return resourse.getWarehouseWithElectrodomestics();
	}
}