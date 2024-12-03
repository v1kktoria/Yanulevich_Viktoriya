package senla.dicontainer.exception;

public class DIException  extends RuntimeException{
    private final DIExceptionEnum code;

    public DIException(DIExceptionEnum code, Object... args) {
        super(code.getMessage(args));
        this.code = code;
    }


    @Override
    public String toString() {
        return "DIException{" +
                "code=" + code +
                ", message=" + getMessage() +
                '}';
    }
}
