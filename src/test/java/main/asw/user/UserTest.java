package main.asw.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import org.junit.Test;
import main.asw.LoadUsers;
import main.asw.user.GeoCords;
import main.asw.user.User;

/**
 * Created by nicolas on 18/02/17 for citizensLoader0.
 */
public class UserTest {
	String mongoUri = "mongodb://admin:aswadmin2018@ds159489.mlab.com:59489/aswdb";
    @Test
    public void testLoadUsers(){
    	String file1 = "src/test/resources/pruebaUsuarios.xls";
  		String host = mongoUri;
    	String file2 = "src/test/resources/agentTypes.csv";
    	LoadUsers.main(file1, host, file2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testLoadUsersWrongArguments(){
    	String file1 = "src/test/resources/pruebaUsuarios.xls";
  		String host = mongoUri;
    	String file2 = "src/test/resources/agentTypes.csv";
    	LoadUsers.main(file1, host, file2, "hello");
    }
    
    @Test
	public void testCorrectUser() throws ParseException {
		GeoCords gc = new GeoCords(43.3619, 5.8494);
		User u = new User("Juan Aza", gc, "juan@gmail.com", "71678798B", 1, "PERSON");
		assertEquals(u.getName(), "Juan Aza");
		assertEquals(u.getEmail(), "juan@gmail.com");
		assertEquals(u.getKind(), 1);
		assertEquals(u.getLocation(), gc.toString());
		assertEquals(u.getKindCode(),"PERSON");
		assertEquals(u.toString(), "User [name=" + u.getName() + ", location=" + gc.toString() + ", email="
				+ u.getEmail() + ", identifier=" + u.getIdentifier() + ", kind=" + u.getKind() + "]");
		assertTrue(gc.getLat() == 43.3619);
		assertTrue(gc.getLng() == 5.8494);
	}

    @Test(expected = IllegalArgumentException.class)
    public void testBadEmail() throws ParseException {
        new User("Juan Aza", new GeoCords(43.3619, 5.8494), "badEmailWhitoutDot", "71678798B", 1, "person");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadNif() throws ParseException {
    	new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juan@gmail.com", "71735454J", 1,"person");
    }
}
