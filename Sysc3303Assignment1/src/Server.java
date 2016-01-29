import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Server extends Host {
   private DatagramPacket sendPacket, receivePacket;
   private DatagramSocket send, receive;

   public Server() throws SocketException {	receive = new DatagramSocket(69);   }

   public void receiveAndEcho() throws Exception
   {
   	while(true){
   		//create packet to receive information
      byte data[] = new byte[100];
      receivePacket = new DatagramPacket(data, data.length);
      //waiting for request
      System.out.println("Server: Waiting for Packet.\n");

      //receive packet from intermediate host
      try {        
         System.out.println("Waiting..."); // so we know we're waiting
         receive.receive(receivePacket);
      } catch (IOException e) {
         System.out.print("IO Exception: likely:");
         System.out.println("Receive Socket Timed Out.\n" + e);
         e.printStackTrace();
         System.exit(1);
      }
      
      //get relevant data
      byte[] rawArray = receivePacket.getData();
      rawArray = Arrays.copyOfRange(rawArray, 0, receivePacket.getLength());
      
      //check if array is of correct format
      if(validate(rawArray) == false)	throw new Exception();
      
      //print info
      printData(receivePacket, "Intermediate","Server");
      //Thread.sleep(5000);
      
      //Valid read request, send back 0301
      if(rawArray[1] == read)
      {
      	byte one = 1;
      	byte three = 3;
      	rawArray = new byte[] {zero,three,zero,one};
      }
      
      //Valid write request,send back 0400
      else if(rawArray[1] == write )
      {
      	byte four = 4;
      	rawArray = new byte[] {zero,four,zero,zero};
      }
      
      //create packet to use to respond to client
      sendPacket = new DatagramPacket(rawArray, rawArray.length,
                               receivePacket.getAddress(), receivePacket.getPort());
      
      //print above information
      printData(sendPacket, "Intermediate","Server");
      
      //create socket to use to send packet to client
      send = new DatagramSocket();
      //send packet to client
      send.send(sendPacket);
      System.out.println("Server: packet sent\n");
      //close socket
      send.close();
      }
   	}
   
   
   // 0 1/2 text 0 mode 0
   // increment counter as it parses through the array of bytes 
   //and verifies if the format is correct
   public boolean validate(byte[] byteArray)
   {
   	byte first = 33;
   	byte last = 126;
   	//As seen at http://www.asciitable.com
   	//int valid = 0; 
   	
   	//Check first character
   	if(byteArray[0] == 0)
   	{
   		//Check if read or write request
   		if (byteArray[1] == read || byteArray[1] == write)
   		{
   			//check filename 
   			if(byteArray[2] >= first && byteArray[2] <= last)
   			{
   				//iterate through filename in search for the zerobyte
   				int i;
   				for (i = 3; i<byteArray.length-2;i++)
   				{
   					if(byteArray[i] == zero)	break;
   					//break out of loop if zero found
   				}	
   				
   				//check mode
   				if(byteArray[i+1]>=first
   								&& byteArray[i+1]<=last)
   						{
   							//check last zero
   							for (int j = i+2;i<=byteArray.length;j++)
   							{
   							if (byteArray[j] == zero) return true;	
   							}
   						}
   					}
   			}
   		}	return false;
   }

   public static void main( String args[] ) 
   		throws Exception
   {
      Server c = new Server();
      c.receiveAndEcho();
   }
}