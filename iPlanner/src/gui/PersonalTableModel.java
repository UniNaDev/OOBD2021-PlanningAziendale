package gui;
import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entita.Progetto;



public class PersonalTableModel extends AbstractTableModel {
	
	private ArrayList<Progetto> progettiTabella=new ArrayList<Progetto>();
	
	
	String[] colnames= {"ProgettoID", "Nome", "Descrizione","Ambito/i", "Tipologia", "Creazione", "Terminazione", "Scadenza"};

	public void setProgettiTabella(ArrayList<Progetto> progettiTabella) {
		this.progettiTabella = progettiTabella;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return progettiTabella.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return colnames[columnIndex];
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Progetto proj =progettiTabella.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return proj.getIdProgettto();
		case 1:
			return proj.getNomeProgetto();
		case 2:
			return proj.getDescrizioneProgetto();
		case 3:
			return proj.getAmbiti();
		case 4:
			return proj.getTipoProgetto();
		case 5:
			return proj.getDataCreazione();
		case 6:
			return proj.getDataTerminazione();
		case 7:
			return proj.getScadenza();
		
		
		}
		return null;
		
		
	}




}