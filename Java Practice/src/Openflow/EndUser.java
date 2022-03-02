import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class EndUser extends Node {
	static final int ENDUSER1_PORT = 1008; // Port of the client
	static final int ENDUSER2_PORT = 1007; // Port of the client
	static final int ROUTER_PORT = 1000; // Port of the server
	static final String DEFAULT_DST_NODE = "localhost";	// Name of the host for the server

	static final int HEADER_LENGTH = 4; // Fixed length of the header
	static final int TYPE_POS = 0; // Position of the type within the header
	static final int LENGTH_POS = 1; 
	static final int INDEX = 2;


	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_INFO = 1; // Indicating a string payload
	static final byte TYPE_ACK = 2;   // Indicating an acknowledgement
	static final byte TYPE_WRK = 3;   // Indicating an acknowledgement
	static final byte TYPE_ENDUSER = 4; // Indicating a string payload
	static final byte TYPE_CONT = 5; // Indicating a string payload



	static final int ACKCODE_POS = 1; // Position of the acknowledgement type in the header
	static final byte ACK_ALLOK = 10; // Inidcating that everything is ok

	Terminal terminal;
	InetSocketAddress dstAddress;

	/**
	 * Constructor
	 *
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	EndUser(Terminal terminal, String dstHost, int dstPort, int srcPort) {
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
		byte[] buffer;
		String content;

		try {
			data = packet.getData();
			switch(data[TYPE_POS]) {
				case TYPE_ACK:
					byte x = (byte) -1;
					if(data[INDEX]!=x) {
						this.sendMessage(-1);
					}
					else {
						terminal.println("Work instruction sent");
					}	
					this.notify();
					break;
				case TYPE_CONT:
					buffer= new byte[data[LENGTH_POS]];
					System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
					content = new String(buffer);
					terminal.println("Message from E1: " +content);
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

	public synchronized void sendMessage(int portNumber) throws Exception {
		byte[] data= null;
		byte[] buffer= null;
		DatagramPacket packet= null;
		String input;

		input= terminal.read("Send message: ");
		buffer = input.getBytes();
		data = new byte[HEADER_LENGTH+buffer.length];
		data[TYPE_POS] = TYPE_ENDUSER;
		byte port = (byte) portNumber;
		int x = (int) portNumber;
		data[INDEX] = port;
		System.out.print(x);
		data[LENGTH_POS] = (byte)buffer.length;
		
		System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
		terminal.println("Sending packet...");
		packet= new DatagramPacket(data, data.length);
		packet.setSocketAddress(dstAddress);
		socket.send(packet);
		terminal.println("Packet sent");
	}
	public synchronized void start() throws Exception {
		terminal.println("Waiting for contact");
		this.wait();
	}
	
	
	public static void main(String[] args) {
		Terminal terminal= new Terminal("End User 1");
		Terminal terminal2= new Terminal("End User 2");

		EndUser user1 = new EndUser(terminal, DEFAULT_DST_NODE, ROUTER_PORT, ENDUSER1_PORT);
		EndUser user2 = new EndUser(terminal2, DEFAULT_DST_NODE, ROUTER_PORT, ENDUSER2_PORT);
		try {
			user1.sendMessage(1);
			user2.start();
		}
		catch(java.lang.Exception e){
			e.printStackTrace();
		}
	}
}
