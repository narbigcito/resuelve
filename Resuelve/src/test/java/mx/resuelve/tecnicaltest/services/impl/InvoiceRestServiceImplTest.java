/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.resuelve.tecnicaltest.services.impl;

import mx.resuelve.tecnicaltest.services.InvoiceRestService;
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
public class InvoiceRestServiceImplTest {

    public InvoiceRestServiceImplTest() {
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
     * Test of request method, of class InvoiceRestServiceImpl.
     */
    @Test
    public void testRequest() throws Exception {
        System.out.println("request");
        String id = "a55f50b5-886b-4846-95c2-c3ac4cc250a7";
        String initDate = "2017-01-01";
        String finalDate = "2017-06-30";
        InvoiceRestServiceImpl instance = null;
        int expResult = 0;
        int result = instance.request(id, initDate, finalDate);
        assertEquals(expResult, result);
    }

}
