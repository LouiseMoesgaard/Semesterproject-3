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
 	System.out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
 	if(args.length > 0 && args[0] != null && args[0].length() > 0){
 		System.out.println("<args1>"+args[0]+"</args1>");
 		System.out.println("<args2>"+args[1]+"</args2>");
 	} else{
 		System.out.println("No Data");
 	}
 }


}