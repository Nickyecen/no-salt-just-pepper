package utilitaries;

import java.util.Objects;

/**
 * Represents an ordered pair of elements of two equal or different types
 * 
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * 
 * @author nickyecen
 */
public class Tuple<T1, T2> {

	// Variables
	private final T1 first;
	private final T2 second;

	// Constructors
	/**
	 * Constructs a tuple with two elements.
	 * 
	 * @param first
	 * @param second
	 * 
	 * @author nickyecen
	 */
	public Tuple(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	// Methods
		// Getters
	
	/**
	 * Gets the first element of the tuple
	 * 
	 * @return the first element of the tuple
	 * 
	 * @author nickyecen
	 */
	public T1 getFirst() {
		return first;
	}
	
	/**
	 * Gets the second element of the tuple
	 * 
	 * @return the second element of the tuple
	 * 
	 * @author nickyecen
	 */
	public T2 getSecond() {
		return second;
	}

		// Default Overwrites
	/**
	 * Checks if this tuple is equal to a given object
	 * Overrides {@link Object#equals(Object)}
	 * 
	 * @author nickyecen
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
		return Objects.equals(first, tuple.first) &&
			   Objects.equals(second, tuple.second);
	}

	/**
	 * Returns a hash code for the tuple
	 * Overrides {@link Object#hashCode()}
	 * 
	 * @author nickyecen
	 */
	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	/**
	 * Returns a string representation of the tuple in the format (first, second).
	 * Overrides {@link Object#toString()}
	 * 
	 * @author nickyecen
	 */
	@Override
	public String toString() {
		return "(" + Objects.toString(first) + ", " + Objects.toString(second) + ")";
	}
	
}
