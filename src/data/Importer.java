package data;

import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * The Importer class is used to read relevant data from CSV files defined as static constants.
 * Methods exist for returning collections of data in a usable format. Data is imported when an
 * instance is constructed and can then be retrieved using getters.
 * 
 * @author Jordan Lees
 * @version 13/12/2018
 */

public class Importer {

	private static final String DANCES = "data/danceShowData_dances.csv";
	private static final String DANCE_GROUPS = "data/danceShowData_danceGroups.csv";
	private static final String RUNNING_ORDER = "data/danceShowData_runningOrder.csv";
	private static final int NUMBER_OF_DANCES = 37;
	
	ArrayList<String> runningOrder;
	LinkedHashMap<String, Set<String>> groups;
	LinkedHashMap<String, Set<String>> dances;
	
	
	public Importer() {
		try {
			groups = makeDataTwoCols(DANCE_GROUPS);
			dances = makeDataTwoCols(DANCES);
		}
		catch (IOException e) {
			System.out.println("Could not import data: " + e.getMessage());
		}
	}
	
	/**
	 * @return a map where Key is the dance group name and Value is the set of members.
	 */
	public LinkedHashMap<String, Set<String>> getDanceGroups() {
		return groups;
	}
	
	/**
	 * @return a map where Key is the dance title and Value is the set of performers.
	 */
	public LinkedHashMap<String, Set<String>> getDances() {
		return dances;
	}
	
	/**
	 * Get a running order of dances from a CSV file.
	 * 
	 * @param file filename containing running order (single-column CSV file)
	 * @return ArrayList of dances if successful; otherwise null.
	 */
	public ArrayList<String> getRunningOrder(String file) {
		try {
			return makeDataOneCol(file);
		}
		catch (IOException e) {
			System.out.println("Could not import data: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Read the chosen CSV file and produce a map where values are sets.
	 * CSV must be formatted as two columns, where col1 is the key and col2 contains
	 * comma-separated values which will be contained in the set.
	 * 
	 * @param file Filename of CSV to import from
	 * @return Map of strings (key) and sets of strings (value).
	 * @throws IOException
	 */	
	private LinkedHashMap<String, Set<String>> makeDataTwoCols(String file) throws IOException {
		BufferedReader in;
		Scanner scan;
		LinkedHashMap<String, Set<String>> data = new LinkedHashMap<String, Set<String>>(50, 0.75f);
		
		in = new BufferedReader(new FileReader(file));
		in.readLine();

		while(in.ready()) {
			String key;
			LinkedHashSet<String> set = new LinkedHashSet<String>();
			scan = new Scanner(in.readLine()).useDelimiter(",\\s*|\\s*\\t\\s*");
			
			key = scan.next();
			
			while(scan.hasNext()) {
				set.add(scan.next());
			}
			
			scan.close();
			data.put(key, set);
		}
		
		in.close();
		
		return data;
	}
	
	/**
	 * Read the chosen CSV file and produce an ArrayList of strings.
	 * CSV must be formatted as either a single column or tab-separated columns. Only 
	 * the first column is read.
	 * 
	 * @param file Filename of CSV to import from
	 * @return ArrayList of strings from the first column
	 * @throws IOException
	 */	
	private ArrayList<String> makeDataOneCol(String file) throws IOException {
		BufferedReader in;
		Scanner scan;
		ArrayList<String> data = new ArrayList<String>(20);
		
		in = new BufferedReader(new FileReader(file));
		in.readLine();

		while(in.ready()) {
			String title;
			scan = new Scanner(in.readLine()).useDelimiter("\\s*\\t");
			
			title = scan.next();
			
			scan.close();
			data.add(title);
		}
		
		in.close();
		
		return data;
	}
	
}
