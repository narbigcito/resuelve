package mx.resuelve.tecnicaltest.services.impl;

import mx.resuelve.tecnicaltest.pojos.Response;
import mx.resuelve.tecnicaltest.services.InvoiceService;
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
public class InvoiceServiceImplTest {
    
    public InvoiceServiceImplTest() {
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
     * Test of getInstance method, of class InvoiceServiceImpl.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        InvoiceService expResult = null;
        InvoiceService result = InvoiceServiceImpl.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class InvoiceServiceImpl.
     */
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        String id = "";
        String initDate = "";
        String finalDate = "";
        InvoiceServiceImpl instance = null;
        Response expResult = null;
        Response result = instance.count(id, initDate, finalDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
