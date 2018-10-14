package mx.resuelve.tecnicaltest.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mx.resuelve.tecnicaltest.exceptions.ClientException;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.TooMuchInvoiceException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceService;

/**
 *
 * @author gibran
 */
public class InvoiceServiceImpl implements InvoiceService {

    private static InvoiceService instance;
    private static final String URL_STRING = "http://34.209.24.195/facturas/?id={0}&start={1}&finish={2}";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private String id;
    private String currentDate;
    private String initDate;
    private String maxDay;

    private InvoiceServiceImpl() {
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

        this.id = id;
        this.currentDate = initDate;
        this.initDate = initDate;
        this.maxDay = finalDate;

        validDate(initDate);
        validDate(finalDate);

        int totalInvoices = 0;
        int totalRequest = 0;

        while (beforeThan(this.currentDate, maxDay)) {
            totalInvoices += binarySearch(finalDate);
            totalRequest++;
            System.out.println("Progress: " + calcProgress() + "%");
        }

        return new Response(totalInvoices, totalRequest);
    }

    /**
     * Tests if the date is before the specified compareDate.
     *
     * @param date
     * @param compareDate
     * @return true if date is before that compareDate.
     * @throws InvalidInputException If the dates are invalid or null;
     */
    private boolean beforeThan(String date, String compareDate) throws InvalidInputException {
        try {
            return DATE_FORMAT.parse(date).before(DATE_FORMAT.parse(compareDate));
        } catch (ParseException e) {
            throw new InvalidInputException("Invalid Dates");
        } catch (NullPointerException e) {
            throw new InvalidInputException("The Dates are required");
        }
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
    private int binarySearch(String finalDate) throws ClientException, UnexpectedException {

        String middleDate = getMiddleDateAsString(currentDate, finalDate);

        try {
            int invoices = request(currentDate, finalDate);
            currentDate = addDays(finalDate, 1);

            return invoices;

        } catch (TooMuchInvoiceException e) {
            return binarySearch(middleDate);
        }

    }

    /**
     *
     * @return the percentage of the current process based on the currentDate and the final date.
     * @throws UnexpectedException
     */
    private long calcProgress() throws UnexpectedException {
        try {
            long maxDayMill = DATE_FORMAT.parse(maxDay).getTime();
            long minDayMill = DATE_FORMAT.parse(initDate).getTime();

            long maxProgress = maxDayMill - minDayMill;

            long currentProgress = DATE_FORMAT.parse(currentDate).getTime() - minDayMill;

            return (currentProgress * 100L) / maxProgress;
        } catch (ParseException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    /**
     * The intermediate date between a range of dates.
     *
     * @param initDate
     * @param finalDate
     * @return
     * @throws UnexpectedException
     */
    private String getMiddleDateAsString(String initDate, String finalDate) throws UnexpectedException {
        try {
            long middleMill = DATE_FORMAT.parse(initDate).getTime();
            middleMill += DATE_FORMAT.parse(finalDate).getTime();

            return DATE_FORMAT.format(new Date(middleMill / 2));
        } catch (ParseException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    /**
     * Sum to the given date the number of days received.
     *
     * @param date
     * @param days
     * @return
     * @throws UnexpectedException
     */
    private String addDays(String date, int days) throws UnexpectedException {
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(DATE_FORMAT.parse(date));
            calendar.add(Calendar.DAY_OF_YEAR, days);
            return DATE_FORMAT.format(calendar.getTime());
        } catch (ParseException e) {
            throw new UnexpectedException(e.getMessage());
        }

    }

    /**
     * @param initDate
     * @param finalDate
     * @return the amount of existing invoices in the range of dates given for the indicated invoice
     * id by connecting to the Resuelve server.
     * @throws ClientException
     * @throws UnexpectedException
     * @throws TooMuchInvoiceException If there are more than 100 invoices.
     */
    private int request(String initDate, String finalDate) throws ClientException,
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

    /**
     *
     * @param date
     * @return true if all data is valid.
     * @throws InvalidInputException if the dates given have an invalid format.
     */
    private static boolean validDate(String date) throws InvalidInputException {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            throw new InvalidInputException(date + " is a invalid date");
        }
    }

}
