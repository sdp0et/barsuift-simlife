package barsuift.simLife;


public class InitException extends Exception {

    private static final long serialVersionUID = 5528389234185705064L;

    public InitException() {
        super();
    }

    public InitException(String message) {
        super(message);
    }

    public InitException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitException(Throwable cause) {
        super(cause);
    }

}
