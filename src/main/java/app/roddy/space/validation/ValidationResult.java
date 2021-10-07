package app.roddy.space.validation;

/**
 * The result of the validation check. Includes a true/false flag to show whether it passed or failed validation, and
 * a String which contains a message or message describing what may have gone wrong.
 */
public class ValidationResult {

    private boolean isValid = true;
    private String message = null;

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void appendMessage(String message) {
        if(this.message == null) {
            this.message = "Errors: ";
        }
        this.message += message + "; ";
    }
}
