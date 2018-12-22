package A1;

import static A1.main.*;

public class Open_Addressing {

	public int m; // number of SLOTS AVAILABLE
	public int A; // the default random number
	int w;
	int r;
	public int[] Table;

	// Constructor for the class. sets up the data structure for you
	protected Open_Addressing(int w, int seed) {

		this.w = w;
		this.r = (int) (w - 1) / 2 + 1;
		this.m = power2(r);
		this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
		this.Table = new int[m];
		// empty slots are initalized as -1, since all keys are positive
		for (int i = 0; i < m; i++) {
			Table[i] = -1;
		}

	}

	/**
	 * Implements the hash function g(k)
	 */
	public int probe(int key, int i) {
		// ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)

		int hashValue;
		int Pw = power2(w);
		int Pr = power2(r);
		int AdotK = A * key;

		// This is g(k,i)
		hashValue = (((AdotK % Pw) >> (w - r)) + i) % Pr;

		return hashValue;
	}

	/**
	 * Checks if slot n is empty
	 */
	public boolean isSlotEmpty(int hashValue) {
		return Table[hashValue] == -1;
	}

	/**
	 * Inserts key k into hash table. Returns the number of collisions encountered
	 */
	public int insertKey(int key) {
		// ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
		int hashValue;
		int i = 0;
		int numOfCols = 0;

		hashValue = probe(key, i);

		boolean isItIn = false;
		
		//i has to be less than slots available(m) because there is no point in  
		// searching after that 
		while (!isItIn && i < m) {
			boolean isEmpty = isSlotEmpty(hashValue);

			// if slot is empty
			if (isEmpty) {
				Table[hashValue] = key;
				return numOfCols;
			}

			// if slot is not empty
			else {
				// check if the key has already been inserted
				if (Table[hashValue] == key) {
					return numOfCols;
				}
				// if the key hasnt already been inserted then increment collisions,
				// increment i and get a new hashValue and continue looking for a slot
				else {
					numOfCols++;
					i++;
					hashValue = probe(key, i);				
				}
			}

		}

		return numOfCols;
	}

	/**
	 * Removes key k from hash table. Returns the number of collisions encountered
	 */
	public int removeKey(int key) {
		// ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
		int i = 0;
		int hashValue;
		int numOfCols = 0;
		boolean isItOut = false;
		hashValue = probe(key,i);
		
		//i has to be less than the slots available(m) because there is no point in 
		// searching after that
		while(!isItOut && i < m) {
			//key is found 
			if ( Table[hashValue] == key) {
				Table[hashValue] = -1;
				return numOfCols;
			}
			//key not found keep on searching 
			else {
				i++;
				numOfCols++;
				hashValue = probe(key,i);
			}
			
		}
		return numOfCols;
	}

}
