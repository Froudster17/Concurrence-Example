import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatRoom {

	private int chatRoomID;
	private int capacity;
	
	private List<User> users;
	private boolean isOpen;

	/**
	 * Constructs a new ChatRoom with the specified ID and capacity.
	 * @param chatRoomID The ID of the chat room.
	 * @param capacity The maximum number of users that can join the chat room.
	 */
	public ChatRoom(int chatRoomID, int capacity) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.
		users = new ArrayList<>();
		this.chatRoomID = chatRoomID;
		this.capacity = capacity;
	}

	// Consider if this should be run asynchronously.
	/**
	 * Opens the Chat Room.
	 */
	public void open() {
		// Code to open the Chat Room.
		isOpen = true;
		System.out.println("Chat Room " + chatRoomID + " open!");
	}
	
	// Consider if this should be run asynchronously.
	/**
	 * Closes the Chat Room.
	 */
	public void close() {
		// Code to close the Chat Room.
		// Think carefully about when you can successfully 
		// close the Chat Room.

		isOpen = false;
		System.out.println("Chat Room " + chatRoomID + " is being closed!");
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			System.out.println("User " + user.getID() + " has been removed from chat room " + chatRoomID);
			iterator.remove();
		}
		System.out.println("Chat Room " + chatRoomID + " closed!");
	}

	// Consider if this should be run asynchronously.
	/**
	 * Allows a user to enter the chat room.
	 * @param user The user who wants to join the chat room.
	 * @return True if the user joined successfully, false otherwise.
	 */
	public synchronized boolean enterRoom(User user) {
		// Code for a User to enter a Chat Room.
		// Consider conditions that need to be true for this 
		// to be successful. 
		// Returns true if joined successfully, false otherwise.

		if (users.size() >= capacity) {
			System.out.println("User " + user.getID() + " could not join Chat Room " + chatRoomID + " due to room capacity. (" + user.getWantToChat() + ")");
			return false;
		}

		if (users.contains(user)) {
			System.out.println("User is already in Chat Room " + chatRoomID);
			return false;
		}

		users.add(user);
		System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
		return true;
	}

	// Consider if this should be run asynchronously.
	/**
	 * Allows a user to leave the chat room.
	 * @param user The user who wants to leave the chat room.
	 */
	public void leaveRoom(User user) {
		// Code for a User to leave a Chat Room.
		users.remove(user);
		System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
	}

	/**
	 * Returns whether the chat room is open or not.
	 * @return True if the chat room is open, false otherwise.
	 */
	public boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * Returns the ID of the chat room.
	 * @return The ID of the chat room.
	 */
	public int getChatRoomID() {
		return chatRoomID;
	}

	/**
	 * Sets the ID of the chat room.
	 * @param chatRoomID The new ID of the chat room.
	 */
	public void setChatRoomID(int chatRoomID) {
		this.chatRoomID = chatRoomID;
	}
}