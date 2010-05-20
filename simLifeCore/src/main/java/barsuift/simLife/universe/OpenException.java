package barsuift.simLife.universe;


public class OpenException extends Exception {

    private static final long serialVersionUID = -6581565994400340921L;

    public OpenException() {
        super("Unable to open the saved universe");
    }

    public OpenException(String message) {
        super(message);
    }

    public OpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenException(Throwable cause) {
        super(cause);
    }

}
