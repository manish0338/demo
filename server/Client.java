import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    private static DatagramSocket socket;
    private static InetAddress address;
    private static XY oldXY = new XY();
    static
    {
        try{
           socket = new DatagramSocket();
           address = InetAddress.getByName("52.53.161.113");
        }catch(Exception e){e.printStackTrace();}
    }

    public static void sendData(Object o){
        

        try{
         
         ByteArrayOutputStream bStream = new ByteArrayOutputStream();
         ObjectOutput oo = new ObjectOutputStream(bStream);
         oo.writeObject(o);
         oo.close();

         byte[] buf = bStream.toByteArray();
         DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5000);
         socket.send(packet);
         
         //DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length);
			socket.receive(packet);
			ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
			Object o1 = iStream.readObject();
			iStream.close();
		
			System.out.println(o1);
        
        }catch(Exception e){e.printStackTrace();}

    }
    
    public static void main(String[] args) {
    	sendData(new DummyPacket());
    }
}
