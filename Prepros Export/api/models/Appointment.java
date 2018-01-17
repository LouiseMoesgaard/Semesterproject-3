package models;

import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class Appointment{
	private int id;
	private int cpr;
  private String date_time;
  private int hospital_id;

    static Connection conn;
      
    private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

    public static ArrayList<Appointment> getAllByCPR(int cpr){

        ArrayList<Appointment> data = new ArrayList<Appointment>();
        try{
          makeConnection();
          Statement stmt = conn.createStatement();
          ResultSet r = stmt.executeQuery("SELECT * FROM Appointment WHERE cpr ="+cpr);

           while (r.next()) {
                  Appointment newAppointment = new Appointment();

                  newAppointment.id = r.getInt("id");
                  newAppointment.cpr = r.getInt("cpr");
                  newAppointment.date_time = r.getString("date_time");
                  newAppointment.hospital_id = r.getInt("Hospital_id");
                  data.add(newAppointment);
          }
        } catch (Exception e) { 
            e.printStackTrace();
        }
      return data;
    }

    public static Appointment getById(int id){
      Appointment data = new Appointment();
      try{
        makeConnection();
        Statement stmt = conn.createStatement();
        ResultSet r = stmt.executeQuery("SELECT * FROM Appointment WHERE id ="+id);
        while (r.next()){
          data.id = r.getInt("id");
          data.cpr = r.getInt("cpr");
          data.date_time = r.getString("date_time");
          data.hospital_id = r.getInt("Hospital_id");
        }
      } catch(Exception e){
        e.printStackTrace();
      }
      return data;
    }

    public static Appointment objectFromXML(String xml){
      Appointment appointment = new Appointment();
      try{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xml));
        Element root = doc.getRootElement();

        appointment.cpr = Integer.parseInt(root.getChild("cpr").getText());
        appointment.date_time = root.getChild("date_time").getText();
        appointment.hospital_id = Integer.parseInt(root.getChild("Hospital_id").getText());
      } catch(Exception e){
        e.printStackTrace();
      }
      return appointment;
    }

    public Appointment save(){
      try{
        makeConnection();
        Statement stmt = conn.createStatement();
        int r = stmt.executeUpdate("INSERT INTO Appointment (cpr, date_time, Hospital_id) VALUES"+
                                  " ("+cpr+",'"+date_time+"',"+hospital_id+")");
        System.out.println("<Status>200 OK</Status>");
      } catch(Exception e){
        e.printStackTrace();
      }

      return this;

    }

    public String getAsXML(String option){
      String obj = "<Appointment id='"+id+"'>";
      obj += "<id>"+id+"</id>"; 
      obj += "<cpr>"+cpr+"</cpr>";
      obj += "<date_time>"+date_time+"</date_time>";
      obj += "<Hospital_id>"+hospital_id+"</Hospital_id>";
      if(option == "hospital"){
        Hospital hospital = Hospital.getByAppointmentId(id);
        obj += hospital.getAsXML();
      }
      obj += "</Appointment>";

        return obj;       
    }


}
