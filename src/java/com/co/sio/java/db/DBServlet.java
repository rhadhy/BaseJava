/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.sio.java.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author fmoctezuma
 */
@WebServlet(name = "DBServlet", urlPatterns = {"/DBServlet"})
public class DBServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBControl db = new DBControl();

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DBServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DBServlet at " + request.getContextPath() + "</h1>");

            try {
               // String query = "SELECT * FROM CMUSUARIOS WHERE idusuario = ? and usuario = ?";
                
                String query = "SELECT  r.id,r.fecha_recibido,r.nit,r.prestador,\n" +
                    "r.remitente,r.fecha_entrega,r.tipo_documento,r.numero_guia,r.cd,r.usb,r.detalle,\n" +
                    "r.entregado_a,r.entregado_por,(CONCAT(u.nombres,CONCAT(' ', u.apellidos ))) as usuario\n" +
                    "FROM CMRECEPCION r INNER JOIN CMUSUARIOS u\n" +
                    "ON r.idusuario = u.idusuario\n" +
                    "";
                //String[] params = {request.getParameter("user"), request.getParameter("usuario")};
                String[] params = null;
                
                ArrayList<HashMap<String, Object>> consultar = db.consultar(query, params);

                out.println(consultar.size() + "</br>");

                JSONArray arr = new JSONArray(consultar);

                out.println(arr.toString());

            } catch (SQLException ex) {
                //Logger.getLogger(DBServlet.class.getName()).log(Level.SEVERE, null, ex);

                out.println(ex.getErrorCode() + "-" + ex.getLocalizedMessage());
                out.println("</br>");
                out.println("<pre>");
                ex.printStackTrace(out);
                out.println("</pre>");
            }

            out.println("</body>");
            out.println("</html>");
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

}
