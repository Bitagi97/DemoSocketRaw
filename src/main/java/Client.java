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

    public static void main(String [] args)
    {
        Thread cl1 = new Thread(new Runnable() {
            public void run() {

                try {

                    mySocket clientSocket = new mySocket(Sport);
                    clientSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
                    //  clientSocket.bind(InetAddress.getByName("127.0.0.1"));
                    // clientSocket.setIPHeaderInclude(false);
                    String msg = new String("43593sf435ggkd");
                    Packet clientPacket = new Packet(Sport,Dport,msg.length(),false,msg);
                    Packet ackPacket;
                    byte[] ipServer = new byte[4];

                    //clientPacket.Checksum = 12;
                    while (true) {
                        clientSocket.send(InetAddress.getByName("127.111.49.44"), clientPacket);
                        ackPacket = clientSocket.recieve(ipServer);
                        if(ackPacket!=null){
                            System.out.println(ackPacket);
                            System.out.println("--------------------------------------------");
                            //clientSocket.close();
                            break;
                        }
                        Thread.sleep(10000);
                        System.out.println("resend");
                    }

                    while (true) {
                        //clientSocket.send(InetAddress.getByName("127.111.49.44"), clientPacket);
                        ackPacket = clientSocket.recieve(ipServer);
                        if(ackPacket!=null){
                            System.out.println(ackPacket);
                            System.out.println("--------------------------------------------");
                            break;
                        }

                    }
                } catch (java.lang.IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cl1.start();
    }
}
