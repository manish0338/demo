import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ReceiveUDP extends Thread {

	private DatagramSocket socket;

	List<SocketAddress> ipList = new ArrayList<SocketAddress>();

	public ReceiveUDP() throws Exception {
		socket = new DatagramSocket(5001);
	}

	public void run() {

		byte[] serializedMessage = new byte[1024];
		while (true) {
			try {

				DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length);
				socket.receive(packet);
				System.out.print(" = ");
				ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
				Object o = iStream.readObject();
				iStream.close();

				try {

					
					if (!ipList.contains(packet.getSocketAddress()))
						ipList.add(packet.getSocketAddress());

					SocketAddress ip = null;
					
					if (ipList.indexOf(ip) % 2 == 0)
						ip = ipList.get(ipList.indexOf(ip) + 1);
					else
						ip = ipList.get(ipList.indexOf(ip) - 1);

					byte[] newSerializedMessage = null;
					if (o instanceof XY)
						newSerializedMessage = getXY((XY) o, ip);
					else if (o instanceof FirePacket)
						newSerializedMessage = getFirePacket((FirePacket) o);

					packet.setSocketAddress(ip);
					packet.setData(newSerializedMessage);
					System.out.print(ip + " = ");
					System.out.println(packet.getAddress());

					packet.setSocketAddress(ip);

					socket.send(packet);
				} catch (IndexOutOfBoundsException e) {
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private byte[] getXY(XY xy, SocketAddress ip) throws IOException {
		ByteArrayOutputStream bStream1 = new ByteArrayOutputStream();
		ObjectOutput newoo = new ObjectOutputStream(bStream1);
		xy.id = ipList.indexOf(ip);
		System.out.println(xy.x + " - " + xy.y);
		newoo.writeObject(xy);
		newoo.close();
		byte[] newSerializedMessage = bStream1.toByteArray();
		return newSerializedMessage;
	}

	private byte[] getFirePacket(FirePacket firePacket) throws IOException {

		ByteArrayOutputStream bStream1 = new ByteArrayOutputStream();
		ObjectOutput newoo = new ObjectOutputStream(bStream1);
		System.out.println("fire : " + firePacket.count);
		newoo.writeObject(firePacket);
		newoo.close();
		byte[] newSerializedMessage = bStream1.toByteArray();
		return newSerializedMessage;
	}

	public static void main(String args[]) throws Exception {
		Thread t1 = new ReceiveUDP();
		t1.start();
	}
}