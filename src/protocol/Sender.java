package protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sender {
    public static void main(String[] args) throws Exception {
        InetAddress hostAddress = InetAddress.getByName("172.22.46.112");
        Integer receiverPort = 5000;
        Integer senderPort = 5001;
        File transmitFile = new File("arq.txt");
        FileOutputStream numberSequenceOutput;
        FileOutputStream ack;

        FileInputStream input = new FileInputStream(transmitFile);
        numberSequenceOutput = new FileOutputStream("seqnum.log");
        ack = new FileOutputStream("ack.log");

        DatagramSocket senderSocket = new DatagramSocket(senderPort);

        GBNSender gbnSender = new GBNSender(numberSequenceOutput, ack);

        gbnSender.send(hostAddress, receiverPort, input, senderSocket);

        numberSequenceOutput.close();
        ack.close();
    }
}
