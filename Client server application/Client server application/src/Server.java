
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {	
	private static final int PORT = 4567;
	private static LinkedList<Integer> Numbers = new LinkedList<Integer>();
	private static String SHUTDOWN = " ";
	private static StringBuffer primeNumbers = new StringBuffer();
	private static StringBuffer notJustPrimeNumbers = new StringBuffer();
	
	
	
									
	static boolean isPrime(int n) {
		if(n == 1)
    	{
    		return false;
    	}
	    for(int i=2;i<n;i++) {
	    	
	        if(n%i==0 )
	            return false;
	    }
	    return true;
	}
	public static void main(String[] args) {		
		System.out.println("Server started!");
		
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("My IP address is: " + localHost);			
			
			ServerSocket serverSocket = new ServerSocket(PORT);
			boolean serverRunning = true;
			while (serverRunning) {
				
				Socket socket = null;
				ObjectInputStream inputStream = null;
				ObjectOutputStream outputStream = null;
				
				try {
					System.out.println("ServerSocket created, waiting for connection...");
		
					socket = serverSocket.accept();
		
					System.out.println("Socket connected, creating streams!");
					
					outputStream = new ObjectOutputStream(socket.getOutputStream());
					outputStream.flush();
					
					inputStream = new ObjectInputStream(socket.getInputStream());
							
					boolean running = true;
					
					while (running) {
						String message = inputStream.readUTF();
					    if(message.equals("print")){
					    	notJustPrimeNumbers.append("Client sent: " );
					    	primeNumbers.append("Client recieved: ");
					    	
					    	for (int i = 0; i < Numbers.size(); i++){
					    		
					    		notJustPrimeNumbers.append(Numbers.get(i) + ",");//Now running all numbers in my array 
					    		                                
								if (isPrime(Numbers.get(i))== true){// running all primes for output
								
									primeNumbers.append(Numbers.get(i) + ",");
								
								}
							}
					    	
					    	// alternatively i could have used .writeObject and sent the arrays instead,
					    	// however in lab i was advised against this.
					    	
					    	outputStream.writeUTF(notJustPrimeNumbers.toString());// sending all numbers sent as string
					    	outputStream.flush();
					    	
							outputStream.writeUTF(primeNumbers.toString());// sending prime numbers to Client as a String
							outputStream.flush();
							
				
						}
					  
					    else{
					    	
					    
						try{
							if (message.equals(SHUTDOWN)){
								running = false;
								break;
							} 
							
							int clientint = Integer.parseInt(message);
							Numbers.add(clientint);
							outputStream.writeUTF(message);
							outputStream.flush();
							
							
								outputStream.writeUTF("Please enter 'print' to print, or "
										+ " another number and press 'Enter' or enter a space"
										+ " to quit and close app:  ");
								outputStream.flush();
							
							
						} catch (NumberFormatException ex){
							outputStream.writeUTF("Can't be an string");
							outputStream.flush();
						}
					    }
					}					
				} catch (IOException ex) {
					System.out.println("Socket disconnected with: " + ex);
				} finally {
					// Close streams and sockets 
					if (inputStream != null) {
						inputStream.close();
					}
					if (outputStream != null) {
						outputStream.close();
					}
					if (socket != null) {
						socket.close();
					}
		
				}
			}
			serverSocket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}
}
