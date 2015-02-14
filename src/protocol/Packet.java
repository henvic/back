package protocol;
import java.io.Serializable;


public class Packet implements Serializable {
	private String fileName;
	private int fileNumber;
	private String fileType;
	private int id;
	private int offset;
	private boolean finalPart;
	private int dataLength;
	private byte[] data;

	public Packet(int fileNumber, String fileType, int id, byte[] data){
		this.fileName = "backChat-file" + fileNumber;
		this.fileNumber = fileNumber;
		this.fileType = fileType;
		this.data = data;
		this.id = id;
		this.offset = 0;
		this.finalPart = true;
	}

	public Packet(int fileNumber, String fileType, int id, int offset, boolean finalPart, byte[] data){
		this.fileName = "backChat-file" + fileNumber;
		this.fileNumber = fileNumber;
		this.fileType = fileType;
		this.data = data;
		this.id = id;
		this.offset = offset;
		this.finalPart = finalPart;
		this.dataLength = this.data.length;
	}

	public String getfileType() {
		return fileType;
	}

	public void setfileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getdata() {
		return data;
	}

	public void setdata(byte[] data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getoffset() {
		return offset;
	}

	public void setoffset(int offset) {
		this.offset = offset;
	}

	public String toString(){
		return fileNumber + "\n" + fileType + "\n" + id + "\n" + offset + "\n" + finalPart + "\n" + dataLength + "\n" + new String(data);
	}

}
