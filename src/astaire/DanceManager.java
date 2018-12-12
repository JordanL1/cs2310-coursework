package astaire;

import data.Importer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

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
		
		return null;
	}

}
