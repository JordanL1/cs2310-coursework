package tests;

import static org.junit.Assert.*;
import astaire.DanceManager;

import org.junit.Test;

public class testDanceManager {

	@Test
	public void testListAllDancersIn() {
		DanceManager dm = new DanceManager();
		
		dm.listAllDancersIn("Let's Pretend");
	}
	
	@Test
	public void testListAllDancesAndPerformers() {
		DanceManager dm = new DanceManager();
		
		dm.listAllDancesAndPerformers();
	}

}
