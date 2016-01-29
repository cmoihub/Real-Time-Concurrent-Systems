//Edited Professor Lynn Marshall's example files
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
   		
   	//Keep on checking how many times the client program has run
   	System.out.println("Cycle: " + i);
   	byte [] format = new byte [100];
   	int index = 0;
   	format[index] = zero;
   	index++;
   	
   	//RRQ - i<10 and is odd 
    //WRQ - i<10 and is even
   	if(i>10)	{//Invalid request
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
   	
   	//add last zero
   	format[index] = zero;
   	index++;	
   	format = Arrays.copyOfRange(format, 0, index);
   	System.out.println("Client: sending a packet containing " + filename);
    System.out.println("Byte format: " + format + "\n");

    //create packet to be sent 
    send = new DatagramPacket(format,
    		format.length,InetAddress.getLocalHost(), intermediatePort);

    printData(send, "Intermediate","Client");

    //send packet to intermediate host
    sendReceive.send(send);
    System.out.println("Client: Packet sent.\n");

    byte data[] = new byte[100];
    //packet used to receive information from intermediate host
    receive = new DatagramPacket(data, data.length);
    System.out.println("Client: Waiting for Packet.\n");
    
    //receive packet from Intermediate Host
    sendReceive.receive(receive);
         
    // print out the received datagram.
    printData(receive, "Intermediate","Client");
    
    //Cycle has ended
    if(i==10) {
      	System.out.println("Program ended");
      	System.exit(1);
    	}
    }//Close socket so as to end cycle
   	sendReceive.close();
   }

   public static void main(String args[]) throws IOException
   {
      Client c = new Client();
      c.sendAndReceive();
   }
}