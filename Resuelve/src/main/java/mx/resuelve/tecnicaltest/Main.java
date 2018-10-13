package mx.resuelve.tecnicaltest;

import mx.resuelve.tecnicaltest.services.InvoiceService;
import mx.resuelve.tecnicaltest.services.impl.InvoiceSerrviceImpl;

/**
 *
 * @author gibran
 */
public class Main {

    public static void main(String[] args) {

        InvoiceService invoiceService = InvoiceSerrviceImpl.getInstance();
        String id = args[0];
        String startDate = args[1];
        String finishDate = args[2];

        System.out.println(invoiceService.count(id, startDate, finishDate));

    }
}
