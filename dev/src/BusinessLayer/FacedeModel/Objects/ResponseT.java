package BusinessLayer.FacedeModel.Objects;

public class ResponseT<T> extends Response {
     T value;

    public ResponseT(String errorMsg) {
        super(errorMsg);
    }

    public ResponseT(T value) {
        super();
        this.value = value;
    }

    public ResponseT(T value, String errorMsg) {
        super(errorMsg);
        this.value = value;
    }
    public T getValue(){
        return value;
    }

    @Override
    public String print() {
        if (getErrorOccurred()) {
            return "";
        }
        return value.toString();
    }
}
