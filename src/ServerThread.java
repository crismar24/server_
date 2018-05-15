
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerThread {
    private static String ipServer;

    public static void main(String args[]) {

        ipServer = getIPComputer();
        int port = 1777;

        GUI gui = new GUI(ipServer, port);
        gui.setVisible(true);

        // Открыть серверный сокет (ServerSocket)
//            ServerSocket servSocket = new ServerSocket(port);
        // Получив соединение начинаем работать с сокетом
//            Socket fromClientSocket = servSocket.accept();

//            Socket connection = new Socket(ipServer, port);
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);

        // Стартуем новый поток для обработки запроса клиента
        // "Слушает" socket ip сервера
        new SocketThread(port, ipServer, gui).start();

        System.out.println("Waiting for a connection...");
        gui.jTextArea.append("\nWaiting for a connection...");

        //gui.setVisible(true);
    }

    private static String getIPComputer() {
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
            ipServer = local.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipServer;
    }


}

