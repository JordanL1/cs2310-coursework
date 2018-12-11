package data;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The Importer class is used to read relevant data from CSV files defined as static constants.
 * Methods exist for returning collections of data in a usable format.
 * 
 * @author Jordan Lees
 * @version 10/12/2018
 */

public class Importer {

	private static final String DANCES = "data/danceShowData_dances.csv";
	private static final String DANCE_GROUPS = "data/danceShowData_danceGroups.csv";
	private static final String RUNNING_ORDER = "data/danceShowData_runningOrder.csv";
	
	public Importer() {
		
	}
	
	/**
	 * Read the CSV file defined as a static constant and produce a data structure containing
	 * dance group names and their associated dancers.
	 * 
	 * @return Map of dance group names (key) with their set of performers (value).
	 * @throws IOException
	 */
	public LinkedHashMap<String, Set<String>> getDanceGroups() throws IOException {
		BufferedReader in;
		Scanner scan;
		LinkedHashMap<String, Set<String>> danceGroups = new LinkedHashMap<String, Set<String>>();
		
		in = new BufferedReader(new FileReader(DANCE_GROUPS));
		in.readLine();

		while(in.ready()) {
			String key;
			LinkedHashSet<String> set = new LinkedHashSet<String>();
			scan = new Scanner(in.readLine()).useDelimiter(", |\\t");
			
			key = scan.next();
			
			while(scan.hasNext()) {
				set.add(scan.next());
			}
			
			scan.close();
			danceGroups.put(key, set);
		}
		
		in.close();
		
		return danceGroups;
	}
	
	/**
	 * Read the CSV file defined as a static constant and produce a data structure containing
	 * dance titles and their associated performers (which may be either performer names or names
	 * of dance groups).
	 * 
	 * @return Map of dance titles (key) with their set of performers (value).
	 * @throws IOException
	 */
	public LinkedHashMap<String, Set<String>> getDances() throws IOException {
		BufferedReader in;
		Scanner scan;
		LinkedHashMap<String, Set<String>> dances = new LinkedHashMap<String, Set<String>>();
		
		in = new BufferedReader(new FileReader(DANCES));
		in.readLine();

		while(in.ready()) {
			String key;
			LinkedHashSet<String> set = new LinkedHashSet<String>();
			scan = new Scanner(in.readLine()).useDelimiter(", |\\t");
			
			key = scan.next();
			
			while(scan.hasNext()) {
				set.add(scan.next());
			}
			
			scan.close();
			dances.put(key, set);
		}
		
		in.close();
		
		return dances;
	}
	
}
