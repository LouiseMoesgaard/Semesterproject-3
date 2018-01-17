import models.*;
import java.util.*;

public class HospitalController {

	private static String getHospitals(){
	 	ArrayList<Hospital> hospitals = Hospital.getAll();
	 	String all = "<Hospitals>";
	 	for(Iterator<Hospital> i = hospitals.iterator(); i.hasNext();){
	 		try{
		 		Hospital hospital = i.next();
		 		all += hospital.getAsXML();
	 		} catch(Exception e){

	 		}
	 	}
	 	all += "</Hospitals>";
	 	return all;
 	}

	public static void main(String[] args) {
		System.out.println("Content-Type: text/xml\n\r");
		System.out.println(getHospitals());	
	}
}