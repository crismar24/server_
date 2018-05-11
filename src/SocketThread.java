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

                        //если такого пользователя нет еще в hashSet тогда добавляем
                        if (!gui.hashSetUsers.contains(user)) {
                                gui.hashSetUsers.add(user);
                        }

                        //очищаем список пользователей
                        gui.modelList.clear();

                        //заполняем List на форме всеми уник. пользователями
                        for (Object userSet : gui.hashSetUsers) {
                            gui.modelList.addElement(userSet.toString());
                        }

                    }

                    // Выделяем только текст сообщения
                    String message = str;

                    // TODO Сделать - Посылать ответ всем юзерам из jList
                    // Создать класс Connection - объект будет создаваться каждый раз(возможно только для ногового пользователя,единожды) при отправке сообщения

                    // Отправим всем клиентам по ip сообщение

                    for (String userSet : gui.hashSetUsers) {
                        Connection connection = new Connection(1777, userSet);
                        connection.init();
                        connection.sent(message);
                        connection.close();
                    }

                    // Оправим на Табло сервера текст сообщения клиента
                    gui.jTextArea.append("\r\n"+message);

                    //отправлять не будем по этому ip. Отправим выше всем клиентам по ip
                    //pw.println(message);

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
