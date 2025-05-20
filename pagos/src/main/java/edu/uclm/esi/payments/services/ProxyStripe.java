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
        long amount = (long) jsoConf.getFloat("price") * 100;
        String key = jsoConf.getString("Stripe.apiKey");
        Stripe.apiKey = key;

        PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                .setCurrency(jsoConf.getString("currency"))
                .setAmount(amount)
                .build();

        try {
            PaymentIntent intent = PaymentIntent.create(params);
            JSONObject jso = new JSONObject(intent.toJson());
            String clientSecret = jso.getString("client_secret");
            return clientSecret;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Error al procesar tu status");
        }
    }

    // public boolean pay(JSONObject jso) {
    //     try {
    //         // Simulación: en Stripe real, aquí confirmarías el PaymentIntent con los datos de la tarjeta
    //         String paymentIntentId = jso.getString("paymentIntentId");
    //         // Aquí podrías usar la API de Stripe para confirmar el pago si fuera necesario
    //         // PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
    //         // intent.confirm(...);

    //         // Simulamos que el pago se realiza correctamente
    //         return true;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }
}
