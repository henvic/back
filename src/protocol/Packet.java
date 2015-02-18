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

    public final static int HEADER_SIZE = 47;

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
		String number = ""+this.fileNumber;
		String type = this.fileType;
		String id = ""+this.id;
		String offset = ""+this.offset;
		String finalPart = "";
		String dataLength = ""+this.dataLength;
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
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public byte[] getBytes() {
		byte[] aux = this.toString().substring(0, HEADER_SIZE).getBytes();
		
		byte[] retorno = new byte[aux.length + this.data.length];
		for(int i = 0; i < aux.length; i++) retorno[i] = aux[i];
		for(int i = 0; i < this.data.length; i++) retorno[i+aux.length] = this.data[i];
		return retorno;
	}
}
