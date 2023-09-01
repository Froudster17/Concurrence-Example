import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatServer {
	
	private ArrayList<ChatRoom> rooms;
	private List<User> users;
	private Admin admin;

	private int capacity;
	private boolean isOpen;

	/**
	 * Creates a new ChatServer object.
	 *
	 * @param capacity The maximum number of users that can be in the server at once.
	 * @param numOfRooms The number of chat rooms in the server.
	 * @param admin The admin for the server.
	 */
	public ChatServer(int capacity, int numOfRooms, Admin admin) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.

		// We initalise the admin attribute and call the
		// assignServer method of the admin with this object
		// as the parameter.
		this.admin = admin;
		admin.assignServer(this);
		this.capacity = capacity;
		this.rooms = new ArrayList<ChatRoom>();
		this.users = new ArrayList<User>();
		for (int i = 0; i < numOfRooms; i++) {
			ChatRoom chatRoom = new ChatRoom(i, capacity);
			rooms.add(chatRoom);
		}
	}

	// Consider if this should be run asynchronously.

	/**
	 * Opens the chat server
	 */
	public void open() {
		// Code to open the Chat Room.
		isOpen = true;
		System.out.println("Chat Server is Opened.");
	}
	
	// Consider if this should be run asynchronously.

	/**
	 * Closes the chat server
	 */
	public void close() {
		// Code to close the Chat Server.
		// Think carefully about when you can successfully 
		// close the Chat Server.
		isOpen = false;
		System.out.println("Chat Server is being Closed.");

		for (ChatRoom room : rooms) {
			room.close();
			}

		for (User user : users) {
			System.out.println("User " + user.getID() + " has been removed from the chat server.");
		}
		users.clear();
		System.out.println("Chat Server is Closed.");
	}

	// Consider if this should be run asynchronously.

	/**
	 * Allows a user to join the chat server.
	 *
	 * @param user The user to join the server.
	 * @return true if the user successfully joined, false otherwise.
	 * @throws InterruptedException If there is an interruption while waiting for space in the server.
	 */
	public synchronized boolean join(User user) throws InterruptedException {
		// Code for a User to enter the Chat Server.
		// Consider conditions that need to be true for this 
		// to be successful.
		// Returns true if joined successfully, false otherwise.

		if (!isOpen) {
			System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ") because the server is not open.");
			return false;
		}

		if (users.contains(user)) {
			System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ") because they are already in the server.");
			return false;
		}

		while (users.size() >= capacity) {
			wait();
		}

		users.add(user);
		System.out.println("User " + user.getID() + " admitted to Chat Server (" + user.getWantToChat() + ").");
		return true;
	}

	// Consider if this should be run asynchronously.
	/**
	 * Allows a user to leave the chat server.
	 *
	 * @param user The user to leave the server.
	 */
	public synchronized void leave(User user) {
		// Code for a User to leave the Chat Server.
		if (users.remove(user)) {
			notifyAll();
			System.out.println("User " + user.getID() + " left the Chat Server.");
		} else {
			System.out.println("Could not remove User " + user.getID() + " as they are not in the Chat Server.");
		}
	}

	/**
	 * Opens a chat room with a given chatRoomID
	 *
	 * @param chatRoomID The chatRoomID to open the room
	 */
	public void openChatRoom(int chatRoomID) {
		// Code to open Chat Room.
		for (ChatRoom room : rooms) {
			if (room.getChatRoomID() == chatRoomID) {
				room.open();
			} else {
				System.out.println("No chat room with this id: " + chatRoomID);
			}
		}
	}

	/**
	 * Closes a chat room with a given chatRoomID
	 *
	 * @param chatRoomID the chatRoomID to close the room
	 */
	public void closeChatRoom(int chatRoomID) {
		// Code to close Chat Room.
		for (ChatRoom room : rooms) {
			if (room.getChatRoomID() == chatRoomID) {
				room.close();
			} else {
				System.out.println("No chat room with this id: " + chatRoomID);
			}
		}
	}

	/**
	 * Allows a user to join a ChatRoom
	 *
	 * @param user The user to join a ChatRoom
	 * @param chatRoomID The chatRoomID
	 * @return true if the user successfully joined a room, false otherwise.
	 */
	public synchronized boolean enterRoom(User user, int chatRoomID) {
		// Code to allow user to enter Chat Room.
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getChatRoomID() == chatRoomID && rooms.get(i).getIsOpen()) {
				rooms.get(i).enterRoom(user);
				user.setRoomNumber(i);
				return true;
			}
		}
		System.out.println("User:" + user.getID() + " failed to join room");
		return false;
	}

	/**
	 * Allows a user to leave a ChatRoom
	 *
	 * @param user The user to leave a ChatRoom
	 * @param chatRoomID The ChatRoomID
	 */
	public synchronized void leaveRoom(User user, int chatRoomID) {
		// Code to allow user to leave Chat Room.
		for (ChatRoom room : rooms) {
			if (room.getChatRoomID() == chatRoomID) {
				room.leaveRoom(user);
			}
		}

	}

	/**
	 * Gets the number of rooms
	 *
	 * @return A list of ChatRooms
	 */
	public int getNumberOfRooms() {
		return rooms.size();
	}


	/**
	 *
	 * @param chatRoomID The chatRoomID
	 * @return true if chat room is opem, false otherwise
	 */
	public boolean isRoomOpen(int chatRoomID) {
		return rooms.get(chatRoomID).getIsOpen();
	}

	/**
	 *
	 * @return The number of users in the ChatServer
	 */
	public int getNumberOfUsers() {
		return users.size();
	}

	/**
	 *
	 * @return A list of all chat rooms
	 */
	public List<ChatRoom> getChatRooms() {
		return rooms;
	}

}