package gui.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import entita.Dipendente;
import entita.Skill;

public class DipendentiTableModel extends AbstractTableModel {
	
	private ArrayList<Dipendente> dipendenteTabella=new ArrayList<Dipendente>();
	
	
	private String[] colnames= {"Nome", "Cognome", "Email", "Età", "Salario", "Valutazione"};

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
		Dipendente dipendente = dipendenteTabella.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return dipendente.getNome();
		case 1:
			return dipendente.getCognome();
		case 2:
			return dipendente.getEmail();
		case 3:
			return dipendente.getEtà();
		case 4:
			return dipendente.getSalario();
		case 5:
			String s = String.format("%.2f",dipendente.getValutazione());
			return s;
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
	            case 3:
	                return Integer.class;
	            case 4:
	                return Float.class;
	            default:
	                return String.class;
	        }
		}
}
