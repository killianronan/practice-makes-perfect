import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Broker extends Node{
	static final int WORKER_PORT = 50000;
	static final int BROKER_PORT = 49998;
	static final int C_AND_C_PORT = 49999;

	static final int HEADER_LENGTH = 3;
	static final int TYPE_POS = 0;
	static final int LENGTH_POS = 1;
	static final int INDEX = 2;

	static final byte TYPE_UNKNOWN = 0;
	static final byte TYPE_STRING = 1;
	static final byte TYPE_ACK = 2;
	static final byte TYPE_WORK = 3;
	static final byte TYPE_RESULT = 4; // Indicating a string payload

	
	static final int ACKCODE_POS = 1;
	static final byte ACK_ALLOK = 10;
	
	InetSocketAddress dstAddress;
	Terminal terminal;
	
	int []availableWorkers = {0,0,0,0,0,0,0};


	Broker(Terminal terminal, int port){
		try{
			this.terminal= terminal;
			socket= new DatagramSocket(port); //initialize terminal and listen to given port.
			listener.go();
		}
		catch(java.lang.Exception e){
			e.printStackTrace();
		}
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
				case TYPE_STRING:
					buffer= new byte[data[LENGTH_POS]];
					System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
					content= new String(buffer);
					
					terminal.println("Worker - "+ data[INDEX] +": "+content);
					this.availableWorkers[data[INDEX]]=WORKER_PORT+data[INDEX];

					data = new byte[HEADER_LENGTH];
					data[TYPE_POS] = TYPE_ACK;
					data[ACKCODE_POS] = ACK_ALLOK;
					byte x = (byte) 0;
					data[INDEX]=x;
					
					DatagramPacket response;
					response = new DatagramPacket(data, data.length);
					response.setSocketAddress(packet.getSocketAddress());
					socket.send(response);
					response.setSocketAddress(new InetSocketAddress("localhost", 49999));
					socket.send(response);
					break;
				case TYPE_WORK: 
	 				buffer= new byte[data[LENGTH_POS]];
					System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
					content= new String(buffer);
					terminal.println("Work instruction: " + content);
					data = new byte[HEADER_LENGTH];
					data[TYPE_POS] = TYPE_ACK;
					data[ACKCODE_POS] = ACK_ALLOK;
					byte y = (byte) -1;

					data[INDEX]=y;
					DatagramPacket response2;
					response2 = new DatagramPacket(data, data.length);
					response2.setSocketAddress(packet.getSocketAddress());
					socket.send(response2);
					this.sendMessage(content);
					break;
				case TYPE_RESULT:
					buffer= new byte[data[LENGTH_POS]];
					System.arraycopy(data, HEADER_LENGTH, buffer, 0, buffer.length);
					content= new String(buffer);
					if(content.equals("quit")) {
						terminal.println("Worker - "+ data[INDEX] +"withdrawn from work");
						this.notify();
					}
					else {
						terminal.println("Worker - "+ data[INDEX] +" results: "+content);
						this.availableWorkers[data[INDEX]]=WORKER_PORT+data[INDEX];
	
						data = new byte[HEADER_LENGTH];
						data[TYPE_POS] = TYPE_ACK;
						data[ACKCODE_POS] = ACK_ALLOK;
						data[INDEX] = 0;
						
						DatagramPacket resultsResponse;
						resultsResponse = new DatagramPacket(data, data.length);
						resultsResponse.setSocketAddress(packet.getSocketAddress());
						socket.send(resultsResponse);
						resultsResponse.setSocketAddress(new InetSocketAddress("localhost", 49999));
						socket.send(resultsResponse);
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
	public synchronized void sendMessage(String content) throws Exception {
		byte[] data= null;
		byte[] buffer= null;
		DatagramPacket packet= null;
		try { 
			buffer = content.getBytes();
			data = new byte[HEADER_LENGTH+buffer.length];
			data[TYPE_POS] = TYPE_WORK;
			data[LENGTH_POS] = (byte)buffer.length;
			
			for(int x = 0; x<this.availableWorkers.length; x++)
			{
				if(this.availableWorkers[x]!=0) {	
					byte index2 = (byte) x;
					data[INDEX]= index2;
					System.arraycopy(buffer, 0, data, HEADER_LENGTH, buffer.length);
					terminal.println("Sending packet...");
					packet= new DatagramPacket(data, data.length);
					dstAddress= new InetSocketAddress("localhost", this.availableWorkers[x]);
					packet.setSocketAddress(dstAddress);
					socket.send(packet);
					terminal.println("Packet sent to "+ (WORKER_PORT+x));
					this.availableWorkers[x]=0;
				}
			}
			terminal.println("Finished sending work");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void start() throws Exception {
		terminal.println("Waiting for contact");
		this.wait();
	}

	public static void main(String[] args) {
		Terminal terminal= new Terminal("Broker");

		Broker broker = new Broker(terminal, BROKER_PORT);
		try{
			while(true) {
				broker.start();
			}
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}
