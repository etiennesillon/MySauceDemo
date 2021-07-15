package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MyTestUtils {
	
	/*******************************************************************************************************/
	
	public static List<String> readFileLines(String fn) {
		
		List<String> ret = new ArrayList<String>();
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(fn));
			String line = in.readLine();
			while(line != null) {
				ret.add(line);
				line = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			ret = null;
		}
		
		return ret;
		
	}
	
	/*********************************************************************/

	public static String getDateTime() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();  
		
		return dtf.format(now);
		
	}
	
}