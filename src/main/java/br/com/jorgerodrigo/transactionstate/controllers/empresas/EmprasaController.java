package br.com.jorgerodrigo.transactionstate.controllers.empresas;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.jorgerodrigo.transactionstate.models.EmpresaModel;

@RestController
@RequestMapping("/api/empresas")
public class EmprasaController {
    private ArrayList<EmpresaModel> empresas = new ArrayList<>();

    @GetMapping
    public ResponseEntity<Object> read() {
        if (empresas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Nenhuma empresa encontrada\"}");
        } else {
            return ResponseEntity.ok(empresas);
        }
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<Object> retrieve(@PathVariable Long empresaId) {
        Optional<EmpresaModel> empresaOptional = empresas.stream()
                .filter(e -> e.getId().equals(empresaId))
                .findFirst();

        if (empresaOptional.isPresent()) {
            return ResponseEntity.ok(empresaOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empresa não encontrada\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody EmpresaModel payload) {
        if (payload.getNome() == null || payload.getNome().isEmpty() ||
            payload.getCnpj() == null || payload.getCnpj().isEmpty() ||
            payload.getAreaAtuacao() == null || payload.getAreaAtuacao().isEmpty()) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }

        var cnpj = payload.getCnpj();
        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"CNPJ inválido\"}");
        }

        for (EmpresaModel empresa : empresas) {
            if (empresa.getCnpj().equals(payload.getCnpj())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"CNPJ já existente no sistema\"}");
            }
        }

        Long ultimoId = empresas.stream()
                            .mapToLong(EmpresaModel::getId)
                            .max()
                            .orElse(0L);
        long proximoId = ultimoId + 1;
        payload.setId(proximoId);  
        payload.setSaldo(150000.00);    
        empresas.add(payload);
        return ResponseEntity.ok(payload);
    }

    @PutMapping("/{empresaId}")
    public ResponseEntity<Object> update(@PathVariable Long empresaId, @RequestBody EmpresaModel payload) {
        var novasEmpresas = empresas.stream().map(e -> {
            if (e.getId().equals(empresaId)) {
                e.setNome(payload.getNome());
                e.setCnpj(payload.getCnpj());
                e.setAreaAtuacao(payload.getAreaAtuacao());
                return e;
            }
            return e;
        }).collect(Collectors.toList());

        boolean atualizacaoRealizada = novasEmpresas.stream().anyMatch(e -> e.getId().equals(empresaId));

        if (atualizacaoRealizada) {
            empresas = new ArrayList<>(novasEmpresas);
            Optional<EmpresaModel> empresaAtualizada = empresas.stream().filter(e -> e.getId().equals(empresaId)).findFirst();
            return ResponseEntity.ok(empresaAtualizada.orElse(null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empresa não encontrada\"}");
        }
    }

    @DeleteMapping("/{empresaId}")
    public ResponseEntity<Object> delete(@PathVariable Long empresaId) {
        boolean empresaRemovida = empresas.removeIf(e -> e.getId().equals(empresaId));

        if (empresaRemovida) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empresa não encontrada\"}");
        }
    }
}
