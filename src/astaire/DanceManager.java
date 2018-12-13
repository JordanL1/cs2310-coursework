package astaire;

import data.Importer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * An implementation of Controller to manage the dance system. Data on dances and groups
 * from the CSV files is imported when the DanceManager is constructed.
 * 
 * @author Jordan Lees
 * @version 13/12/2018
 *
 */

public class DanceManager implements Controller {
	
	private Importer importer;
	private Map<String, Set<String>> danceGroups;
	private Map<String, Dance> dances;
	
	public DanceManager() {
		importer = new Importer();
		danceGroups = importer.getDanceGroups();
		dances = new LinkedHashMap<String, Dance>();
		
		Map<String, Set<String>> danceData = importer.getDances();
		
		for (Map.Entry<String, Set<String>> entry : danceData.entrySet()) {
			String title = entry.getKey();
			Set<String> performers = entry.getValue();
			
			Dance dance = new Dance(title);
			
			for (String performer : performers) {
				if (danceGroups.containsKey(performer)) {
					dance.addAll(danceGroups.get(performer));
				}
				else {
					dance.add(performer);
				}
			}
			
			dances.put(title, dance);
		}
		
	}

	@Override
	public String listAllDancersIn(String dance) {
		Dance selectedDance = dances.get(dance);
		
		if (selectedDance != null) {
			StringBuilder sb = new StringBuilder(selectedDance.getTitle() + ": ");
			sb.append(selectedDance.getSortedPerformers());
			sb.append("\n");
			return sb.toString();
		}

		return "Dance does not exist.";
	}

	@Override
	public String listAllDancesAndPerformers() {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> sortedDances = new ArrayList<String>(dances.keySet());
		sortedDances.sort(null);
		
		for (String title : sortedDances) {
			sb.append(listAllDancersIn(title));
		}
		
		return sb.toString();
	}

	@Override
	public String checkFeasibilityOfRunningOrder(String filename, int gaps) {
		ArrayList<String> runningOrder = importer.getRunningOrder(filename);
		ArrayList<String> overlaps = new ArrayList<String>();
		
		/*
		 * For each item in the proposed running order, check the next (gaps) number
		 * of items in the sequence for overlapping performers. If any performers overlap,
		 * add a string to the overlaps set.
		 */
		if (runningOrder != null) {
			for (int i = 0; i < runningOrder.size(); i++) {
				Dance current = dances.get(runningOrder.get(i));
				
				for (int j = 1; j <= gaps && i + j < runningOrder.size(); j++) {
					Dance nextj = dances.get(runningOrder.get(i + j));
					Set<String> anyOverlaps = current.doPerformersOverlap(nextj);
					
					if (!anyOverlaps.isEmpty()) {
						overlaps.add(current.getTitle() + " and " + nextj.getTitle() + 
								" both require performers: " + anyOverlaps.toString());
						
					}
				}
			}
			
			if (overlaps.size() > 0) {
				StringBuilder sb = new StringBuilder("Running order:\n" + runningOrder.toString() +
						"\n not feasible because...\n");
				
				for (String line : overlaps) {
					sb.append(line + "\n");
				}
				
				return sb.toString();
			}	
			else {
				return "Running order feasible:\n" + runningOrder.toString();
			}
		}
		
		return "Error loading running order.";
	}

	@Override
	public String generateRunningOrder(int gaps) {
		ArrayList<Dance> order = makeRunningOrder(gaps);
		StringBuilder sb = new StringBuilder();
		
		if (order != null) {
			sb.append("Running order found!\n");
			
			for (Dance d : order) {
				sb.append(listAllDancersIn(d.getTitle()));
			}
		}
		else {
			sb.append("Could not find a running order.\n");
		}
		
		return sb.toString();	
	}
	
	/**
	 * Generate a running order of Dance instances.
	 * 
	 * @param gaps
	 * @return A feasible running order if it exists; otherwise null.
	 */
	private ArrayList<Dance> makeRunningOrder(int gaps) {
		LinkedHashSet<String> subSet = new LinkedHashSet<String>(importer.getRunningOrder("data/danceShowData_runningOrder.csv"));
		ArrayList<Dance> order;
		
		/*
		 * Add a random dance from the subset as the first in the list, repeatedly iterate through each 
		 * item in the subset and check whether there are any overlaps between it and the last 
		 * (gaps) number of performers. If not, add it to the sequence and remove from the subset.
		 * 
		 * Continue until either there are either no items remaining in the subset (in which case,
		 * return the feasible order) or the length of the subset has not changed after an iteration
		 * (meaning none of the remaining Dances can be added).
		 * 
		 * Repeat this process, starting the order from each item in the subSet, until a solution
		 * is found or every item from the subset has been tried as the first item.
		 */
		for (String title : subSet) {
			Dance first = dances.get(title);
			LinkedHashSet<String> subSetCloned = (LinkedHashSet<String>) subSet.clone();
			order = new ArrayList<Dance>();
			order.add(first);
			subSetCloned.remove(first.getTitle());
			Boolean canContinue = true;
			
			while (canContinue && !subSetCloned.isEmpty()) {
				
				canContinue = true;
				int sizeBefore = subSetCloned.size();
				Set<String> removals = new LinkedHashSet<String>();
				
				for (String currentTitle : subSetCloned) {
					Dance current = dances.get(currentTitle);
					Boolean match = true;
					
					for (int i = -1; i + order.size() >= 0 && Math.abs(i) <= gaps; i--) {
						if (!current.doPerformersOverlap(order.get(i + order.size())).isEmpty()) {
							match = false;
						}
					}
					
					if (match == true) {
						order.add(current);
						removals.add(currentTitle);
					}
				}
				
				subSetCloned.removeAll(removals);
				
				if (subSetCloned.isEmpty()) {
					return order;
				}
				else if (sizeBefore == subSetCloned.size()) {
					canContinue = false;
				}
			}
		}
		
		return null;
	}
}
