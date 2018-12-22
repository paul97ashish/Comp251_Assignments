package A1;

import java.util.*;
import static A1.main.*;

public class Chaining {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public ArrayList<ArrayList<Integer>> Table;

    //Constructor for the class. sets up the data structure for you
    protected Chaining(int w, int seed) {
        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.Table = new ArrayList<ArrayList<Integer>>(m);
        for (int i = 0; i < m; i++) {
            Table.add(new ArrayList<Integer>());
        }
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
    }

    /**
     * Implements the hash function h(k)
     */
    public int chain(int key) {
        //ADD YOUR CODE HERE (change return statement)
    	// this is h(k)
    	int hashValue;
    	int AdotK = this.A * key;
    	int Pw = power2(w);  
    	
    	hashValue = (AdotK % Pw) >> ( w - r );
        
        return hashValue;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table.get(hashValue).size() == 0;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (change return statement)
    	int numOfCols = 0;
    	int hashValue;
    	boolean isEmpty;
    	
    	hashValue = chain(key);
    	isEmpty = isSlotEmpty(hashValue);
    	
    	//if there are no slots remaining;
    	if(m==0) {
    		return -1;
    		
    	}
    	//The thing is empty
    	else if (isEmpty) {
    		Table.get(hashValue).add(key);
    		return numOfCols;
    	}
    	
    	// Key was already inserted. i.e this is a duplicate key 
    	else if (Table.get(hashValue).contains(key)) {
    		return numOfCols;
    	}
    	//all the slots are full
    	
    	// there are collisions
    	else {
    		numOfCols = Table.get(hashValue).size();
    		Table.get(hashValue).add(key);
    		return numOfCols;
    	}
    }

}
