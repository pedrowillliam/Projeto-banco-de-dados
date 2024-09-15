package com.example.mariadb;

import com.example.mariadb.service.TestService;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MariadbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MariadbApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(TestService testService) {
        return args -> {
            testService.testConnection();
        
			List<Map<String, Object>> products = testService.getAllProducts();
            System.out.println("Todos os produtos:");
            products.forEach(System.out::println);

			List<Map<String, Object>> productsFromJapan = testService.getProductsFromJapan();
        	System.out.println("Produtos de fornecedores do Japão com estoque maior que 120:");
        	productsFromJapan.forEach(System.out::println);

			 
			 List<Map<String, Object>> warehouseStockSummary = testService.getWarehouseStockSummary();
			 System.out.println("Resumo de armazéns e estoques com quantidade total >= 200:");
			 warehouseStockSummary.forEach(System.out::println);
		};
    }
}
