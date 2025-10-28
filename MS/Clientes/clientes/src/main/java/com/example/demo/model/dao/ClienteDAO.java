package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.entity.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

}
