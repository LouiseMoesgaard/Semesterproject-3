package models;

import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class Message{
	private int id;
	private int cpr;
  private String text;
  private String timestamp;
  private int appointment_id;

    static Connection conn;
      
    private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

    public static ArrayList<Message> getAllByAppointmentId(int appointment_id){

        ArrayList<Message> data = new ArrayList<Message>();
        try{
          makeConnection();
          Statement stmt = conn.createStatement();
          ResultSet r = stmt.executeQuery("SELECT * FROM Message WHERE Appointment_id ="+appointment_id);

           while (r.next()) {
                  Message newMessage = new Message();

                  newMessage.id = r.getInt("id");
                  newMessage.cpr = r.getInt("cpr");
                  newMessage.text = r.getString("text");
                  newMessage.timestamp = r.getString("timestamp");
                  newMessage.appointment_id = r.getInt("Appointment_id");
                  data.add(newMessage);
          }
        } catch (Exception e) { 
            e.printStackTrace();
        }
      return data;
    }

    public static Message objectFromXML(String xml){
      Message message = new Message();
      try{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xml));
        Element root = doc.getRootElement();

        message.cpr = Integer.parseInt(root.getChild("cpr").getText());
        message.text = root.getChild("text").getText();
        message.appointment_id = Integer.parseInt(root.getChild("Appointment_id").getText());
      } catch(Exception e){
        e.printStackTrace();
      }
      return message;
    }

    public Message save(){
      try{
        Timestamp tstp = new Timestamp(System.currentTimeMillis());
        timestamp = tstp.toString();
        makeConnection();
        Statement stmt = conn.createStatement();
        int r = stmt.executeUpdate("INSERT INTO Message (cpr, text, timestamp, Appointment_id) VALUES"+
                                  " ("+cpr+",'"+text+"','"+timestamp+"',"+appointment_id+")");
      } catch(Exception e){
        e.printStackTrace();
      }

      return this;

    }

    public String getAsXML(){
      String obj = "<Message id='"+id+"'>";
      obj += "<id>"+id+"</id>"; 
      obj += "<cpr>"+cpr+"</cpr>";
      obj += "<text>"+text+"</text>";
      obj += "<timestamp>"+timestamp+"</timestamp>";
      obj += "<Appointment_id>"+appointment_id+"</Appointment_id>";
      obj += "</Message>";

        return obj;       
    }


}
