package main.asw.parser;

import main.asw.user.User;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 15/02/17.
 */
public class ParserTest {

	private final static String BASE_PATH = "src/test/resources/";
	private final static String TEST_OK_FILE_NAME = "pruebaUsuarios.xls";
	private final static String TEST_WRONG_AGENT_TYPE = "pruebaUsuarios2.xls";
	private final static String TEST_BAD_DATE_AFTER_TODAY = "badDateAfterToday.xls";
	private final static String TEST_BAD_DATE_FORMAT = "badDateFormat.xls";
	private final static String TEST_NO_FOUND_FILE = "noExiste";
	private static final String TEST_LESS_LINES = "lessLines.xls";
	private static final String TEST_MORE_LINES = "moreLines.xls";
	private static final String TEST_WITH_ERRORS = "test-errors.xls";
	private final static String TEST_CSV_AGENTS = "agentTypes.csv";

	private ParserImpl parser;

	@Test
	public void testAllOKFile() throws IOException, ParseException {
		try {
			parser = ParserFactory.getParser(BASE_PATH + TEST_OK_FILE_NAME,
					BASE_PATH + TEST_CSV_AGENTS);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String baseName = "Prueba";
		String baseEmail = "prueba";
		int kind = 1;
		parser.readList();
		assertEquals(20, parser.getUsers().size());
		for (int i = 0; i < parser.getUsers().size(); i++) {
			String index = (i + 1 < 10) ? "0" + (i + 1) : (i + 1) + "";
			User user = parser.getUsers().get(i);
			assertEquals(baseName + index, user.getName());
			assertEquals(baseEmail + index + "@prueba.es", user.getEmail());
			assertEquals(kind, user.getKind());
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWrongTypes() throws IOException, ParseException {
		try {
			parser = ParserFactory.getParser(BASE_PATH + TEST_WRONG_AGENT_TYPE,
					BASE_PATH + TEST_CSV_AGENTS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parser.readList();

	}

	@Test(expected = IOException.class)
	public void testNoFoundFile() throws IOException {
		parser = ParserFactory.getParser(BASE_PATH + TEST_NO_FOUND_FILE,
				BASE_PATH + TEST_CSV_AGENTS);
	}

	@Test
	public void testMoreLines() throws IOException, ParseException {
		parser = ParserFactory.getParser(BASE_PATH + TEST_MORE_LINES, BASE_PATH
				+ TEST_CSV_AGENTS);
		parser.readList();
		assertEquals(0, parser.getUsers().size());
	}

	@Test
	public void testLessLines() throws IOException, ParseException {
		parser = ParserFactory.getParser(BASE_PATH + TEST_LESS_LINES, BASE_PATH
				+ TEST_CSV_AGENTS);
		parser.readList();
		assertEquals(0, parser.getUsers().size());
	}

	@Test(expected = NumberFormatException.class)
	public void testWithParseErrors() throws IOException, ParseException {
		parser = ParserFactory.getParser(BASE_PATH + TEST_WITH_ERRORS,
				BASE_PATH + TEST_CSV_AGENTS);
		parser.readList();
	}

	@Test
	public void testCSVNotFound() throws IOException, ParseException {
		parser = ParserFactory.getParser(BASE_PATH + TEST_OK_FILE_NAME,
				BASE_PATH + TEST_NO_FOUND_FILE);
	}
	


}
