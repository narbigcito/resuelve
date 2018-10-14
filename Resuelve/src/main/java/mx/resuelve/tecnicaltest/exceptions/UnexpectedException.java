package mx.resuelve.tecnicaltest.exceptions;

/**
 *
 * @author gibran
 */
public class UnexpectedException extends Exception{

    public UnexpectedException() {
        super("Unexpected error");
    }
    
    public UnexpectedException(String message){
        super(message);
    }
    
}
