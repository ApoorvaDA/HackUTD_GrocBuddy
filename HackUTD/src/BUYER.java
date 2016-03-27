import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BUYER implements Runnable{

	String user_id = "";
	String time_range = "";
	String store = "";
	PrintWriter out;
	public BUYER(String uid,String tr,String s,PrintWriter o)
	{
		user_id = uid;
		time_range = tr;
		store = s;
		out = o;
	}
	@Override
	public void run() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("entering it");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/HackUTD","root","");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(time_range);
			ArrayList<String> list_users_req = new ArrayList<>();
			Date newDate = new Date();
			ResultSet rs = null;
			ResultSet rs1 = null;
			Statement stmt = null;
		//	String get_users = "select user_id from list_stores where store="+store+" and '"+time_range+"'<(select time_range from transaction where user_id=list_stores.user_id)";
			String get_users = "select user_id from list_stores where store="+store+" and '"+time_range+"<time_val";
			while(date.compareTo(newDate)<0)
			{				
				stmt = (Statement) conn.prepareStatement(get_users);
				rs = stmt.executeQuery(get_users);
				while(rs.next())
				{
					String u = rs.getString("user_id");
					String getData = "select item_list,max_price from transaction where user_id = "+u;
					rs1 = stmt.executeQuery(getData);
					
					out.println(u);
					out.flush();
					out.println("");
					out.flush();
					
					out.println(rs1.getString("item_list"));
					out.flush();
					out.println("");
					out.flush();
					
					out.println(rs1.getString("max_price"));
					out.flush();
					out.println("");
					out.flush();
					
					
					out.println("send user co-ordinates");
					out.flush();
					out.println("");
					out.flush();
					
					out.println("End");
					out.flush();
					out.println("");
					out.flush();
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}

}
