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
	
	
	String[] colnames= {"CF", "Nome", "Cognome","Sesso", "Età", "Email", "Salario", "Valutazione"};

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
			Period period = new Period(dipendente.getDataNascita(), LocalDate.now(), PeriodType.yearMonthDay());
			int age = period.getYears();
			return age;
		case 5:
			return dipendente.getEmail();
		case 6:
			return dipendente.getSalario();
		case 7:
			String s = String.format("%.2f",dipendente.getValutazione());
			return s;

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





