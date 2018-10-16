package mx.resuelve.tecnicaltest.utils;

import mx.resuelve.tecnicaltest.exceptions.InvalidInputException;
import mx.resuelve.tecnicaltest.exceptions.UnexpectedException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gibran
 */
public class DateUtilsTest {

    /**
     * Test of validDate method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.InvalidInputException
     * @throws java.lang.Exception
     */
    @Test
    public void testValidDate() throws InvalidInputException {
        System.out.println("validDate");
        String date = "2017-01-01";
        boolean result = DateUtils.validDate(date);
        assertTrue(result);
    }

    /**
     * Test of validDate method, of class DateUtils.
     *
     * Testing incorrect input
     *
     * @throws java.lang.Exception
     */
    @Test(expected = InvalidInputException.class)
    public void testValidDateWrongInput() throws InvalidInputException {
        System.out.println("validDate");
        String date = "incorrect";
        DateUtils.validDate(date);
    }

    /**
     * Test of beforeThan method, of class DateUtils.
     */
    @Test
    public void testBeforeThan() throws InvalidInputException {
        System.out.println("beforeThan");
        String date = "2017-01-01";
        String compareDate = "2017-01-02";
        boolean result = DateUtils.beforeThan(date, compareDate);
        assertTrue(result);
    }

    /**
     * Test of beforeThan method, of class DateUtils.
     *
     * Testing both dates are the same
     */
    @Test
    public void testBeforeThanCaseEsquals() throws InvalidInputException {
        System.out.println("beforeThan");
        String date = "2017-01-01";
        String compareDate = "2017-01-01";
        boolean result = DateUtils.beforeThan(date, compareDate);
        assertFalse(result);
    }

    /**
     * Test of beforeThan method, of class DateUtils.
     *
     * compareDate is earlier
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.InvalidInputException
     */
    @Test
    public void testBeforeThanCaseAfter() throws InvalidInputException {
        System.out.println("beforeThan");
        String date = "2017-01-02";
        String compareDate = "2017-01-01";
        boolean result = DateUtils.beforeThan(date, compareDate);
        assertFalse(result);
    }

    /**
     * Test of getMiddleDateAsString method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     */
    @Test
    public void testGetMiddleDateAsString() throws UnexpectedException {
        System.out.println("getMiddleDateAsString");
        String initDate = "2017-01-01";
        String finalDate = "2017-01-03";
        String expResult = "2017-01-02";
        String result = DateUtils.getMiddleDateAsString(initDate, finalDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMiddleDateAsString method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     *
     * Same dates
     */
    @Test
    public void testGetMiddleDateAsStringSameDates() throws UnexpectedException {
        System.out.println("getMiddleDateAsString");
        String initDate = "2017-01-01";
        String finalDate = "2017-01-01";
        String expResult = "2017-01-01";
        String result = DateUtils.getMiddleDateAsString(initDate, finalDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDays method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     */
    @Test
    public void testAddDays() throws UnexpectedException {
        System.out.println("addDays");
        String date = "2017-01-01";
        int days = 1;
        String expResult = "2017-01-02";
        String result = DateUtils.addDays(date, days);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDays method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     *
     * Adding Zero days
     */
    @Test
    public void testAddDaysZero() throws UnexpectedException {
        System.out.println("addDays");
        String date = "2017-01-01";
        int days = 0;
        String expResult = "2017-01-01";
        String result = DateUtils.addDays(date, days);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDays method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     *
     * Adding negative days
     */
    @Test
    public void testAddDaysNegative() throws UnexpectedException {
        System.out.println("addDays");
        String date = "2017-01-03";
        int days = -1;
        String expResult = "2017-01-02";
        String result = DateUtils.addDays(date, days);
        assertEquals(expResult, result);
    }

    /**
     * Test of calcProgress method, of class DateUtils.
     *
     * @throws mx.resuelve.tecnicaltest.exceptions.UnexpectedException
     */
    @Test
    public void testCalcProgress() throws UnexpectedException {
        System.out.println("calcProgress");
        String maxDay = "2017-01-011";
        String initDate = "2017-01-01";
        String currentDate = "2017-01-06";
        long expResult = 50L;
        long result = DateUtils.calcProgress(maxDay, initDate, currentDate);
        assertEquals(expResult, result);
    }

}
