import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.lang.Thread;

public class MultiCastSender {
    public final static String GROUP_ADRESSS = "224.0.0.2";
    public final static int PORT = 8888;
    
    public static void main(String[] args) {
        MulticastSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName(GROUP_ADRESSS);
            socket = new MulticastSocket();
            DatagramPacket outPacket = null;
            while(true) {
                System.out.println("Enter msg: ");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String messenger = br.readLine();
                byte[] msg = messenger.getBytes();
                outPacket = new DatagramPacket(msg, msg.length, address, PORT);
                socket.send(outPacket);
                System.out.println("Server sent packet with msg: " + messenger);
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                socket.close();
            }
        }
    }
}
