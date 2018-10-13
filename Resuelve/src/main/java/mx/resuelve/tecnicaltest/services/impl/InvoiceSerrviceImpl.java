package mx.resuelve.tecnicaltest.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import mx.resuelve.tecnicaltest.services.InvoiceService;

/**
 *
 * @author gibran
 */
public class InvoiceSerrviceImpl implements InvoiceService {

    private static InvoiceService instance;
    private static final String URL_STRING = "http://34.209.24.195/facturas/?id={0}&start={1}&finish={2}";

    private InvoiceSerrviceImpl() {
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceSerrviceImpl();
        }

        return instance;
    }

    @Override
    public int count(String id, String initDate, String finalDate) {

        try {

            URL url = new URL(MessageFormat.format(URL_STRING, id, initDate, finalDate));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            /*     if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }*/
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return 5;

    }

}
