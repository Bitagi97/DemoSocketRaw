
public class Packet {
    public int SourcePort;
    public int DestinationPort;
    public String Checksum;
    public int Length;
    public boolean isACK;
    public String message;
    public int base = 1000000007;
    public Packet(){}
    public Packet(int s, int p, int Lenth,boolean isack){
        SourcePort = s;
        DestinationPort = p;
        Length = Lenth;
        isACK = isack;
    }
    public Packet(int s, int p, int Lenth,boolean isack,String msg){
        SourcePort = s;
        DestinationPort = p;
        Length = Lenth;
        isACK = isack;
        message = msg;
    }
    public void setChecksum(){
        int check = (((SourcePort % base + DestinationPort%base)%base + Length%base)%base +(isACK ? 1 : 0)) %base;
        for(int i=0;i<Length;i++)
            check = (check+(int)message.charAt(i))%base;
        Checksum = Integer.toString(check);
    }
    public String toString(){
        //String s = new String();
        return "Source port: " + SourcePort+"\nDestinationPort: "+DestinationPort+"\nChecksum: "+Checksum+"\nLength: "+Length+"\nisACK: "+isACK+"\nMessage: "+message;
    }

}
