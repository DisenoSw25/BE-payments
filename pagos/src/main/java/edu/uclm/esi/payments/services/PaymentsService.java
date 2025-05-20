package edu.uclm.esi.payments.services;

import java.io.InputStream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.payments.model.Payment;
import edu.uclm.esi.payments.dao.PaymentDAO;

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
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String s = new String(b);
            return new JSONObject(s);
        }
    }

    public void confirmPayment(Payment payment) {
        payment.setStatus("OK");
        paymentDAO.save(payment);
    }
}
