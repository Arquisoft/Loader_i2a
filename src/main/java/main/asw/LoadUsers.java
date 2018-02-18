package main.asw;

import main.asw.parser.Parser;
import main.asw.parser.ParserFactory;
import main.asw.repository.PersistenceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main application
 *
 * @author Labra
 * @author MIGUEL
 */

public class LoadUsers {

	private final static Logger log = LoggerFactory.getLogger(LoadUsers.class);

	public static void main(String... args) {
		if (args.length == 3) {
			try {
				PersistenceFactory.getUserDAO().setMongoHost(args[1]);
				Parser parser = ParserFactory.getParser(args[0], args[2]);
				parser.readList();
				parser.insert();
			} catch (IOException e) {
				printUsage();
				log.error("Error connecting to the database or parsing the file. \n Arguments: MongoHOST: " + args[1] + 
							"Files: " + args[0] + ", " + args[2]);
			}
		} else {
			printUsage();
			log.error("Wrong number of parameters during the main execution");
			throw new IllegalArgumentException("Wrong number of parameters");
		}
	}

	private static void printUsage(){
		System.out.println("Invalid parameters. You must only have:\n"
				+ "\t <xls path> \t <mongo host> \t <CSV path>");
	}

}
