package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import data.Importer;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;

public class testImporter {

	@Test
	public void test() {
		Importer importer = new Importer();
		LinkedHashMap<String, Set<String>> groups = importer.getDanceGroups();
		LinkedHashMap<String, Set<String>> dances = importer.getDances();
		
		for (String key : groups.keySet()) {
			System.out.println(key + groups.get(key).toString());
		}
		
		for (String key : dances.keySet()) {
			System.out.println(key + dances.get(key).toString());
		}
		
	}

}
