import java.util.ArrayList;
import java.util.Comparator;

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private BST<User> friends;
    private int userID;
    private List<String> interests;
    
    /** Constructors */

    /**
     * Initializies empty User object with 
     * a temporary string for first name, last
     * name, username, and password, as well as 
     * sets friends BST and list of interests to null.
     */
    public User() {
        this.firstName = "none";
        this.lastName = "none";
        this.userName = "none";
        this.password = "none";
        this.friends = null;
        this.interests = null;
    }
    
    /**
     * Initializies empty User object with 
     * a temporary string for first name, last
     * name, and password, as well as 
     * sets friends BST and list of interests to null. 
     * It sets username of the User to the parameter.
     * @param userName - the username of the User
     */
    public User(String userName) {
        this.firstName = "none";
        this.lastName = "none";
        this.userName = userName;
        this.password = "none";
        this.friends = null;
        this.interests = null;
    }

    /**
     * Initializies empty User object with 
     * a temporary string for first name and last
     * name and set userName and password to the 
     * parameters if login is true. Else it sets 
     * the first and last name to the parameters and 
     * the username and password to a temporary string. 
     * It also sets the friends BST and the list of interests to null.
     * @param string_one - the username/firstname depending on login 
     * @param string_two - the password/lastname depending on login
     * @param login - whether they are logged in or not 
     */
    public User(String string_one, String string_two, boolean login) {
        if(login == true) {
            this.firstName = "first Name Unknown";
            this.lastName = "Last Name Unknown";
            this.userName = string_one;
            this.password = string_two;
        }
        else {
            this.firstName = string_one;
            this.lastName = string_two;
            this.userName = "userName unknown";
            this.password = "password unknown";
        }
        this.userID = 0;
        this.friends = null;
        this.interests = null;
    }

    /**
     * Initializies empty User object with 
     * a temporary string for first name, last
     * name, username, and password, as well as 
     * sets friends and interests to null
     * @param firstName - the user's first name
     * @param lastName - the user's last name
     * @param userName - the user's username
     * @param password - the user's password
     * @param friends - the BST containing the user's friends
     * @param interests - the List containing user's interests
     * @param userID - the user's id number
     */
    public User(String firstName, String lastName, String userName, String password, BST<User> friends, List<String> interests, int userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.friends = friends;
        this.interests = interests;
        this.userID = userID;
    }

    /**
     * Initializies empty User object with the
     * first name, last name, username, and 
     * password set to the parameters
     */
    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    /** Accessors */
    
    /**
     * @return firstName - the first name of the User
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @return lastName - the last name of the User
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return userName - the username of the User
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return userID - the user id of the User 
     */
    public int getUserID() {
        return userID;
    }
    
    /**
     * @return friends - the BST of the user's friends
     */
    public BST<User> getFriendList() {
        return friends;
    }

    /**
     * @return interests - the List of the user's interest
     */
    public List<String> getInterests() {
        return interests;
    }

    /**
     * @return interests.getLast() - a interest of the User
     */
    public String getUserInterest() {
        return interests.getLast();
    }
    
    /**
     * Checks if the password that the User entered matched their password
     * @param anotherPassword
     * @return true/false - if the password the user entered 
     * matches their password
     */
    public boolean passwordMatch(String anotherPassword) {
        if(password.equals(anotherPassword)) {
            return true;
        }
        return false;
    }

    /**
     * @return getFirstName() + getLastName() - the first and last 
     * name of the user 
     */
    public String getFirstLast() {
        return this.getFirstName() + this.getLastName();
    }

    /**
     * @return getUserName() + password - the username and password 
     * of the user
     */
    public String getUserNameAndPass() {
        return this.getUserName() + this.password;
    }

    /**
     * @return password - the password of the user 
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * @return User - the user returned by the BST search for friends
     */
    public User getFriendByName(String fname, String lname) {
        NameComparator n = new NameComparator();
        User u = new User(fname, lname, false);
        if(friends.search(u, n) == null) {
            return null;
        }
        return friends.search(u, n);
    }
    
    /** Mutators */
    
    /**
     * Adds the User to their friends BST
     * @param u - the User they wish to add
     */
    public void addFriend(User u) {
        NameComparator n = new NameComparator();
        friends.insert(u, n);
    }

    /**
     * Adds an interest to the User's list of interests
     * @param s - the interest user wishes to add
     */
    public void addInterest(String s) {
        interests.addLast(s);
    }

    /**
     * Removes the friend that the user is searching for
     * @param u - the user they wish to remove
     */
    public void removeFriend(User u) {
        NameComparator n = new NameComparator();
        friends.remove(u, n);
    }

    /**
     * Sets the firstName of the user to the parameter
     * @param firstName - the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName of the user to the parameter
     * @param lastName - the last name of user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the username of the user to the parameter
     * @param userName - the username of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 
     * Sets the password of the user to the parameter
     * @param password - the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the list of user's interests to the parameter
     * @param list - the List of the user's interests 
     */
    public void setInterests(List<String> list) {
        this.interests = list;
    }

    /**
     * Sets the user's friends BST to the parameter
     * @param list - the List of the user's friends 
     */
    public void setFriends(BST<User> list) {
        this.friends = list;
    }

    /**
     * Sets the user's userID to the parameter
     * @param UserID - the user's user id number
     */
    public void setUserID(int UserID) {
            this.userID = UserID;
    }

    static class NameComparator implements Comparator<User> {
        /**
         * Compares the two mutual fund accounts by name of the fund
         * uses the String compareTo method to make the comparison
         * @param account1 the first User
         * @param account2 the second User
         */
        @Override public int compare(User account1, User account2) {
            return account1.getFirstName().compareTo(account2.getFirstName());
        }
    }

    /**
     * Prints the user's friends BST ordered by their names 
     */
    public void printAccountsByName() {
        if(friends.getSize() == 1) {
            System.out.println("\nYour Friend: ");
            friends.inOrderPrint();
        }
        else {
            System.out.println("\nYour Friends: ");
            friends.inOrderPrint();
        }

    }

    /**
     * Checks whether two User objects are equal by comparing 
     * their first and last names
     */
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        } else {
            User c = (User) o;
            if(this.firstName.equals(c.firstName) && this.lastName.equals(c.lastName)) {
                if(c.getUserID() != 0) {
                    return this.getUserID() == c.getUserID();
                }
                return true;
            }
            else {
                return this.userName.equals(c.userName) && this.password.equals(c.password);
            }
        }
    }

    /**
     * Takes in the first and last name of the User and 
     * gives a unique hash code 
     * @return sum - the hash code for the first and last name
     */
    public int hashCode() {
        String key = this.firstName + this.lastName;
        int sum = 0;
        for(int i = 0; i < key.length(); i++) {
            sum += (int)key.charAt(i);
        }
        return sum;
    }

    /**
     * Prints the name (first and last) and the username of the User
     */
    @Override public String toString() {
        return "\nName: " + getFirstName() + " " + getLastName()
                + "\nUsername: " + getUserName();
    }

    /**
     * Prints the name (first and last), username, friends, and interests
     * of the user.
     */
    public void printUserProfile(){
        System.out.println("\n\nName: " + getFirstName() + " " + getLastName()
                + "\nUsername: " + getUserName()
                + "\n\nFriends: ");
        friends.inOrderPrint();
        if(friends.getSize() == 1) {
            System.out.println("You only have one friend!");
        }
        System.out.println("\nInterest:\n" + interests);
    }

    /**
     * Prints the first name, last name, username, password, the number of friends,
     * friends, the number of interest, and the interests of the user
     * @return firstName + lastName + username + password + 
     * friends.getSize() + friends + interests.getLength() + interests
     */
    public String writeUserInfo(){
        String friendsList = "";
        ArrayList<User> e = new ArrayList<>();
        while(!friends.isEmpty()){
            e.add(friends.getRoot());
            removeFriend(friends.getRoot());
        }
        for(int i = 0; i < e.size(); i++){
            friendsList += "\n" + e.get(i).getFirstName() + " " + e.get(i).getLastName();
            addFriend(e.get(i));
        }
        return getFirstName() + " " + getLastName() + "\n" + getUserName() + "\n" + getPassword() +
                "\n" + friends.getSize() + friendsList  + "\n" + interests.getLength() +
                "\n" + getInterests();
    }
}

