public class Interest {
    private int id;
    private String name;

    /** Constructors **/
    
    /**
     * Initializes empty Interest object with 
     * 0 for the id and a temporary string for
     * name.
     */
    public Interest() {
        this.id = 0;
        this.name = "none";
    }

    /**
     * Initialized empty Interest object with 
     * 0 for the id and the parameter, name, for
     * the name of the interest.
     * @param name - the name of the interest
     */
    public Interest(String name) {
        this.id = 0;
        this.name = name;
    }

    /**
     * Initialized empty Interest object with 
     * 0 for the id and the parameter, name, for
     * the name of the interest.
     * @param id - the id of the interest 
     * @param name - the name of the interest
     */
    public Interest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Accessors **/
    
    /**
     * Accesses the id of the interest
     * @return id - the id number of the interest
     */
    public int getInterestID() {
        return id;
    }

    /**
     * Access the name of the interest
     * @return name - the name of the interest
     */
    public String getName() {
        return name;
    }

    /** Mutators **/ 
    
    /**
     * Sets the id of the interest to the 
     * parameter, id
     * @param id - the id of the interest
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the interest to the 
     * parameter, name
     * @param name - the name of the interest 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Additional Methods **/

    /**
     * Provides a unique hash code by 
     * hashing the name of the interest
     */
    public int hashCode() {
        String key = this.name;
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += (int) key.charAt(i);
        }
        return sum;
    }

    /**
     * @return name + id - the name and the id
     * of the interest
     */
    @Override public String toString() {
        return "Hobby Name: " + name
                + "\nID: " + id;
    }

    /**
     * Checks if two Interest objects are the same by 
     * checking their names 
     * @return boolean - if the interest objects are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Interest)) {
            return false;
        } else {
            Interest c = (Interest) o;
            if(c.id == 0) {
                return this.getName().equals(c.getName());
            }
            return this.getInterestID() == (c.getInterestID()) && this.getName().equals(c.getName());
        }
    }
}
