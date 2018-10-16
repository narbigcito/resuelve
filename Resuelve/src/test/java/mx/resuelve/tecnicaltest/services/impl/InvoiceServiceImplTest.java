package mx.resuelve.tecnicaltest.services.impl;

import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceRestService;
import mx.resuelve.tecnicaltest.services.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author gibran
 */
public class InvoiceServiceImplTest {

    public InvoiceServiceImplTest() {
    }

    private InvoiceRestService invoiceRestService;

    @Before
    public void setUp() {
        invoiceRestService = Mockito.mock(InvoiceRestService.class);
    }

    /**
     * Test of count method, of class InvoiceServiceImpl.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.ClientException
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     * @throws mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException
     * @throws mx.resuelve.tecnicaltest.exceptions.InvalidInputException
     */
    @Test
    public void testCount() throws ClientException, UnexpectedException,
            TooMuchInvoiceException, InvalidInputException {
        System.out.println("count");
        String id = "a55f50b5-886b-4846-95c2-c3ac4cc250a7";
        String initDate = "2017-01-01";
        String finalDate = "2018-01-01";
        InvoiceService invoiceService = InvoiceServiceImpl.getInstance(invoiceRestService);
        int totalInvoicesExpect = 50;
        int totalRequestsExpect = 2;
        Mockito.when(invoiceRestService.request(id, initDate, finalDate)).thenThrow(new TooMuchInvoiceException());
        Mockito.when(invoiceRestService.request(id, "2017-07-03", finalDate)).thenReturn(50);
        Response result = invoiceService.count(id, initDate, finalDate);
        assertEquals(totalInvoicesExpect, result.getTotalInvoices());
        assertEquals(totalRequestsExpect, result.getTotalRequest());
    }

    /**
     * Test of count method, of class InvoiceServiceImpl.
     *
     * trying to give the final date less than the initial
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.ClientException
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     * @throws mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException
     * @throws mx.resuelve.tecnicaltest.exceptions.InvalidInputException
     */
    @Test
    public void testCountFinalDateLessThanInitDate() throws ClientException, UnexpectedException,
            TooMuchInvoiceException, InvalidInputException {
        System.out.println("count");
        String id = "a55f50b5-886b-4846-95c2-c3ac4cc250a7";
        String initDate = "2017-01-02";
        String finalDate = "2018-01-01";
        InvoiceService invoiceService = InvoiceServiceImpl.getInstance(invoiceRestService);
        int totalInvoicesExpect = 0;
        int totalRequestsExpect = 1;
        Mockito.when(invoiceRestService.request(id, initDate, finalDate)).thenReturn(0);
        Response result = invoiceService.count(id, initDate, finalDate);
        assertEquals(totalInvoicesExpect, result.getTotalInvoices());
        assertEquals(totalRequestsExpect, result.getTotalRequest());
    }

    /**
     * Test of count method, of class InvoiceServiceImpl.
     *
     * trying to give wrong inputs
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.ClientException
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     * @throws mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException
     * @throws mx.resuelve.tecnicaltest.exceptions.InvalidInputException
     */
    @Test(expected = InvalidInputException.class)
    public void testCountWrongInputs() throws ClientException, UnexpectedException,
            TooMuchInvoiceException, InvalidInputException {
        System.out.println("count");
        String id = "a55f50b5-886b-4846-95c2-c3ac4cc250a7";
        String initDate = "This is incorect";
        String finalDate = "20180101";
        InvoiceService invoiceService = InvoiceServiceImpl.getInstance(invoiceRestService);
        Mockito.when(invoiceRestService.request(id, initDate, finalDate)).thenReturn(50);
        invoiceService.count(id, initDate, finalDate);
    }

}
