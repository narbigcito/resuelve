package mx.resuelve.tecnicaltest.services.impl;

import java.text.ParseException;
import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceRestService;
import mx.resuelve.tecnicaltest.services.InvoiceService;
import mx.resuelve.tecnicaltest.utils.DateUtils;

/**
 *
 * @author gibran
 */
public class InvoiceServiceImpl implements InvoiceService {

    private static InvoiceService instance;
    private final InvoiceRestService invoiceRestService;

    private String currentDate;
    private String initDate;
    private String maxDay;

    private InvoiceServiceImpl() {
        invoiceRestService = InvoiceRestServiceImpl.getInstance();
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceServiceImpl();
        }

        return instance;
    }

    /**
     * Validate that the entries are correct and call a function that performs a binary search to
     * find smaller date ranges where the server does return a number of invoices in the given
     * range.
     *
     * @param id
     * @param initDate
     * @param finalDate
     * @return A response Object with the bills counted and the number of requests that were made.
     * @throws ClientException
     * @throws UnexpectedException
     * @throws InvalidInputException
     */
    @Override
    public Response count(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException, InvalidInputException {

        this.currentDate = initDate;
        this.initDate = initDate;
        this.maxDay = finalDate;

        DateUtils.validDate(initDate);
        DateUtils.validDate(finalDate);

        int totalInvoices = 0;
        int totalRequest = 0;

        while (DateUtils.beforeThan(this.currentDate, maxDay)) {
            totalInvoices += binarySearch(id, finalDate);
            totalRequest++;
            System.out.println("Progress: " + calcProgress() + "%");
        }

        return new Response(totalInvoices, totalRequest);
    }

    /**
     * It makes a request to the server using a binary search recursively until a numeric response
     * from the server is obtained updating the currentDate in which the search is found.
     *
     * @param finalDate
     * @return The number of invoices existing between the current currentDate and the current
     * finalDate.
     * @throws ClientException
     * @throws UnexpectedException
     */
    private int binarySearch(String id, String finalDate) throws ClientException, UnexpectedException {

        String middleDate = DateUtils.getMiddleDateAsString(currentDate, finalDate);

        try {
            int invoices = invoiceRestService.request(id, currentDate, finalDate);
            currentDate = DateUtils.addDays(finalDate, 1);

            return invoices;

        } catch (TooMuchInvoiceException e) {
            return binarySearch(id, middleDate);
        }

    }

    /**
     *
     * @return the percentage of the current process based on the currentDate and the final date.
     * @throws UnexpectedException
     */
    private long calcProgress() throws UnexpectedException {
        try {
            long maxDayMill = DateUtils.DATE_FORMAT.parse(maxDay).getTime();
            long minDayMill = DateUtils.DATE_FORMAT.parse(initDate).getTime();

            long maxProgress = maxDayMill - minDayMill;

            long currentProgress = DateUtils.DATE_FORMAT.parse(currentDate).getTime() - minDayMill;

            return (currentProgress * 100L) / maxProgress;
        } catch (ParseException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

}
