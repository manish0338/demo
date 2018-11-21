import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Acceptor extends Thread
{
    // instance variables - replace the example below with your own
    public static XY xy = new XY();

    private DatagramSocket socket;
    /**
     * Constructor for objects of class Client
     */
    public Acceptor() throws Exception
    {
        
		socket = new DatagramSocket(5001);
    }

    public void run()
    {
        

		try {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(bStream);
			XY temp = new XY();
			temp.x = 90;
			temp.y = 90;
			oo.writeObject(temp);
			oo.close();
			byte[] serializedMessage = bStream.toByteArray();
			while (true) {
				try {
					
					DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length);
					socket.receive(packet);
					
					ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
					XY data = (XY) iStream.readObject();
					iStream.close();
					System.out.println(data.x + " -ai " + data.y);
					Acceptor.xy.x = data.x;
					Acceptor.xy.y = data.y;
					Acceptor.xy.health = data.health;
					Acceptor.xy.id = data. id;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	
    }
}
