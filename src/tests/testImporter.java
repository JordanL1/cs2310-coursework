package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import data.Importer;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;

public class testImporter {

	@Test
	public void test() {
		Importer importer = new Importer();
		LinkedHashMap<String, Set<String>> groups = null;
		
		try {
			 groups = importer.getDanceGroups();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		for (String key : groups.keySet()) {
			System.out.println(key + groups.get(key).toString());
			
			for (String name : groups.get(key)) {
				System.out.println(name);
			}
		}
		
	}
	

	@Test
	public void testDances() {
		Importer importer = new Importer();
		LinkedHashMap<String, Set<String>> dances = null;
		
		try {
			 dances = importer.getDances();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		for (String key : dances.keySet()) {
			System.out.println(key + dances.get(key).toString());
			
			for (String name : dances.get(key)) {
				System.out.println(name);
			}
		}
		
	}

}
