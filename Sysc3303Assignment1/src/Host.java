import java.net.DatagramPacket;
import java.util.Arrays;

public abstract class Host {
	protected final static int intermediatePort = 68;
	protected final static byte zero = 0;
	protected final static byte read = 1;
	protected final static byte write = 2;
	public void bytePrinter(DatagramPacket packet)
	{
		//bytes = receivePacketClient.getData();
      byte[] bytes = Arrays.copyOfRange(packet.getData(), 
      		0, packet.getLength());
      System.out.println("Byte format: " + Arrays.toString(bytes) + "\n");
	}

	public void stringPrinter(DatagramPacket packet)
	{
		int len = packet.getLength();
		System.out.println("Length: " + len);
		System.out.print("Containing: ");
		System.out.println(new String(packet.getData(),0,len) + "\n");
	}
	
	public void printData(DatagramPacket packet, String dest, 
			String src)
	{
     System.out.println(src + " Host: Sending packet:");
     System.out.println("To " + dest + packet.getAddress());
     System.out.println(dest + " port: " + packet.getPort() + "\n");
     stringPrinter(packet);
     bytePrinter(packet);
	}
}