package com.example.mariadb.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestResource {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer countProduto() {
        String sql = "SELECT COUNT(*) FROM produto";
       return jdbcTemplate.queryForObject(sql, Integer.class);
    }

     public List<Map<String, Object>> getAllProducts() {
        String sql = "SELECT * FROM produto";
        return jdbcTemplate.queryForList(sql);
    }

     // 1. Consulta para retornar produtos de fornecedores do Japão
     public List<Map<String, Object>> getProductsFromJapan() {
        String sql = """
            SELECT p.nome, pi.descricao, p.preco_venda_minimo
            FROM Produto p
            JOIN ProdutoIdioma pi ON p.produto_id = pi.produto_id
            JOIN Fornecedor f ON p.fornecedor_id = f.fornecedor_id
            JOIN Estoque e ON p.produto_id = e.produto_id
            WHERE f.pais = 'Japão'
            GROUP BY p.produto_id, p.nome, pi.descricao, p.preco_venda_minimo
            HAVING MIN(e.quantidade) > 120""";
        
        return jdbcTemplate.queryForList(sql);
     }

     // 4. Método para retornar dados de armazéns e estoques com soma de quantidade >= 200
     public List<Map<String, Object>> getWarehouseStockSummary() {
        String sql = """
            SELECT a.nome AS NomeArmazem, 
                   e.codigo AS CodigoEstoque, 
                   c.nome AS NomeCategoria, 
                   SUM(e.quantidade) AS TotalQuantidade, 
                   AVG(e.quantidade) AS MediaQuantidade
            FROM Estoque e
            JOIN Armazem a ON e.armazem_id = a.armazem_id
            JOIN Produto p ON e.produto_id = p.produto_id
            JOIN Categoria c ON p.categoria_id = c.categoria_id
            GROUP BY a.nome, e.codigo, c.nome
            HAVING SUM(e.quantidade) >= 200
        """;

        return jdbcTemplate.queryForList(sql);
     }
}
