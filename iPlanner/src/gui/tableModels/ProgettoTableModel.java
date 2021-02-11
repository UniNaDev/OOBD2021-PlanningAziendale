package gui.tableModels;
import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entita.AmbitoProgetto;
import entita.Progetto;



public class ProgettoTableModel extends AbstractTableModel {
	
	private ArrayList<Progetto> progettiTabella=new ArrayList<Progetto>();
	

	DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	String[] colnames= {"Nome", "Descrizione","Ambito/i", "Tipologia", "Creazione", "Terminazione", "Scadenza"};

	//metodo che ritorna la lista di progetti contenuti nella tabella
	
	public ArrayList<Progetto> getProgettiTabella() {
		return progettiTabella;
	}
	
	public void setProgettiTabella(ArrayList<Progetto> progettiTabella) {
		this.progettiTabella = progettiTabella;
	}
	
	

	@Override
	public int getRowCount() {
	
		return progettiTabella.size();
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
			return proj.getDataCreazione().toString(formatDate);
		case 5:
			if(proj.getDataTerminazione()==null)
				return null;
			else 
				return proj.getDataTerminazione().toString(formatDate);
		case 6:
			return proj.getScadenza().toString(formatDate);
		}
		return null;
	}
	
	public Progetto getSelected(int rowIndex) {
		return progettiTabella.get(rowIndex);
	}


		//Per sorting corretto
		public Class getColumnClass(int column) {
	        switch (column) {
	        	case 2:
	        		return AmbitoProgetto.class;
	            case 4:
	                return LocalDate.class;
	            case 5:
	                return LocalDate.class;
	            case 6:
	                return LocalDate.class;
	            default:
	                return String.class;
	        }
		}

	
	
}
