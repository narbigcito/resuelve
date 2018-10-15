package mx.resuelve.tecnicaltest.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.services.InvoiceRestService;

/**
 *
 * @author gibran
 */
public class InvoiceRestServiceImpl implements InvoiceRestService {

    private static InvoiceRestService instance;

    private static final String URL_STRING = "http://34.209.24.195/facturas/?id={0}&start={1}&finish={2}";

    private InvoiceRestServiceImpl() {
    }

    public static InvoiceRestService getInstance() {
        if (instance == null) {
            instance = new InvoiceRestServiceImpl();
        }

        return instance;
    }

    /**
     * @param id
     * @param initDate
     * @param finalDate
     * @return the amount of existing invoices in the range of dates given for the indicated invoice
     * id by connecting to the Resuelve server.
     * @throws ClientException
     * @throws UnexpectedException
     * @throws TooMuchInvoiceException If there are more than 100 invoices.
     */
    @Override
    public int request(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException, TooMuchInvoiceException {
        try {
            URL url = new URL(MessageFormat.format(URL_STRING, id, initDate, finalDate));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String response = br.readLine();
                conn.disconnect();

                try {
                    return Integer.valueOf(response);
                } catch (NumberFormatException e) {
                    throw new TooMuchInvoiceException();
                }

            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String response = br.readLine();

                conn.disconnect();

                throw new ClientException(response);
            }

        } catch (IOException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

}
