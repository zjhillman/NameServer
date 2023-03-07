import java.net.*;

public class NameServer {
    final static int MAX_LEN = 128;

    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("This program requires 1 command line argument(s)");
        } else {
            try {
                int myPort = Integer.parseInt(args[0]);
                String names = "";

                // establish server
                DatagramSocket dataSocket = new DatagramSocket(myPort);
                System.out.println("Ready for connection at " + InetAddress.getLocalHost().toString());

                while (true) {
                    byte[] buffer = new byte[MAX_LEN];
                    DatagramPacket datagram = new DatagramPacket(buffer, MAX_LEN);

                    // wait for message
                    dataSocket.receive(datagram);
                    String message = new String(datagram.getData(), datagram.getOffset(), datagram.getLength());

                    // if name is already in the list, exit
                    if (message.contains("done"))
                        break;

                    // handle message
                    System.out.println("received: " + message);
                    names += message + "\n";
                    System.out.println("Names\n-----\n" + names);

                    // send response
                    buffer = new byte[MAX_LEN];
                    buffer = names.getBytes();
                    datagram = new DatagramPacket(buffer, buffer.length,
                            datagram.getAddress(), datagram.getPort());
                    dataSocket.send(datagram);
                }
                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
