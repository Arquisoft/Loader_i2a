package main.asw.parser;

import main.asw.repository.DBUpdate;
import main.asw.repository.RepositoryFactory;
import main.asw.user.GeoCords;
import main.asw.user.User;

import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 3/02/17 for citizensLoader0.
 */
class ParserImpl implements Parser {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(Parser.class);

	private CellLikeDataContainer dataSource;
	private String csvFile;
	private List<User> users;

	ParserImpl(String filename, String csvFilename) throws IOException {
		this.dataSource = new ApachePoiDataContainer(filename);
		this.csvFile = csvFilename;
	}

	@Override
	public void readList() {
		try {
			loadData();
		} catch (IOException e) {
			log.error("Error handling the file");
		} catch (ParseException e) {
			log.error("Error parsing the file");
		}
	}

	@Override
	public void insert(String host) {
		DBUpdate dbupdate = RepositoryFactory.getDBUpdate();
		dbupdate.insert(users, host);
		dbupdate.writeReport();
	}

	private void loadData() throws IOException, ParseException {
		List<User> users = new ArrayList<>();

		while (dataSource.nextRow()) {
			if (dataSource.getNumberOfColumns() == 5) {
				users.add(rowToUser());
			} else {
				log.error(
						"ParseError: Error reading line " + dataSource.toString() + " , number of rows: "
								+ dataSource.getNumberOfRows() + " , number of columns: "
								+ dataSource.getNumberOfColumns() + "the number of columns is different than expected",
						dataSource.getCurrentRow());
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
		String kindCode = identifyAgentType(kind);
		if (kindCode!=null) {
			return new User(name, location, email, identifier, kind, kindCode);
		} else
			throw new IllegalArgumentException("The kind of Agent is not correct");
	}

	/**
	 * This method parses the CSV file in order to make sure that the type of agent
	 * is allowed
	 * 
	 * @param kind
	 * @return true if it exists
	 */
	private String identifyAgentType(int kind) {
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] agentType = line.split(",");
				if (kind == Integer.valueOf(agentType[0])) {
					return agentType[1];
				} else
					continue;
			}
			br.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> getUsers() {
		return users;
	}

}
