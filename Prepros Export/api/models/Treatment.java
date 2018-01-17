package models;

import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import static java.nio.charset.StandardCharsets.*;

public class Treatment{
	private int id;
	private String diagnosis;
  private String description;
  private String treatment_type;
  private String updated;
  private int appointment_id;

    static Connection conn;
      
    private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?useUnicode=true&characterEncoding=utf-8&user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

    public static ArrayList<Treatment> getAllByCPR(int cpr){

        ArrayList<Treatment> data = new ArrayList<Treatment>();
        try{
          makeConnection();
          Statement stmt = conn.createStatement();
          ResultSet r = stmt.executeQuery("SELECT Treatment.* FROM Treatment WHERE cpr ="+cpr);

           while (r.next()) {
                  Treatment newTreatment = new Treatment();

                  newTreatment.id = r.getInt("id");
                  newTreatment.diagnosis = r.getString("diagnosis");
                  newTreatment.description = r.getString("description");
                  newTreatment.treatment_type = r.getString("treatment_type");
                  newTreatment.updated = r.getString("updated");
                  newTreatment.appointment_id = r.getInt("Appointment_id");
                  data.add(newTreatment);
          }
        } catch (Exception e) { 
            e.printStackTrace();
        }
      return data;
    }

    public static Treatment objectFromXML(String xml){
      Treatment treatment = new Treatment();
      try{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xml));
        Element root = doc.getRootElement();

        treatment.appointment_id = Integer.parseInt(root.getChild("Appointment_id").getText());
        treatment.diagnosis = root.getChild("diagnosis").getText();
        treatment.description = root.getChild("description").getText();
        treatment.treatment_type = root.getChild("treatment_type").getText();
      } catch(Exception e){
        e.printStackTrace();
      }
      return treatment;
    }

    public Treatment save(){
      try{
        Timestamp tstp = new Timestamp(System.currentTimeMillis());
        updated = tstp.toString();
        makeConnection();
        Statement stmt = conn.createStatement();
        int r = stmt.executeUpdate("INSERT INTO Treatment (diagnosis, description, treatment_type, updated, Appointment_id) VALUES"+
                                  " ("+diagnosis+",'"+description+"', '"+treatment_type+"', '"+updated+"', "+appointment_id+")");
      } catch(Exception e){
        e.printStackTrace();
      }

      return this;

    }

    public String getAsXML(String option){
      String obj = "<Treatment id='"+id+"'>";
      obj += "<id>"+id+"</id>"; 
      obj += "<diagnosis>"+diagnosis+"</diagnosis>";
      obj += "<description>"+description+"</description>";
      obj += "<treatment_type>"+treatment_type+"</treatment_type>";
      obj += "<updated>"+updated+"</updated>";
      obj += "<Appointment_id>"+appointment_id+"</Appointment_id>";
      if(option == "appointment"){
        Appointment app = Appointment.getById(appointment_id);
        obj += app.getAsXML("hospital");
      }
      obj += "</Treatment>";

        return obj;       
    }


}
