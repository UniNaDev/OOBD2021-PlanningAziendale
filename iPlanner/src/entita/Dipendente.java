/*Classe Dipendente.
*Rappresenta l'entità dipendente dell'azienda. Contiene i suoi dati anagrafici, il suo salario, la sua valutazione
*e le sue credenziali per accedere al sistema. Ad esso poi sono ovviamente associati progetti, meetings e skill lavorative.
*Contiene anche il metodo che genera correttamente il suo codice fiscale a partire dai suoi dati anagrafici. In questo modo
*il codice fiscale è validato a priori se i suoi dati anagrafici sono corretti.
***************************************************************************************************************************/

package entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;

public class Dipendente {

	//ATTRIBUTI
	//----------------------------------------
	
	//Attributi caratteristici
	private String cf;	//codice fiscale del dipendente
	private String nome;	//nome del dipendente
	private String cognome;	//cognome del dipendente
	private char sesso;	//sesso del dipendente (M=maschio, F=femmmina)
	private LocalDate dataNascita;	//data di nascita del dipendente
	private LuogoNascita luogoNascita;	//luogo di nascita del dipendente
	private String indirizzo;	//indirizzo del dipendente
	private String email;	//email del dipendente per accedere al servizio
	private String telefonoCasa;	//telefono di casa del dipendente
	private String cellulare;	//numero di cellulare del dipendente
	private float salario;	//salario attuale del dipendente
	private String password;	//password del dipendente con cui può accedere al servizio
	private float valutazione;	//valutazione in decimi del dipendente in azienda
	
	//Altri attributi
	private String[] vocali = {"A","E","I","O","U"};
	private String[] simboliNonRichiesti = {" ", "À","Á","È","É","Ì","Ò","Ù","Ä","Ë","Ï","Ö","Ü"};
	
	//Attributi per associazioni
	private ArrayList<Skill> skills = new ArrayList<Skill>();	//skill del dipendente
	private ArrayList<PartecipazioneMeeting> partecipazioniMeeting = new ArrayList<PartecipazioneMeeting>();	//lista di partecipazione del dipendente ai meeting
	private ArrayList<Meeting> partecipa=new ArrayList<Meeting>();
	private ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();	//lista delle collaborazioni del dipendente
	
	//METODI
	//----------------------------------------
	
	//Costruttore
	public Dipendente(String cf, String nome, String cognome, char sesso, LocalDate dataNascita,
			LuogoNascita luogoNascita, String indirizzo, String email, String telefonoCasa, String cellulare,
			float salario, String password, float valutazione) {
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.indirizzo = indirizzo;
		this.email = email;
		this.telefonoCasa = telefonoCasa;
		this.cellulare = cellulare;
		this.salario = salario;
		this.password = password;
		this.valutazione = valutazione;
	}
	
	
	
	//Metodo che genera il codice fiscale di un dipendente partendo dai suoi dati anagrafici
	public String generaCF() {
		String temp = cfNomeCognome();	//genera i primi 6 caratteri per nome e cognome (es: LMMNDR)
		
		temp += String.valueOf(dataNascita.getYear()).substring(2);	//aggiunge l'anno al CF nei 2 caratteri successivi (es: LMMNDR95)
		
		temp += convertiMese(String.valueOf(dataNascita.getMonthOfYear()));	//aggiunge il mese al CF nel carattere successivo (es: LMMNDR95P)
		
		//aggiunge il giorno della data di nascita nei due caratteri successivi e aggiunge 40 al se è femmina (es: LMMNDR95P16)
		String giorno = "";
		if (this.sesso == 'M')
			if (dataNascita.getDayOfMonth() < 10) {
				giorno = "0"+String.valueOf(dataNascita.getDayOfMonth());
			}
			else {
				giorno = String.valueOf(dataNascita.getDayOfMonth());
			}
		else
			temp += String.valueOf(dataNascita.getDayOfMonth()+40);
		temp += giorno;
		
		temp += luogoNascita.getCodiceComune();	//aggiunge il codice del comune in cui è nato (es: LMMNDR95P16F839)
		
		temp += carattereControllo(temp);	//aggiunge il carattere di controllo (es: LMMNDR95P16F839I)
		
		return temp;
	}

	//Getter e setter per quasi ogni attributo
	public String getCf() {
		return cf;
	}
	
	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public char getSesso() {
		return sesso;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public LuogoNascita getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(LuogoNascita luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonoCasa() {
		return telefonoCasa;
	}

	public void setTelefonoCasa(String telefonoCasa) {
		this.telefonoCasa = telefonoCasa;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setValutazione(float valutazione) {
		this.valutazione = valutazione;
	}
	
	public float getValutazione() {
		return valutazione;
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}

	public ArrayList<PartecipazioneMeeting> getPartecipazioniMeeting() {
		return partecipazioniMeeting;
	}

	public void setPartecipazioniMeeting(ArrayList<PartecipazioneMeeting> partecipazioniMeeting) {
		this.partecipazioniMeeting = partecipazioniMeeting;
	}

	public ArrayList<CollaborazioneProgetto> getCollaborazioni() {
		return collaborazioni;
	}

	public void setCollaborazioni(ArrayList<CollaborazioneProgetto> collaborazioni) {
		this.collaborazioni = collaborazioni;
	}

	//toString
	
	
	public ArrayList<Meeting> getPartecipa() {
		return partecipa;
	}



	public void setPartecipa(ArrayList<Meeting> partecipa) {
		this.partecipa = partecipa;
	}



	@Override
	public String toString() {
		
		String temp=getCollaborazioni().toString();
//		return nome + " " +cognome+"-"+getCollaborazioni();
		return temp;
	}
	
	

	//Metodo che calcola la stringa di caratteri del codice fiscale corrispondente a cognome e nome
	private String cfNomeCognome() {
		//setta in maiuscolo nome e cognome del dipendente
		String nomeUp = this.nome.toUpperCase();
		String cognomeUp = this.cognome.toUpperCase();
		
		//elimina vocali e caratteri accentati da nome e cognome
		String nomeSenzaVocali = nomeUp, cognomeSenzaVocali=cognomeUp;
		for (String c: vocali) {
			nomeSenzaVocali = nomeSenzaVocali.replace(c, "");
			cognomeSenzaVocali = cognomeSenzaVocali.replace(c, "");
		}
		for (String c: simboliNonRichiesti) {
			nomeSenzaVocali = nomeSenzaVocali.replace(c,"");
			cognomeSenzaVocali = cognomeSenzaVocali.replace(c, "");
		}
		
		String risultato1 = "";
		//controlla che ci siano almeno 3 caratteri nel cognome risultante e nel caso li prende
		if (cognomeSenzaVocali.length() >= 3) {
			risultato1 = risultato1 + cognomeSenzaVocali.substring(0,3);
		}
		//altrimenti cerca delle vocali nel cognome originale e le aggiunge
		else {
			risultato1 = risultato1+cognomeSenzaVocali;
			int j = 0;
			while (j < vocali.length && risultato1.length() < 3) {
				for (int i = 0; i < cognomeUp.length(); i++) {
					if (String.valueOf(cognomeUp.charAt(i)).equals(vocali[j])) {
						risultato1 += cognomeUp.charAt(i);
					}
					if (risultato1.length() == 3)
						break;
				}	
				j++;
			}
		}
		//se non ci sono nemmeno vocali sufficienti aggiunge delle X alla fine
		while (risultato1.length() < 3) {
			risultato1 += "X";
		}
		
		String risultato2 = "";
		//controlla che ci siano almeno 3 caratteri nel nome risultante e nel caso li prende
		if (nomeSenzaVocali.length() > 3) {
			risultato2 = risultato2 + nomeSenzaVocali.charAt(0) + nomeSenzaVocali.charAt(2) + nomeSenzaVocali.charAt(3);	//prima, terza e quarta consonante
		}
		else if (nomeSenzaVocali.length() == 3) {
			risultato2 = risultato2 + nomeSenzaVocali.substring(0,3);	//uniche tre consonanti
		}
		//altrimenti cerca delle vocali nel nome originale e le aggiunge
		else {
			risultato2 = risultato2+nomeSenzaVocali;
			int k = 0;
			while (k < vocali.length && risultato2.length() < 3) {
				for (int i = 0; i < nomeUp.length(); i++) {
					if (String.valueOf(nomeUp.charAt(i)).equals(vocali[k])) {
						risultato2 += nomeUp.charAt(i);
					}
					if (risultato2.length() == 3)
						break;
				}	
				k++;
			}
		}
		//se non ci sono nemmeno vocali sufficienti aggiunge delle X alla fine
		while (risultato2.length() < 3) {
			risultato2 += "X";
		}
		
		//unisce i due risultati per nome e cognome e lo restituisce
		return risultato1+risultato2;
	}
	



	//Metodo che converte il mese di nascita nel carattere corrispondente per il codice fiscale
	private String convertiMese(String numeroMese) {
		String temp = "";
		
		switch(numeroMese) {
		case "1":
			temp = "A";	//Gennaio = A
			break;
		case "2":
			temp = "B";	//Febbraio = B
			break;
		case "3":
			temp = "C";	//Marzo = C
			break;
		case "4":
			temp = "D";	//Aprile = D
			break;
		case "5":
			temp = "E"; //Maggio = E
			break;
		case "6":
			temp = "H";	//Giugno = H
			break;
		case "7":
			temp = "L";	//Luglio = L
			break;
		case "8":
			temp = "M";	//Agoisto = M
			break;
		case "9":
			temp = "P";	//Settembre = P
			break;
		case "10":
			temp = "R";	//Ottobre = R
			break;
		case "11":
			temp = "S";	//Novembre = S
			break;
		case "12":
			temp = "T";	//Dicembre = T
			break;
		}
		
		return temp;
	}
	
	//Metodo che converte i caratteri di posto pari del codice fiscale secondo una specifica tabella
	private int convertiDispari(char c) {
		int carattereConvertito = 0;
		
		if(c == '0')
			carattereConvertito = 1;
		else if(c == '1')
			carattereConvertito = 0;
		else if(c == '2')
			carattereConvertito = 5;
		else if(c == '3')
			carattereConvertito = 7;
		else if(c == '4')
			carattereConvertito = 9;
		else if(c == '5')
			carattereConvertito = 13;
		else if(c == '6')
			carattereConvertito = 15;
		else if(c == '7')
			carattereConvertito = 17;
		else if(c == '8')
			carattereConvertito = 19;
		else if(c == '9')
			carattereConvertito = 21;
		else if(c == 'A')
			carattereConvertito = 1;
		else if(c == 'B')
			carattereConvertito = 0;
		else if(c == 'C')
			carattereConvertito = 5;
		else if(c == 'D')
			carattereConvertito = 7;
		else if(c == 'E')
			carattereConvertito = 9;
		else if(c == 'F')
			carattereConvertito = 13;
		else if(c == 'G')
			carattereConvertito = 15;
		else if(c == 'H')
			carattereConvertito = 17;
		else if(c == 'I')
			carattereConvertito = 19;
		else if(c == 'J')
			carattereConvertito = 21;
		else if(c == 'K')
			carattereConvertito = 2;
		else if(c == 'L')
			carattereConvertito = 4;
		else if(c == 'M')
			carattereConvertito = 18;
		else if(c == 'N')
			carattereConvertito = 20;
		else if(c == 'O')
			carattereConvertito = 11;
		else if(c == 'P')
			carattereConvertito = 3;
		else if(c == 'Q')
			carattereConvertito = 6;
		else if(c == 'R')
			carattereConvertito = 8;
		else if(c == 'S')
			carattereConvertito = 12;
		else if(c == 'T')
			carattereConvertito = 14;
		else if(c == 'U')
			carattereConvertito = 16;
		else if(c == 'V')
			carattereConvertito = 10;
		else if(c == 'W')
			carattereConvertito = 22;
		else if(c == 'X')
			carattereConvertito = 25;
		else if(c == 'Y')
			carattereConvertito = 24;
		else if(c == 'Z')
			carattereConvertito = 23;
	
		return carattereConvertito;
	}
	
	//Metodo che converte i caratteri di posto dispari del codice fiscale secondo una specifica tabella
	private int convertiPari(char c) {
		int carattereConvertito = 0;
		
		if(c == '0')
			carattereConvertito = 0;
		else if(c == '1')
			carattereConvertito = 1;
		else if(c == '2')
			carattereConvertito = 2;
		else if(c == '3')
			carattereConvertito = 3;
		else if(c == '4')
			carattereConvertito = 4;
		else if(c == '5')
			carattereConvertito = 5;
		else if(c == '6')
			carattereConvertito = 6;
		else if(c == '7')
			carattereConvertito = 7;
		else if(c == '8')
			carattereConvertito = 8;
		else if(c == '9')
			carattereConvertito = 9;
		else if(c == 'A')
			carattereConvertito = 0;
		else if(c == 'B')
			carattereConvertito = 1;
		else if(c == 'C')
			carattereConvertito = 2;
		else if(c == 'D')
			carattereConvertito = 3;
		else if(c == 'E')
			carattereConvertito = 4;
		else if(c == 'F')
			carattereConvertito = 5;
		else if(c == 'G')
			carattereConvertito = 6;
		else if(c == 'H')
			carattereConvertito = 7;
		else if(c == 'I')
			carattereConvertito = 8;
		else if(c == 'J')
			carattereConvertito = 9;
		else if(c == 'K')
			carattereConvertito = 10;
		else if(c == 'L')
			carattereConvertito = 11;
		else if(c == 'M')
			carattereConvertito = 12;
		else if(c == 'N')
			carattereConvertito = 13;
		else if(c == 'O')
			carattereConvertito = 14;
		else if(c == 'P')
			carattereConvertito = 15;
		else if(c == 'Q')
			carattereConvertito = 16;
		else if(c == 'R')
			carattereConvertito = 17;
		else if(c == 'S')
			carattereConvertito = 18;
		else if(c == 'T')
			carattereConvertito = 19;
		else if(c == 'U')
			carattereConvertito = 20;
		else if(c == 'V')
			carattereConvertito = 21;
		else if(c == 'W')
			carattereConvertito = 22;
		else if(c == 'X')
			carattereConvertito = 23;
		else if(c == 'Y')
			carattereConvertito = 24;
		else if(c == 'Z')
			carattereConvertito = 25;
	
		return carattereConvertito;
	}
	
	//Metodo che calcola il carattere di controllo del codice fiscale
	private char carattereControllo(String tempCF) {
		char temp = ' ';	//inizializza il carattere di controllo
		
		int sommaDispari = 0, sommaPari = 0;	//inizializza le somme dei valori dei caratteri di posto pari/dispari
		
		//per ogni carattere che compone il codice fiscale privo di carattere di controllo (i primi 15 caratteri in pratica)
		for (int i = 0; i < tempCF.length(); i++) {
			//controlla se il suo posto è pari o dispari
			if (i%2 == 1) {
				sommaPari += convertiPari(tempCF.charAt(i)); //conversione di un carattere dispari e aggiornamento della somma dei caratteri dispari
			}
			else {
				sommaDispari += convertiDispari(tempCF.charAt(i)); //conversione di un carattere pari e aggiornamento della somma dei caratteri pari
			}
		}
		
		int risultatoParziale = (sommaDispari+sommaPari)%26;	//calcola il risultato parziale seguendo l'algoritmo di generazione del carattere di controllo
		
		//converte il risultato parziale sempre seguendo le tabelle per la generazione di codici fiscali
		if (risultatoParziale == 0)
			temp = 'A';
		else if(risultatoParziale == 1)
			temp ='B';
		else if(risultatoParziale == 2)
			temp ='C';
		else if(risultatoParziale == 3)
			temp ='D';
		else if(risultatoParziale == 4)
			temp ='E';
		else if(risultatoParziale == 5)
			temp ='F';
		else if(risultatoParziale == 6)
			temp ='G';
		else if(risultatoParziale == 7)
			temp ='H';
		else if(risultatoParziale == 8)
			temp ='I';
		else if(risultatoParziale == 9)
			temp ='J';
		else if(risultatoParziale == 10)
			temp ='K';
		else if(risultatoParziale == 11)
			temp ='L';
		else if(risultatoParziale == 12)
			temp ='M';
		else if(risultatoParziale == 13)
			temp ='N';
		else if(risultatoParziale == 14)
			temp ='O';
		else if(risultatoParziale == 15)
			temp ='P';
		else if(risultatoParziale == 16)
			temp ='Q';
		else if(risultatoParziale == 17)
			temp ='R';
		else if(risultatoParziale == 18)
			temp ='S';
		else if(risultatoParziale == 19)
			temp ='T';
		else if(risultatoParziale == 20)
			temp ='U';
		else if(risultatoParziale == 21)
			temp ='V';
		else if(risultatoParziale == 22)
			temp ='W';
		else if(risultatoParziale == 23)
			temp ='X';
		else if(risultatoParziale == 24)
			temp ='Y';
		else if(risultatoParziale == 25)
			temp ='Z';
		
		return temp;
	}
}
