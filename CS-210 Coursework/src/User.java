import java.util.concurrent.ThreadLocalRandom;
// Is this a passive or active class?
public class User implements Runnable {

	private static int sleepScale = 100;
	
	private int userID;

	private int roomNumber;
	private ChatServer chatServer;

	private boolean joinedServer;
	private boolean joinedMainRoom;

	private int wantToChat;

	/**
	 * Constructs a new User with the given user ID and ChatServer.
	 *
	 * @param userID the unique ID of the user
	 * @param chatServer the ChatServer the user is connected to
	 */
	public User(int userID, ChatServer chatServer) {
		// Set the initial value of class variables.

		// Set wantToChat to random value in range 
		// of 10 to 15.
		// Int Range (MAX, MIN) -> (int)Math.random() * (MAX-MIN+1) + MIN
		wantToChat = (int)(Math.random() * (15-10+1) + 10);
		this.userID = userID;
		this.chatServer = chatServer;
	}

	/**
	 *
	 * @return The wantToChat value
	 */
	public int getWantToChat() {
		return wantToChat;
	}

	/**
	 *
	 * @return The ID of the user
	 */
	public int getID() {
		return userID;
	}

	// Within the run method we need to code the different actions
	// a user will take when started.
	public void run() {
		try {
			// While the user is still intersted in chatting ...
			while (wantToChat > 0) {
				if (!joinedServer) {
					// Try and join Chat Server ...
					// Reduce wantToChat?
					if (chatServer.join(this) == true) {
						joinedServer = true;
						wantToChat -= 0.5;
					} else {
						wantToChat -= 0.5;
					}
				// What should the user try and do next?
				} else if (!joinedMainRoom) {
					// Try and join Main Chat Room
					if (chatServer.enterRoom(this, 0) == true) {
						joinedMainRoom = true;
						wantToChat -= 1;
					} else {
						wantToChat -= 1;
					}
				// What is the final action the user should keep
				// attempting to do?
				} else {
					if (chatServer.getNumberOfRooms() > 0) {
						Thread.sleep((int) (Math.random() * sleepScale));
						chatServer.leaveRoom(this, roomNumber);
						int randomRoomNumber = ThreadLocalRandom.current().nextInt(1, chatServer.getNumberOfRooms());
						chatServer.enterRoom(this, randomRoomNumber);
						roomNumber = randomRoomNumber;
						wantToChat -= 2;
					} else {
						wantToChat -= 2;
					}
					// Try and join a random Chat Room
				}
				Thread.sleep((int) (Math.random() * sleepScale));
			}

			chatServer.leaveRoom(this, roomNumber);
			chatServer.leave(this);

			// What should happen when the user no longer wants to chat?
			System.out.println("User stopped chatting");
		} catch (InterruptedException ex) {
			System.out.println("Interrupted User Thread (" + userID + ")");
		}
		System.out.println("User Thread (" + userID + ") has ended!");
	}

	/**
	 * Sets whether the user has joined the main room
	 * @param joined true if the user has joined the main room, false otherwise
	 */
	public void setJoinedMainRoom(boolean joined) {
		joinedMainRoom = joined;
	}

	/**
	 * The room number of the chat room the user is currently in
	 * @return room number the user is in
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Sets the room number
	 * @param roomNumber room number the user is currently in
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

}