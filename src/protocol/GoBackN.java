package protocol;

public class GoBackN {
    private Packet[] window;
    private int windowSize;
    private int requestNumber;
    private int sequenceNumber;
    private int sequenceBase;
    private int sequenceMax;

    public GoBackN(int windowSize) {
        this.windowSize = windowSize;
        this.window = new Packet[windowSize];
        this.requestNumber = 0;
        this.sequenceBase = 0;
        this.sequenceMax = windowSize - 1;

    }

//    public void receiver(Packet packet) {
//        if (packet.getId() == requestNumber && packet.isValid()) {
//            accept(packet);
//            requestNumber += 1;
//        } else {
//            refuse(packet);
//        }
//
//        request(requestNumber);
//    }
//
//    public void sender(int requestNumber) {
//        if (requestNumber > sequenceBase) {
//            sequenceMax = sequenceMax + (requestNumber - sequenceBase);
//            sequenceBase = requestNumber;
//        }
//
//        if (udp.availabe()) {
//            udp.transmit(window[requestNumber]);
//        }
//    }
}