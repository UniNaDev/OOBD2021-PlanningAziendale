package gui.tableModels;

import java.util.Comparator;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DataComparator implements Comparator<String> {

	private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	@Override
	public int compare(String data1, String data2) {
        LocalDate date1 = LocalDate.parse(data1,formatDate);
        LocalDate date2= LocalDate.parse(data2,formatDate);
        if (date1.equals(date2))
         return 0;
        else if (date1.isBefore(date2))
         return -1;
        else
         return 1;
	}

}
