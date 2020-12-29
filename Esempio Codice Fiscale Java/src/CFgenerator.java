import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.LocalDate;

public class CFgenerator {

	//QUESTA CLASSE CONTIENE TUTTI I METODI PER ANDARE A GENERARE IL CODICE FISCALE DI UN DIPENDENTE
	
	/* Genera le prime 6 cife in base al NOME e COGNOME , prende le prime 3 consonanti di ognuno e se non
	   sono abbastanza prende le vocali */
	public String FirstSixCharacters(String nome ,String cognome)
	{					
		//CONVERTE TUTTO IN MAIUSCOLO
		nome = nome.toUpperCase();
		cognome = cognome.toUpperCase();
		
		//CONVERTE IL NOME IN TUTTE CONSONANTI RIMUOVENDO LE VOCALI
		String consonantiNome = nome.replace("A", "");
		consonantiNome = consonantiNome.replace("E", "");
		consonantiNome = consonantiNome.replace("I", "");
		consonantiNome = consonantiNome.replace("O", "");
		consonantiNome = consonantiNome.replace("U", "");
						
		String result = consonantiNome;	//a questo punto result conterrà tutte le consonanti del nome
		
		//SE IL NOME NON HA ALMENTO 3 CONSONANTI ALLORA RICAVO LE VOCALI
		if(result.length() <3)
		{
			String vocaliNome = nome.replace("B", "");
			vocaliNome = vocaliNome.replace("C", "");
			vocaliNome = vocaliNome.replace("D", "");
			vocaliNome = vocaliNome.replace("F", "");
			vocaliNome = vocaliNome.replace("G", "");
			vocaliNome = vocaliNome.replace("H", "");
			vocaliNome = vocaliNome.replace("J", "");
			vocaliNome = vocaliNome.replace("K", "");
			vocaliNome = vocaliNome.replace("L", "");
			vocaliNome = vocaliNome.replace("M", "");
			vocaliNome = vocaliNome.replace("N", "");
			vocaliNome = vocaliNome.replace("P", "");
			vocaliNome = vocaliNome.replace("Q", "");
			vocaliNome = vocaliNome.replace("R", "");
			vocaliNome = vocaliNome.replace("S", "");
			vocaliNome = vocaliNome.replace("T", "");
			vocaliNome = vocaliNome.replace("V", "");
			vocaliNome = vocaliNome.replace("W", "");
			vocaliNome = vocaliNome.replace("X", "");
			vocaliNome = vocaliNome.replace("Y", "");
			vocaliNome = vocaliNome.replace("Z", "");
			
			//INFINE CONCATENO LE COSONANTI (CHE NON ERANO ABBASTANZA) CON LE VOCALI
			
			result = result + vocaliNome;//a questo punto result conterrà tutte le consonanti e le vocali nell ordine giusto
		}								 
		
		result = result.substring(0, 3); //ALLA FINE DI TUTTA LA CONVERSIONE PRENDE SOLO LE PRIME 3 LETTERE
		
		
		//RIPETE LO STESSO IDENTICO CON IL COGNOME
		
		String consonantiCognome = cognome.replace("A", "");
		consonantiCognome = consonantiCognome.replace("E", "");
		consonantiCognome = consonantiCognome.replace("I", "");
		consonantiCognome = consonantiCognome.replace("O", "");
		consonantiCognome = consonantiCognome.replace("U", "");
		
		String result1 = consonantiCognome;
		
		if(result1.length()<3)
			{
			String vocaliCognome = cognome.replace("B", "");
			vocaliCognome = vocaliCognome.replace("C", "");
			vocaliCognome = vocaliCognome.replace("D", "");
			vocaliCognome = vocaliCognome.replace("F", "");
			vocaliCognome = vocaliCognome.replace("G", "");
			vocaliCognome = vocaliCognome.replace("H", "");
			vocaliCognome = vocaliCognome.replace("J", "");
			vocaliCognome = vocaliCognome.replace("K", "");
			vocaliCognome = vocaliCognome.replace("L", "");
			vocaliCognome = vocaliCognome.replace("M", "");
			vocaliCognome = vocaliCognome.replace("N", "");
			vocaliCognome = vocaliCognome.replace("P", "");
			vocaliCognome = vocaliCognome.replace("Q", "");
			vocaliCognome = vocaliCognome.replace("R", "");
			vocaliCognome = vocaliCognome.replace("S", "");
			vocaliCognome = vocaliCognome.replace("T", "");
			vocaliCognome = vocaliCognome.replace("V", "");
			vocaliCognome = vocaliCognome.replace("W", "");
			vocaliCognome = vocaliCognome.replace("X", "");
			vocaliCognome = vocaliCognome.replace("Y", "");
			vocaliCognome = vocaliCognome.replace("Z", "");
			
			result1 = result1 + vocaliCognome;
			}
		
		result1= result1.substring(0,3); //result1 contiene il cognome convertito, quindi ne prendiamo solo le prime 3 lettere
		
		
		//ALLA FINE DI TUTTO IL PROCEDIMENTO CONCATENA IL NOME E COGNOME CONVERTITI
		result = result1 + result;
		
		
		return result; //result a questo punto conterrà effettivamente le prime 6 cifre del Codice Fiscale
	
	}


	/*Genera le cifre dalla 7 alla 11 in base alla DATA DI NASCITA ed al SESSO (nel caso delle donne aggiunge
	  40 al giorno di nascita) */
	public String SeventhToEleventhChar(LocalDate dataDiNascita,char sesso)
	{
		
		//ESTRAE L'ANNO DALLA DATA DI NASCITA , LO CONVERTE IN STRINGA E NE PRENDE SOLO LE ULTIME DUE CIFRE
		String anno = String.valueOf(dataDiNascita.getYear()).substring(2);//ValueOf converte l'int in stringa
		
			//ad esempio con l'anno 1999 prende solo 99 , con 2020 prende solo 20  , 1975 solo 75 e cosi via..
		
		
		//ESTRAE IL MESE
		String mese = String.valueOf(dataDiNascita.getMonthOfYear());
		
		
		
		//ESTRAE IL GIORNO
		int giornoInt = dataDiNascita.getDayOfMonth(); //ho preso il giorno come INT per poter sommare 40 nel caso il sesso sia F
		
		if(sesso == 'F')
			giornoInt = giornoInt + 40; //se il sesso è femminile si aggiunge 40 al giorno
		
		String giorno = String.valueOf(giornoInt);//riconverte il giorno in stringa in modo da poter fare la concatenzione in seguito
		
		//se il giorno ha una sola cifra ci mette 0 avanti (esempio: 6 diventa 06)
		if(giorno.length() == 1)
			giorno = "0"+ giorno;
		
			//fino a questo punto abbiamo estratto i dati,ora bisogna convertire il mese nella corrispettiva lettera dell alfabeto		
		
		
		//IN BASE AL VALORE DEL MESE LO CONVERTE NELLA CORRISPONDENETE LETTERA DELL ALFABETO
		String meseConvertito = "0";
		
		
		if (mese.equals("1"))
			meseConvertito = "A";
		else if(mese.equals("2"))
			meseConvertito = "B";
		else if(mese.equals("3"))
			meseConvertito = "C";
		else if(mese.equals("4"))
			meseConvertito = "D";
		else if(mese.equals("5"))
			meseConvertito = "E";
		else if(mese.equals("6"))
			meseConvertito = "H";
		else if(mese.equals("7"))
			meseConvertito = "L";
		else if(mese.equals("8"))
			meseConvertito = "M";
		else if(mese.equals("9"))
			meseConvertito = "P";
		else if(mese.equals("10"))
			meseConvertito = "R";
		else if(mese.equals("11"))
			meseConvertito = "S";
		else if(mese.equals("12"))
			meseConvertito = "T";
		
		
		//ALLA FINE SI OTTENGONO I 5 CARATTERI CONCATENANDO ANNO, MESE (come lettera) e GIORNO
		String result = anno + meseConvertito + giorno;
		
		return result;
		
	}

	/*Genera le cifre dalla 12 alla 15 che dipendono unicamente dal COMUNE DI NASCITA , prende quindi in input il nome
	  del comune ed estrae dal DATABASE il codicecomune */
	public String TwelfthToFifteenthChar(String nomeComune)
	{
		//////////////////////
		
		//QUESTO SARÀ PROBABILMENTE UN METODO PRESENTE IN LuogoNascitaDAO PER ORA LO SCRIVO DIRETTAMENTE QUI
	
		//////////////////////
	
		String db = "jdbc:postgresql://databaseprogetto.ceaxreltfrnh.eu-central-1.rds.amazonaws.com:5432/postgres";
		String result = "NULL";
		
		try 
		{
			Connection conn = DriverManager.getConnection(db,"postgres","postgres");
		
			Statement stmt = conn.createStatement();
			
			//seleziona le righe della tabella luogonascita in cui il nome è nomeComune preso in input 
			ResultSet rs = stmt.executeQuery("SELECT * FROM luogonascita WHERE nomecomune ='" + nomeComune + "'");
			
			rs.next();
			result = rs.getString("codcomune");	//prende solo la colonna del codiceComune	
		} 
		catch (SQLException e) 
		{			
			System.out.println(e);
		}
	
		
		return result;
		
	}

	
	/* Questi due metodi ConvertiDispari e ConvertiPari vanno a convertire un carattere in base ad una specifica tabella
	   presa da wikipedia (i caratteri dispari vengono convertiti in un modo mentre quelli pari in un altro).	   
	   Questi caratteri serviranno per calcolare il carattere di controllo finale del Codice Fiscale */
	public int ConvertiDispari(char carattere) 
	{
		int carattereConvertito = 0;
		
		//in base al valore del carattere lo converte nel corrispettivo intero (preso dalla tabella su wikipedia)
		if(carattere == '0')
			carattereConvertito = 1;
		else if(carattere == '1')
			carattereConvertito = 0;
		else if(carattere == '2')
			carattereConvertito = 5;
		else if(carattere == '3')
			carattereConvertito = 7;
		else if(carattere == '4')
			carattereConvertito = 9;
		else if(carattere == '5')
			carattereConvertito = 13;
		else if(carattere == '6')
			carattereConvertito = 15;
		else if(carattere == '7')
			carattereConvertito = 17;
		else if(carattere == '8')
			carattereConvertito = 19;
		else if(carattere == '9')
			carattereConvertito = 21;
		else if(carattere == 'A')
			carattereConvertito = 1;
		else if(carattere == 'B')
			carattereConvertito = 0;
		else if(carattere == 'C')
			carattereConvertito = 5;
		else if(carattere == 'D')
			carattereConvertito = 7;
		else if(carattere == 'E')
			carattereConvertito = 9;
		else if(carattere == 'F')
			carattereConvertito = 13;
		else if(carattere == 'G')
			carattereConvertito = 15;
		else if(carattere == 'H')
			carattereConvertito = 17;
		else if(carattere == 'I')
			carattereConvertito = 19;
		else if(carattere == 'J')
			carattereConvertito = 21;
		else if(carattere == 'K')
			carattereConvertito = 2;
		else if(carattere == 'L')
			carattereConvertito = 4;
		else if(carattere == 'M')
			carattereConvertito = 18;
		else if(carattere == 'N')
			carattereConvertito = 20;
		else if(carattere == 'O')
			carattereConvertito = 11;
		else if(carattere == 'P')
			carattereConvertito = 3;
		else if(carattere == 'Q')
			carattereConvertito = 6;
		else if(carattere == 'R')
			carattereConvertito = 8;
		else if(carattere == 'S')
			carattereConvertito = 12;
		else if(carattere == 'T')
			carattereConvertito = 14;
		else if(carattere == 'U')
			carattereConvertito = 16;
		else if(carattere == 'V')
			carattereConvertito = 10;
		else if(carattere == 'W')
			carattereConvertito = 22;
		else if(carattere == 'X')
			carattereConvertito = 25;
		else if(carattere == 'Y')
			carattereConvertito = 24;
		else if(carattere == 'Z')
			carattereConvertito = 23;
	
		return carattereConvertito;
	}
	
	public int ConvertiPari(char carattere)
	{
		int carattereConvertito = 0;
		
		//in base al valore del carattere lo converte nel corrispettivo intero (preso dalla tabella su wikipedia)
		if(carattere == '0')
			carattereConvertito = 0;
		else if(carattere == '1')
			carattereConvertito = 1;
		else if(carattere == '2')
			carattereConvertito = 2;
		else if(carattere == '3')
			carattereConvertito = 3;
		else if(carattere == '4')
			carattereConvertito = 4;
		else if(carattere == '5')
			carattereConvertito = 5;
		else if(carattere == '6')
			carattereConvertito = 6;
		else if(carattere == '7')
			carattereConvertito = 7;
		else if(carattere == '8')
			carattereConvertito = 8;
		else if(carattere == '9')
			carattereConvertito = 9;
		else if(carattere == 'A')
			carattereConvertito = 0;
		else if(carattere == 'B')
			carattereConvertito = 1;
		else if(carattere == 'C')
			carattereConvertito = 2;
		else if(carattere == 'D')
			carattereConvertito = 3;
		else if(carattere == 'E')
			carattereConvertito = 4;
		else if(carattere == 'F')
			carattereConvertito = 5;
		else if(carattere == 'G')
			carattereConvertito = 6;
		else if(carattere == 'H')
			carattereConvertito = 7;
		else if(carattere == 'I')
			carattereConvertito = 8;
		else if(carattere == 'J')
			carattereConvertito = 9;
		else if(carattere == 'K')
			carattereConvertito = 10;
		else if(carattere == 'L')
			carattereConvertito = 11;
		else if(carattere == 'M')
			carattereConvertito = 12;
		else if(carattere == 'N')
			carattereConvertito = 13;
		else if(carattere == 'O')
			carattereConvertito = 14;
		else if(carattere == 'P')
			carattereConvertito = 15;
		else if(carattere == 'Q')
			carattereConvertito = 16;
		else if(carattere == 'R')
			carattereConvertito = 17;
		else if(carattere == 'S')
			carattereConvertito = 18;
		else if(carattere == 'T')
			carattereConvertito = 19;
		else if(carattere == 'U')
			carattereConvertito = 20;
		else if(carattere == 'V')
			carattereConvertito = 21;
		else if(carattere == 'W')
			carattereConvertito = 22;
		else if(carattere == 'X')
			carattereConvertito = 23;
		else if(carattere == 'Y')
			carattereConvertito = 24;
		else if(carattere == 'Z')
			carattereConvertito = 25;
	
		return carattereConvertito;
	}


	//Genera il carattere di controllo a partire dalle 15 cifre precedenti , seguendo l'algoritmo preso da Wikipedia
	public char LastChar(String codiceFiscaleIncompleto)
	{		
		char carattereControllo =0;
		
		//prende i caratteri che si trovano nelle posizioni dispari
		String caratteriDispari = "" + codiceFiscaleIncompleto.charAt(0) + codiceFiscaleIncompleto.charAt(2) + codiceFiscaleIncompleto.charAt(4)
								     + codiceFiscaleIncompleto.charAt(6) + codiceFiscaleIncompleto.charAt(8) + codiceFiscaleIncompleto.charAt(10)
								     + codiceFiscaleIncompleto.charAt(12)+ codiceFiscaleIncompleto.charAt(14);
		
		//prende i caratteri che si trovano nelle posizioni pari
		String caratteriPari = "" + codiceFiscaleIncompleto.charAt(1) + codiceFiscaleIncompleto.charAt(3) + codiceFiscaleIncompleto.charAt(5)
		  						  + codiceFiscaleIncompleto.charAt(7) + codiceFiscaleIncompleto.charAt(9) + codiceFiscaleIncompleto.charAt(11)
		  						  + codiceFiscaleIncompleto.charAt(13);
		
		//Utilizza il metodo ConvertiDispari() creato in precedenza per attribuire ad ogni carattere un valore. 
		//Successivamente somma tra di loro tutti questi valori
		int sommaDispari = this.ConvertiDispari(caratteriDispari.charAt(0)) + this.ConvertiDispari(caratteriDispari.charAt(1)) + this.ConvertiDispari(caratteriDispari.charAt(2))
						 + this.ConvertiDispari(caratteriDispari.charAt(3)) + this.ConvertiDispari(caratteriDispari.charAt(4)) + this.ConvertiDispari(caratteriDispari.charAt(5))
						 + this.ConvertiDispari(caratteriDispari.charAt(6)) + this.ConvertiDispari(caratteriDispari.charAt(7));
		
		//Utilizza il metodo ConvertiPari() creato in precedenza per attribuire ad ogni carattere un valore. 
		//Successivamente somma tra di loro tutti questi valori			
		int sommaPari = this.ConvertiPari(caratteriPari.charAt(0)) + this.ConvertiPari(caratteriPari.charAt(1)) + this.ConvertiPari(caratteriPari.charAt(2))
					  + this.ConvertiPari(caratteriPari.charAt(3)) + this.ConvertiPari(caratteriPari.charAt(4)) + this.ConvertiPari(caratteriPari.charAt(5))
					  + this.ConvertiPari(caratteriPari.charAt(6));
		
		
		//SOMMA QUINDI I DUE NUMERI OTTENUTI E PRENDE IL RESTO DELLA DIVISIONE PER 26 (come descritto dall algoritmo che calcola il codice di controllo)
		int risultato = (sommaPari + sommaDispari)%26;
	
		//Infine converte il risultato nel corrispettivo carattere di controllo (come mostrato nella tabella su wikipedia)
		if (risultato == 0)
			carattereControllo = 'A';
		else if(risultato == 1)
			carattereControllo ='B';
		else if(risultato == 2)
			carattereControllo ='C';
		else if(risultato == 3)
			carattereControllo ='D';
		else if(risultato == 4)
			carattereControllo ='E';
		else if(risultato == 5)
			carattereControllo ='F';
		else if(risultato == 6)
			carattereControllo ='G';
		else if(risultato == 7)
			carattereControllo ='H';
		else if(risultato == 8)
			carattereControllo ='I';
		else if(risultato == 9)
			carattereControllo ='J';
		else if(risultato == 10)
			carattereControllo ='K';
		else if(risultato == 11)
			carattereControllo ='L';
		else if(risultato == 12)
			carattereControllo ='M';
		else if(risultato == 13)
			carattereControllo ='N';
		else if(risultato == 14)
			carattereControllo ='O';
		else if(risultato == 15)
			carattereControllo ='P';
		else if(risultato == 16)
			carattereControllo ='Q';
		else if(risultato == 17)
			carattereControllo ='R';
		else if(risultato == 18)
			carattereControllo ='S';
		else if(risultato == 19)
			carattereControllo ='T';
		else if(risultato == 20)
			carattereControllo ='U';
		else if(risultato == 21)
			carattereControllo ='V';
		else if(risultato == 22)
			carattereControllo ='W';
		else if(risultato == 23)
			carattereControllo ='X';
		else if(risultato == 24)
			carattereControllo ='Y';
		else if(risultato == 25)
			carattereControllo ='Z';
		
		
		//RESTITUISCE FINALMENTE IL CARATTERE DI CONTROLLO	
		return carattereControllo;
	}

	
}


