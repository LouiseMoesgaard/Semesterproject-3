import java.net.*;
import java.io.*;

public class Server{
	private static int portNumber;

	public static void main(String[] args) {
		portNumber = Integer.parseInt(args[0]);
		serve();

	}

	private static void serve(){
		try{ 
		    ServerSocket serverSocket = new ServerSocket(portNumber);
		    Socket clientSocket = serverSocket.accept();
		    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		    String inputLine, outputLine;
		    Protocol protocol = new Protocol();

		    while ((inputLine = in.readLine()) != null) {
		        out.println(protocol.validate(inputLine));
		       
		    }
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}