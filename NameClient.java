import java.net.*;
import java.io.*;

public class NameClient {
    final static int MAX_LEN = 128;

    public static void main (String args[]) {
        if (args.length != 3)
         System.out.println
            ("This program requires 3 command line arguments");
        else {
            try {
                InetAddress serverName = InetAddress.getByName(args[0]);
                int myPort = Integer.parseInt(args[1]);
                int foreignPort = Integer.parseInt(args[2]);
                String message;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
                // get message from standard in
                System.out.print("Enter a name: ");
                message = br.readLine();

                // send message
                DatagramSocket dataSocket = new DatagramSocket(myPort);
                byte[ ] buffer = message.getBytes();
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, 
                                                            serverName, foreignPort);
                dataSocket.send(datagram);

                // wait for response
                buffer = new byte[MAX_LEN];
                datagram = new DatagramPacket(buffer, MAX_LEN);
                dataSocket.receive(datagram);
                message = new String(buffer);
                System.out.println("Message received: " + message);

                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } // end try/catch
        } // end else
    } // end main
}