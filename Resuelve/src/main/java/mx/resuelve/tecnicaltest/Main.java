package mx.resuelve.tecnicaltest;

import java.util.logging.Logger;
import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceService;
import mx.resuelve.tecnicaltest.services.impl.InvoiceServiceImpl;

/**
 *
 * @author gibran
 */
public class Main {

    public static void main(String[] args) {

        try {
            InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
            String id = args[0];
            String startDate = args[1];
            String finishDate = args[2];

            Response response = invoiceService.count(id, startDate, finishDate);

            System.out.println("Total invoices between " + startDate + " and "
                    + finishDate + ": " + response.getTotalInvoices());
            System.out.println("Total requests: " + response.getTotalRequest());
        } catch (ClientException | UnexpectedException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

    }
}
