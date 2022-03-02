import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class Router extends Node {
	static final int ROUTER_PORT = 1000; // Port of the client
	static final int ENDUSER1_PORT = 1008; // Port of the server
	static final int ENDUSER2_PORT = 1007; // Port of the server

	static final int CONTROLLER_PORT = 1009; // Port of the client

	static final String DEFAULT_DST_NODE = "localhost";	// Name of the host for the server

	static final int HEADER_LENGTH = 4; // Fixed length of the header
	static final int TYPE_POS = 0; // Position of the type within the header
	static final int LENGTH_POS = 1;
	static final int INDEX = 2;
	static final int DST_PORT = 3;
	static final byte TYPE_CONT = 5; // Indicating a string payload

	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_INFO = 1; // Indicating a string payload
	static final byte TYPE_ACK = 2;   // Indicating an acknowledgement
	static final byte TYPE_HELLO = 3;   // Indicating an acknowledgement
	static final byte TYPE_ENDUSER = 4; // Indicating a string payload



	
	static final int ACKCODE_POS = 1; // Position of the acknowledgement type in the header
	static final byte ACK_ALLOK = 10; // Inidcating that everything is ok

	Terminal terminal;
	InetSocketAddress dstAddress;
	String message = null;
	boolean forward = true;
	int in;
	int out;
	int [][] flowTable = {
			{ENDUSER1_PORT, ENDUSER2_PORT},
			{in, out}
		};//if (!dst) in = out; out = in;

	Router(Terminal terminal, String dstHost, int dstPort, int srcPort) {
		try {
			this.flowTable[1][0]= -1;
			this.flowTable[1][1]= -1;
			this.in = -1;
			this.out = -1;
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
		DatagramPacket response;

		data = packet.getData();
		switch(data[TYPE_POS]) {
			case TYPE_ACK:
				terminal.println("Received ack");
				this.notify();
				break;
			case TYPE_CONT:
				if(message!=null) {
					buffer = message.getBytes();
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_CONT;
					data[LENGTH_POS] = (byte)buffer.length;
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", this.out);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
	                this.notify();
				}
				else {
					buffer= new byte[data[LENGTH_POS]];
					System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
					content = new String(buffer);
					terminal.println(content);
					buffer = content.getBytes();
					data = new byte[HEADER_LENGTH+buffer.length];
					data[TYPE_POS] = TYPE_CONT;
					data[LENGTH_POS] = (byte)buffer.length;
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", this.out);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
	                this.notify();
				}
				break;
			case TYPE_INFO:
				buffer= new byte[data[LENGTH_POS]];
				System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
				content = new String(buffer);
				int result = Integer.parseInt(content);			
				System.out.println(result);
				terminal.println("Next Hop: "+ result);
				this.out=result;
				
				this.notify();
				break;
			case TYPE_HELLO:
				buffer= new byte[data[LENGTH_POS]];
				System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
				content = new String(buffer);
				terminal.println("Controller: " + content);
				data = new byte[HEADER_LENGTH];
				data[TYPE_POS] = TYPE_ACK;
				data[ACKCODE_POS] = ACK_ALLOK;
				
				response = new DatagramPacket(data, data.length);
				response.setSocketAddress(packet.getSocketAddress());
				socket.send(response);
				break;
				
			case TYPE_ENDUSER:
				buffer= new byte[data[LENGTH_POS]];
				System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
				content = new String(buffer);
				terminal.println("Enduser" + data[INDEX]+ " :" + content);
                message = content;
                terminal.print(message);
				if(data[INDEX]==1) {//coming from enduser1
					if(!forward) {
						int tmp = this.in;
						this.in = this.out;
						this.out = tmp;
						forward = true;
					}
					if(this.in<0||this.out<0) {
						sendMessage(this.flowTable[0][0]);
					}
				}
				if(data[INDEX]==0) {//coming from enduser2
					if(forward) {
						int tmp = this.in;
						this.in = this.out;
						this.out = tmp;
						forward = false;
					}
					if(this.in<0||this.out<0) {
						sendMessage(this.flowTable[0][1]);
					}
				}
				break;
			default:
				terminal.println("Unexpected packet" + packet.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void sendMessage(int index) throws Exception {
		byte[] data= null;
		byte[] buffer= null;
		byte x= (byte)index;
		DatagramPacket packet= null;

		switch(index) {
			case ENDUSER1_PORT:
				String routeRequest = "Requesting routing information";
				buffer = routeRequest.getBytes();
				data = new byte[HEADER_LENGTH+buffer.length];
				data[TYPE_POS] = TYPE_INFO;
				data[LENGTH_POS] = (byte)buffer.length;
				data[INDEX] = 1;

				terminal.println("sending : " + data[INDEX]);
				
				System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
				terminal.println("Sending packet...");
				packet= new DatagramPacket(data, data.length);
				dstAddress= new InetSocketAddress("localhost", CONTROLLER_PORT);
				packet.setSocketAddress(dstAddress);
				socket.send(packet);
				terminal.println("Packet sent");
				break;
			case ENDUSER2_PORT:

			default:
				String startup = "Hello";
				buffer = startup.getBytes();
				data = new byte[HEADER_LENGTH+buffer.length];
				data[TYPE_POS] = TYPE_HELLO;
				data[LENGTH_POS] = (byte)buffer.length;
				data[INDEX] = x;
				terminal.println("sending : " + data[INDEX]);
		
				System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
				terminal.println("Sending packet...");
				packet= new DatagramPacket(data, data.length);
				dstAddress= new InetSocketAddress("localhost", CONTROLLER_PORT);
				packet.setSocketAddress(dstAddress);
				socket.send(packet);
				terminal.println("Packet sent");
				break;
		}

	}
	public synchronized void start() throws Exception {
		terminal.println("Waiting for contact");
		this.wait();
	}
	
	public static void main(String[] args) {
		Router routers[] = new Router[3];
		try{
			for(int x = 0; x<routers.length; x++) {
				routers[x] = new Router(new Terminal("Router - "+(ROUTER_PORT+x)), DEFAULT_DST_NODE, CONTROLLER_PORT, ROUTER_PORT+x);
				routers[x].sendMessage(x);
			}
		}	
		catch(java.lang.Exception e){
			e.printStackTrace();
		}
	}
}
