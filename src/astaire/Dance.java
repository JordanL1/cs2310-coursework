package astaire;

import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.Set;

/**
 * The Dance class models a single dance record. It stores the dance title and list
 * of performers by name. 
 * 
 * @author Jordan Lees
 * @version 10/12/2018
 *
 */

public class Dance {
	
	private String title;
	private TreeSet<String> performers;
	
	public Dance(String title) {
		this.title = title;
		performers = new TreeSet<String>();
	}
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * Add a single performer to the dance.
	 * 
	 * @param name of performer
	 */
	public void add (String name) {
		performers.add(name);
	}
	
	/**
	 * Add a set of performers (e.g. a Dance Group) to the dance.
	 * 
	 * @param names a Set of names as strings
	 */
	public void addAll(Set<String> names) {
		performers.addAll(names);
	}
	
	/**
	 * @return a collection of all the performers in this dance, in no particular order
	 */
	public LinkedHashSet<String> getPerformers() {
		return new LinkedHashSet<String>(performers);
	}
	
	/**
	 * @return a collection of all performers in this dance, sorted by natural ordering (alphabetical).
	 */
	public TreeSet<String> getSortedPerformers() {
		return performers;
	}
	
	
	/**
	 * Given another set of performers, check whether there are any performers in common
	 * between the two sets.
	 * 
	 * @param comparison A set of performer names to compare
	 * @return True if any performer in set comparison also exists in this dance
	 */
	public Set<String> doPerformersOverlap(Dance comparison) {
		Set<String> performersCloned = (Set<String>) getPerformers().clone();
		performersCloned.retainAll(comparison.getPerformers());
		
		return (performersCloned);
	}

}
