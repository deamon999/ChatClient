package ua.kiev.prog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login {
    public static int sendLogin(String login, String password) throws IOException {
        URL obj = new URL(Utils.getURL() + "/login");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        String temp = login + "," + password;
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        try {
            os.write(temp.getBytes());
            return conn.getResponseCode();
        } finally {
            os.close();
        }
    }

    public static void getUserList() throws IOException {
        URL obj = new URL(Utils.getURL() + "/login");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        byte[] temp;
        String answer;
        try (InputStream is = conn.getInputStream()) {
            temp = new byte[is.available()];
            is.read(temp);
            answer = new String(temp);
        }
        String[] display = answer.split(",");
        System.out.println("List of users: ");
        for(String s:display){
            System.out.println(s);
        }

    }
}
