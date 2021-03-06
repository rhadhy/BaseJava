/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.sio.java.servlets;

import com.co.sio.java.dao.UsuariosDao;
import com.co.sio.java.model.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;

/**
 *
 * @author bmunoz
 */
@WebServlet(name = "Usuarios", urlPatterns = {"/Usuarios"})
public class UsuariosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Usuarios</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Usuarios at " + request.getContextPath() + "</h1>");
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
        try{
            
            UsuariosDao usuariosdao =  new UsuariosDao();//INSTANCIA DEL MODELO
            Usuarios usuarios = new Usuarios();
            String json = usuariosdao.ListaUsuarios(); //METODO PARA LISTAR DATOS
            
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache,must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.getWriter().print(json);
            response.getWriter().close();
        }
        catch(Exception e){
            System.out.println(e);
        }
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
        
         UsuariosDao usuariosdao =  new UsuariosDao();
         Usuarios usuarios =  new Usuarios();
         String operacion =  request.getParameter("oper");

           HttpSession sesion = request.getSession();
        
        Integer userid = Integer.parseInt(sesion.getAttribute("idusuario").toString());
        /*captura y seteo de variables*/
        if (operacion.charAt(0)!='d' && operacion.charAt(0)!='p' && operacion.charAt(0)!='n' ) {
             usuarios.setUsuario(request.getParameter("USUARIO"));
             usuarios.setNombres(request.getParameter("NOMBRES"));
             usuarios.setApellidos(request.getParameter("APELLIDOS"));
             usuarios.setContrasena(request.getParameter("CONTRASENA"));
             usuarios.setCorreo(request.getParameter("CORREO"));
             usuarios.setFechanacimiento(request.getParameter("FECHANACIMIENTO"));
             usuarios.setIdrol(Integer.parseInt(request.getParameter("ROL")));
             usuarios.setFechacreacion(request.getParameter("FECHACREACION"));
             usuarios.setEstado(Integer.parseInt(request.getParameter("ESTADO")));
             usuarios.setIdusuario(userid);
        }
        
        /*insercion de nuevos registros*/
        if (operacion.charAt(0)=='a') {
             try {
                usuariosdao.Insertar(usuarios);
            } catch (Exception ex) {
                Logger.getLogger(RecepcionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }/*actualizacion de registros*/
        if (operacion.charAt(0)=='e') {
            try {
                int idgrilla = 0;
                boolean idusuario = (request.getParameter("IDUSUARIO")==null);
                boolean idgrid = (request.getParameter("id")==null);

                if(!idgrid){
                   idgrilla = Integer.parseInt(request.getParameter("id")); 
                }
               if(!idusuario){
                  idgrilla = Integer.parseInt(request.getParameter("IDUSUARIO"));                 
               }
                usuarios.setIdusuario(idgrilla);
                usuariosdao.Actualizar(usuarios);
            } catch (Exception ex) {
                Logger.getLogger(RecepcionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }/*borrado de datos*/
        if (operacion.charAt(0)=='d') {
             try {
                 int idgrilla;
                 idgrilla = Integer.parseInt(request.getParameter("id")); 
                 usuarios.setIdusuario(idgrilla);
                usuariosdao.Borrar(usuarios);
            } catch (Exception ex) {
                Logger.getLogger(RecepcionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*cambio de contraseña*/
        if (operacion.charAt(0)=='p') {
            try {
               String contrasena = request.getParameter("contrasena");
               String json = usuariosdao.VerificarContrasena(sesion.getAttribute("idusuario").toString(), contrasena);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache,must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.getWriter().print(json);
                response.getWriter().close();
                
            } catch (Exception ex) {
                Logger.getLogger(RecepcionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (operacion.charAt(0)=='n') {
             try {
                 String contrasenanueva = request.getParameter("confirmar");
                usuariosdao.CambioContrasena(sesion.getAttribute("idusuario").toString(), contrasenanueva);
                
                 String [] verdadero = {"true"};
                 JSONArray json = new JSONArray(verdadero);
                 
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache,must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.getWriter().print(json);
                response.getWriter().close();
             } catch (Exception ex) {
                  Logger.getLogger(RecepcionServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
         }

        
        
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
