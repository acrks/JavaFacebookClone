import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class mainInterface {
	public static void main(String[] args) throws IOException {
		//Declare HashTables
		HashTable<User> userName = new HashTable<>(30);
		HashTable<User> userLogin = new HashTable<>(30);
		ArrayList<User> userArrayList = new ArrayList<>();
		HashTable<Interest> userInterest = new HashTable<>(30);
		//Create ArrayList of BST of Users
		ArrayList<BST<User>> interestArrayList = new ArrayList<>();
		String password = "", username = "", first = "", last = "";
		//Set int variable to act as seed for interestID
		int n = 1;
		File file1 = new File("users.txt");
		Scanner input = new Scanner(file1);
		//Read in users.txt file
		int j = Integer.parseInt(input.nextLine());
		Graph userGraph = new Graph(j);
		readFile(input, n, password, username, first, last, userName, userLogin, userInterest, interestArrayList, userGraph, userArrayList);
//		System.out.println(userGraph);
		//LOGIN function
		User thisUser = login(input, username, userLogin, userName, userArrayList, userInterest, interestArrayList, userGraph);
		//TODO: This is a check to make sure the right user is return by the login function. Need to delete prior to turning in
		System.out.println(thisUser);
		//Boolean variable to help keep prompting user for options
		boolean exit = false;
		//While loop to prompt user for options
		while (!exit) {
			// Print options
			printOptions();
			input = new Scanner(System.in);
			// Switch case for the input options
			switch (input.nextLine()) {
				//View My Friends (has sub-menu)- BST
				case "A":
					viewFriends(input, thisUser, userName, userInterest, interestArrayList, userGraph);
					break;
				//Search for a New Friend (has sub-menu)	-Hash table
				case "B":
					searchForFriends(input, thisUser, userName, userInterest, interestArrayList, userGraph);
					break;
				//Get Friend Recommendations (has sub-menu) - Graph
				case "C":
					getFriendRecommendations(input, thisUser, userGraph, userArrayList, userName);
					break;
				//Quit and Write Records to a File
				case "X":
					exit = true;
					System.out.print("\nYou can find a summary of our network in your files");
					writeFile(userName, userArrayList);
					break;
				default:
					System.out.print("\nInvalid menu option. Please enter A-C or X to exit.\n");
					break;
			}
		}
		// Close input
		input.close();

	}
	
	/**
	 * Reads in users from users.txt to populate in hash table for names
	 * of users, and populates hash table that is hashed by login info. 
	 * @param input - Scanner object
	 * @param n - the number of users 
	 * @param password - password of the User
	 * @param username - username of the User
	 * @param first - the firstname of the User
	 * @param last - the lastname of the User 
	 * @param userHashTable - the HashTable hashed by Users
	 * @param userHashTableLogin - the HashTable hashed by User's login info
	 * @param interestHashTable - the HashTable hashed by User's interests
	 * @param interestArrayList - the ArrayList for the interests 
	 * @param userGraph - the Graph object maintaining relations of the Users
	 * @param userArrayList - the ArrayList maintaining the User objects
	 * @throws IOException
	 * @postcondition Users are retrieved from the file and populated into HashTable
	 */
	public static void readFile(Scanner input, int n, String password, String username, String first, String last, HashTable<User> userHashTable, HashTable<User> userHashTableLogin, HashTable<Interest> interestHashTable, ArrayList<BST<User>> interestArrayList, Graph userGraph, ArrayList<User> userArrayList) throws IOException {
		//UserID starts from 1, so need an empty user for arraylist to start at 0
		userArrayList.add(new User());
		String line = "";
		//Create name comparator for sorting BST of friends
		User.NameComparator cName = new User.NameComparator();
		//Set int variable to act as seed for interestID
		int j = 0;
		//While loop to read each line of txt file
		while (input.hasNextLine()) {
			//Read each of the variables needed for a user object
			first = input.next();
			last = input.nextLine();
			//Gets rid of leading space from last name
			last = last.trim();
			username = input.nextLine();
			password = input.nextLine();

			//Create an empty BST to eventually read friends into
			BST<User> friendList = new BST<User>();
			//Create user object using previously set variables
			//Get the number of friends
			int numFriends = Integer.parseInt(input.nextLine());
			//for loop to read each name and add to friendlist
			for (int i = 0; i < numFriends; i++) {
				String friendF = input.next();
				String friendL = input.next();
				input.nextLine();
				User u = new User(friendF, friendL, false);
				friendList.insert(u, cName);
			}
			//Create an empty list to add interests to
			List<String> interestList = new List<String>();
			//Get the number of interests from txt file
			int numInterest = input.nextInt();
			//for loop to read interests into List
			input.nextLine();
			for (int i = 0; i < numInterest; i++) {
				String interest = input.nextLine();
			}
			//Create user object (friendlist is filled with user objects w/ only first+last names)

			User c = new User(first, last, username, password, friendList, interestList, n);
			//Insert user into hashTable
			userHashTable.put(c.getFirstLast(), c);
			userArrayList.add(c);
			//Set the line variable to the username/password
			line = c.getUserNameAndPass();
			//Populate the hash table that is used to log in with the full list of users with all variables filled
			userHashTableLogin.put(line, c);

			//Increment n for userID
			n++;
		}

		//Redeclare file w/ txt file
		File file1 = new File("users.txt");
		//Reset input to read txt file again
		input = new Scanner(file1);
		//While loop to read each line of txt file
		String scam = input.nextLine();
		while(input.hasNextLine()) {
			first = input.next();
			last = input.nextLine();
			last = last.trim();
			username = input.nextLine();
			password = input.nextLine();

			//Create user object to search hash table with
			User user = new User(first, last, username, password);
			user = userHashTableLogin.get(user.getUserNameAndPass(), user);
			//Search hash table for user using first+last names
			//user = userHashTable.get(user.getFirstLast(), user);
			//Get number of friends for that user
			int numFriends = input.nextInt();
			//for loop to read in friends
			BST<User> userList = new BST<>();
			user.setFriends(userList);
			for (int i = 0; i < numFriends; i++) {
				String friendF = input.next();
				String friendL = input.nextLine();
				friendL = friendL.trim();
				//Get name of friend to search hash table with
				User friendToAdd = new User(friendF, friendL, false);
				//Set first+last name of friendToAdd to variable line
				line = friendToAdd.getFirstLast();
				//Search hashtable using name
				friendToAdd = userHashTable.get(line, friendToAdd);
				//Add fully populated friend
				user.addFriend(friendToAdd);
//				System.out.println("Adding " + user.getUserID() + " to " + friendToAdd.getUserID());
				userGraph.addDirectedEdge(user.getUserID(), friendToAdd.getUserID());
			}
			//Read in the number of hobbies each user has
			int numHobbies = Integer.parseInt(input.nextLine());
			for (int i = 0; i < numHobbies; i++) {
				//Get the name of the hobby
				String hobby1 = input.nextLine();
				//Create new interest using the name
				Interest interest = new Interest(hobby1);
				//Add the interest to the List of interests in the User object
				user.addInterest(hobby1);
				//Check to see if the hashTable already contains that interest
				if (interestHashTable.contains(hobby1, interest)) {
					//Get the index for the interest
					int index = interestHashTable.get(hobby1, interest).getInterestID();
					//Go to the index in the ArrayList and add the user to the BST
					interestArrayList.get(index).insert(user, cName);
				}
				//Case: If the interest is not in the hashTable
				else{
					//Add the interestID/index to the Interest object
					interest.setId(j);
					//Insert the interest object into the HashTable
					interestHashTable.put(hobby1, interest);
					//Create an empty BST to add into the ArrayList
					BST<User> userInterestBST = new BST<>();
					//Insert the current user into the BST
					userInterestBST.insert(user, cName);
					//Insert that BST into the ArrayList at index j
					interestArrayList.add(j, userInterestBST);
					//Increment the index for the next interest that is not in the hashTable
					j++;
				}
			}
		}
		input.close();
	}

	/**
	 * Creates a new User object if the user is new, or retrieves 
	 * the User object of the returning user.
	 * @param input - the Scanner object
	 * @param username - username of the User
	 * @param userHashTable - the HashTable hashed by User
	 * @param userHashTableByName - the HashTable hashed by User's first and last name
	 * @param userArrayList - the ArrayList maintaining the User objects
	 * @param interestHashTable - the HashTable hashed by User's interests
	 * @param interestArrayList - the ArrayList for the interests 
	 * @param userGraph - the Graph object maintaining relations of the Users
	 * @return User object that is created/retrieved during login
	 * @postcondition User object is created or retrieved from database
	 */
	public static User login(Scanner input, String username, HashTable<User> userHashTable, HashTable<User> userHashTableByName, ArrayList<User> userArrayList, HashTable<Interest> interestHashTable, ArrayList<BST<User>> interestArrayList, Graph userGraph) {
		//Declare line variable
		String line = "";
		//Declare scanner
		input = new Scanner(System.in);
		//Print welcome message
		System.out.println("Welcome to Friends Interface! Please login.");
		//Prompt user for username
		User u = getUserInfo(input);
		//Set line variable to username+password
		line = u.getUserNameAndPass();
		//Create new user object using username/password
		boolean userExist = userHashTable.contains(line, u);
		boolean userCreated = false;
		//Declare variable for login attempts
		int attempts = 0;
		//Set while loop to loop while the contains function returns false
		while (!userExist) {
			attempts++;
			//Inform user of login attempts remaining
			if (attempts < 3) {
				System.out.println("\nWe could not find that user in our system. You have " + (3 - attempts)
						+ " attempts left.");
				System.out.println("\nDo you want to create your own account? It's free!");
				System.out.print("Enter your choice [Y/N]: ");
				String userChoice = input.next();
				if(userChoice.equalsIgnoreCase("Y")) {
					userCreated = true;
					break;
			}
			}
			else {
				System.out.println(
						"\nWe could not find that username in the database. Please contact the system administrator to handle your request.");
				System.exit(0);
			}
			//Kill the program if the user cannot be found
			//Prompt user for login info again
			System.out.println("\nPlease try again!");
			u = getUserInfo(input);
			userExist = userHashTable.contains(u.getUserNameAndPass(), u);
		}
		if(userCreated) {
			userGraph.resizeGraph(1);
			u = createUser(input, userHashTable, userHashTableByName, userArrayList, interestHashTable, interestArrayList, userGraph);
		}
		else {
			line = u.getUserNameAndPass();
			u = userHashTable.get(line, u);
		}
		System.out.println("\nWelcome back " + u.getFirstName() + " " + u.getLastName() + "!");
		return u;
	}

	/**
	 * Creates a User object for new users and instantiates their 
	 * information into the object
	 * @param input - the Scanner object 
	 * @param userHashTable - the HashTable hashed by User
	 * @param userHashTableByName - the HashTable hashed by User's first and last name
	 * @param userArrayList - the ArrayList maintaining the User objects
	 * @param interestHashTable - the HashTable hashed by User's interests
	 * @param interestArrayList - the ArrayList for the interests 
	 * @param userGraph - the Graph object maintaining relations of the Users
	 * @return User object that is created for new user
	 * @postcondition User object for new user is created 
	 */
	public static User createUser(Scanner input, HashTable<User> userHashTable, HashTable<User> userHashTableByName, ArrayList<User> userArrayList, HashTable<Interest> interestHashTable, ArrayList<BST<User>> interestArrayList, Graph userGraph) {
		User.NameComparator cName = new User.NameComparator();
		User potentialUser = new User();
		boolean goodToGo = false;
		System.out.print("\nWhat is your first name?: ");
		String firstName = input.next();
		System.out.print("What is your last name?: ");
		String lastName = input.next();
		while(!goodToGo) {
			System.out.print("\nWhat would you like your username to be?: ");
			String username = input.next();
			System.out.print("What would you like your password to be?: ");
			String password = input.next();
			potentialUser = new User(firstName, lastName, username, password);
			if(userHashTable.contains(potentialUser.getUserNameAndPass(), potentialUser)) {
				System.out.println("This username and password combination are currently in use. Please enter a new username/password");
			}
			else {
				BST<User> friendBST = new BST<>();
				List<String> userInterestList = new List<>();
				potentialUser.setFriends(friendBST);
				potentialUser.setInterests(userInterestList);
				potentialUser.setUserID(userArrayList.size());
				userHashTableByName.put(potentialUser.getFirstLast(), potentialUser);
				goodToGo = true;
			}
		}
		goodToGo = false;
		int j = 0;
		while(!goodToGo) {
			if(j > userHashTableByName.get(potentialUser.getFirstLast(), potentialUser).getFriendList().getSize()) {
				System.out.println("Would you like to continue to interests?");
				System.out.print("Enter your choice [Y/N]: ");
				String userChoice = input.next();
				if(userChoice.equalsIgnoreCase("Y")) {
					goodToGo = true;
				}
			}
			System.out.println("\nWould you like to add some people to your friends list? (Must add at least one friend to proceed)");
			System.out.print("Enter your choice [Y/N]: ");
			String userChoice = input.next();
			if(potentialUser.getFriendList().isEmpty() && userChoice.equalsIgnoreCase("N")) {
				System.out.println("\nYou must add at least one friend to your friend list!");
			}
			else if (userChoice.equalsIgnoreCase("Y")){
					searchForFriends(input, potentialUser, userHashTableByName, interestHashTable, interestArrayList, userGraph);
					if(!potentialUser.getFriendList().isEmpty()){
						j++;
					}
				}
			else {
				goodToGo = true;
			}
		}
		j = 0;
		goodToGo = false;
		while(!goodToGo) {
			if(j > 0) {
				System.out.println("\nWould you like to continue to submit all your information to build your profile?");
				System.out.print("Enter your choice [Y/N]: ");
				String userChoice = input.next();
				input.nextLine();
				if(userChoice.equalsIgnoreCase("Y")) {
					break;
				}
			}
			System.out.println("\nWould you like to add some interests to your profile? (Must add at least one interest to proceed)");
			System.out.print("Enter your choice [Y/N]: ");
			String userChoice = input.next();
			if(j < 1 && userChoice.equalsIgnoreCase("N")) {
				System.out.println("\nYou must add at least one interest to your profile!");
			}
			else if (userChoice.equalsIgnoreCase("Y")){
				System.out.print("\nEnter the interest you would like to add: ");
				input.nextLine();
				String interest = input.nextLine();
				Interest potentialInterest = new Interest(interest);
				if(interestHashTable.contains(interest, potentialInterest)) {
					System.out.println("\n" + interest + " was added to your list of interests!");
					userHashTableByName.get(potentialUser.getFirstLast(), potentialUser).addInterest(interest);
					j++;
				}
				else {
					System.out.println("\nThis interest does not exist in our database, please enter the information again");
				}
			}
		}
		potentialUser = userHashTableByName.get(potentialUser.getFirstLast(), potentialUser);
		userHashTable.put(potentialUser.getUserNameAndPass(), potentialUser);
		userArrayList.add(potentialUser);
		return potentialUser;
	}
	
	/**
	 * Prints out the options for the user menu and prompts the user
	 * to enter their choice. 
	 */
	public static void printOptions() {
		System.out.println("\nPlease select from the following options:\n\nA. View My Friends\n" + "B. Search For a New Friend\n" + "C. Get Friend Recommendations\n"
				+ "X. Quit and Write To File");
		System.out.print("\nEnter your choice: ");
	}

	/**
	 * Retrieves user's username and password, and creates a User object
	 * with the login info.
	 * @param input - the Scanner object 
	 * @return User object instantiated with user's login info
	 */
	public static User getUserInfo(Scanner input) {
		System.out.print("\nEnter your username: ");
		String username = input.next();
		System.out.print("Enter your password: ");
		String userPassword = input.next();
		return new User(username, userPassword, true);
	}

	/**
	 * Asks user whether they want to view, add or remove friends. If the 
	 * user selects view, their friends will be displayed. If add is clicked,
	 * it will ask for the user's, whom they want to friend, first, last, and 
	 * username, and add them. If the user wishes to delete, it will ask them 
	 * the first, last, and username of the friend they wish to remove and remove them
	 * from their friends list.
	 * @param input - Scanner object 
	 * @param thisUser - User object that is currently logged in
	 * @param hashTableUsers - the HashTable of Users
	 * @param hashTableinterest - the HashTable hashed by User's interests
	 * @param interestArrayList - the ArrayList that contains User's interests
	 * @param userGraph - the Graph object maintaining relations between the Users
	 */
	public static void viewFriends(Scanner input, User thisUser, HashTable<User> hashTableUsers, HashTable<Interest> hashTableinterest, ArrayList<BST<User>> interestArrayList, Graph userGraph) {
		User.NameComparator cName = new User.NameComparator();
		if (thisUser.getFriendList().isEmpty()) {
			System.out.println("\nYou have no friends in your friend list!");
		} else {
			System.out.print("\nPlease select an option from the menu below:\n\nA. View friend list\nB. View a friend's profile\n"
					+ "C. Remove a friend\n");
			System.out.print("\nEnter your choice: ");
			String choice = input.nextLine();
			//View friend list
			if (choice.equals("A")) {
				//Display Friends
				thisUser.printAccountsByName();
				//Follow up options to add/remove/exit to main menu
				System.out.print("\nWould you like to make changes to your friend list?\n\nPlease select an option from the menu below:\nA. Add Friend\nB. Remove Friend\nC. Exit to Main Menu");
				System.out.print("\n\nEnter your choice: ");
				String c = input.nextLine();
				//Option to add friend
				if (c.equalsIgnoreCase("A")) {
					//Call searchForFriends method
					//TODO: NEEDS SEARCH BY USERNAME IF NAME AND LASTNAME IS DUP
					searchForFriends(input, thisUser, hashTableUsers, hashTableinterest, interestArrayList, userGraph);
				}
				//Option to remove friend
				else if (c.equals("B")) {
					//TODO: NEEDS SEARCH BY USERNAME IF NAME AND LASTNAME IS DUP
					removeFriends(input, thisUser, hashTableUsers, userGraph);
				}
				//Exit to main menu
				else if (c.equals("C")) {
					return;
				} else {
					//User enters invalid option
					System.out.println("\nInvalid option!");
				}
				//Option to view a selected friend profile
			} else if (choice.equals("B")) {
				//Call getUserByName to get User object of friend who you would like to display
				User userByName = createUserByName(input);
				System.out.print("Please enter the user's username: ");
				String c = input.nextLine();
				//Search the friendlist by name
				if (thisUser.getFriendByName(userByName.getFirstName(), userByName.getLastName()) == null){
					System.out.println("\nThis user does not exist is your friends list! Cannot view profile!");
				} else {
					// System.out.println("\nUsers that has that name: ");
					try{
						ArrayList<User> userLists = new ArrayList<>();
						for(int i = 0; i < thisUser.getFriendList().getSize(); i++) {
							User userToDisplay = thisUser.getFriendList().search(userByName, cName);
							if (userToDisplay.getUserName().equals(c)) {
								userToDisplay.printUserProfile();
								i = thisUser.getFriendList().getSize();
							} else {
								User userCopy = userToDisplay;
								userLists.add(userCopy);
								thisUser.getFriendList().remove(userToDisplay, new User.NameComparator());
								userToDisplay = thisUser.getFriendList().search(userByName, cName);
								if(i == thisUser.getFriendList().getSize()-1) {
									System.out.println("\nThis user does not exist is your friends list! Cannot view profile!");
								}
							}
						}
						for(int i = 0; i < userLists.size(); i++) {
							thisUser.getFriendList().insert(userLists.get(i), new User.NameComparator());
						}
					}catch(NullPointerException e) {
						System.out.println("\nThis user does not exist is your friends list! Cannot view profile!");
					}

				}

			}
			//Remove Friend
			else if (choice.equals("C")) {
				removeFriends(input, thisUser, hashTableUsers, userGraph);
			}
		}
	}
	
	/**
	 * Asks who the User wants to remove from their friend list and removes them. 
	 * @param input - the Scanner object
	 * @param thisUser - the User object of user that is currently logged in
	 * @param hashTableUsers - the HashTable of Users 
	 * @param userGraph - the Graph object maintaining relations between the Users
	 */
	public static void removeFriends(Scanner input, User thisUser, HashTable<User> hashTableUsers, Graph userGraph){
		if(thisUser.getFriendList().isEmpty()) {
			System.out.println("You have no friends to remove!");
			return;
		}
		User userRemove = createUserByName(input);
		if(!hashTableUsers.contains(userRemove.getFirstLast(), userRemove)) {
			System.out.println("This user does not have a profile in our database");
			return;
		}
		userRemove = thisUser.getFriendList().search(userRemove, new User.NameComparator());
		if(thisUser.getFriendByName(userRemove.getFirstName(), userRemove.getLastName()) == null)  {
			System.out.println("This friend does not exist in your friend list");
			return;
		}
		System.out.print("Please enter the user's username: ");
		String c = input.nextLine();
		try{
			ArrayList<User> userLists = new ArrayList<>();

			for(int i = 0; i < thisUser.getFriendList().getSize(); i++) {
				User userToDisplay = thisUser.getFriendList().search(userRemove, new User.NameComparator());
				if (userToDisplay.getUserName().equals(c)) {
					System.out.print("Would you like to remove " + userRemove.getFirstName() + " " + userRemove.getLastName() + " from your friends list?:\n\n1. Yes\n2. No\n");
					System.out.print("\nEnter your choice (1 or 2): ");
					String userChoice = input.nextLine();
					if (userChoice.equals("1")) {
						System.out.println("\nAre you sure you want to remove " + userRemove.getFirstName()+ " " + userRemove.getLastName()
								+ " from your friends list?\n" + "\n" + "1. Yes\n" + "2. No");
						System.out.print("\nEnter your choice (1 or 2): ");
						String confirm = input.nextLine();
						if (confirm.equals("1")) {
							thisUser.removeFriend(userRemove);
							userGraph.removeDirectedEdge(thisUser.getUserID(), userRemove.getUserID());
							System.out.println("\n"+ userRemove.getFirstName() + " " + userRemove.getLastName() + " has been removed from your friends list!");
						} else if (confirm.equals("2")) {
							System.out.println("\nThis user was not removed from your friends list!\n");
						} else {
							System.out.println("\nInvalid Choice!");
						}
					} else if (userChoice.equals("2")) {
						System.out.println("\nThe user was not removed from your friends list!");
					} else {
						System.out.println("\nInvalid Choice!");
					}
					i = thisUser.getFriendList().getSize();
				} else {
					User userCopy = userToDisplay;
					userLists.add(userCopy);
					thisUser.getFriendList().remove(userToDisplay, new User.NameComparator());
					userToDisplay = thisUser.getFriendList().search(userRemove, new User.NameComparator());
					if(i == thisUser.getFriendList().getSize()-1) {
						System.out.println("\nThis user does not exist is your friends list! Cannot remove!");
					}
				}
			}
			for(int i = 0; i < userLists.size(); i++) {
				thisUser.getFriendList().insert(userLists.get(i), new User.NameComparator());
			}
		}catch(NullPointerException e) {
			System.out.println("\nThis user does not exist is your friends list! Cannot view profile!");
		}

	}

	/**
	 * Allows User to search for friends by their name or interests. If they wish 
	 * to search by name, it will prompt them for the user's first and last name, and
	 * display the people fitting their search. If they wish to search by interest, it 
	 * will prompt them to enter an interest and display people who maintain that interest.
	 * @param input - Scanner object
	 * @param thisUser - User object that is currently logged in
	 * @param hashTableUsers - the HashTable of Users
	 * @param hashTableInterest - the HashTable hashed by User's interests
	 * @param interestArrayList - the ArrayList that contains User's interests
	 * @param userGraph - the Graph object maintaining relations between the Users
	 */
	public static void searchForFriends(Scanner input, User thisUser, HashTable<User> hashTableUsers, HashTable<Interest> hashTableInterest, ArrayList<BST<User>> interestArrayList, Graph userGraph) {
		User.NameComparator cName = new User.NameComparator();
		input = new Scanner(System.in);
		System.out.println("\nSearch Users By:\n" + "\n" + "1. Name\n" + "2. Interest");
		System.out.print("\nEnter your choice (1 or 2): ");
		String choice = input.nextLine();
		// Search by Name	- Hash Table Search (name key)
		if (choice.equals("1")) {
			User userByName = createUserByName(input);
			String fullName = userByName.getFirstLast();
			if(!hashTableUsers.contains(fullName, userByName)){
				System.out.println("\nThis user does not have a profile in our database");
				return;
			}
			if (userByName.getFirstLast().equals(thisUser.getFirstLast())) {
				System.out.println("\nThe user your looking for is yourself.");
			} else if (hashTableUsers.get(fullName, userByName) != null) {
				System.out.println("\nUsers that have that name: ");
				ArrayList<User> userLists = new ArrayList<>();
				hashTableUsers.printDuplicates(fullName, userByName, userLists);
				System.out.println("\nWould you like to add anyone to your friends list?:\n" + "\n" + "1. Yes\n" + "2. No");
				System.out.print("\nEnter your choice (1 or 2): ");
				String newFriend = input.nextLine();
				if (newFriend.equals("1")) {
					System.out.print("\nWhich user would you like to add?");
					System.out.print("\nEnter the user's username: ");
					String pickFriend = input.nextLine();
					User userToAdd = null;
					for(int i = 0; i < userLists.size(); i++) {
						if(userLists.get(i).getUserName().equals(pickFriend)) {
							userToAdd = userLists.get(i);
						}
					}
					if(userToAdd == null) {
						System.out.println("\nCannot add this user. It's not one of the options!");
						return;
					}
					//User toAdd = userLists.get(pickFriend - 1);
					//int id2compare = toAdd.getUserID();
					ArrayList<User> userLists2 = new ArrayList<>();
					int add = 0;
					if(thisUser.getFriendList().isEmpty()){
						add = -1;
					}
					for (int i = add; i < thisUser.getFriendList().getSize(); i++) {
						User checkLists = thisUser.getFriendList().search(userToAdd, new User.NameComparator());
						if (userToAdd.getUserName().equals(pickFriend)) {
							if (checkLists != null) {
								if (checkLists.getUserName().equals(userToAdd.getUserName())) {
									System.out.println("\nThis user already exist is your friends list! Cannot add!");
									i = thisUser.getFriendList().getSize();
								} else {
									thisUser.addFriend(userToAdd);
									userGraph.addDirectedEdge(thisUser.getUserID(), userToAdd.getUserID());
									i = thisUser.getFriendList().getSize();
									System.out.println("\n" + userToAdd.getFirstName() + " " + userToAdd.getLastName()
											+ " has been added to your friends list!");
								}
							} else {
								thisUser.addFriend(userToAdd);
								userGraph.addDirectedEdge(thisUser.getUserID(), userToAdd.getUserID());
								i = thisUser.getFriendList().getSize();
								System.out.println("\n" + userToAdd.getFirstName() + " " + userToAdd.getLastName()
										+ " has been added to your friends list!");
							}
						} else {
							User userCopy = userToAdd;
							userLists2.add(userCopy);
							thisUser.getFriendList().remove(userToAdd, new User.NameComparator());
							userToAdd = thisUser.getFriendList().search(userByName, cName);
							if (i == thisUser.getFriendList().getSize()) {
								System.out.println("\nThis user is not an option above! Cannot add!");
							}

						}
					}
					for (int i = 0; i < userLists2.size(); i++) {
						thisUser.getFriendList().insert(userLists2.get(i), new User.NameComparator());
					}

				} else if (newFriend.equals("2")) {
					System.out.println("\nThe user was not added to your friends list!");
				} else {
					System.out.println("\nInvalid Choice!");
				}
			}
		} else if (choice.equals("2")) {// Search by Interest	- Hash Table Search (name key)
			//TODO: NEEDS SEARCH BY USERNAME IF NAME AND LASTNAME IS DUP
			System.out.print("Please enter the interest: ");
			String interestName = input.nextLine();
			Interest interest = new Interest(interestName);
			//Edge case if the interest is not in the hashTable of interests
			if(!hashTableInterest.contains(interestName, interest)) {
				System.out.println("\n" + interestName + " does not exist as an interest in our database!");
				return;
			}
			//If there are no users interested in this hobby
			if (hashTableInterest.get(interestName, interest) == null) {
				System.out.println("\nThere are no users that are interested in " + interestName + "!");
			}
			//If there are users who are interested in this hobby
			else {
				//Get the interest ID from the hashtable
				int index = hashTableInterest.get(interestName, interest).getInterestID();
				//Create a BST of users with that hobby
				BST<User> theBST = interestArrayList.get(index);
				//Display said users
				System.out.println("\nThe following people have an interest in " + interestName);
				theBST.inOrderPrint();
				//Give the option for the user to add any of these to the friend list
				System.out.println("\nWould you like to add any of these users to your friends list?:\n\n1. Yes\n2. No");
				System.out.print("\nEnter your choice (1 or 2): ");
				String userChoice = input.nextLine();
				if (userChoice.equals("1")) {
					User userByName = createUserByName(input);
					System.out.print("Please enter the user's username: ");
					String c = input.nextLine();
					if(thisUser.getUserName().equals(c)) {
						System.out.println("\nThe user you are to add is yourself. Cannot add!");
						return;
					}
					if(!hashTableUsers.contains(userByName.getFirstLast(), userByName)){
						System.out.println("\nThis user does not exist in our database");
						return;
					}

					ArrayList<User> userLists = new ArrayList<>();
					int add = 0;
					if(thisUser.getFriendList().isEmpty()){
						add = -1;
					}
					for (int i = add; i < theBST.getSize(); i++) {
						User userToAdd = theBST.search(userByName, cName);
						if (userToAdd != null) {
							if (userToAdd.getUserName().equals(c)) {
								User checkLists = thisUser.getFriendList().search(userToAdd, new User.NameComparator());
								if (checkLists != null) {
									if (checkLists.getUserName().equals(userToAdd.getUserName())) {
										System.out
												.println("\nThis user already exist is your friends list! Cannot add!");
										i = theBST.getSize();
									} else {
										thisUser.addFriend(userToAdd);
										userGraph.addDirectedEdge(thisUser.getUserID(), userToAdd.getUserID());
										i = theBST.getSize();
										System.out.println("\n" + userToAdd.getFirstName() + " "
												+ userToAdd.getLastName() + " has been added to your friends list!");
									}
								} else {
									thisUser.addFriend(userToAdd);
									userGraph.addDirectedEdge(thisUser.getUserID(), userToAdd.getUserID());
									i = theBST.getSize();
									System.out.println("\n" + userToAdd.getFirstName() + " " + userToAdd.getLastName()
											+ " has been added to your friends list!");
								}

							} else {
								User userCopy = userToAdd;
								userLists.add(userCopy);
								theBST.remove(userToAdd, new User.NameComparator());
								userToAdd = theBST.search(userByName, cName);
								if (i == theBST.getSize()) {
									System.out.println("\nThis user is not an option above! Cannot add!");
								}
							}
						} else {
							System.out.println("\nThis user is not an option above! Cannot add!");
						}
					}
					for (int i = 0; i < userLists.size(); i++) {
						theBST.insert(userLists.get(i), new User.NameComparator());
					}
				} else if (userChoice.equals("2")) {
					System.out.println("\nNo one was added to your friends list!");
				} else {
					System.out.println("\nInvalid Choice!");
				}

			}
		}

	}
	/**
	 * Creates a new User object and instantiates it with their first and last name
	 * @param input - the Scanner object
	 * @return the User object instantiated by first and last name
	 */
	public static User createUserByName(Scanner input) {
		System.out.print("Please enter the first name of the user: ");
		String fname = input.nextLine();
		System.out.print("Please enter the last name of the user: ");
		String lname = input.nextLine();
		User userByName = new User(fname, lname, false);
		return userByName;
	}

	/**
	 * Gives user friend recommendations based on their friend's information. It will
	 * display recommendations and ask user if they want to add the friend(s) that 
	 * are recommended. 
	 * @param input - the Scanner object
	 * @param thisUser - the user currently logged in 
	 * @param userGraph - the Graph object maintaining relations between the Users
	 * @param userArrayList - the ArrayList maintaining the User objects
	 * @param userHashTable - the HashTable of Users
	 */
	public static void getFriendRecommendations(Scanner input, User thisUser, Graph userGraph, ArrayList<User> userArrayList, HashTable<User> userHashTable) {
		//  View Recommendations
		if(thisUser.getFriendList().getSize() == 0) {
			System.out.println("We cannot recommend anyone because your network is empty! Please add at least one friend and retry!");
			return;
		}
		userGraph.BFS(thisUser.getUserID());
		for(int i = 1; i < userGraph.getNumVertices(); i++) {
			if(userGraph.getDistance(i) >= 2) {
				if (thisUser.getFriendList().search(userArrayList.get(i), new User.NameComparator()) == null)
					System.out.print(userArrayList.get(i).getFirstName() + userGraph.getConnection(userArrayList.get(i).getUserID(), thisUser.getUserID(), userArrayList));
					System.out.println(userArrayList.get(i));
					System.out.println();
			}
		}
		System.out.println("\nWould you like to add any of these users to your friends list?:\n\n1. Yes\n2. No");
		System.out.print("\nEnter your choice (1 or 2): ");
		String userChoice = input.nextLine();
		if(userChoice.equals("1")) {
			System.out.print("Please enter the user's username you would like to add as a friend: ");
			String u = input.nextLine();
			User friendToAdd = null;
			for(int i = 0; i < userArrayList.size(); i++) {
				if(userArrayList.get(i).getUserName().equals(u)) {
					friendToAdd = userArrayList.get(i);
					thisUser.addFriend(friendToAdd);
					i = userArrayList.size() - 1;
				}
			}
			if(friendToAdd == null) {
				System.out.println("\nThis user does not have a profile in our database");
				return;
			}
			userGraph.addDirectedEdge(thisUser.getUserID(), friendToAdd.getUserID());
			System.out.println("\nSuccessfully added " + friendToAdd.getFirstName() + " " + friendToAdd.getLastName() + " to your friend's list.");
		}else if(userChoice.equals("2")) {
			System.out.println("\nNo user was added to your friends list!");
		}else {
			System.out.println("\nInvalid Choice!");
		}

//		For this part of the project, you should include all Users with a distance >= 2 in the list of recommended friends.
//		In other words, not just friends of friends, but friends of friends of friends, and so forth.
//		The only Users that will not be recommended are those that do not have any connections (i.e. distance of -1).
//		The searching for friends by interest should be a different menu option not related to BFS.
		//  Add Friend
	}
	/**
	 * Writes the database out to the file when the user chooses to exit the interface.
	 * @param username - the HashTable hashed by username 
	 * @param userArrayList - the ArrayList storing all the Users 
	 * @throws IOException
	 */
	public static void writeFile(HashTable<User> username, ArrayList<User> userArrayList) throws IOException {
		String result = "";
		FileWriter fileWriter = new FileWriter("usersOutput.txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print((userArrayList.size()-1) + "\n");
		for (int i = 1; i < userArrayList.size(); i ++){
			User u = userArrayList.get(i);
			result += u.writeUserInfo();
		}
		printWriter.print(result);
		printWriter.close();
	}
}
