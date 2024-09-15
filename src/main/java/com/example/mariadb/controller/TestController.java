package com.example.mariadb.controller;

import com.example.mariadb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/produtos-japao")
    public List<Map<String, Object>> getProductsFromJapan(Model model) {
        List<Map<String, Object>> productsFromJapan = testService.getProductsFromJapan();
       // model.addAttribute("productsFromJapan", productsFromJapan);
       System.out.println(productsFromJapan);
        return productsFromJapan;  // Nome do template Thymeleaf
    }

    @GetMapping("/resumo-armazem")
    public String getWarehouseStockSummary(Model model) {
        List<Map<String, Object>> warehouseStockSummary = testService.getWarehouseStockSummary();
        model.addAttribute("warehouseStockSummary", warehouseStockSummary);
        return "resumo-armazem";  // Nome do template Thymeleaf
    }
}
