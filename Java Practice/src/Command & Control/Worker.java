import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class Worker extends Node {
	static final int WORKER_PORT = 50000; // Port of the client
	static final int BROKER_PORT = 49998; // Port of the server
	static final int C_AND_C_PORT = 49999; // Port of the client

	static final String DEFAULT_DST_NODE = "localhost";	// Name of the host for the server

	static final int HEADER_LENGTH = 3; // Fixed length of the header
	static final int TYPE_POS = 0; // Position of the type within the header
	static final int LENGTH_POS = 1;
	static final int INDEX = 2;



	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_STRING = 1; // Indicating a string payload
	static final byte TYPE_ACK = 2;   // Indicating an acknowledgement
	static final byte TYPE_WORK = 3;   // Indicating an acknowledgement
	static final byte TYPE_RESULT = 4; // Indicating a string payload


	
	static final int ACKCODE_POS = 1; // Position of the acknowledgement type in the header
	static final byte ACK_ALLOK = 10; // Inidcating that everything is ok

	Terminal terminal;
	InetSocketAddress dstAddress;

	Worker(Terminal terminal, String dstHost, int dstPort, int srcPort) {
		try {
			this.terminal= terminal;
			dstAddress= new InetSocketAddress(dstHost, dstPort);
			socket= new DatagramSocket(srcPort);
			listener.go();
		}
		catch(java.lang.Exception e) {e.printStackTrace();}
	}

	public synchronized void onReceipt(DatagramPacket packet) {
		try {
		String content;
		byte[] data;
		byte[] buffer;

		data = packet.getData();
		switch(data[TYPE_POS]) {
			case TYPE_ACK:
				terminal.println("Received ack");
				this.notify();
				break;
			case TYPE_WORK:
				byte index = data[INDEX];
				buffer= new byte[data[LENGTH_POS]];
				System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
				content = new String(buffer);
				terminal.println("C & C: " + content);
				data = new byte[HEADER_LENGTH];
				data[TYPE_POS] = TYPE_ACK;
				data[ACKCODE_POS] = ACK_ALLOK;
				
				DatagramPacket response;
				response = new DatagramPacket(data, data.length);
				response.setSocketAddress(packet.getSocketAddress());
				socket.send(response);
				this.sendMessage(index, 1);
				break;
			default:
				terminal.println("Unexpected packet" + packet.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void sendMessage(int index,int type) throws Exception {
		byte[] data= null;
		byte[] buffer= null;
		DatagramPacket packet= null;
		switch(type) {
			case 0:
				byte x = (byte) index;

				String work = "Volunteering for work";
				buffer = work.getBytes();
				data = new byte[HEADER_LENGTH+buffer.length];
				data[TYPE_POS] = TYPE_STRING;
				data[INDEX]=x;
				data[LENGTH_POS] = (byte)buffer.length;
		
				System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
				terminal.println("Sending packet...");
				packet= new DatagramPacket(data, data.length);
				packet.setSocketAddress(dstAddress);
				socket.send(packet);
				terminal.println("Packet sent");
				break;
			case 1:
				byte temp = (byte) index;
				String input;
				input= terminal.read("Results: ");
				buffer = input.getBytes();
				data = new byte[HEADER_LENGTH+buffer.length];
				data[TYPE_POS] = TYPE_RESULT;
				data[INDEX] = temp;
				data[LENGTH_POS] = (byte)buffer.length;
				
				System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
				terminal.println("Sending packet...");
				packet= new DatagramPacket(data, data.length);
				packet.setSocketAddress(dstAddress);
				socket.send(packet);
				if(input.equals("quit")) {
					this.notify();
				}
				terminal.println("Packet sent");
		}
	}
	
	public static void main(String[] args) {

		Worker workers[] = new Worker[7];
		Scanner scanner = new Scanner(System.in);
		
		try{
			for(int x = 0; x<workers.length; x++) {
				System.out.println("Enter worker name: ");
				String name = scanner.next();
				workers[x] = new Worker(new Terminal(name+ "-" +(WORKER_PORT+x)), DEFAULT_DST_NODE, BROKER_PORT, WORKER_PORT+x);
				workers[x].sendMessage(x,0);
			}
		}	
		catch(java.lang.Exception e){
			e.printStackTrace();
		}
		scanner.close();
	}
}
