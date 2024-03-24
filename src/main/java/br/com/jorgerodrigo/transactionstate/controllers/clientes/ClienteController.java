package br.com.jorgerodrigo.transactionstate.controllers.clientes;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jorgerodrigo.transactionstate.models.ClienteModel;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ArrayList<ClienteModel> clientes = new ArrayList<>();

    @GetMapping
    public ArrayList<ClienteModel> read() {
        return clientes;
    }

    @GetMapping("/{clienteId}")
    public Optional<ClienteModel> retrieve(@PathVariable Long clienteId){
        return clientes.stream()
        .filter(c -> c.getId().equals(clienteId))
        .findFirst();
    }

    @PostMapping("")
    public ClienteModel create(@RequestBody ClienteModel payload) { 
        Long ultimoId = clientes.stream()
                            .mapToLong(ClienteModel::getId)
                            .max()
                            .orElse(0L);
        long proximoId = ultimoId + 1;
        payload.setId(proximoId);      
        clientes.add(payload);
        return payload;
    }

    @PutMapping("/{clienteId}")
    public Optional<ClienteModel> update(@PathVariable Long clienteId,
            @RequestBody ClienteModel payload) {
        var novosClientes = clientes.stream().map(c -> {
            if (c.getId().equals(clienteId)) {
                payload.setId(c.getId());
                return payload;
            }
            return c;
        });

        clientes = new ArrayList<>(novosClientes.toList());
        return clientes.stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst();
    }
    
    @DeleteMapping("/{clienteId}")
    public void delete(@PathVariable Long clienteId) {
        var novosClientes = clientes.stream().filter(c -> !c.getId().equals(clienteId));
        clientes = new ArrayList<>(novosClientes.toList());
    }

}
