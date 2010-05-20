package barsuift.simLife.universe;


public class SaveException extends Exception {

    private static final long serialVersionUID = -6581565994400340921L;

    public SaveException() {
        super("Unable to save the universe");
    }

    public SaveException(String message) {
        super(message);
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveException(Throwable cause) {
        super(cause);
    }

}
