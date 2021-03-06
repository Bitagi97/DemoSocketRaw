import com.savarese.rocksaw.net.RawSocket;

import java.net.InetAddress;

public class Server {


    public static void main(String[] args) {

        Thread se1 = new Thread(new Runnable() {
            public void run() {
                try {
                    mySocket serverSocket = new mySocket(32000);
                    serverSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
                    serverSocket.bind(InetAddress.getByName("127.111.49.44"));
                    byte[] ipClient = new byte[4];
                    //serverSocket.setReceiveBufferSize(50);
                   // System.out.println(InetAddress.getLocalHost());
                    while (true) {
                        Packet data ;
                        data = serverSocket.recieve(ipClient);
                       // System.out.println(InetAddress.getByAddress(ipClient));
                       // System.out.println(ipClient);
                        if(data!=null) {
                            System.out.println(data.toString());
                            System.out.println("---------------------------------------------");
                            String msg = "This is ACK packet";
                            Packet ackPacket = new Packet(data.DestinationPort,data.SourcePort,msg.length(),true,msg);
                            System.out.println(ackPacket);
                            serverSocket.send(InetAddress.getByAddress(ipClient),ackPacket);
                            //--------------------------------------------------------------------
                            String msgForHash = new String(ipClient);
                            Packet hashPacket = new Packet(serverSocket.PORT,1600,msgForHash.length(),false,msgForHash);
                            serverSocket.send(InetAddress.getByName("127.0.0.3"),hashPacket);
                           // hashPacket.DestinationPort = 81;
                          //  serverSocket.send(InetAddress.getByName("127.0.0.4"),hashPacket);
                           // break;
                        }
                    }
                } catch (Exception ex) {
                    // System.out.println(ex.toString());
                    ex.printStackTrace();
                }
            }
        });
        se1.start();

    }
}