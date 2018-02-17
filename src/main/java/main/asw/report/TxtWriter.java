package main.asw.report;

import main.asw.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Pineirin
 * @since 14/02/2017.
 */
class TxtWriter implements ReportWriter {

    private final static Logger log = LoggerFactory.getLogger(TxtWriter.class);

    @Override
    public void writeReport(List<User> users) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        for (User user : users)
            try {
                fileWriter = new FileWriter("Generated/GeneratedTxt/" + user.getEmail() + ".txt");
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Greetings: " + user.getName() + ".\n"
                        + "This is your personal information that we have received: \n"
                		+ "Location: " + user.getLocation() + ".\n"
                		+ "Email: " + user.getEmail() + ".\n"
                        + "Kind: " + user.getKind() + ".\n"
                        + "\n"
                        + "Your user name is your identifier: " + user.getIdentifier() + "\n"
                        + "Your password is: " + user.getUnencryptedPass());
                log.info("Exported user with userId = " + user.getIdentifier() + " correctly to TXT format");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (IOException ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
    }
}
