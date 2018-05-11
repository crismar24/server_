import java.io.IOException;

public interface UserConnection {
    int port = 0;
    String ip = "";
    void init() throws IOException;
    void sent(String  message);
    void recive();
    void close() throws IOException;
}
