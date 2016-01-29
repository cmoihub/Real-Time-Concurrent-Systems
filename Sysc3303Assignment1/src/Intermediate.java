import java.io.IOException;
import java.net.*;

public class Intermediate extends Host {
	private DatagramPacket sendPacketClient, receivePacketClient,
		sendPacketServer, receivePacketServer;
	private DatagramSocket receive, sendReceive, send;
	private final static int serverPort = 69;
	public Intermediate() throws SocketException {
			receive = new DatagramSocket(intermediatePort);
			sendReceive = new DatagramSocket();
		}

	public void intermediate() throws IOException, InterruptedException
	{
		while(true){
			//waiting for client
			byte data[] = new byte[100];
			receivePacketClient = new DatagramPacket(data, data.length);
			System.out.println("Intermediate host: Waiting for Packet.\n");

      try {
         System.out.println("Waiting..."); // so we know we're waiting
         receive.receive(receivePacketClient);
      } catch (IOException e) {
         System.out.print("IO Exception: likely:");
         System.out.println("Receive Socket Timed Out.\n" + e);
         e.printStackTrace();
         System.exit(1);
      }
      
      //print request received from client
      printData(receivePacketClient,"Client","Intermediate");
      int clientPort = receivePacketClient.getPort();
      InetAddress address = receivePacketClient.getAddress();
     //delay program for 5 seconds so as to get result 
      Thread.sleep(5000);

     //Create packet with information received from client
     sendPacketServer = new DatagramPacket(data, receivePacketClient.getLength(),
                              address,serverPort);
     
     //1st print out
     printData(sendPacketServer,"Server","Intermediate");
     
     //send out packet on send/receive socket to port 69
     sendReceive.send(sendPacketServer);
     
     //waiting for server
     data = new byte[100];
     receivePacketServer = new DatagramPacket(data, data.length);
     System.out.println("Intermediate Host: Waiting for Packet.\n");
     
     //receive packet from server
     try {        
        System.out.println("Waiting..."); // so we know we're waiting
        sendReceive.receive(receivePacketServer);
     } catch (IOException e) {
        System.out.print("IO Exception: likely:");
        System.out.println("sendReceive Socket Timed Out.\n" + e);
        e.printStackTrace();
        System.exit(1);
     }
     
     //2nd print out
     printData(receivePacketServer,"Server","Intermediate");
     Thread.sleep(5000);
     
   //Create packet with information received from server 
     sendPacketClient = new DatagramPacket(data, receivePacketServer.getLength(),
                              address,clientPort);
     
     //3rd printout
     printData(sendPacketClient,"Client","Intermediate");
     
     send = new DatagramSocket();
     //send server's response to client
     send.send(sendPacketClient);
     
     //print out above information
     System.out.println("Server: packet sent");
     
     //close the appropriate socket
      send.close();
      }
	}
	public static void main( String args[] ) 
			throws IOException, InterruptedException
   {
      Intermediate h = new Intermediate();
      h.intermediate();
   }
}