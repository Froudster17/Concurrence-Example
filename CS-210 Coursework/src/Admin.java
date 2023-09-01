import java.util.concurrent.ThreadLocalRandom;

// Note: This is an active class and must implemnet runnable
public class Admin implements Runnable {

	private static int sleepScale = 100;

	private String name;
	private ChatServer chatServer;
	private int numOfActions = 15;  //Could be either openning or closing a chatroom

	/**
	 Constructs an Admin object with the given name.
	 @param name the name of the admin
	 */
	public Admin(String name) {
		// Set the initial value of class variables
		this.name = name;
	}

	/**
	 Assigns a given ChatServer object to the admin.
	 @param chatServer the ChatServer object to assign
	 */
	public void assignServer(ChatServer chatServer) {
		// Store given Chat Server in Class Attribute
		this.chatServer = chatServer;
	}

	// Does this class require a run() method? If so consider how to ensure
	// when the thread is run that it performs all required actions.

	public void run() {

		// you need to open the chat server and the chat rooms

		// run actions randomly (HINT: you may use a randomised sleep time before doing the action)
		//close the chat server and the chat rooms

		try {
			// open chat server and chat rooms
			chatServer.open();
			chatServer.openChatRoom(0);

			// run actions randomly
			for (int i = 0; i < numOfActions; i++) {

				int randomAction = ThreadLocalRandom.current().nextInt(1, 2);

				if (randomAction == 1) {
					int randomRoomNumber = ThreadLocalRandom.current().nextInt(1, chatServer.getNumberOfRooms());
					chatServer.openChatRoom(randomRoomNumber);
				}
				else {
					int randomRoomNumber = ThreadLocalRandom.current().nextInt(1, chatServer.getNumberOfRooms());
					chatServer.closeChatRoom(randomRoomNumber);
				}


				// sleep for a random amount of time
				Thread.sleep((int) (Math.random() * sleepScale));
			}

			for (ChatRoom room : chatServer.getChatRooms()) {
				room.close();
			}

			chatServer.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}