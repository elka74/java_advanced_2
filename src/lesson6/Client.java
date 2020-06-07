package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8189;
    private DataOutputStream out;
    private DataInputStream in;

    Socket socket = null;
    Scanner sc = new Scanner(System.in);

    public void client() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread t3 = new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            break;
                        } else {
                            System.out.println(str + "\n");
                        }
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
            });
            t3.start();
            Thread t4 = new Thread(() ->{
                String str1 = sc.nextLine();
                try {
                    out.writeUTF(str1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            t4.start();
            t3.join();
            t4.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

