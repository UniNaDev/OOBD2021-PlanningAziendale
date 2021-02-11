package gui.tableModels;

import java.util.Comparator;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class OrarioComparator implements Comparator<String> {

	private DateTimeFormatter formatHour = DateTimeFormat.forPattern("HH:mm");
	
	@Override
	public int compare(String ora1, String ora2) {
		LocalTime orario1 = LocalTime.parse(ora1, formatHour);
		LocalTime orario2 = LocalTime.parse(ora2, formatHour);
		if (orario1.equals(orario2))
			return 0;
		else if (orario1.isBefore(orario2))
			return -1;
		else
			return 1;
	}

}
