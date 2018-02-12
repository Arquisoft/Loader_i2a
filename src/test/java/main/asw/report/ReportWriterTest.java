package main.asw.report;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import main.asw.user.GeoCords;
import main.asw.user.User;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Pineirin
 * @since 14/02/2017.
 */
public class ReportWriterTest {

    private final static Logger log = LoggerFactory.getLogger(ReportWriterTest.class);

    @Test
    public void testTxtWriter() {

        File dir = new File("Generated/GeneratedTxt");
        dir.mkdirs();

        List<User> users = new ArrayList<>();
        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));
        users.add(new User("Lorena Castillero", new GeoCords(43.3619, 5.8494), "lorenacastillero@gmail.com", "84078892T", 1));
        users.add(new User("Jesus Atorrasagasti", new GeoCords(43.3619, 5.8494), "jesus@gmail.com", "54693254J", 1));

        ReportWriter textWriter = ReportFactory.createTxtWriter();
        textWriter.writeReport(users);

        File file = new File("Generated/GeneratedTxt/juanaza@gmail.com.txt");
        File file2 = new File("Generated/GeneratedTxt/lorenacastillero@gmail.com.txt");
        File file3 = new File("Generated/GeneratedTxt/jesus@gmail.com.txt");
        File file4 = new File("juan@gmail.com.txt");

        assertEquals(true, file.exists());
        assertEquals(true, file2.exists());
        assertEquals(true, file3.exists());
        assertEquals(false, file4.exists());

        String[] lines = readerTxt(file);
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerTxt(file2);
        assertTrue(lines[0].contains("Greetings: Lorena Castillero."));
        assertTrue(lines[3].contains("Email: lorenacastillero@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerTxt(file3);
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[4].contains("Kind: 1"));
        assertTrue(lines[7].contains("Your password is: "));

        assertEquals(true, file.delete());
        assertEquals(true, file2.delete());
        assertEquals(true, file3.delete());
        assertEquals(false, file4.delete());

        dir.delete();
    }


    @Test
    public void testDocxWriter() {

        File dir = new File("Generated/GeneratedDocx");
        dir.mkdirs();

        List<User> users = new ArrayList<>();

        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));
        users.add(new User("Lorena Castillero", new GeoCords(43.3619, 5.8494), "lorenacastillero@gmail.com", "84078892T", 1));
        users.add(new User("Jesus Atorrasagasti", new GeoCords(43.3619, 5.8494), "jesus@gmail.com", "54693254J", 1));

        ReportWriter docxWriter = ReportFactory.createDocxWriter();
        docxWriter.writeReport(users);

        File file = new File("Generated/GeneratedDocx/juanaza@gmail.com.docx");
        File file2 = new File("Generated/GeneratedDocx/lorenacastillero@gmail.com.docx");
        File file3 = new File("Generated/GeneratedDocx/jesus@gmail.com.docx");
        File file4 = new File("juan@gmail.com.txt");

        assertEquals(true, file.exists());
        assertEquals(true, file2.exists());
        assertEquals(true, file3.exists());
        assertEquals(false, file4.exists());


        String[] lines = readerDocx(file);
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerDocx(file2);
        assertTrue(lines[0].contains("Greetings: Lorena Castillero."));
        assertTrue(lines[3].contains("Email: lorenacastillero@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerDocx(file3);
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[4].contains("Kind: 1"));
        assertTrue(lines[7].contains("Your password is: "));


        assertEquals(true, file.delete());
        assertEquals(true, file2.delete());
        assertEquals(true, file3.delete());
        assertEquals(false, file4.delete());

        dir.delete();
    }


    @Test
    public void testPdfWriter() {

        File dir = new File("Generated/GeneratedPdf");
        dir.mkdirs();

        List<User> users = new ArrayList<>();

        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));
        users.add(new User("Lorena Castillero", new GeoCords(43.3619, 5.8494), "lorenacastillero@gmail.com", "84078892T", 1));
        users.add(new User("Jesus Atorrasagasti", new GeoCords(43.3619, 5.8494), "jesus@gmail.com", "54693254J", 1));

        ReportWriter pdfWriter = ReportFactory.createPdfWriter();
        pdfWriter.writeReport(users);

        File file = new File("Generated/GeneratedPdf/juanaza@gmail.com.pdf");
        File file2 = new File("Generated/GeneratedPdf/lorenacastillero@gmail.com.pdf");
        File file3 = new File("Generated/GeneratedPdf/jesus@gmail.com.pdf");
        File file4 = new File("juan@gmail.com.txt");

        assertEquals(true, file.exists());
        assertEquals(true, file2.exists());
        assertEquals(true, file3.exists());
        assertEquals(false, file4.exists());

        String filename1 = "Generated/GeneratedPdf/juanaza@gmail.com.pdf";
        String filename2 = "Generated/GeneratedPdf/lorenacastillero@gmail.com.pdf";
        String filename3 = "Generated/GeneratedPdf/jesus@gmail.com.pdf";


        String[] lines = readerPdf(filename1);
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerPdf(filename2);
        assertTrue(lines[0].contains("Greetings: Lorena Castillero."));
        assertTrue(lines[3].contains("Email: lorenacastillero@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerPdf(filename3);
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[4].contains("Kind: 1"));
        assertTrue(lines[7].contains("Your password is: "));

        assertEquals(true, file.delete());
        assertEquals(true, file2.delete());
        assertEquals(true, file3.delete());
        assertEquals(false, file4.delete());

        dir.delete();
    }

    @Test
    public void testReportWriter() {

        File dir = new File("Generated/GeneratedTxt");
        dir.mkdirs();
        File dir2 = new File("Generated/GeneratedDocx");
        dir2.mkdirs();
        File dir3 = new File("Generated/GeneratedPdf");
        dir3.mkdirs();

        List<User> users = new ArrayList<>();

        users.add(new User("Juan Aza", new GeoCords(43.3619, 5.8494), "juanaza@gmail.com", "71678798B", 1));
        users.add(new User("Jesus Atorrasagasti", new GeoCords(43.3619, 5.8494), "jesus@gmail.com", "54693254J", 1));

        ReportWriter textWriter = ReportFactory.createTxtWriter();
        ReportWriter docxWriter = ReportFactory.createDocxWriter();
        ReportWriter pdfWriter = ReportFactory.createPdfWriter();
        textWriter.writeReport(users);
        docxWriter.writeReport(users);
        pdfWriter.writeReport(users);

        File file = new File("Generated/GeneratedTxt/juanaza@gmail.com.txt");
        File file2 = new File("Generated/GeneratedTxt/jesus@gmail.com.txt");
        File file3 = new File("Generated/GeneratedDocx/juanaza@gmail.com.docx");
        File file4 = new File("Generated/GeneratedDocx/jesus@gmail.com.docx");
        File file5 = new File("Generated/GeneratedPdf/juanaza@gmail.com.pdf");
        File file6 = new File("Generated/GeneratedPdf/jesus@gmail.com.pdf");
        File file7 = new File("juan@gmail.com.txt");
        File file8 = new File("jesus@gmail.com.pdf");

        assertEquals(true, file.exists());
        assertEquals(true, file2.exists());
        assertEquals(true, file3.exists());
        assertEquals(true, file4.exists());
        assertEquals(true, file5.exists());
        assertEquals(true, file6.exists());
        assertEquals(false, file7.exists());
        assertEquals(false, file8.exists());
        String contraseña1;
        String contraseña2;
        String contraseña3;

        String[] lines = readerTxt(file);
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));
        contraseña1 = lines[7];

        lines = readerTxt(file2);
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[3].contains("Email: jesus@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));

        lines = readerDocx(file3);
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));
        contraseña2 = lines[7];
        
        lines = readerDocx(file4);
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[3].contains("Email: jesus@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));
        
        lines = readerPdf("Generated/GeneratedPdf/juanaza@gmail.com.pdf");
        assertTrue(lines[0].contains("Greetings: Juan Aza."));
        assertTrue(lines[1].contains("This is your personal information that we have received: "));
        assertTrue(lines[7].contains("Your password is: "));
        contraseña3 = lines[7];
        
        lines = readerPdf("Generated/GeneratedPdf/jesus@gmail.com.pdf");
        assertTrue(lines[0].contains("Greetings: Jesus Atorrasagasti."));
        assertTrue(lines[3].contains("Email: jesus@gmail.com"));
        assertTrue(lines[7].contains("Your password is: "));

        assertTrue(contraseña1.contains(contraseña2));
        assertTrue(contraseña1.contains(contraseña3));

        assertEquals(true, file.delete());
        assertEquals(true, file2.delete());
        assertEquals(true, file3.delete());
        assertEquals(true, file4.delete());
        assertEquals(true, file5.delete());
        assertEquals(true, file6.delete());
        assertEquals(false, file7.delete());
        assertEquals(false, file8.delete());

        new File("Generated/GeneratedTxt").delete();
        new File("Generated/GeneratedDocx").delete();
        new File("Generated/GeneratedPdf").delete();
        new File("Generated").delete();
    }


    private String[] readerTxt(File file) {
        String[] lines = new String[9];
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            int i = 0;
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
                lines[i] = sb.toString();
                i++;
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if( bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return lines;
    }

    private String[] readerDocx(File file) {
        String[] lines = new String[9];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fileInputStream);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
               lines = paragraph.getText().split("\n");
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return lines;
    }

    private String[] readerPdf(String filename1) {
        PdfReader reader = null;
        String[] lines = new String[9];

        try {

            reader = new PdfReader(filename1);

            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

            lines = textFromPage.split("\n");

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }finally {
            if( reader != null)
                reader.close();
        }

        return lines;
    }

}