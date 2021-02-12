/*Classe Dipendente.
*Rappresenta l'entità dipendente dell'azienda. Contiene i suoi dati anagrafici, il suo salario, la sua valutazione
*e le sue credenziali per accedere al sistema. Ad esso poi sono ovviamente associati progetti, meetings e skill lavorative.
*Contiene anche il metodo che genera correttamente il suo codice fiscale a partire dai suoi dati anagrafici. In questo modo
*il codice fiscale è validato a priori se i suoi dati anagrafici sono corretti.
***************************************************************************************************************************/

package entita;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Dipendente {
	private String cf;
	private String nome;
	private String cognome;
	private char sesso; //(M = maschio | F = femmina)
	private LocalDate dataNascita;
	private LuogoNascita luogoNascita;
	private String indirizzo;
	private String email;
	private String telefonoCasa;
	private String cellulare;
	private float salario;
	private String password;
	private float valutazione;
	
	private ArrayList<Skill> skills = new ArrayList<Skill>();
	private ArrayList<PartecipazioneMeeting> partecipazioniMeeting = new ArrayList<PartecipazioneMeeting>();
	private ArrayList<Meeting> partecipa =new ArrayList<Meeting>(); //TODO: probabilmente inutile (basta partecipazioneMeeting)
	private ArrayList<CollaborazioneProgetto> collaborazioni = new ArrayList<CollaborazioneProgetto>();
	private String tipologieProgetto;
	
	private String[] vocali = {"A","E","I","O","U"};
	private String[] simboliNonAccettati = {" ", "À","Á","È","É","Ì","Ò","Ù","Ä","Ë","Ï","Ö","Ü"};
	
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
	
	public String generaCF() {
		String cf = calcolaCaratteriNomeCognome();
		cf += String.valueOf(dataNascita.getYear()).substring(2);
		cf += calcolaCarattereMeseNascita(String.valueOf(dataNascita.getMonthOfYear()));
		String caratteriGiornoNascita = "";
		if (this.sesso == 'M')
			if (dataNascita.getDayOfMonth() < 10) {
				caratteriGiornoNascita = "0"+String.valueOf(dataNascita.getDayOfMonth());
			}
			else {
				caratteriGiornoNascita = String.valueOf(dataNascita.getDayOfMonth());
			}
		else
			cf += String.valueOf(dataNascita.getDayOfMonth()+40);
		cf += caratteriGiornoNascita;
		cf += luogoNascita.getCodiceComune();
		cf += calcolaCarattereControllo(cf);
		return cf;
	}

	private String calcolaCaratteriNomeCognome() {
		String nomeMaiuscolo = this.nome.toUpperCase();
		String cognomeMaiuscolo = this.cognome.toUpperCase();
		String nomeSenzaVocali = nomeMaiuscolo, cognomeSenzaVocali=cognomeMaiuscolo;
		for (String c: vocali) {
			nomeSenzaVocali = nomeSenzaVocali.replace(c, "");
			cognomeSenzaVocali = cognomeSenzaVocali.replace(c, "");
		}
		for (String c: simboliNonAccettati) {
			nomeSenzaVocali = nomeSenzaVocali.replace(c,"");
			cognomeSenzaVocali = cognomeSenzaVocali.replace(c, "");
		}
		String caratteriCognomeCF = "";
		if (cognomeSenzaVocali.length() >= 3) {
			caratteriCognomeCF = caratteriCognomeCF + cognomeSenzaVocali.substring(0,3);
		}
		else {
			caratteriCognomeCF = caratteriCognomeCF+cognomeSenzaVocali;
			int j = 0;
			while (j < vocali.length && caratteriCognomeCF.length() < 3) {
				for (int i = 0; i < cognomeMaiuscolo.length(); i++) {
					if (String.valueOf(cognomeMaiuscolo.charAt(i)).equals(vocali[j])) {
						caratteriCognomeCF += cognomeMaiuscolo.charAt(i);
					}
					if (caratteriCognomeCF.length() == 3)
						break;
				}	
				j++;
			}
		}
		while (caratteriCognomeCF.length() < 3) {
			caratteriCognomeCF += "X";
		}
		String caratteriNomeCF = "";
		if (nomeSenzaVocali.length() > 3) {
			caratteriNomeCF = caratteriNomeCF + nomeSenzaVocali.charAt(0) + nomeSenzaVocali.charAt(2) + nomeSenzaVocali.charAt(3);
		}
		else if (nomeSenzaVocali.length() == 3) {
			caratteriNomeCF = caratteriNomeCF + nomeSenzaVocali.substring(0,3);
		}
		else {
			caratteriNomeCF = caratteriNomeCF+nomeSenzaVocali;
			int k = 0;
			while (k < vocali.length && caratteriNomeCF.length() < 3) {
				for (int i = 0; i < nomeMaiuscolo.length(); i++) {
					if (String.valueOf(nomeMaiuscolo.charAt(i)).equals(vocali[k])) {
						caratteriNomeCF += nomeMaiuscolo.charAt(i);
					}
					if (caratteriNomeCF.length() == 3)
						break;
				}	
				k++;
			}
		}
		while (caratteriNomeCF.length() < 3) {
			caratteriNomeCF += "X";
		}
		return caratteriCognomeCF+caratteriNomeCF;
	}
	

	private String calcolaCarattereMeseNascita(String numeroMese) {
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
	
	private char calcolaCarattereControllo(String tempCF) {
		char carattereControllo = ' ';
		int sommaDispari = 0, sommaPari = 0;
		
		for (int i = 0; i < tempCF.length(); i++) {
			if (i%2 == 1) {
				sommaDispari += convertiCaratteriPostoDispari(tempCF.charAt(i));
			}
			else {
				sommaPari += convertiCaratteriPostoPari(tempCF.charAt(i));
			}
		}
		
		int risultatoParziale = (sommaDispari+sommaPari)%26;
		
		if (risultatoParziale == 0)
			carattereControllo = 'A';
		else if(risultatoParziale == 1)
			carattereControllo ='B';
		else if(risultatoParziale == 2)
			carattereControllo ='C';
		else if(risultatoParziale == 3)
			carattereControllo ='D';
		else if(risultatoParziale == 4)
			carattereControllo ='E';
		else if(risultatoParziale == 5)
			carattereControllo ='F';
		else if(risultatoParziale == 6)
			carattereControllo ='G';
		else if(risultatoParziale == 7)
			carattereControllo ='H';
		else if(risultatoParziale == 8)
			carattereControllo ='I';
		else if(risultatoParziale == 9)
			carattereControllo ='J';
		else if(risultatoParziale == 10)
			carattereControllo ='K';
		else if(risultatoParziale == 11)
			carattereControllo ='L';
		else if(risultatoParziale == 12)
			carattereControllo ='M';
		else if(risultatoParziale == 13)
			carattereControllo ='N';
		else if(risultatoParziale == 14)
			carattereControllo ='O';
		else if(risultatoParziale == 15)
			carattereControllo ='P';
		else if(risultatoParziale == 16)
			carattereControllo ='Q';
		else if(risultatoParziale == 17)
			carattereControllo ='R';
		else if(risultatoParziale == 18)
			carattereControllo ='S';
		else if(risultatoParziale == 19)
			carattereControllo ='T';
		else if(risultatoParziale == 20)
			carattereControllo ='U';
		else if(risultatoParziale == 21)
			carattereControllo ='V';
		else if(risultatoParziale == 22)
			carattereControllo ='W';
		else if(risultatoParziale == 23)
			carattereControllo ='X';
		else if(risultatoParziale == 24)
			carattereControllo ='Y';
		else if(risultatoParziale == 25)
			carattereControllo ='Z';
		return carattereControllo;
	}
	
	private int convertiCaratteriPostoPari(char c) {
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
	
	private int convertiCaratteriPostoDispari(char c) {
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

	public int getEtà() {
		Period period = new Period(dataNascita, LocalDate.now(), PeriodType.yearMonthDay());
		int età = period.getYears();
		
		return età;
	}
	
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

	public String getTipologieProgetto() {
		return tipologieProgetto;
	}

	public void setTipologieProgetto(String tipologieProgetto) {
		this.tipologieProgetto = tipologieProgetto;
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
	
	public ArrayList<Meeting> getPartecipa() {
		return partecipa;
	}


	public void setPartecipa(ArrayList<Meeting> partecipa) {
		this.partecipa = partecipa;
	}
	
	@Override
	public String toString() {	
		return nome + " " +cognome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dipendente other = (Dipendente) obj;
		if (cf == null) {
			if (other.cf != null)
				return false;
		} else if (!cf.equals(other.cf))
			return false;
		return true;
	}
}
