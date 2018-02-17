package main.asw.parser;

import main.asw.repository.DBUpdate;
import main.asw.repository.RepositoryFactory;
import main.asw.user.GeoCords;
import main.asw.user.User;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nicolas on 3/02/17 for citizensLoader0.
 */
class ParserImpl implements Parser {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(Parser.class);

	private CellLikeDataContainer dataSource;
	private List<User> users;

	ParserImpl(String filename) throws IOException {
		this.dataSource = new ApachePoiDataContainer(filename);
	}

	@Override
	public void readList() {
		try {
			loadData();
		} catch (IOException e) {
			log.error("Error handling the file");
		}
	}

	@Override
	public void insert() {
		DBUpdate dbupdate = RepositoryFactory.getDBUpdate();
		dbupdate.insert(users);
		dbupdate.writeReport();
	}

	private void loadData() throws IOException {
		List<User> users = new ArrayList<>();

		while (dataSource.nextRow()) {
			if (dataSource.getNumberOfColumns() == 5) {
				try {
					users.add(rowToUser());
				} catch (ParseException | IllegalArgumentException e) {
					// Thrown by the Date Parser
					log.error("ParseError: Error reading line " + dataSource.toString() + " , number of rows:"
							+ dataSource.getNumberOfRows() + " , number of columns:" + dataSource.getNumberOfColumns()
							+ e.getMessage(), dataSource.getCurrentRow());
				}
			} else {
				log.error("ParseError: Error reading line " + dataSource.toString()
						+ " the number of columns is different than expected", dataSource.getCurrentRow());
			}

		}
		this.users = users;
	}

	private User rowToUser() throws ParseException {
		String name = dataSource.getCell(0);
		String[] loc = dataSource.getCell(1).split(",");
		GeoCords location = new GeoCords(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
		String email = dataSource.getCell(2);
		String identifier = dataSource.getCell(3);
		int kind = (int) Double.parseDouble(dataSource.getCell(4));

		/**
		 * private String name; private GeoCords location; private String email; private
		 * String identifier; private int kind;
		 */

		return new User(name, location, email, identifier, kind);

	}

	public List<User> getUsers() {
		return users;
	}

}
