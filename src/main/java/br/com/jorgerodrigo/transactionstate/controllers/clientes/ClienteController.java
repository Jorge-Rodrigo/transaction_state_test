package br.com.jorgerodrigo.transactionstate.controllers.clientes;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.jorgerodrigo.transactionstate.models.ClienteModel;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ArrayList<ClienteModel> clientes = new ArrayList<>();

    @GetMapping
    public ResponseEntity<Object> read() {
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Nenhum cliente encontrado\"}");
        } else {
            return ResponseEntity.ok(clientes);
        }
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Object> retrieve(@PathVariable Long clienteId){
        Optional<ClienteModel> clienteOptional = clientes.stream()
        .filter(c -> c.getId().equals(clienteId))
        .findFirst();

        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody ClienteModel payload) { 
        if (payload.getNome() == null || payload.getNome().isEmpty() ||
            payload.getCpf() == null || payload.getCpf().isEmpty() ||
            payload.getEmail() == null || payload.getEmail().isEmpty() || 
            payload.getProfissao() == null || payload.getProfissao().isEmpty()) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }
        
        var cpf = payload.getCpf();
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"CPF inválido\"}");
        }
         
        
        for (ClienteModel cliente : clientes) {
            if (cliente.getCpf().equals(payload.getCpf())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"CPF já existente no sistema\"}");
            }
        }

        Long ultimoId = clientes.stream()
                            .mapToLong(ClienteModel::getId)
                            .max()
                            .orElse(0L);
        long proximoId = ultimoId + 1;
        payload.setId(proximoId);  
        payload.setSaldo(1000.00);    
        clientes.add(payload);
        return ResponseEntity.ok(payload);
    }

    @PatchMapping("/{clienteId}")
    public ResponseEntity<Object> updateSaldo(@PathVariable Long clienteId,
            @RequestBody ClienteModel payload) {

        if (payload.getNome() != null || payload.getCpf() != null || payload.getEmail() != null || payload.getProfissao() != null || payload.getSaldo() == 0.0) {
            return ResponseEntity.badRequest().body("O payload deve conter apenas o campo 'saldo'");
        }        
        Optional<ClienteModel> clienteOptional = clientes.stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst();

        if (clienteOptional.isPresent()) {
            ClienteModel cliente = clienteOptional.get();
            cliente.setSaldo(payload.getSaldo());
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
        }
    }

    @PutMapping("/{clienteId}")
public ResponseEntity<Object> update(@PathVariable Long clienteId, @RequestBody ClienteModel payload) {
    var novosClientes = clientes.stream().map(c -> {
        if (c.getId().equals(clienteId)) {
            c.setNome(payload.getNome());
            c.setCpf(payload.getCpf());
            c.setEmail(payload.getEmail());
            c.setProfissao(payload.getProfissao());
            c.setSaldo(payload.getSaldo());
            return c;
        }
        return c;
    }).collect(Collectors.toList());

    
    boolean atualizacaoRealizada = novosClientes.stream().anyMatch(c -> c.getId().equals(clienteId));

    if (atualizacaoRealizada) {
        clientes = new ArrayList<>(novosClientes);
        Optional<ClienteModel> clienteAtualizado = clientes.stream().filter(c -> c.getId().equals(clienteId)).findFirst();
        return ResponseEntity.ok(clienteAtualizado.orElse(null));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
    }
}
    
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Object> delete(@PathVariable Long clienteId) {
        boolean clienteRemovido = clientes.removeIf(c -> c.getId().equals(clienteId));
    
        if (clienteRemovido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
        }
    }

}
