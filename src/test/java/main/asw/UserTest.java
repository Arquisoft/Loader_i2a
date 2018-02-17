package main.asw;

import main.asw.user.GeoCords;
import main.asw.user.User;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nicolas on 18/02/17 for citizensLoader0.
 */
public class UserTest {

    private Date parseDate(String birthDateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        df.setLenient(false);
        date = df.parse(birthDateString);
        return date;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadEmail() throws ParseException {
        new User("Juan Aza", new GeoCords(43.3619, 5.8494), "badEmailWhitoutDot", "71678798B", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadNif() throws ParseException {
    	new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juan@gmail.com", "71735454J", 1);
    }
}
