import java.net.*;
import java.io.*;
import java.util.*;

public class Client{
	private static int portNumber;
	private static String hostName;

	public static void main(String[] args) {
		hostName = args[0];
		portNumber = Integer.parseInt(args[1]);
		initialize();
	}

	private static void initialize() {
		try {
		    Socket client = new Socket(hostName, portNumber);
		    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		    Scanner reader = new Scanner(System.in);
		    System.out.println("say hello: ");
			String userInput = reader.nextLine();
			out.println(userInput);
		    String fromServer;
		    while ((fromServer = in.readLine()) != null) {
			    System.out.println("Server Says: " + fromServer);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}