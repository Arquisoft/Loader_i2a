package main.asw.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import main.asw.user.GeoCords;
import main.asw.user.User;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by MIGUEL on 21/02/2017.
 */
public class PersistenceTest {

    private DBUpdate dbUpdate;
    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> coll;
    private List<User> users;
    private long oldCount;
    private long newCount;

    @Before
    public void setUp(){
        dbUpdate = RepositoryFactory.getDBUpdate();
        mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:EIIASW2018$@ds229448.mlab.com:29448/db_loader_i2a"));
        db = mongoClient.getDatabase("db_loader_i2a");
        coll = db.getCollection("loader_i2a_collection");
        users = new ArrayList<>();
    }

    @After
    public void tearDown(){
        for (int i = 0; i < users.size(); i++){
            Document query = new Document("userId", users.get(i).getIdentifier());
            coll.deleteMany(query);
        }
    }

    /**
     * Tests the insertion of users in the DB by means of our persistence layer.
     * Checks that we cannot add two users with the same userId.
     */
    @Test
    public void testInsert(){
        insertUsers();
        //Only the non-repeated users should be in the database. We tried to insert one duplicated
        assertTrue(newCount == oldCount+users.size()-1);

        Document query = new Document("userId", users.get(3).getIdentifier());
        assertEquals((coll.count(query)), 1);
    }

    /**
     * Adds users to the database by means of our persistence layer
     */
    private void insertUsers(){
        oldCount = coll.count();
        
        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));
        users.add(new User("Lorena Castillero", new GeoCords(43.3619, 5.8494), "lorenacastillero@gmail.com", "84078892T", 1));
        users.add(new User("Jesus Atorrasagasti", new GeoCords(43.3619, 5.8494), "jesus@gmail.com", "54693254J", 1));
        users.add(new User("Pepe Antonio", new GeoCords(43.3619, 5.8494), "pepe@gmail.com", "23635383P", 1));
        
        //Same userId
        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));

        dbUpdate.insert(users);
        
        dbUpdate.writeReport();

        newCount = coll.count();
    }
}
