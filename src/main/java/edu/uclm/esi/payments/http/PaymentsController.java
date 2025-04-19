package edu.uclm.esi.payments.http;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import edu.uclm.esi.payments.services.PaymentsService;

@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "*")
public class PaymentsController {
	
	@Autowired
	private PaymentsService paymentsService;
	
	static {
		Stripe.apiKey = " ";
	}

	@GetMapping("/prepay")
	public String prepay() {
		
		try {
			return paymentsService.prepay();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Error al procesar solicitud");
		}
		
	}
}
