package communication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
                message = "Dear " + representative + ",\n\n" + message;
                message = message + "\n\nSincerely,\n\n" + request.getParameter("name");
                GoogleMail.Send("savingsandiego2014", "password1123", recipient, subject, message);
            } else if (command.equals("getAreaEmail")) {
                //return supervisor name and form letter
                String pathRoot = this.getServletContext().getRealPath("/") + "/data/text/";
                String location = request.getParameter("location");
                String filePath = pathRoot+location + ".txt";
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
                String user = request.getParameter("user");

                //write text file
                String pathRoot = this.getServletContext().getRealPath("/") + "/data/";

                String message = request.getParameter("message");
                String representative = locationRepresentativeHash.get(request.getParameter("location"));
                message = "Dear " + representative + ",\n\n" + message;
                message = message + "\n\nSincerely,\n\n" + request.getParameter("name");
                File outFile = new File(pathRoot + user + ".txt");
                FileWriter fw = new FileWriter(outFile);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(message);
                bw.close();
                fw.close();
                JSONObject toReturn = new JSONObject();
                toReturn.put("filePath", "data/" + user + ".txt");
                out.write(toReturn.toString());
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

    private void initRepresentatives() {
        locationRepresentativeHash = new HashMap();
        locationRepresentativeHash.put("test", "Jenhan Tao");
        locationRepresentativeHash.put("District1", "Greg Cox");
        locationRepresentativeHash.put("District2", "Dianne Jacob");
        locationRepresentativeHash.put("District3", "Dave Roberts");
        locationRepresentativeHash.put("District4", "Ron Roberts");
        locationRepresentativeHash.put("District5", "Bill Horn");

    }

    private void initEmails() {
        locationEmailHash = new HashMap();
        locationEmailHash.put("District1", "greg.cox@sdcounty.ca.gov");
        locationEmailHash.put("District2", "dianne.jacob@sdcounty.ca.gov");
        locationEmailHash.put("District3", "dave.roberts@sdcounty.ca.gov");
        locationEmailHash.put("District4", "ron-roberts@sdcounty.ca.gov");
        locationEmailHash.put("District5", "bill.horn@sdcounty.ca.gov");
        locationEmailHash.put("test", "jenhantao@gmail.com");

    }
    private HashMap<String, String> locationRepresentativeHash;
    private HashMap<String, String> locationEmailHash;

}
