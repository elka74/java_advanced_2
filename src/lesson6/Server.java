package lesson6;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        final int PORT = 8189;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

            socket = server.accept();
            System.out.println("Клиент подключился ");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            Thread t1 = new Thread(() -> {
                while (true) {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            if (str.equals("/end")) {
                                System.out.println("Клиент отключился");
                                break;
                            }
                            System.out.println("Клиент: " + str + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket.close();

            t1.start();
            Thread t2 = new Thread(() -> {
                String strS = new String();
                try {
                    out.writeUTF(strS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t2.start();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}


