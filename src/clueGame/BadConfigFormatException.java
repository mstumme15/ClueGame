package clueGame;

public class BadConfigFormatException extends Exception {

	// Default constructor
	public BadConfigFormatException() {
		super("Error: bad configuration file format");
	}
	
	// Parameterized constructor - outputs message with file name
	public BadConfigFormatException(String fileName) {
		super("Error: " + fileName + " contains bad configuration format");
	}
	
	// Parameterized constructor - outputs message with file name and error detail
	public BadConfigFormatException(String fileName, String error) {
		super("Error: " + fileName + " contains bad " + error + " configuration format");
	}
	
}
