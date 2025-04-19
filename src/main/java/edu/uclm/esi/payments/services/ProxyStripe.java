package edu.uclm.esi.payments.services;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class ProxyStripe {

	public String prepay(JSONObject jsoConf) {
		long amount = (long) (jsoConf.getFloat("price")*100);
		
		String key = jsoConf.getString("Stripe.apiKey");
		Stripe.apiKey = key;
		
		PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder().setCurrency("eur").setAmount(1200L)
				.build();

		try {
			PaymentIntent intent = PaymentIntent.create(params);
			JSONObject jso = new JSONObject(intent.toJson());
			String clientSecret = jso.getString("client_secret");
			return clientSecret;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, e.getMessage());
		}
	}

}
