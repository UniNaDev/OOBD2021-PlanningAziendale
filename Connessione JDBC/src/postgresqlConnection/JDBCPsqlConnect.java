package postgresqlConnection;
import java.sql.*;

public class JDBCPsqlConnect {

	String url= "jdbc:postgresql://databaseprogetto.cu47fid9uycm.eu-south-1.rds.amazonaws.com:5432/postgres";
	String user= "postgres";
	String password= "postgres";
	String classdriver="org.postgresql.Driver";
		          
	public void connect() {
	
		//Ckeck esistenza classe
		try {
			Class.forName(classdriver);
			System.out.println("Class Found");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Class Not Found");
		}
		
		//Creazione connessione con il driver
		try {
			
			Connection Conn= DriverManager.getConnection(url, user, password);
			System.out.println("Connessione al server PostgreSQL effettuata");
			
			//Creazione Statement
			Statement MyStatement=Conn.createStatement();
			ResultSet resultSet = null;
			try {
				//Esecuzione Statement
				resultSet = MyStatement.executeQuery("SELECT * FROM persona");
				
				//Stampa ResultSet
				while(resultSet.next())
				{
					
					System.out.println("ID_Code: "+resultSet.getString("cf"));
					System.out.println("Nome: "+resultSet.getString("nome"));
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Wrong Query");
			}
			
			//Chiusura
			resultSet.close();
			MyStatement.close();
			Conn.close();
	
		} catch (SQLException e) {
			
			System.out.println("Connessione al server PostgreSQL fallita");
		}
		
	}
	
	public static void main(String[] args) {
		
		JDBCPsqlConnect sqlConn=new JDBCPsqlConnect();
		sqlConn.connect();
		
	}

}
