package com.example.demo.controller;

import com.example.demo.model.dao.ClienteDAO;
import com.example.demo.model.entity.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/{id}")  
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteDAO.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/curp/{curp}")
    public ResponseEntity<Cliente> obtenerClientePorCurp(@PathVariable String curp) {
        Optional<Cliente> cliente = clienteDAO.findByCurp(curp);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteDAO.save(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteExistenteOpt = clienteDAO.findById(id);

        if (clienteExistenteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cliente clienteExistente = clienteExistenteOpt.get();

        // Solo actualizará los campos que se envien en el post, los demás no los afectará
        if (cliente.getCurp() != null && !cliente.getCurp().isBlank()) {
            clienteExistente.setCurp(cliente.getCurp());
        }
        if (cliente.getNombre() != null) clienteExistente.setNombre(cliente.getNombre());
        if (cliente.getApellidoPaterno() != null) clienteExistente.setApellidoPaterno(cliente.getApellidoPaterno());
        if (cliente.getApellidoMaterno() != null) clienteExistente.setApellidoMaterno(cliente.getApellidoMaterno());
        if (cliente.getCorreo() != null) clienteExistente.setCorreo(cliente.getCorreo());
        if (cliente.getTelefono() != null) clienteExistente.setTelefono(cliente.getTelefono());
        if (cliente.getEstado() != null) clienteExistente.setEstado(cliente.getEstado());

        // Guarda el cliente con los datos actualizados en la db
        Cliente clienteActualizado = clienteDAO.save(clienteExistente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }
    	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        Optional<Cliente> clienteOpt = clienteDAO.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setEstado(false); 
            clienteDAO.save(cliente);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}

