package mx.resuelve.tecnicaltest.pojos;

/**
 *
 * @author gibran
 */
public class Response {

    private int totalInvoices;
    private int totalRequest;

    public Response(int totalInvoices, int totalRequest) {
        this.totalInvoices = totalInvoices;
        this.totalRequest = totalRequest;
    }

    /**
     * @return the totalInvoices
     */
    public int getTotalInvoices() {
        return totalInvoices;
    }

    /**
     * @param totalInvoices the totalInvoices to set
     */
    public void setTotalInvoices(int totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    /**
     * @return the totalRequest
     */
    public int getTotalRequest() {
        return totalRequest;
    }

    /**
     * @param totalRequest the totalRequest to set
     */
    public void setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
    }

}
