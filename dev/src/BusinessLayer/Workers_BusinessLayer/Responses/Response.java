package BusinessLayer.Workers_BusinessLayer.Responses;

public class Response {
    private final String ErrorMessage;
    public Response(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public Response() {
        ErrorMessage = null;
    }

    public boolean ErrorOccurred(){
        return ErrorMessage != null;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
}

