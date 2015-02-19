package protocol;

import java.nio.ByteBuffer;

/**
 * Reference https://github.com/haochending/Go-Back-N/
 */

public class GBNPacket {
    private int type;
    private int sequenceNumber;
    private String data;

    public static int ACK_PACKET = 0;
    public static int DATA_PACKET = 1;
    public static int END_OF_TRANSMISSION_PACKET = 2;

    public static final int SEQUENCE_MOD = 32;
    public static final int PACKET_LENGTH = 512;

    public GBNPacket(int type, int sequenceNumber, String strData) {
        this.type = type;
        this.sequenceNumber = sequenceNumber % SEQUENCE_MOD;
        data = strData;
    }

    public static GBNPacket createEndOfTransmission(int SeqNum) {
        return new GBNPacket(GBNPacket.END_OF_TRANSMISSION_PACKET, SeqNum, new String());
    }

    public int getType() {
        return type;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public int getLength() {
        return data.length();
    }

    public byte[] getData() {
        return data.getBytes();
    }

    public byte[] getUDPData() {
        ByteBuffer buffer = ByteBuffer.allocate(PACKET_LENGTH);
        buffer.putInt(type);
        buffer.putInt(sequenceNumber);
        buffer.putInt(data.length());
        buffer.put(data.getBytes(), 0, data.length());

        return buffer.array();
    }

    public static GBNPacket parseUDPData(byte[] udpData) {
        ByteBuffer buffer = ByteBuffer.wrap(udpData);
        int type = buffer.getInt();
        int sequenceNumber = buffer.getInt();
        int length = buffer.getInt();
        byte data[] = new byte[length];

        buffer.get(data, 0, length);

        return new GBNPacket(type, sequenceNumber, new String(data));
    }
}
