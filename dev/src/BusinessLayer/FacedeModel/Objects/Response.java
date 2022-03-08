package BusinessLayer.FacedeModel.Objects;

public class Response {
     String errorMsg;
     Boolean errorOccurred;

    public Response() {
        errorOccurred = false;
    }

    public Response(String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorOccurred = true;
    }

    public Boolean getErrorOccurred() {
        return errorOccurred;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String print() {
        if (errorOccurred) {
            return "";
        }
        return errorMsg;
    }

}
