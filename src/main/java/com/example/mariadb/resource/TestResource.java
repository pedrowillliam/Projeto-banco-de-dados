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
    public List<Map<String, Object>> getAllClients() {
        String sql = "SELECT * FROM cliente";
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

     //3. nome e endereço dos armazéns com pelo menos 200 estoques de produtos eletrodomésticos e < 1000 total
     public List<Map<String, Object>> getWarehouseWithElectrodomestics() {
        String sql = """
            SELECT a.nome AS NomeArmazem, 
                a.endereco AS Endereco
            FROM Armazem a
            JOIN Estoque e ON a.armazem_id = e.armazem_id
            JOIN Produto p ON e.produto_id = p.produto_id
            JOIN Categoria c ON p.categoria_id = c.categoria_id
            WHERE c.nome = 'Eletrodomésticos'
            GROUP BY a.armazem_id, a.nome, a.endereco
            HAVING SUM(e.quantidade) >= 200 AND SUM(e.quantidade) < 1000
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // 2.Listar o nome, limite de crédito, cidade e estado dos clientes americanos
    // que já realizaram mais de 20 pedidos com o valor
    // total de cada pedido > 10.000 e que nunca compraram produtos da
    // categoria "Smartphone".
    public List<Map<String, Object>> getAmericanClientsWithCriteria() {
        String sql = """
                SELECT c.nome, c.limite_credito, c.cidade, c.estado
                FROM cliente c
                JOIN pedido p ON c.id = p.cliente_id
                WHERE c.pais = 'Estados Unidos'
                AND p.valor_total > 10000
                AND c.id IN (
                    SELECT c2.id
                    FROM cliente c2
                    JOIN pedido p2 ON c2.id = p2.cliente_id
                    GROUP BY c2.id
                    HAVING COUNT(p2.id) > 20
                )
                AND c.id NOT IN (
                    SELECT DISTINCT c3.id
                    FROM cliente c3
                    JOIN pedido p3 ON c3.id = p3.cliente_id
                    JOIN item_pedido ip3 ON p3.id = ip3.pedido_id
                    JOIN produto pr3 ON ip3.produto_id = pr3.id
                    JOIN categoria cat3 ON pr3.categoria_id = cat3.id
                    WHERE cat3.nome = 'Smartphone'
                )
                GROUP BY c.nome, c.limite_credito, c.cidade, c.estado""";

        return jdbcTemplate.queryForList(sql);
    }

}

