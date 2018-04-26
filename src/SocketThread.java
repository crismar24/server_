import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Этот отдельный класс для обработки запроса клиента,
// который запускается в отдельном потоке
class SocketThread extends Thread
{
    private Socket fromClientSocket;
    private GUI gui;
    DefaultListModel model;

    public SocketThread(Socket fromClientSocket, GUI gui) {

        this.fromClientSocket = fromClientSocket;
        this.gui = gui;
        this.model = model;
    }

    @Override
    public void run() {
        // Автоматически будут закрыты все ресурсы
        try (Socket localSocket = fromClientSocket;
             PrintWriter pw = new PrintWriter(localSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(localSocket.getInputStream()))) {

            // Читаем сообщения от клиента
            String str;
            while ((str = br.readLine()) != null) {
                // Печатаем сообщение
                System.out.println("The message: " + str);
                // Сравниваем с "bye" и если это так - выходим из цикла и закрываем соединение
                if (str.contains("Request of connection of the server")) {
                    // Посылаем клиенту Положительную проверку на соединение с Сервером
                    pw.println("- Server correct-");
                    //break;
                } else {
                    //получить пользователя
                    int firstNumberChar = str.indexOf(": ");
                    String user = "";
                    if (firstNumberChar > 0) {
                        user = str.substring(0, firstNumberChar);
                        // TODO Сделать - если в пришедшем Ответе есть новый пользователь - то добавляем его в userList
                        gui.modelList.addElement(user);

                    }

                    // TODO Сделать - Посылать ответ всем юзерам из jList

                    // Посылаем клиенту ответ

                    // Выделяем только текст сообщения
                    String message = str;
                    gui.jTextArea.append("\r\n"+message);
                    pw.println(message);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
