import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

import static java.awt.Color.getColor;

public class GUI extends JFrame {
    HashSet<String> hashSetUsers = new HashSet<String>();

    DefaultListModel<String> modelList;
    JList<String> jList;
    JTextArea label;
    JTextArea jTextArea;
    String ipServer;
    int port;

    public GUI(String ipServer, int port) throws HeadlessException {
        this.ipServer = ipServer;
        this.port = port;

        setTitle("Server host");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        label = new JTextArea("IP сервера: " + this.ipServer + ", порт: " + this.port);
        label.setSize(600, 150);
        label.setBackground(Color.ORANGE);
        label.setEditable(false);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(label, BorderLayout.NORTH);

        jTextArea = new JTextArea();
        jTextArea.setSize(300, 500);
        jTextArea.setEditable(false);
        // Параметры переноса слов
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(jTextArea);
        sp.createVerticalScrollBar();
        sp.setSize(250, 500);
        mainPanel.add(sp, BorderLayout.WEST);

        modelList = new DefaultListModel<>();
        jList = new JList<String>(modelList);
        jList.setPreferredSize(new Dimension(240, 500));
        //TODO сделать  функциональность - добавить пользователя при появлении нового, удалять по кнопке Kick user (блокировать пользователя)
        // Панель кнопок
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        JButton kickButton = new JButton("Info user");
        buttonsPanel.setPreferredSize(new Dimension(250, 24));
        buttonsPanel.add(kickButton);
        JButton deleteUser = new JButton("Kick user");
        buttonsPanel.add(deleteUser);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.add(new JScrollPane(jList), BorderLayout.EAST);
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(650, 500));
        pack();

    }


}
