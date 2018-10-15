package mx.resuelve.tecnicaltest.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gibran
 */
public class DateUtilsTest {

    public DateUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of validDate method, of class DateUtils.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testValidDate() throws Exception {
        System.out.println("validDate");
        String date = "2017-01-01";
        boolean expResult = true;
        boolean result = DateUtils.validDate(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of beforeThan method, of class DateUtils.
     */
    @Test
    public void testBeforeThan() throws Exception {
        System.out.println("beforeThan");
        String date = "2017-01-01";
        String compareDate = "2017-01-02";
        boolean expResult = true;
        boolean result = DateUtils.beforeThan(date, compareDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMiddleDateAsString method, of class DateUtils.
     */
    @Test
    public void testGetMiddleDateAsString() throws Exception {
        System.out.println("getMiddleDateAsString");
        String initDate = "2017-01-01";
        String finalDate = "2017-01-03";
        String expResult = "2017-01-02";
        String result = DateUtils.getMiddleDateAsString(initDate, finalDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDays method, of class DateUtils.
     */
    @Test
    public void testAddDays() throws Exception {
        System.out.println("addDays");
        String date = "2017-01-01";
        int days = 1;
        String expResult = "2017-01-02";
        String result = DateUtils.addDays(date, days);
        assertEquals(expResult, result);
    }

    /**
     * Test of calcProgress method, of class DateUtils.
     */
    @Test
    public void testCalcProgress() throws Exception {
        System.out.println("calcProgress");
        String maxDay = "2017-01-011";
        String initDate = "2017-01-01";
        String currentDate = "2017-01-06";
        long expResult = 50L;
        long result = DateUtils.calcProgress(maxDay, initDate, currentDate);
        assertEquals(expResult, result);
    }

}
