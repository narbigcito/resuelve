package mx.resuelve.tecnicaltest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;

/**
 *
 * @author gibran
 */
public class DateUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * @param date
     * @return true if all data is valid.
     * @throws InvalidInputException if the dates given have an invalid format.
     */
    public static boolean validDate(String date) throws InvalidInputException {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            throw new InvalidInputException(date + " is a invalid date");
        }
    }

    /**
     * Tests if the date is before the specified compareDate.
     *
     * @param date
     * @param compareDate
     * @return true if date is before that compareDate.
     * @throws InvalidInputException If the dates are invalid or null;
     */
    public static boolean beforeThan(String date, String compareDate) throws InvalidInputException {
        try {
            return DATE_FORMAT.parse(date).before(DATE_FORMAT.parse(compareDate));
        } catch (ParseException e) {
            throw new InvalidInputException("Invalid Dates");
        } catch (NullPointerException e) {
            throw new InvalidInputException("The Dates are required");
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
    public static String getMiddleDateAsString(String initDate, String finalDate) throws UnexpectedException {
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
    public static String addDays(String date, int days) throws UnexpectedException {
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
     *
     * @param maxDay
     * @param initDate
     * @param currentDate
     * @return the percentage of reach the maxDay based on the currentDate and the initDate.
     * @throws UnexpectedException
     */
    public static long calcProgress(String maxDay, String initDate, String currentDate) throws UnexpectedException {
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

}
