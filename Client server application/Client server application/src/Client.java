
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client extends JFrame {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = 4567;
	private static String SHUTDOWN = " ";

	static JTextArea _textArea;
	private JTextField _textField;
	private ObjectInputStream _inputStream;
	private ObjectOutputStream _outputStream;
	private int count = 0;// for initial prompt to GUI from client
	
	
	public Client() {
		
		super("Assignment 8");
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_textArea = new JTextArea(30, 60);
		_textArea.setEditable(false);
		add(new JScrollPane(_textArea), BorderLayout.CENTER);
		_textField = new JTextField(30);
		_textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String message = _textField.getText();
				sendMessage(message);
				if(message.equals(SHUTDOWN)){
					exitGUI();
				}
				_textField.setText("");	
			}
		});
		add(_textField, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
		System.out.println("Client started!");
				
		Socket socket = null;
		try {
			System.out.println("Creating and connecting Socket.");

			InetAddress localHost = InetAddress.getLocalHost();
			socket = new Socket(localHost, PORT);
			
			System.out.println("Socket connected, creating streams!");

			_outputStream = new ObjectOutputStream(socket.getOutputStream());
			_outputStream.flush();
			
			_inputStream = new ObjectInputStream(socket.getInputStream());
						
			try {
		
				while (true) {
						if(count == 0)
						{
							displayMessage("Please enter a number and press 'Enter' or enter a space to quit and close app:  ");
							count++;
						}

							String message = _inputStream.readUTF();
					       
							System.out.println("Received message: " + message);// have the numbers also displayed elsewhere besides the GUI.
							displayMessage( message);
						}
				
				
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				// Close streams and sockets regardless of whether or 
				// not an exception occurred.  Make sure they were
				// initialized first, though!
				if (_inputStream != null) {
					_inputStream.close();
				}
				if (_outputStream != null) {
					_outputStream.close();
				}
				if (socket != null) {
					socket.close();
				}
				
				System.out.println("Shutdown successful!");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	private void sendMessage(String message) {
		try {
			_outputStream.writeUTF(message);
			_outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void displayMessage(final String message) {
		
				
				_textArea.append(message + "\n");
			
		
	}
	
	private void exitGUI(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		
		 new Client();
     	
	}	
	
}