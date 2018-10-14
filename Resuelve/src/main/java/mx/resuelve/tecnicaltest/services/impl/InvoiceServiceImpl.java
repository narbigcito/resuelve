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

    @Override
    public Response count(String id, String initDate, String finalDate) throws ClientException,
            UnexpectedException {

        this.id = id;
        this.currentDate = initDate;
        this.initDate = initDate;
        this.maxDay = finalDate;

        int totalInvoices = 0;
        int totalRequest = 0;

        while (beforeThan(this.currentDate, maxDay)) {
            totalInvoices += binarySearch(finalDate);
            totalRequest++;
            System.out.println("Progress: " + calcProgress() + "%");
        }

        return new Response(totalInvoices, totalRequest);
    }

    private boolean beforeThan(String date, String compareDate) throws UnexpectedException {
        try {
            return DATE_FORMAT.parse(date).before(DATE_FORMAT.parse(compareDate));
        } catch (ParseException e) {
            throw new UnexpectedException();
        }
    }

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

    private String getMiddleDateAsString(String initDate, String finalDate) throws UnexpectedException {
        try {
            long middleMill = DATE_FORMAT.parse(initDate).getTime();
            middleMill += DATE_FORMAT.parse(finalDate).getTime();

            return DATE_FORMAT.format(new Date(middleMill / 2));
        } catch (ParseException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

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

}
