package gui.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import entita.Dipendente;
import entita.Meeting;
import entita.Skill;

public class PartecipantiTableModel extends AbstractTableModel {
	
	private ArrayList<Dipendente> dipendenteTabella=new ArrayList<Dipendente>();
	
	
	String[] colnames= {"CF","Nome","Cognome","Sesso","Età","Email","Salario","Valutazione","Skill","Tipologia progetto"};

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
		Dipendente dipendente =dipendenteTabella.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return dipendente.getCf();
		case 1:
			return dipendente.getNome();
		case 2:
			return dipendente.getCognome();
		case 3:
			return dipendente.getSesso();
		case 4:
			return dipendente.getEtà();
		case 5:
			return dipendente.getEmail();
		case 6:
			return dipendente.getSalario();
		case 7:
			String s = String.format("%.2f",dipendente.getValutazione());
			return s;
		case 8:
			return dipendente.getSkills();
		case 9:
			return dipendente.getCollaborazioni();
			
		}
		return null;
	}


	
	public Dipendente getSelected(int rowIndex) {
		Dipendente dipendente = dipendenteTabella.get(rowIndex);
		return dipendente;
	}
	
	//Per sorting corretto
	public Class<?> getColumnClass(int column) {
        switch (column) {
            case 4:
                return Integer.class;
            case 6:
                return Float.class;
            case 3:
                return Character.class;
            default:
                return String.class;
        }
	}

}

	





