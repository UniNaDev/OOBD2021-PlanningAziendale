package tableModels;
import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entita.Progetto;



public class ProgettoTableModel extends AbstractTableModel {
	
	private ArrayList<Progetto> progettiTabella=new ArrayList<Progetto>();
	
	
	String[] colnames= {"Nome", "Descrizione","Ambito/i", "Tipologia", "Creazione", "Terminazione", "Scadenza"};

	//metodo che ritorna la lista di progetti contenuti nella tabella
	public ArrayList<Progetto> getProgettiTabella()
	{
		return this.progettiTabella;
	}
	
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
		return colnames.length;
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
			return proj.getNomeProgetto();
		case 1:
			return proj.getDescrizioneProgetto();
		case 2:
			return proj.getAmbiti();
		case 3:
			return proj.getTipoProgetto();
		case 4:
			return proj.getDataCreazione();
		case 5:
			return proj.getDataTerminazione();
		case 6:
			return proj.getScadenza();
		}
		return null;
	}
	
	public Progetto getSelected(int rowIndex) {
		return progettiTabella.get(rowIndex);
	}


	
	
}
