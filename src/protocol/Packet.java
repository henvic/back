package protocol;
import java.io.Serializable;

public class Packet implements Serializable {
	private int seqNumber;
	private String type;
	private int id;
	private int offset;
	private boolean finalPart;
	private int length;
	private byte[] data;

    public final static int HEADER_SIZE = 47;

	public Packet(int seqNumber, String type, int id, int offset, boolean finalPart, byte[] data, int length){
		this.seqNumber = seqNumber;
		this.type = type;
		this.data = data;
		this.id = id;
		this.offset = offset;
		this.finalPart = finalPart;
		this.length = length;
	}

	public Packet (String port) {

	}

	public boolean isFinalPart() {
		return finalPart;
	}

	public void setFinalPart(boolean finalPart) {
		this.finalPart = finalPart;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String fileType) {
		this.type = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString(){
		String number = ""+this.seqNumber;
		String type = this.type;
		String id = ""+this.id;
		String offset = ""+this.offset;
		String finalPart = "";
		String dataLength = ""+this.length;
		while(number.length() != 8){
			number = "0" + number;
		}
		while(type.length() != 8){
			type += ".";
		}
		while(id.length() != 8){
			id = "0" + id;
		}
		while(offset.length() != 8){
			offset = "0" + offset;
		}
		while(dataLength.length() != 8){
			dataLength = "0" + dataLength;
		}
		if(this.finalPart) {
			finalPart += "0";
		} else {
			finalPart += "1";
		}
		
		return number + "\n" + type + "\n" + id + "\n" + offset + "\n" + finalPart + "\n" + dataLength + "\n" + new String(data);
	}

	public int getDataLength() {
		return length;
	}

	public void setDataLength(int dataLength) {
		this.length = dataLength;
	}

	public byte[] getBytes() {
		byte[] aux = this.toString().substring(0, HEADER_SIZE).getBytes();
		
		byte[] retorno = new byte[aux.length + this.data.length];
		for(int i = 0; i < aux.length; i++) retorno[i] = aux[i];
		for(int i = 0; i < this.data.length; i++) retorno[i+aux.length] = this.data[i];
		return retorno;
	}
}
