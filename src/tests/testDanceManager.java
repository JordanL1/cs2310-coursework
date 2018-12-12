package tests;

import static org.junit.Assert.*;
import astaire.DanceManager;

import org.junit.Test;

public class testDanceManager {

	@Test
	public void testListAllDancersIn() {
		DanceManager dm = new DanceManager();
		
		System.out.println(dm.listAllDancersIn("Let's Pretend"));
	}
	
	@Test
	public void testListAllDancesAndPerformers() {
		DanceManager dm = new DanceManager();
		
		System.out.println(dm.listAllDancesAndPerformers());
	}
	
	@Test
	public void testCheckFeasibility() {
		DanceManager dm = new DanceManager();
		
		System.out.println(dm.checkFeasibilityOfRunningOrder("data/danceShowData_runningOrder.csv", 1));
		
		System.out.println(dm.checkFeasibilityOfRunningOrder("data/danceShowData_runningOrder.csv", 2));
	}

}
