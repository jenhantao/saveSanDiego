package communication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ExchangeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        if (fileLocationHash == null) {
            initFileLocations();
        }
        if (locationRepresentativeHash == null) {
            initRepresentatives();
        }
        if (locationEmailHash == null) {
            initEmails();
        }
        response.setContentType("application/json");
        String command = request.getParameter("command");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (command.equals("sendMail")) {
                String recipient = locationEmailHash.get(request.getParameter("location"));
                String subject = request.getParameter("name") + " Wants You to Help Save San Diego"; //will get this based on location
                String message = request.getParameter("message");
                String representative = locationRepresentativeHash.get(request.getParameter("location"));
                message="Dear "+representative+",\n\n"+message;
                message = message+"\n\nSincerely,\n\n"+request.getParameter("name");
                GoogleMail.Send("savingsandiego2014", "password1123", recipient, subject, message);
            } else if (command.equals("getAreaEmail")) {
                //return supervisor name and form letter
                String location = request.getParameter("location");
                String filePath = fileLocationHash.get(location + "_report");
                filePath = fileLocationHash.get(location + "_email");
                File file = new File(filePath);
                BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String message = "";
                String line = reader.readLine();
                while (line != null) {
                    message = message + line + "\n";
                    line = reader.readLine();
                }
                String representative = locationRepresentativeHash.get(location);
                JSONObject toReturn = new JSONObject();
                toReturn.put("representative", representative);
                toReturn.put("message", message);
                out.print(toReturn.toString());
            } else if (command.equals("getAreaReportCard")) {

            } else if (command.equals("getText")) {
                //return link to text file
            }
        } catch (Exception e) {
            e.printStackTrace();

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String exceptionAsString = stringWriter.toString().replaceAll("[\r\n\t]+", "<br/>");
            if (out != null) {
                out.println("{\"result\":\"" + exceptionAsString + "\",\"status\":\"bad\"}");
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void initFileLocations() {
        String pathRoot = this.getServletContext().getRealPath("/") + "/WEB-INF/data/";
        //key location_report/email_location
        fileLocationHash = new HashMap();
        fileLocationHash.put("area1_email", pathRoot + "test");
        fileLocationHash.put("area2_email", pathRoot + "test");
        fileLocationHash.put("area3_email", pathRoot + "test");
    }

    private void initRepresentatives() {
        locationRepresentativeHash = new HashMap();
        locationRepresentativeHash.put("area1", "test representative");
        locationRepresentativeHash.put("area2", "test representative");
        locationRepresentativeHash.put("area3", "test representative");
    }

    private void initEmails() {
        locationEmailHash = new HashMap();
        locationEmailHash.put("area1", "jenhantao@gmail.com");
        locationEmailHash.put("area2", "justin.k.huang@gmail.com");
        locationEmailHash.put("area3", "test representative");
    }
    private HashMap<String, String> fileLocationHash = null;
    private HashMap<String, String> locationRepresentativeHash;
    private HashMap<String, String> locationEmailHash;

}
