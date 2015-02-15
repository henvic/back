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
		this.dataLength = data.length;
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
	
	public Packet(int fileNumber, String fileType, int id, int offset, boolean finalPart, byte[] data, int dataLength){
		this.fileName = "backChat-file" + fileNumber;
		this.fileNumber = fileNumber;
		this.fileType = fileType;
		this.data = data;
		this.id = id;
		this.offset = offset;
		this.finalPart = finalPart;
		this.dataLength = dataLength;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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
		return fileNumber + "\n" + fileType + "\n" + id + "\n" + offset + "\n" + finalPart + "\n" + dataLength + "\n" + new String(data);
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

}
