import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Connection implements UserConnection {
    private int port;
    private String ip;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    public Connection(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void init() throws IOException {

        // Открыть сокет (Socket) для обращения к локальному компьютеру
        // Сервер мы будем запускать на этом же компьютере
        // Это специальный класс для сетевого взаимодействия c клиентской стороны
        socket = new Socket(ip, port);

        // Создать поток для чтения символов из сокета
        // Для этого надо открыть поток сокета - socket.getInputStream()
        // Потом преобразовать его в поток символов - new InputStreamReader
        // И уже потом сделать его читателем строк - BufferedReader
        // ip server / client ?
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Создать поток для записи символов в сокет
        // ip server / client ?
        pw = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void sent(String message) {
        // Отправляем строку в сокет
        pw.println(message);
    }

    @Override
    public void recive() {

    }

    @Override
    public void close() throws IOException {
        //br.close();
        pw.close();
        socket.close();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return port == that.port &&
                Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {

        return Objects.hash(port, ip);
    }
}
