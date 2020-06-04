package lesson6;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Client {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;
    public void client() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();

                        if (str.equals("/end")) {
                            break;
                        }

                        System.out.println(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg() {
        try {
            String str = new String();
            out.writeUTF (str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
