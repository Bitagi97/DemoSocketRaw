import com.savarese.rocksaw.net.RawSocket;

import java.io.IOException;
import java.net.InetAddress;

public class mySocket extends RawSocket{
    public int PORT;
    public byte[] data = new byte[1000];
    static public int CS = 100;
    static public int[] pow100 = new int[7];
    public mySocket(int port){
        PORT = port;
        pow100[0] = 1;
        for(int i=1;i<=6;i++)
            pow100[i]=pow100[i-1]*100;
    }
    public void inttoarr(int a, int j){
        int tmp = a;
        int i = j;
        while(tmp > 0){
            data[i] = (byte) (tmp % CS);
            tmp /= CS;
            i++;
        }

    }
    public Packet convertBytePacket(byte[] data){
        Packet newPacket = new Packet();
        newPacket.SourcePort = arrtoint(20,24,data);
        newPacket.DestinationPort = arrtoint(25,29,data);
        newPacket.Length = arrtoint(30,34,data);
        if(data[35]==1)
            newPacket.isACK = true;
        else
            newPacket.isACK = false;
        byte[] msg = new byte[newPacket.Length+1];
        for(int i=36;i<36+newPacket.Length;i++){
            msg[i-36] = data[i];
        }
        newPacket.message = new String(msg);
        newPacket.setChecksum();
        return newPacket;
    }
    public void convertPacketByte(Packet a){
        inttoarr(a.SourcePort,0);
        inttoarr(a.DestinationPort,5);
        inttoarr(a.Length,10);
        if(a.isACK)
            data[15] = 1;
        else
            data[15] = 0;
        byte[] msg = a.message.getBytes();
        for(int i = 16;i<=15+msg.length;i++){
            data[i] = msg[i-16];
        }

        //System.out.println(data);
        //System.out.println(data.length);
    }
    public void send(InetAddress ad, Packet a) throws IOException {
        //System.out.println(data);
        convertPacketByte(a);
        super.write(ad, data);
    }
    public Packet recieve(InetAddress sourceAd) throws IOException {
        //System.out.println(super.getReceiveBufferSize());

        //System.out.println(sourceAd.toString().getBytes().length);
        byte[] data1 = new byte[2000];
        super.read(data1);
       // System.out.println(data1);
        //for(int i=25;i<=29;i++)
          //  System.out.println(data1[i]);
        //System.out.println(arrtoint(25,29,data1));

        if(arrtoint(25,29, data1) == PORT){
            return convertBytePacket(data1);
        }
        return null;
    }

    public int arrtoint(int i1, int i2, byte[] data) {
        int result = 0;
        for(int i=i2;i>=i1;i--) {
            result = result * CS + data[i];
           // System.out.println("+"+ result);
        }
        return result;
    }

}
