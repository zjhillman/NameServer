import java.net.*;

public class NameServer {
    final static int MAX_LEN = 128;

    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println
            ("This program requires 2 command line arguments");
        } else {
            try {
                InetAddress clientName = InetAddress.getByName("localhost");
                int myPort = Integer.parseInt(args[0]);
                int clientPort = Integer.parseInt(args[1]);
                String names = "";

                // establish server
                DatagramSocket dataSocket = new DatagramSocket(myPort);
                System.out.println("Ready for connection.");

                while (true) {
                    byte[ ] buffer = new byte[MAX_LEN];
                    DatagramPacket datagram = new DatagramPacket(buffer, MAX_LEN);

                    // wait for message
                    dataSocket.receive(datagram);
                    String message = new String(buffer);

                    // if name is already in the list, exit
                    if (names.contains(message.trim())) 
                        break;

                    // handle message
                    System.out.println("received: " + message);
                    names += message + "\n";
                    System.out.println("Names\n-----\n" + names);

                    // send response
                    buffer = new byte[MAX_LEN];
                    buffer = names.getBytes("UTF-8");
                    datagram = new DatagramPacket(buffer, buffer.length,
                                                clientName, clientPort);
                    dataSocket.send(datagram);
                }
                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace( );
            }
        }
    }
    
}
