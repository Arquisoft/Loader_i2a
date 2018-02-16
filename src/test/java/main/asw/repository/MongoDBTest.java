package main.asw.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import main.asw.user.GeoCords;
import main.asw.user.User;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by MIGUEL on 16/02/2017.
 * <p>
 * This class runs tests against a temporal database (in-memory)
 */
public class MongoDBTest {

    private static final String MONGO_HOST = "mongodb://admin:EIIASW2018$@ds237808.mlab.com:37808/db_test";
//    private static final String IN_MEM_CONNECTION_URL = MONGO_HOST + ":" + MONGO_PORT;

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoClient mongoClient;

    /**
     * Deploys an in-memory database for simple testing
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        //MongodStarter runtime = MongodStarter.getDefaultInstance();
        //mongodExe = runtime.prepare(new MongodConfig(Version.V2_0_5, MONGO_PORT, Network.localhostIsIPv6()));
        //mongod = mongodExe.start();
    	mongoClient = new MongoClient(new MongoClientURI(MONGO_HOST));
    }

    /**
     * Shuts down the in-memory DB
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        /*if (mongod != null) {
            mongod.stop();
            mongodExe.stop();
        }*/
    }

    /**
     * Tests user insertion on DB
     */
    @Test
    public void testUserInsertion() {
        MongoDatabase db = mongoClient.getDatabase("db_test");
        db.getCollection("users").deleteMany(new BsonDocument());
        MongoCollection<Document> coll = db.getCollection("users");
        User u = new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1);
        Document doc = new Document("name", u.getName())
        		.append("location", u.getLocation())
                .append("email", u.getEmail())
                .append("identifier", u.getIdentifier())
                .append("kind", u.getKind())
                .append("password", u.getPassword());
        coll.insertOne(doc);

        assertEquals(1, coll.count());
        assertEquals("Juan Aza", coll.find().first().get("name"));
        assertEquals(doc.toJson(), coll.find().first().toJson());

        db.getCollection("users").deleteMany(new BsonDocument());
    }

}
