package edu.uclm.esi.payments.http;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.payments.model.Payment;
import edu.uclm.esi.payments.services.PaymentsService;

@RestController
@RequestMapping("payments")
@CrossOrigin("*")

public class PaymentsController {

    @Autowired
    private PaymentsService service;

    @GetMapping("/prepay")
    public String prepay() {
        try {
            return this.service.prepay();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Error al procesar tu status");
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody Payment payment) {
        try {
            service.confirmPayment(payment);
            return ResponseEntity.ok("Pago registrado correctamente");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el pago");
        }
    }
}
