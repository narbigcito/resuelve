package mx.resuelve.tecnicaltest.services;

import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;

/**
 *
 * @author gibran
 */
public interface InvoiceService {

    /**
     * Account all existing invoices between initDate and finalDate.
     *
     * @param id
     * @param initDate
     * @param finalDate
     * @return A response Object with the bills counted and the number of requests that were made.
     * @throws ClientException if the rest service detected an error by the client.
     * @throws UnexpectedException if an unexpected error occurs in the execution of the process.
     * @throws InvalidInputException if any input parameter is incorrect.
     */
    public Response count(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException, InvalidInputException;

}
