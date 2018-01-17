import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class Protocol{
	static Connection conn;

	private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

	public String validate(String input){
		String cmd = "";
		String uuid = "";
		String pass = "";
		String id = "";
		String response = "";

		try{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(input));
        Element root = doc.getRootElement();

        cmd = root.getChild("cmd").getText();
        uuid = root.getChild("prm").getText();
        pass = root.getChild("pass").getText();
        id = root.getChild("id").getText();

        if(!authenticate(id, pass)){
        	return noAuth(id, uuid);
        }
        if(cmd.equals("01")){
			switch(id) {
				case "0001": pass = "!$!A";
							 break;
				case "0002": pass = "mj$@";
							 break;
				case "0003": pass = "oNJp";
							 break;
				case "0004": pass = "%$yF";
							 break;
				case "0005": pass = "$xw%";
							 break;
				case "0006": pass = "rBvA";
							 break;
				case "0008": pass = "ekhq";
							 break;
				default: pass = "";
						 break;
			}
			String ntc = getAppointments(uuid);
			if(ntc.equals("")){
				response =  "<rpl>"+	
        				"<uuid>"+uuid+"</uuid>"+
        				"<stat>01</stat>"+
        				"<id>0007</id>"+
        				"<total>01</total>"+
        				"<seq>01</seq>"+
        				"<pass>"+pass+"</pass></rpl>";
			} else{
				response =  "<rpl>"+	
        				"<uuid>"+uuid+"</uuid>"+
        				"<stat>00</stat>"+
        				"<id>0007</id>"+
        				"<total>01</total>"+
        				"<seq>01</seq>"+
        				"<pass>"+pass+"</pass>"
        				+getAppointments(uuid)+
        				"</rpl>";
			}
        }

      } catch(Exception e){
        e.printStackTrace();
      }
      return response;
	}

	private boolean authenticate(String id, String pass){
		switch(id) {
			case "0001": return pass.equals("my@r");
			case "0002": return pass.equals("B@wN");
			case "0003": return pass.equals("V$E$");
			case "0004": return pass.equals("!ttT");
			case "0005": return pass.equals("hr$j");
			case "0006": return pass.equals("!BfB");
			case "0008": return pass.equals("dRG@");
			default: return false;
		}
	}

	private String noAuth(String id, String uuid){
		String pass;
		switch(id) {
				case "0001": pass = "!$!A";
							 break;
				case "0002": pass = "mj$@";
							 break;
				case "0003": pass = "oNJp";
							 break;
				case "0004": pass = "%$yF";
							 break;
				case "0005": pass = "$xw%";
							 break;
				case "0006": pass = "rBvA";
							 break;
				case "0008": pass = "ekhq";
							 break;
				default: pass = "";
						 break;
			}
        	return "<rpl>"+	
        				"<uuid>"+uuid+"</uuid>"+
        				"<stat>02</stat>"+
        				"<id>0007</id>"+
        				"<total>01</total>"+
        				"<seq>01</seq>"+
        				"<pass>"+pass+"</pass>"+
        			"</rpl>";
	}

	private String getAppointments(String uuid) {
		String ntc = "";
		try{
			makeConnection();
			Statement stmt = conn.createStatement();
      		ResultSet r = stmt.executeQuery("SELECT cpr FROM cprRegister WHERE uuid = '"+uuid+"'");
      		long cpr = 0;
      		while (r.next()) {
				cpr = r.getLong("cpr");
			}
			stmt = conn.createStatement();
      		r = stmt.executeQuery("SELECT * FROM Appointment WHERE cpr ="+cpr);
      		while (r.next()) {
      			ntc += generateAppointmentXML(r);
            }
      	} catch(Exception e){
			e.printStackTrace();
		}
		return ntc;
	}

	private String generateAppointmentXML(ResultSet row){
		String ntc = "<ntc>";
		try{
			String[] date_time = row.getString("date_time").split(" ");
			ntc+= "<date>"+date_time[0]+"</date>";
			ntc+= "<time>"+date_time[1].substring(0, date_time[1].lastIndexOf(":"))+"</time>";

			int hospital_id = row.getInt("Hospital_id");
			Statement stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("SELECT * FROM Hospital WHERE id ="+hospital_id);
	        String title = "";
	        while (result.next()) {
				title = result.getString("name")+" ";
				title += date_time[0]+" "+date_time[1];
			}
			ntc+= "<titl>"+title+"<titl>";
			ntc+= "<url>su7.eduhost.dk/appointment_citiz.html<url>";
		} catch(Exception e){
			e.printStackTrace();
		}
		ntc+="</ntc>";
		return ntc;
	}
}