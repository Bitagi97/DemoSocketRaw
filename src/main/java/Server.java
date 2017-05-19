import com.savarese.rocksaw.net.RawSocket;

import java.net.InetAddress;

public class Server {
    static byte[] data = new byte[40];


    public static void main(String[] args) {

        try {
            mySocket serverSocket = new mySocket(32000);
            serverSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
            serverSocket.bind(InetAddress.getLocalHost());
            //serverSocket.setReceiveBufferSize(50);
            while (true) {
                    Packet data = new Packet();
                    data = serverSocket.recieve(InetAddress.getByName("192.168.0.1"));
                    if(data!=null) {

                        System.out.println(data.toString());
                    }
            }
            } catch (Exception ex) {
               // System.out.println(ex.toString());
                ex.printStackTrace();
            }

    }
}