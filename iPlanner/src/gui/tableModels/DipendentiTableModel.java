package gui.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import entita.Dipendente;

public class DipendentiTableModel extends AbstractTableModel {
	
	private ArrayList<Dipendente> dipendenteTabella=new ArrayList<Dipendente>();
	
	
	private String[] colnames= {"Nome", "Cognome", "Email", "Età", "Salario", "Valutazione"};

	public void setDipendenteTabella(ArrayList<Dipendente> dipendenteTabella) {
		this.dipendenteTabella = dipendenteTabella;
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
			Period period = new Period(dipendente.getDataNascita(), LocalDate.now(), PeriodType.yearMonthDay());
			int age = period.getYears();
			return age;
		case 4:
			return dipendente.getSalario();
		case 5:
			return dipendente.getValutazione();
		}
		return null;
	}
	
	public Dipendente getSelected(int rowIndex) {
		Dipendente dipendente = dipendenteTabella.get(rowIndex);
		return dipendente;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex){
		return getValueAt(0, columnIndex).getClass();
	}
}
