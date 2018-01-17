package models;

import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class Hospital{
	private int id;
	private String name;
  private String section;
  private String address;

    static Connection conn;
      
    private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

    public static ArrayList<Hospital> getAll(){

        ArrayList<Hospital> data = new ArrayList<Hospital>();
        try{
          makeConnection();
          Statement stmt = conn.createStatement();
          ResultSet r = stmt.executeQuery("SELECT * FROM Hospital");

          while (r.next()) {
            Hospital newHospital = new Hospital();

            newHospital.id = r.getInt("id");
            newHospital.name = r.getString("name");
            newHospital.section = r.getString("section");
            newHospital.address = r.getString("address");
            data.add(newHospital);
          }
        } catch (Exception e) { 
            e.printStackTrace();
        }
      return data;
    }

    public static Hospital getByAppointmentId(int id){
      Hospital newHospital = new Hospital();
      try{
          makeConnection();
          Statement stmt = conn.createStatement();
          ResultSet r = stmt.executeQuery("SELECT Hospital.* FROM Hospital LEFT OUTER JOIN Appointment ON Hospital.id = Appointment.Hospital_id Where Appointment.id="+id);

           while (r.next()) {
                  newHospital.id = r.getInt("id");
                  newHospital.name = r.getString("name");
                  newHospital.section = r.getString("section");
                  newHospital.address = r.getString("address");
          }
        } catch (Exception e) { 
            e.printStackTrace();
        }
      return newHospital;
    }

    public static Hospital objectFromXML(String xml){
      Hospital hospital = new Hospital();
      try{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xml));
        Element root = doc.getRootElement();

        hospital.name = root.getChild("name").getText();
        hospital.section = root.getChild("section").getText();
        hospital.address = root.getChild("address").getText();
      } catch(Exception e){
        e.printStackTrace();
      }
      return hospital;
    }

    public Hospital save(){
      try{
        makeConnection();
        Statement stmt = conn.createStatement();
        int r = stmt.executeUpdate("INSERT INTO Hospital (name, section, address) VALUES"+
                                  " ("+name+",'"+section+"',"+address+")");
      } catch(Exception e){
        e.printStackTrace();
      }

      return this;

    }

    public String getAsXML(){
      String obj = "<Hospital id='"+id+"'>";
      obj += "<id>"+id+"</id>"; 
      obj += "<name>"+name+"</name>";
      obj += "<section>"+section+"</section>";
      obj += "<address>"+address+"</address>";
      obj += "</Hospital>";

        return obj;       
    }


}
