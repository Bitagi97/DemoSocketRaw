import com.savarese.rocksaw.net.RawSocket;

import java.net.InetAddress;


public class md5Server {

    public static void main(String[] args) {

        Thread se1 = new Thread(new Runnable() {
            public void run() {
                try {
                    mySocket serverSocket = new mySocket(1600);
                    serverSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
                    serverSocket.bind(InetAddress.getByName("127.0.0.3"));
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
                            ipClient = data.message.getBytes();
                            System.out.println(InetAddress.getByAddress(ipClient));
                            System.out.println("---------------------------------------------");


                           break;
                        }
                    }
                    while(true){
                        String msg = "This is ACK packet of HD5";
                        Packet ackPacket = new Packet(serverSocket.PORT,100,msg.length(),true,msg);
                        System.out.println(ackPacket);
                        serverSocket.send(InetAddress.getByAddress(ipClient),ackPacket);
                        Thread.sleep(1000);
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
