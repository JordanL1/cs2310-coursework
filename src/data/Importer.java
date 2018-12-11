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
 * TODO: Set default capacities for data structures
 * 
 * The Importer class is used to read relevant data from CSV files defined as static constants.
 * Methods exist for returning collections of data in a usable format. Data is imported when an
 * instance is constructed and can then be retrieved using getters.
 * 
 * @author Jordan Lees
 * @version 10/12/2018
 */

public class Importer {

	private static final String DANCES = "data/danceShowData_dances.csv";
	private static final String DANCE_GROUPS = "data/danceShowData_danceGroups.csv";
	private static final String RUNNING_ORDER = "data/danceShowData_runningOrder.csv";
	
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
	
	public LinkedHashMap<String, Set<String>> getDanceGroups() {
		return groups;
	}
	
	public LinkedHashMap<String, Set<String>> getDances() {
		return dances;
	}
	
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
	 * @return Map of dance group names (key) with their set of performers (value).
	 * @throws IOException
	 */	
	private LinkedHashMap<String, Set<String>> makeDataTwoCols(String file) throws IOException {
		BufferedReader in;
		Scanner scan;
		LinkedHashMap<String, Set<String>> data = new LinkedHashMap<String, Set<String>>();
		
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
	
	private ArrayList<String> makeDataOneCol(String file) throws IOException {
		BufferedReader in;
		Scanner scan;
		ArrayList<String> data = new ArrayList<String>();
		
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
