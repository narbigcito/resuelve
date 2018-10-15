package mx.resuelve.tecnicaltest.services;

import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;

/**
 *
 * @author gibran
 */
public interface InvoiceRestService {

    /**
     * @param initDate
     * @param finalDate
     * @return the amount of existing invoices in the range of dates given for the indicated invoice id.
     * @throws ClientException
     * @throws UnexpectedException
     * @throws TooMuchInvoiceException If there are more than 100 invoices.
     */
    int request(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException, TooMuchInvoiceException;

}
