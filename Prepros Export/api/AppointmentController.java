import models.*;
import java.util.*;
import java.io.*;

public class AppointmentController {

	private static String getAppointments(int cpr){
	 	ArrayList<Appointment> app = Appointment.getAllByCPR(cpr);
	 	String all = "<Appointments>";
	 	for(Iterator<Appointment> i = app.iterator(); i.hasNext();){
	 		try{
		 		Appointment appointment = i.next();
		 		all += appointment.getAsXML("hospital");
	 		} catch(Exception e){

	 		}
	 	}
	 	all += "</Appointments>";
	 	return all;
 	}
 public static void main(String[] args) {

 	System.out.println("Content-Type: text/xml\n\r");
 	if(args.length > 0 && args[0] != null && args[0].length() > 0){
 		int cpr = Integer.parseInt(args[0].split("=")[1]);
 		System.out.println(getAppointments(cpr));	
 	} else{
 		try{
 			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
 			String data = in.readLine();
 			Appointment appointment = Appointment.objectFromXML(data);
 			appointment.save();
 		} catch(Exception e){
 			System.out.println("POST error: "+e.getStackTrace());
 		}
 	}
 }


}