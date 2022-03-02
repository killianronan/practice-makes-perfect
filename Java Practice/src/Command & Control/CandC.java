import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class CandC extends Node {
	static final int C_AND_C_PORT = 49999; // Port of the client
	static final int BROKER_PORT = 49998; // Port of the server
	static final String DEFAULT_DST_NODE = "localhost";	// Name of the host for the server

	static final int HEADER_LENGTH = 3; // Fixed length of the header
	static final int TYPE_POS = 0; // Position of the type within the header
	static final int LENGTH_POS = 1; 
	static final int INDEX = 2;


	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_STRING = 1; // Indicating a string payload
	static final byte TYPE_ACK = 2;   // Indicating an acknowledgement
	static final byte TYPE_WRK = 3;   // Indicating an acknowledgement
	static final byte TYPE_RESULT = 4; // Indicating a string payload


	static final int ACKCODE_POS = 1; // Position of the acknowledgement type in the header
	static final byte ACK_ALLOK = 10; // Inidcating that everything is ok

	Terminal terminal;
	InetSocketAddress dstAddress;

	/**
	 * Constructor
	 *
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	CandC(Terminal terminal, String dstHost, int dstPort, int srcPort) {
		try {
			this.terminal= terminal;
			dstAddress= new InetSocketAddress(dstHost, dstPort);
			socket= new DatagramSocket(srcPort);
			listener.go();
		}
		catch(java.lang.Exception e){
			e.printStackTrace();
		}
	}

	public synchronized void onReceipt(DatagramPacket packet) {
		byte[] data;
		try {
			data = packet.getData();
			switch(data[TYPE_POS]) {
				case TYPE_ACK:
					byte x = (byte) -1;
					if(data[INDEX]!=x) {
						this.sendMessage();
					}
					else {
						terminal.println("Work instruction sent");
					}	
					this.notify();
					break;
				default:
					terminal.println("Unexpected packet" + packet.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void sendMessage() throws Exception {
		byte[] data= null;
		byte[] buffer= null;
		DatagramPacket packet= null;
		String input;

		input= terminal.read("Work instructions: ");
		buffer = input.getBytes();
		data = new byte[HEADER_LENGTH+buffer.length];
		data[TYPE_POS] = TYPE_WRK;
		data[INDEX] = 0;

		data[LENGTH_POS] = (byte)buffer.length;
		System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);

		terminal.println("Sending packet...");
		packet= new DatagramPacket(data, data.length);
		packet.setSocketAddress(dstAddress);
		socket.send(packet);
		terminal.println("Packet sent");
	}

	
	public static void main(String[] args) {
		Terminal terminal= new Terminal("C & C");
		new CandC(terminal, DEFAULT_DST_NODE, BROKER_PORT, C_AND_C_PORT);
	}
}
