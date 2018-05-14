import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Этот отдельный класс для обработки запроса клиента,
// который запускается в отдельном потоке
class SocketThread extends Thread {
    private Socket fromClientSocket;
    private GUI gui;
    DefaultListModel model;
    static int quantityOfCalls;

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

            // Читаем сообщения от клиента ? / клиентов ?
            String str;
            while ((str = br.readLine()) != null) {
                // Печатаем сообщение
                System.out.println("The message: " + str);
                if (str.contains("Request of connection of the server")) {
                    // Посылаем клиенту Положительную проверку на соединение с Сервером
                    pw.println("- Server correct-");
                } else {
                    //получить пользователя
                    int firstNumberChar = str.indexOf(": ");
                    String user = "";
                    if (firstNumberChar > 0) {
                        user = str.substring(0, firstNumberChar);
                        // TODO Сделать - если в пришедшем Ответе есть новый пользователь - то добавляем его в userList

                        String message = str;
//                        Выделяем только текст сообщения
//                        String message = str.substring(user.length()+2);

                        // Если такого пользователя нет - еще в hashSetUsers тогда добавляем,
                        // отправляем сообщение всем из hashSetUsers
                        // кроме fromClientSocket.getLocalSocketAddress() - того, от кого пришло само сообщение
                        // - т.к. клиент сами себе на табло посылает свое же сообщение
                        // _ Наверно так не правильно - пусть лучше приходит от сервера только
                        if (!gui.hashSetUsers.contains(user)) {
                            gui.hashSetUsers.add(user);
//                            Connection connection = new Connection(1777, user);
//                            connection.init();
//                            connection.sent(message);
//                            connection.close();

                            //отправим сообщение клиенту ответом сокета .
//                            pw.println(message);
                        }

                        // TODO Сделать - Посылать ответ всем юзерам из hashSetUsers
                        // Создать класс Connection - объект будет создаваться каждый раз(возможно только для ногового пользователя,единожды) при отправке сообщения
                        // Отправим всем клиентам по ip сообщение
                        quantityOfCalls++;
                        System.out.println("quantity Of Calls - "+ quantityOfCalls);
                        for (String userSet : gui.hashSetUsers) {
//                            if (!fromClientSocket.getLocalAddress().toString().substring(1).equals(userSet)) {
                                Connection connection = new Connection(1777, userSet);
                                connection.init();
                                connection.sent(message);
                                connection.close();
//                            }
                        }

                        //очищаем список пользователей
                        gui.modelList.clear();

                        //заполняем List на форме всеми уник. пользователями
                        for (Object userSet : gui.hashSetUsers) {
                            gui.modelList.addElement(userSet.toString());
                        }

                        // Оправим на Табло сервера текст сообщения клиента
                        gui.jTextArea.append("\r\n"+message);

                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
