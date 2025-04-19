package edu.uclm.esi.payments.services;

import java.io.InputStream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentsService {
	
	@Autowired
	private ProxyStripe proxyStripe;
	
	public String prepay() throws Exception {
		
		JSONObject jsoConf = this.readConf("conf.json");
		
		return this.proxyStripe.prepay(jsoConf);
		
		
	}

	private JSONObject readConf(String fileName) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream fis = classLoader.getResourceAsStream(fileName)) {
			byte[] b = new byte[fis.available()];
			fis.read(b);
			String s = new String(b);
			return new JSONObject(s);
		}
	}
}
