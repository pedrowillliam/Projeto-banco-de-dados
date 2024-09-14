package com.example.mariadb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testConnection() {
        String sql = "SELECT COUNT(*) FROM produto";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("Número de produtos: " + count);
    }
}
