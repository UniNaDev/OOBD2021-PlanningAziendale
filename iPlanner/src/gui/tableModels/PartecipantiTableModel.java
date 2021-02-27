package gui.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import entita.CollaborazioneProgetto;
import entita.Dipendente;
import entita.Meeting;
import entita.Skill;

public class PartecipantiTableModel extends AbstractTableModel {
	
	private ArrayList<Dipendente> dipendenteTabella=new ArrayList<Dipendente>();
	
	String[] colnames= {"Nome","Cognome","Sesso","Età","Email","Salario","Valutazione","Skill","Tipologia progetto"};

	public void setDipendenteTabella(ArrayList<Dipendente> dipendenteTabella) {
		this.dipendenteTabella = dipendenteTabella;
	}
	
	public ArrayList<Dipendente> getDipendenteTabella() {
		return dipendenteTabella;
	}

	@Override
	public int getRowCount() {
		return dipendenteTabella.size();
	}

	@Override
	public int getColumnCount() {
		return colnames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colnames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex >= 0) {
			
			Dipendente dipendente =dipendenteTabella.get(rowIndex);
			
			switch(columnIndex) {
			case 0:
				return dipendente.getNome();
			case 1:
				return dipendente.getCognome();
			case 2:
				return dipendente.getSesso();
			case 3:
				return dipendente.getEtà();
			case 4:
				return dipendente.getEmail();
			case 5:
				return dipendente.getSalario();
			case 6:
				String valutazioneDipendente = String.format("%.2f",dipendente.getValutazione());
				return valutazioneDipendente;
			case 7:
				return dipendente.getSkills();
			case 8:
				return dipendente.getTipologieProgetto();
				
			}
			return null;
			
		}
		return null;
	
	}

	public Dipendente getSelected(int rowIndex) {
		if (rowIndex >= 0) {
			Dipendente dipendente = dipendenteTabella.get(rowIndex);
			return dipendente;
		}
		else
			return null;
	}
	
	//Per sorting corretto
	public Class<?> getColumnClass(int column) {
        switch (column) {
            case 3:
                return Integer.class;
            case 5:
                return Float.class;
            case 2:
                return Character.class;
            case 7:
            	return Skill.class;
            default:
                return String.class;
        }
	}

}

	





