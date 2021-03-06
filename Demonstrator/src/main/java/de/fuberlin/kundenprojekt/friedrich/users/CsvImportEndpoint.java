package de.fuberlin.kundenprojekt.friedrich.users;

import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Endpoint to handle CSV import.
 *
 * @author Team Friedrich
 */
@MultipartConfig
@WebServlet("/csvimport")
public class CsvImportEndpoint extends HttpServlet {

    @Inject
    PasswordHasher passwordHasher;

    /**
     * Import a CSV user list as exported from the BCS.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fileName = uploadFile(req, "/tmp");

            String content = new String(Files.readAllBytes(Paths.get("/tmp/" + fileName)), StandardCharsets.ISO_8859_1);
            List<User> users = parseCSV(content);
            users.forEach(UserRepository::storeUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("status", "CSV file imported!");

        req.getRequestDispatcher("WEB-INF/users.jsp").forward(req, resp);
    }

    /**
     * Parse the contents of the uploaded CSV file
     *
     * @param content The contents of the uploaded CSV file
     * @return the created users
     * @throws IOException if reading the csv data fails
     */
    private List<User> parseCSV(String content) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT
                .withDelimiter(';').withQuote('"')
                .withRecordSeparator("\n").withFirstRecordAsHeader()
                .parse(new StringReader(content));
        return StreamSupport.stream(parser.spliterator(), false)
                .map((record) -> new User(
                        record.get("E-Mail"),
                        record.get("Nachname"),
                        record.get("E-Mail"),
                        passwordHasher.hash("password"),
                        record.get("Telefon")
                )).collect(Collectors.toList());
    }

    /**
     * Store an uploaded file on the server filesystem
     *
     * @param req     The incoming request
     * @param baseDir Where to put the uploaded file
     * @return The file path of the uploaded file
     * @throws IOException      if write fails
     * @throws ServletException if something with reading the request fails
     */
    private String uploadFile(HttpServletRequest req, String baseDir) throws IOException, ServletException {
        InputStream isr = null;
        FileOutputStream outputStream = null;
        String fileName = "";
        try {

            for (Part p : req.getParts()) {
                isr = req.getPart(p.getName()).getInputStream();
                int bufferSize = isr.available();
                byte[] buffer = new byte[bufferSize];

                String partHeader = p.getHeader("content-disposition");

                isr.read(buffer);

                for (String temp : partHeader.split(";")) {
                    if (temp.trim().startsWith("filename")) {
                        fileName = temp.substring(temp.indexOf("=") + 1).trim().replace("\"", "");
                    }
                }

                outputStream = new FileOutputStream(baseDir + "/" + fileName);
                outputStream.write(buffer);
                outputStream.close();
                isr.close();
            }
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return fileName;
    }
}
