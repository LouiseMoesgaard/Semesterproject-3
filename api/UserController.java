import models.*;
import java.util.*;

public class UserController {

 private static String getUser(String username, String password){
 	User user = User.getUser(username, password);
 	String xml = user.getAsXML();
 	return xml;
 }

 public static void main(String[] args) {
 	System.out.println("Content-Type: text/xml\n\r");
 	if(args.length > 0 && args[0] != null && args[0].length() > 0){
 		String username = args[0].split("&")[0].split("=")[1];
 		String password = args[0].split("&")[1].split("=")[1];
 		System.out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
 		System.out.println(getUser(username, password));	
 	} else{
 		System.out.println("No Data");
 	}
 }


}