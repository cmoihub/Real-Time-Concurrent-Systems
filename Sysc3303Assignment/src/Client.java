//Edited Professor Lynn Marshall's example files
//
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client extends Host{
   private DatagramPacket send, receive;
   private DatagramSocket sendReceive;
   private String filename, mode;
   private byte[] filebytes, modebytes;
   
   public Client() throws SocketException {	sendReceive = new DatagramSocket();	}

   public void sendAndReceive() throws IOException
   {
   	for (int i = 1;i<=11;i++){
   	System.out.println("Cycle: " + i);
   	byte [] format = new byte [100];
   	int index = 0;
   	format[index] = zero;
   	index++;
   	//RRQ - i<10 and is odd 
      //WRQ - i<10 and is even
   	if(i>10)	{
   		format[index] = zero;
      	index++;
   	}
      if(i<=10 && i%2 == 1)	{
      	format[index] = read;
      	index++;
      }
      else	{
      	format[index] = write;
      	index++;
      }
      
      //copy filename into array
      filename = "filename.txt";
      filebytes = filename.getBytes();
   	System.arraycopy(filebytes, 0, format, index, filebytes.length);
   	index+=filebytes.length;
   	
   	//add middle zero byte
   	format[index] = zero;
   	index++;
   	
   	//copy mode into array
   	mode = "octet";
   	modebytes = mode.getBytes();
   	System.arraycopy(modebytes, 0, format, index, modebytes.length);
   	index+=modebytes.length;
   	//index++;
   	
   	//   	add last zero
   	format[index] = zero;
   	index++;	
   	format = Arrays.copyOfRange(format, 0, index);
   	System.out.println("Client: sending a packet containing " + filename);
      System.out.println("Byte format: " + format + "\n");
      
      send = new DatagramPacket(format, 
      		format.length,InetAddress.getLocalHost(), intermediatePort);

      printData(send, "Intermediate","Client");
      sendReceive.send(send);
      System.out.println("Client: Packet sent.\n");

      //if(i==11)	continue;
      
      byte data[] = new byte[100];
      receive = new DatagramPacket(data, data.length);

      try {
         // Block until a datagram is received via sendReceiveSocket.  
         sendReceive.receive(receive);
      } catch(IOException e) {
         e.printStackTrace();
         System.exit(1);
      }

      // Process the received datagram.
      printData(receive, "Intermediate","Client");
      if(i==10) {
      	System.out.println("Program ended");
      	System.exit(1);
      }
      }

      // We're finished, so close the socket.
      sendReceive.close();
   }

   public static void main(String args[]) throws IOException
   {
      Client c = new Client();
      c.sendAndReceive();
   }
}