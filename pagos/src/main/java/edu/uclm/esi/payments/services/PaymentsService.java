package edu.uclm.esi.payments.services;

import java.io.InputStream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.payments.dao.PaymentDAO;
import edu.uclm.esi.payments.model.Payment;

@Service
public class PaymentsService {

    @Autowired
    private ProxyStripe proxyStripe;

    @Autowired
    private PaymentDAO paymentDAO;

    public String prepay() throws Exception {
        JSONObject jsoConf = this.readConf("conf.json");

        return this.proxyStripe.prepay(jsoConf);

    }

    private JSONObject readConf(String fileName) throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream fis = classLoader.getResourceAsStream(fileName)) {
            if (fis == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el archivo de configuración: " + fileName);
            }
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String s = new String(b);
            return new JSONObject(s);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo de configuración", e);
        }
    }

    public void confirmPayment(Payment payment) {
        try {
            payment.setStatus("OK");
            paymentDAO.save(payment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al anotar el pago", e);
        }
    }
}
