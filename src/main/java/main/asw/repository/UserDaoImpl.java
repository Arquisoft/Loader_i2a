package main.asw.repository;

import static com.mongodb.client.model.Filters.eq;

import java.io.FileInputStream;
import java.util.Properties;

import org.bson.Document;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import main.asw.user.User;

public class UserDaoImpl implements UserDao {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserDao.class);
	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<Document> coll;
	private Properties properties;
	String mongoUri = "mongodb://admin:aswadmin2018@ds159489.mlab.com:59489/aswdb";

	public UserDaoImpl() {
		if (loadProperties()) {
			this.mongoClient = new MongoClient(new MongoClientURI(mongoUri));
			this.db = mongoClient.getDatabase(properties.getProperty("database"));
			this.coll = db.getCollection(properties.getProperty("collection"));
		}
	}

	private boolean loadProperties() {
		try {
			FileInputStream input = new FileInputStream("src/main/resources/database.properties");
			this.properties = new Properties();
			this.properties.load(input);
			return true;
		} catch (Exception e) {
			log.error("Error loading database.properties file");
			return false;
		}
	}

	/**
	 * Saves a given user in the database if there ins't already one with the same
	 * userId
	 * 
	 * @param u
	 *            User to be saved
	 * 
	 */
	@Override
	public boolean saveUser(User u) {
		if (coll.find(eq("userId", u.getIdentifier())).first() == null) {
			Document doc = new Document("name", u.getName()).append("location", u.getLocation())
					.append("email", u.getEmail()).append("userId", u.getIdentifier()).append("kind", u.getKindCode())
					.append("kindCode", u.getKind()).append("password", u.getPassword());
			coll.insertOne(doc);
			log.info("User with userId = " + u.getIdentifier() + " added to the database");
			return true;
		} else {
			log.warn("A user with userId = " + u.getIdentifier() + " is already in the database");
			return false;
		}
	}

	@Override
	public void setMongoHost(String host) {
		if (host.equals("localhost")) {
			mongoClient = new MongoClient(host, 27017);
			db = mongoClient.getDatabase("aswdb");
			coll = db.getCollection("users");
		} else {
			if (loadProperties()) {
				this.mongoClient = new MongoClient(new MongoClientURI(host));
				this.db = mongoClient.getDatabase(properties.getProperty("database"));
				this.coll = db.getCollection(properties.getProperty("collection"));
			}
		}

	}

}
