import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Acceptor extends Thread
{
    // instance variables - replace the example below with your own
    public static XY xy = new XY();
    public static Object myxy = new XY();
    private static int bulletCount = 0;
    private DatagramSocket socket;
    /**
     * Constructor for objects of class Client
     */
    public Acceptor() throws Exception
    {
        
        socket = new DatagramSocket(5000);
    }

    public void run()
    {
        

        try {
            byte[] serializedMessage = new byte[1024];
            int i = 30;
            while (i>0) {
                try {
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(Acceptor.myxy);
            oo.close();

            byte[] buf = bStream.toByteArray();
                    if(MyWorld.mw.current instanceof GameOverGameWorldState)
                        System.out.println(i--);
                    System.out.print(" = ");
                    DatagramPacket packet0 = new DatagramPacket(buf, buf.length, InetAddress.getByName("52.53.161.113"), 5001);    
                    socket.send(packet0);                        

                    DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length);
                    socket.setSoTimeout(5000);
                    socket.receive(packet);
                    
                    ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                    Object o = iStream.readObject();
                    iStream.close();
                    
                    if (o instanceof XY){
                            XY data = (XY) o;
                            
                            System.out.println(data.x + " -ai "+ data.health[0] +" ai- " + data.y);
                            Acceptor.xy.x = data.x;
                            Acceptor.xy.y = data.y;
                            Acceptor.xy.id = data.id;
                            int sender = (data.id==0)?1:0;
                            System.out.println("Sender:"+ sender+", "+Acceptor.xy.health[0]+":"+Acceptor.xy.health[1]);
                            Acceptor.xy.health[sender] = data.health[sender];
                            
                        }else if(o instanceof FirePacket && Acceptor.bulletCount!=((FirePacket) o).count){
                                FirePacket firePacket = (FirePacket) o;
                                Acceptor.bulletCount = firePacket.count;
                                Bullets bullet;
                                Bullet2 bullet2;
                                if(firePacket.direction==1){
                                    bullet= new Bullets();
                                    MyWorld.mw.addObject(bullet,firePacket.x,firePacket.y);
                                }
                                else{
                                    bullet2= new Bullet2();
                                MyWorld.mw.addObject(bullet2,firePacket.x,firePacket.y);
                            }
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    
    }
}
