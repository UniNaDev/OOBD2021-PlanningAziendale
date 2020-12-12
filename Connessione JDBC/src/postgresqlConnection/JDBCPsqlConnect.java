package postgresqlConnection;

import java.sql.*;

public class JDBCPsqlConnect {

	
	String url= "jdbc:postgresql://localhost:5432/MikeDB";
	String user= "postgres";
	String password= "michele";
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
			System.out.println("Connessione al server PostgreSQL effettuata con successo");
			
			//Creazione Statement
			Statement MyStatement=Conn.createStatement();
			ResultSet resultSet = null;
			try {
				//Esecuzione Statement
				resultSet = MyStatement.executeQuery("SELECT * FROM persona ORDER BY id_code");
				
				//Stampa ResultSet
				while(resultSet.next())
				{
					
					System.out.println("ID_Code"+resultSet.getString("id_code"));
					System.out.println("Nome"+resultSet.getString("nome"));
					
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
