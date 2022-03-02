import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class Controller extends Node {
	static final int CONTROLLER_PORT = 1009; // Port of the client
	static final int ROUTER_PORT = 1000; // First port of the router
	static final int ENDUSER1_PORT = 1008; // Port of enduser1
	static final int ENDUSER2_PORT = 1007; // Port of enduser2

	static final String DEFAULT_DST_NODE = "localhost";	// Name of the host for the server

	static final int HEADER_LENGTH = 4; // Fixed length of the header
	static final int TYPE_POS = 0; // Position of the type within the header
	static final int LENGTH_POS = 1; 
	static final int INDEX = 2;
	static final int DST_PORT = 3;


	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_INFO = 1; // Indicating a string payload
	static final byte TYPE_ACK = 2;   // Indicating an acknowledgement
	static final byte TYPE_HELLO = 3;   // Indicating an acknowledgement
	static final byte TYPE_ENDUSER = 4; // Indicating a string payload
	static final byte TYPE_CONT = 5; // Indicating a string payload



	static final int ACKCODE_POS = 1; // Position of the acknowledgement type in the header
	static final byte ACK_ALLOK = 10; // Inidcating that everything is ok

	Terminal terminal;
	InetSocketAddress dstAddress;
	
	int [] routers = {ROUTER_PORT,ROUTER_PORT+1,ROUTER_PORT+2};
	int []in = {ENDUSER1_PORT,routers[0],routers[1]};
	int []out = {routers[1],routers[2],ENDUSER2_PORT};
	int [][] flowTable = {
			{ENDUSER1_PORT, ENDUSER2_PORT},
			{routers[0],in[0],out[0]},//1=R
			{routers[1],in[1],out[1]},
			{routers[2],in[2],out[2]}
		};
	//if (!dst) in = out; out = in;
	/**
	 * Constructor 
	 *
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	Controller(Terminal terminal, String dstHost, int dstPort, int srcPort) {
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
			int index = (int) data[INDEX];
			switch(data[TYPE_POS]) {
				case TYPE_ACK:
					terminal.println("Received ack");
					this.notify();
					break;
				case TYPE_INFO:
					terminal.println("Received info request");
					terminal.println("direction enduser: "+data[INDEX]);
					String port1 = ""+ out[0];
					String port2 = ""+ out[1];
					String port3 = ""+ out[2];

					terminal.println("E1->E2");
					buffer = port1.getBytes();
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_INFO;
					data[LENGTH_POS] = (byte)buffer.length;
					terminal.println("sending : " + out[0]);

					data[INDEX] = (byte) out[0];
//					byte port = (byte) 1;
//					data[DST_PORT] = port;		
					terminal.println("sending : " + data[INDEX]);
					
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", ROUTER_PORT);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);

					buffer = port2.getBytes();
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_INFO;
					data[LENGTH_POS] = (byte)buffer.length;
					terminal.println("sending : " + out[0]);

					data[INDEX] = (byte) out[1];
	
					terminal.println("sending : " + data[INDEX]);
					
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", ROUTER_PORT+1);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
	
					buffer = port3.getBytes();
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_INFO;
					data[LENGTH_POS] = (byte)buffer.length;
					terminal.println("sending : " + out[0]);

					data[INDEX] = (byte) out[0];
	
					terminal.println("sending : " + buffer);
					
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", ROUTER_PORT+2);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_CONT;
					data[LENGTH_POS] = (byte)buffer.length;
					terminal.println("Continuation beginning ");
					data[INDEX] = (byte) out[0];
					terminal.println("sending : " + data[INDEX]);
					
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", ROUTER_PORT);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
					this.notify();
					break;

				case TYPE_HELLO:
						buffer= new byte[data[LENGTH_POS]];
						System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
						content = new String(buffer);
						terminal.println("Router "+ index + " :" + content);
						
						buffer = content.getBytes();
						data = new byte[HEADER_LENGTH+buffer.length];
						data[TYPE_POS] = TYPE_HELLO;
						data[LENGTH_POS] = (byte)buffer.length;
				
						System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
						terminal.println("Sending response...");
						packet= new DatagramPacket(data, data.length);
						this.dstAddress= new InetSocketAddress("localhost", ROUTER_PORT+index);
						packet.setSocketAddress(dstAddress);
						socket.send(packet);
						terminal.println("Response sent");
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

	public synchronized void start() throws Exception {
		terminal.println("Waiting for contact");
		this.wait();
	}
	
	public void printTable(){
		terminal.println("Routing Table:");
		terminal.println("End User1: "+ this.flowTable[0][0]);
		terminal.println("End User2: "+ this.flowTable[0][1]);
		terminal.println("Router: "+ this.flowTable[1][0] + " in:" + this.flowTable[1][1] + " out:" + this.flowTable[1][2]);
		terminal.println("Router: "+ this.flowTable[2][0] + " in:" + this.flowTable[2][1] + " out:" + this.flowTable[2][2]);
		terminal.println("Router: "+ this.flowTable[3][0] + " in:" + this.flowTable[3][1] + " out:" + this.flowTable[3][2]);
	}
	public static void main(String[] args) {
		Controller controller = new Controller(new Terminal("Controller"), DEFAULT_DST_NODE, ROUTER_PORT, CONTROLLER_PORT);
		controller.printTable();
		try{
			while(true) {
				controller.start();
			}
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}
