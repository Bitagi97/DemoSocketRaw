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
        int check = arrtoint(35,39,data);
        if(data[40]==1)
            newPacket.isACK = true;
        else
            newPacket.isACK = false;
        byte[] msg = new byte[newPacket.Length];
        for(int i=41;i<41+newPacket.Length;i++){
            msg[i-41] = data[i];
        }
        newPacket.message = new String(msg);
        newPacket.setChecksum();
        if(check!=newPacket.Checksum)
            return null;
       // System.arraycopy( src, 0, dest, 0, src.length );
        //if(newPacket.Checksum.compareTo((String)tmp))
           // return null;
        return newPacket;
    }
    public void convertPacketByte(Packet a){
        inttoarr(a.SourcePort,0);
        inttoarr(a.DestinationPort,5);
        inttoarr(a.Length,10);
        inttoarr(a.Checksum,15);
        if(a.isACK)
            data[20] = 1;
        else
            data[20] = 0;

        byte[] msg = a.message.getBytes();

        for(int i = 21;i<21+msg.length;i++){
            data[i] = msg[i-21];
        }


        //System.out.println(data);
        //System.out.println(data.length);
    }
    public void send(InetAddress ad, Packet a) throws IOException {

        for(int i=0;i<100;i++)
            data[i]=0;
        convertPacketByte(a);
        super.write(ad, data);
    }
    public Packet recieve(byte[] sourceAd) throws IOException {
        //System.out.println(super.getReceiveBufferSize());

        //System.out.println(sourceAd.toString().getBytes().length);

        byte[] data1 = new byte[2000];
        //System.out.println(PORT+"A   "+InetAddress.getByAddress(sourceAd));
        if(sourceAd == null)
            read(data1);
        else
           read(data1,sourceAd);
        //System.out.println(PORT+"B   "+InetAddress.getByAddress(sourceAd));
       // System.out.println(data1);
        //for(int i=25;i<=29;i++)
          //  System.out.println(data1[i]);
        //System.out.println(arrtoint(25,29,data1));
       // System.out.println(arrtoint(25,29,data1));
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
