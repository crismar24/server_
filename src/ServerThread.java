
import java.io.IOException;
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

        try {
            // Открыть серверный сокет (ServerSocket)
            ServerSocket servSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for a connection...");
                gui.jTextArea.append("\nWaiting for a connection...");
                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();

                // Стартуем новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket, gui).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
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

