package astaire;

import data.Importer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;

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
		System.out.println(dances.get(dance).getSortedPerformers());
		return null;
	}

	@Override
	public String listAllDancesAndPerformers() {
		for (String title : dances.keySet()) {
			listAllDancersIn(title);
		}
		return null;
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
				return "Running order:\n" + runningOrder.toString() + "\n not feasible because...\n"
						+ overlaps.toString();
			}
			else {
				return "Running order feasible:\n" + runningOrder.toString();
			}
		}
		
		return "Error loading running order.";
	}

	@Override
	public String generateRunningOrder(int gaps) {
		// TODO Auto-generated method stub
		return null;
	}

}
