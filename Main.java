package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    protected static String login;
    protected static String to = "all";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if (Login.sendLogin(login, password) == 200) {
            try {
                Thread th = new Thread(new GetThread());
                th.setDaemon(true);
                th.start();
                Information.getInformation();
                System.out.println("Enter your message: ");
                while (true) {
                    String text = scanner.nextLine();
                    if (text.equals("!exit")) {
                        break;
                    }
                    switch (text) {
                        case "!list":
                            Login.getUserList();
                            break;
                        case "!to":
                            System.out.println("Enter login to send message to:");
                            String toPr = scanner.nextLine();
                            Main.to = toPr;
                            System.out.println("Enter your message:");
                            String textPrivate = scanner.nextLine();
                            Message priv = new Message(login, toPr, textPrivate);
                            int resOne = priv.send(Utils.getURL() + "/add");
                            if (resOne != 200) { // 200 OK
                                System.out.println("HTTP error occured: " + resOne);
                                return;
                            }
                            break;
                        default:
                            Message m = new Message(login, to, text);
                            int res = m.send(Utils.getURL() + "/add");
                            if (res != 200) { // 200 OK
                                System.out.println("HTTP error occured: " + res);
                                return;
                            }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                scanner.close();
            }
        } else {
            System.out.println("Wrong password!");
            scanner.close();
        }

    }
}
