import models.*;
import java.util.*;

public class TreatmentController {

	private static String getTreatments(int cpr){
 	ArrayList<Treatment> trt = Treatment.getAllByCPR(cpr);
 	String all = "<Treatments>";
 	for(Iterator<Treatment> i = trt.iterator(); i.hasNext();){
 		try{
	 		Treatment treatment = i.next();
	 		all += treatment.getAsXML("appointment");
 		} catch(Exception e){

 		}
 	}
 	all += "</Treatments>";
 	return all;
 }

 public static void main(String[] args) {

 	System.out.println("Content-Type: text/xml\n\r");
 	if(args.length > 0 && args[0] != null && args[0].length() > 0){
 		int cpr = Integer.parseInt(args[0].split("=")[1]);
 		System.out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
 		System.out.println(getTreatments(cpr));	
 	} else{
 		System.out.println("No Data");
 	}
 }


}