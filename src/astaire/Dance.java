package astaire;

import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.Set;

public class Dance {
	
	private String title;
	private LinkedHashSet<String> performers;
	
	public Dance(String title) {
		this.title = title;
		performers = new LinkedHashSet<String>();
	}
	
	public void add (String name) {
		performers.add(name);
	}
	
	public void addAll(Set<String> names) {
		performers.addAll(names);
	}
	
	public LinkedHashSet<String> getPerformers() {
		return performers;
	}
	
	/**
	 * @return a collection of all performers in this dance, ordered by natural ordering (alphabetical).
	 */
	public TreeSet<String> getSortedPerformers() {
		return new TreeSet<String>(performers);
	}
	
	
	/**
	 * Given another set of performers, check whether there are any performers in common
	 * between the two sets.
	 * 
	 * @param comparison A set of performer names to compare
	 * @return True if any performer in set comparison also exists in this dance
	 */
	public Boolean doPerformersOverlap(Set<String> comparison) {
		Set<String> performersCloned = (Set<String>) performers.clone();
		performersCloned.retainAll(comparison);
		
		return (!performersCloned.isEmpty());
	}

}
