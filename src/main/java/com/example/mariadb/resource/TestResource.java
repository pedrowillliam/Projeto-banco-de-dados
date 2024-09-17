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
            SELECT c.Nome, c.Limite_credito, c.Cidade, c.Estado
            FROM Cliente c
            JOIN Pedido p ON c.Cliente_id = p.Cliente_id
            JOIN produto_pedido pp ON p.Pedido_id = pp.Pedido_id
            JOIN Produto prod ON pp.Produto_id = prod.Produto_id
            JOIN Categoria cat ON prod.Categoria_id = cat.Categoria_id
            WHERE c.País = 'Estados Unidos'
              AND p.Pedido_id NOT IN (
                  SELECT p.Pedido_id
                  FROM Pedido p
                  JOIN produto_pedido pp ON p.Pedido_id = pp.Pedido_id
                  JOIN Produto prod ON pp.Produto_id = prod.Produto_id
                  JOIN Categoria cat ON prod.Categoria_id = cat.Categoria_id
                  WHERE cat.nome = 'Smartphone'
              )
            GROUP BY c.Cliente_id
            HAVING COUNT(DISTINCT p.Pedido_id) > 20
               AND SUM(pp.Quantidade * pp.preco_venda_aplicado) > 10000
        """;
        
        return jdbcTemplate.queryForList(sql);
    }
    //7.
    public List<Map<String, Object>> getProductsWithPriceDifferenceAndWarehouseCount() {
        String sql = """
            SELECT p.nome, 
                   p.data_garantia, 
                   p.descricao
            FROM produtos p
            JOIN estoques e ON p.id = e.produto_id
            JOIN armazens a ON e.armazem_id = a.id
            WHERE (p.preco_venda - p.preco_minimo) < 2000
            GROUP BY p.id, p.nome, p.data_garantia, p.descricao
            HAVING COUNT(DISTINCT e.armazem_id) >= 5
        """;
        return jdbcTemplate.queryForList(sql);
    }    

}

