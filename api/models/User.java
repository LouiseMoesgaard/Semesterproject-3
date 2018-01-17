package models;

import java.sql.*;
import java.util.*;
import java.io.StringReader;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class User{
	private int id;
	private String username;
	private int staff;

	static Connection conn;

	private static void makeConnection() { 
    	try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://192.168.1.123/DTUProject?user=remote&password=#Br0wni3s&useSSL=false");
        } catch (Exception e) { 
          e.printStackTrace();
        }

    }

	public static User getUser(String username, String password){
		User user = new User();
		try{
			makeConnection();
			Statement stmt = conn.createStatement();
			ResultSet r = stmt.executeQuery("SELECT * FROM User WHERE username ='"+username+"' AND password='"+password+"'");
    while (r.next()) {
				user.id = r.getInt("id");
				user.username = r.getString("username");
				user.staff = r.getInt("staff");
			}

        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }
        return user;
	}

	public String getAsXML(){
      String obj = "<User id='"+id+"'>";
      obj += "<id>"+id+"</id>"; 
      obj += "<username>"+username+"</username>";
      obj += "<staff>"+staff+"</staff>";
      obj += "</User>";

      return obj;       
    }
}