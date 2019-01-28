import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import gui.SelectForm;

public class Client {
	private static final String IP = "localhost";
	//Using Port
	private static final int PORT = 5000;
	public static void main(String[] args)
			throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		// TODO Auto-generated method stub


		Socket socket = new Socket(IP, PORT);
		SelectForm frame =new SelectForm(socket);
		frame.setVisible(true);
		
	}

}
