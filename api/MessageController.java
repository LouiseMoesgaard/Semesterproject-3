import models.*;
import java.util.*;

public class MessageController {

 private static String getMessages(int appointment_id){
 	ArrayList<Message> mes = Message.getAllByAppointmentId(appointment_id);
 	String all = "<Messages>";
 	for(Iterator<Message> i = mes.iterator(); i.hasNext();){
 		try{
	 		Message message = i.next();
	 		all += message.getAsXML();
 		} catch(Exception e){

 		}
 	}
 	all += "</Messages>";
 	return all;
 }

 public static void main(String[] args) {
 	System.out.println("Content-Type: text/xml\n\r");
 	if(args.length > 0 && args[0] != null && args[0].length() > 0){
 		int appointment_id = Integer.parseInt(args[0].split("=")[1]);
 		System.out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
 		System.out.println(getMessages(appointment_id));	
 	} else{
 		System.out.println("No Data");
 	}
 }


}