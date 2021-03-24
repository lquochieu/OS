import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkedThread extends Thread {
    private Socket socket;
    public WorkedThread(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        System.out.println("Processing: " + socket);
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            while(true) {
                int ch = is.read();
                if(ch == -1) {
                    break;
                }
                os.write(ch);
            }
        } catch (IOException e) {
            System.err.println("Request processing error: " + e);
        }
        System.out.println("Completing processing: " + socket);
    }
}
