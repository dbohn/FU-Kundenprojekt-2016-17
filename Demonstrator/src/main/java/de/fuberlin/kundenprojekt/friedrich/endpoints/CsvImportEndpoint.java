package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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

@MultipartConfig
public class CsvImportEndpoint extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fileName = uploadFile(req, "/tmp");

            String content = new String(Files.readAllBytes(Paths.get("/tmp/" + fileName)), StandardCharsets.ISO_8859_1);
            //content = toUTF8(content);
            List<User> users = parseCSV(content);
            users.forEach(UserRepository::storeUser);

            //resp(resp, content);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("./users.jsp");
    }

    private List<User> parseCSV(String content) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT
                .withDelimiter(';').withQuote('"')
                .withRecordSeparator("\n").withFirstRecordAsHeader()
                .parse(new StringReader(content));
        return StreamSupport.stream(parser.spliterator(), false)
                .map((record) -> new User(record.get("E-Mail"), record.get("Nachname"), record.get("E-Mail"), "password", record.get("Telefon"))).collect(Collectors.toList());
    }

    private String toUTF8(String input) throws UnsupportedEncodingException {
        return new String(input.getBytes(StandardCharsets.UTF_8), "UTF-8");
    }

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

    private void resp(HttpServletResponse resp, String msg) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<p>" + msg + "</p>");
        out.close();
    }
}