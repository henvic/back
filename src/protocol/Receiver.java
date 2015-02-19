package protocol;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Reference https://github.com/haochending/Go-Back-N/
 */

public class Receiver {
    public static void main(String args[]) throws IOException {
        InetAddress hostAddress = InetAddress.getByName("127.0.0.1");
        Integer emulatorPort = 5001;
        Integer receiverPort = 5000;
        File writtenFile = new File("seqnum.log");

        if (!writtenFile.exists()) {
            System.out.println("Given file not exist! New file created!");
            writtenFile.createNewFile();
        }

        if (!writtenFile.canWrite()) {
            System.out.println("Given file not writable!");
            System.exit(3);
        }

        GBNReceiver newReceiver = new GBNReceiver(hostAddress, emulatorPort,
            receiverPort, writtenFile);

        newReceiver.start();
    }

}