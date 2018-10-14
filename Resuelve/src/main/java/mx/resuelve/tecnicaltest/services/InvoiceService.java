package mx.resuelve.tecnicaltest.services;

import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;

/**
 *
 * @author gibran
 */
public interface InvoiceService {

    public Response count(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException;

}
