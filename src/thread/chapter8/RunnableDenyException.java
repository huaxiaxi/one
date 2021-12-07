package thread.chapter8;

public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String s) {
        super(s);
    }
}
