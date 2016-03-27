import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
	//	Class.forName("com.mysql.jdbc.Driver");
	//	System.out.println("entering it");
	//	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/HackUTD","root","");
	//	System.out.println("worked   ");
		ServerSocket server = new ServerSocket(17999);
		System.out.println("SERVER STARTED");
		Socket gotit = server.accept();
		System.out.println("ACCEPTED CLIENT");
		PrintWriter out = new PrintWriter(gotit.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(gotit.getInputStream()));
	//	out.println("HELLO");
	//	out.flush();
		while(true)
		{
			out.println("0");//user must reply with requester or buyer
			out.flush();
			out.println("");
			out.flush();
			String distinguish = "";
			distinguish = in.readLine();
			if(distinguish!=null)
			{
				if(distinguish.equals("BUYER"))
				{
					out.println("4");//user must reply with userid
					out.flush();
					out.println("");
					out.flush();
					String line = in.readLine();
					String user_id = "";
					if(line!=null)
					{
						user_id = line;
					}
					out.println("5");//user must reply with location
					out.flush();
					out.println("");
					out.flush();
					line = in.readLine();
					String location = "";
					if(line!=null)
					{
						location = line;
					}
					
					
					out.println("6");//user must reply with approx time at which je will be at the store.
					out.flush();
					out.println("");
					out.flush();
					line = in.readLine();
					String time_at_store = "";
					if(line!=null)
					{
						time_at_store = line;
					}
					String insert_buyer = "insert into buyer_Stagin values("+user_id+","+location+",'"+time_at_store+"')";
					//Statement stmt = (Statement) conn.createStatement();
					//stmt.executeUpdate(insert_buyer);
					(new BUYER(user_id,time_at_store,location,out)).run();
				}
			}
			if(distinguish.equals("REQUESTER"))
			{
				System.out.println("reply: ");
				out.println("1");//user must reply with userid
				out.flush();
				out.println("");
				out.flush();
				String line = in.readLine();
				String user_id = "";
				if(line!=null)
				{
					user_id = line;
				}
				out.println("2");//user must reply with list of items 
				out.flush();
				out.println("");
				out.flush();
				line = in.readLine();
				String list = "";
				if(line!=null)
				{
					list = line;
				}
				out.println("3");//user must reply with _price 
				out.flush();
				out.println("");
				out.flush();
				line = in.readLine();
				int max_price = 0;
				if(line!=null)
				{
					max_price = Integer.parseInt(line);
				}
				
				
				out.println("4");//user must reply with store list 
				out.flush();
				out.println("");
				out.flush();
				line = in.readLine();
				String store = "";
				if(line!=null)
				{
					store = line;
				}
				
				
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String transaction_id = user_id + date.getTime();
				Date newDate = new Date(date.getTime() + 2*3600*1000);
				String time_range = sdfDate.format(newDate);
				String insert = "insert into transaction values("+user_id+","+transaction_id+","+list+",'"+max_price+"','"+time_range+"')";
				//Statement stmt = (Statement) conn.createStatement();
				//stmt.executeUpdate(insert);
				//ArrayList<String> list1 = new ArrayList<>();
				String[] arr = store.split(",");				
				for(int i=0;i<arr.length;i++)
				{
					String ins = "insert into list_stores values("+user_id+","+transaction_id+","+arr[i]+",'"+time_range+"')";
					//stmt = (Statement) conn.createStatement();
					//stmt.executeUpdate(ins);
				}
			}
		}
		
		
	}

	
	
	
}
