package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entita.Dipendente;
import entita.Meeting;
import entita.Skill;

public class DipendenteTableModel extends AbstractTableModel {
	
	private ArrayList<Dipendente> dipendenteTabella=new ArrayList<Dipendente>();
	
	
	String[] colnames= {"CF", "Nome", "Cognome","Sesso", "Età", "Email", "Salario", "Valutazione"};

	public void setDipendenteTabella(ArrayList<Dipendente> dipendenteTabella) {
		this.dipendenteTabella = dipendenteTabella;
	}

	@Override
	public int getRowCount() {
		return dipendenteTabella.size();
	}

	@Override
	public int getColumnCount() {

		return 8;
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
			return dipendente.getDataNascita();
		case 5:
			return dipendente.getEmail();
		case 6:
			return dipendente.getSalario();
		case 7:
			return dipendente.getValutazione();

		}
		return null;
		
		
	}




}

