package mx.resuelve.tecnicaltest;

import java.util.logging.Logger;
import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceService;
import mx.resuelve.tecnicaltest.services.impl.InvoiceServiceImpl;

/**
 *
 * @author gibran
 */
public class Main {

    /**
     * The main method, invokes the service that counts the invoices based on the 
     * parameters received.
     * 
     * @param args: A invoice id, two dates that represent the range for which you want to 
     * count the bills in the format : yyyy-MM-dd", all three fields are required.
     */
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
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The id, the start date and the finish date are requiered");
        } catch (UnexpectedException e) {
            Logger.getGlobal().severe(e.getMessage());
        } catch (ClientException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }

    }
}
