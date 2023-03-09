package translate;

/**
 *
 * @author Dang Minh Tuan SE150430
 */
public class TranslateException extends Exception {

    public TranslateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Exception:" + super.getMessage();
    }

}
