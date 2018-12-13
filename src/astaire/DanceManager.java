package astaire;

import data.Importer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.ArrayDeque;

/**
 * TODO: Set data structure default sizes
 * @author Jordan Lees
 * @version 11/12/2018
 *
 */

public class DanceManager implements Controller {
	
	private Importer importer;
	private LinkedHashMap<String, Set<String>> danceGroups;
	private LinkedHashMap<String, Dance> dances;
	
	public DanceManager() {
		importer = new Importer();
		danceGroups = importer.getDanceGroups();
		dances = new LinkedHashMap<String, Dance>();
		
		LinkedHashMap<String, Set<String>> danceData = importer.getDances();
		
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
		TreeSet<String> sortedDances = new TreeSet<String>(dances.keySet());
		
		for (String title : sortedDances) {
			sb.append(listAllDancersIn(title));
		}
		
		return sb.toString();
	}

	@Override
	public String checkFeasibilityOfRunningOrder(String filename, int gaps) {
		ArrayList<String> runningOrder = importer.getRunningOrder(filename);
		ArrayList<String> overlaps = new ArrayList<String>();
		
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
	
	private ArrayList<Dance> makeRunningOrder(int gaps) {
		LinkedHashSet<String> subSet = new LinkedHashSet<String>(importer.getRunningOrder("data/danceShowData_runningOrder.csv"));
		ArrayList<Dance> order;
		
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
						System.out.println(order + "\n" + subSetCloned);
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
