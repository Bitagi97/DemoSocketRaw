import com.savarese.rocksaw.net.RawSocket;

import java.io.IOException;
import java.net.InetAddress;

// using 0->4 for source port
// using 5->9 for des port
// using 10->14 for Length
// using 15 for ACK
public class Client {
    static String serverName = "LOCALHOST";
    static int Sport = 100;
    static int Dport = 32000;
    static int IsACK = 0;
    static int cs = 100;
    static byte[] data = new byte[20];

    public static void main(String [] args)
    {

        try {
            mySocket clientSocket = new mySocket(Sport);
            clientSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
            clientSocket.bind(InetAddress.getByName("127.0.0.1"));
            String msg = new String("43593sf435ggkd");
            Packet clientPacket = new Packet(Sport,Dport,msg.length(),false,msg);

            clientSocket.send(InetAddress.getLocalHost(), clientPacket);
        } catch (java.lang.IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
