import java.net.*;
import java.io.*;

public class NameClient {
    final static int MAX_LEN = 128;

    public static void main(String args[]) {
        if (args.length != 3)
            System.out.println("This program requires 3 command line arguments");
        else {
            try {
                // parse arguments
                InetAddress serverName = InetAddress.getByName(args[0]);
                int myPort = Integer.parseInt(args[1]);
                int serverPort = Integer.parseInt(args[2]);

                String message;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                DatagramSocket dataSocket = new DatagramSocket(myPort);

                while (true) {
                    // get message from standard in
                    System.out.print("Enter a name: ");
                    message = br.readLine();

                    // send message
                    byte[] buffer = message.getBytes();
                    DatagramPacket datagram = new DatagramPacket(buffer, buffer.length,
                            serverName, serverPort);
                    dataSocket.send(datagram);

                    // break if "done" is entered
                    if (message.toLowerCase().contains("done"))
                        break;

                    // wait for response
                    buffer = new byte[MAX_LEN];
                    datagram = new DatagramPacket(buffer, MAX_LEN);
                    dataSocket.receive(datagram);
                    message = new String(datagram.getData(), datagram.getOffset(), datagram.getLength());
                    System.out.println("List received\n-------------\n" + message);
                }

                br.close();
                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } // end try/catch
        } // end else
    } // end main
}