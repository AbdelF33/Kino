package adminModels;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public abstract class DbConnection {

	//private static String url = "jdbc:mysql://sql2.olympe.in/85yk4qp6";
	private static String url = "jdbc:mysql://localhost/kino_db";
	private static String login = "85yk4qp6";
	private static String password = "javaKino2015";
	protected static Connection cn = null;
	protected Statement st = null;
	protected ResultSet rs = null;


	public static Connection connect() throws SQLException{

	        try{

	            Class.forName("com.mysql.jdbc.Driver").newInstance();

	        }catch(ClassNotFoundException cnfe){
	            System.err.println("Error: "+cnfe.getMessage());
	        }catch(InstantiationException ie){
	            System.err.println("Error: "+ie.getMessage());
	        }catch(IllegalAccessException iae){
	            System.err.println("Error: "+iae.getMessage());
	        }
	 
	        cn = (Connection) DriverManager.getConnection(url,login,password);
	        return cn;
	    }

	    public static Connection getConnection() throws SQLException, ClassNotFoundException{

	        if(cn !=null && !cn.isClosed())
	            return cn;
	        connect();
	        return cn;
	 
	    }

		public Statement getSt() {
			return st;
		}

		public void setSt(Statement st) {
			this.st = st;
		}

		public ResultSet getRs() {
			return rs;
		}

		public void setRs(ResultSet rs) {
			this.rs = rs;
		}
		

}
