import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer {
    public final static int SERVER_PORT = 7;
    public final static byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            System.out.println("Biding to port: " + SERVER_PORT + " , please wait...");
            ds = new DatagramSocket(SERVER_PORT);
            System.out.println("Server started");
            System.out.println("Waiting for messenger from client....");

            while (true) {
                DatagramPacket incomming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incomming);

                String messenger = new String(incomming.getData(), 0, incomming.getLength());
                System.out.println("Receive: " + messenger);

                DatagramPacket outsending = new DatagramPacket(messenger.getBytes(), incomming.getLength(),
                        incomming.getAddress(), incomming.getPort());
                ds.send(outsending);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }

}
