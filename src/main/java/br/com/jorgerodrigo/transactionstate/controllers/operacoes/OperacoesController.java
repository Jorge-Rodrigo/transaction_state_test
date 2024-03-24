package br.com.jorgerodrigo.transactionstate.controllers.operacoes;

import br.com.jorgerodrigo.transactionstate.controllers.clientes.ClienteController;
import br.com.jorgerodrigo.transactionstate.controllers.empresas.EmprasaController;
import br.com.jorgerodrigo.transactionstate.models.ClienteModel;
import br.com.jorgerodrigo.transactionstate.models.EmpresaModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/operacoes")
public class OperacoesController {

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private EmprasaController empresaController;

    @PostMapping("/saque/{clienteId}")
    public ResponseEntity<Object> saque(@PathVariable Long clienteId, @RequestBody Map<String, Object> requestBody) {
        DecimalFormat df = new DecimalFormat("#.00");
        Long empresaId = Long.parseLong(requestBody.get("empresaId").toString());
        double valor = Double.parseDouble(requestBody.get("valor").toString());
        ResponseEntity<Object> clienteOptional = clienteController.retrieve(clienteId);

        if (clienteOptional.getStatusCode().is2xxSuccessful()) {

            ClienteModel cliente = (ClienteModel) clienteOptional.getBody();
            ResponseEntity<Object> empresaResponse = empresaController.retrieve(empresaId);

            if (empresaResponse.getStatusCode().is2xxSuccessful()) {

                EmpresaModel empresa = (EmpresaModel) empresaResponse.getBody();

                if (empresa.getSaldo() >= valor) {
                    empresa.setSaldo(empresa.getSaldo() - valor);
                    double valorComTaxa = valor - 3.99; 
                    cliente.setSaldo(cliente.getSaldo() + valorComTaxa);

                    cliente.setSaldo(Double.parseDouble(df.format(cliente.getSaldo())));
                    empresa.setSaldo(Double.parseDouble(df.format(empresa.getSaldo())));
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("Cliente", cliente);
                    response.put("Empresa", empresa);
                    
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                   
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Saldo Insuficiente\"}");
                }
                
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empresa não encontrada\"}");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
        } 
    }
    
    @PostMapping("/deposito/{clienteId}")
    public ResponseEntity<Object> deposito(@PathVariable Long clienteId, @RequestBody Map<String, Object> requestBody) {
        DecimalFormat df = new DecimalFormat("#.00");
        Long empresaId = Long.parseLong(requestBody.get("empresaId").toString());
        double valor = Double.parseDouble(requestBody.get("valor").toString());
        ResponseEntity<Object> clienteOptional = clienteController.retrieve(clienteId);

        if (clienteOptional.getStatusCode().is2xxSuccessful()) {
            ClienteModel cliente = (ClienteModel) clienteOptional.getBody();
            ResponseEntity<Object> empresaResponse = empresaController.retrieve(empresaId);
            if (empresaResponse.getStatusCode().is2xxSuccessful()) {
                EmpresaModel empresa = (EmpresaModel) empresaResponse.getBody();
                if (cliente.getSaldo() >= valor) {
                    cliente.setSaldo(cliente.getSaldo() - valor);
                    double valorComTaxa = valor - 3.99; 
                    empresa.setSaldo(empresa.getSaldo() + valorComTaxa);

                    cliente.setSaldo(Double.parseDouble(df.format(cliente.getSaldo())));
                    empresa.setSaldo(Double.parseDouble(df.format(empresa.getSaldo())));

                    Map<String, Object> response = new HashMap<>();
                    response.put("Cliente", cliente);
                    response.put("Empresa", empresa);

                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Saldo Insuficiente\"}");
                }
                
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empresa não encontrada\"}");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Cliente não encontrado\"}");
        } 
    }

 
}