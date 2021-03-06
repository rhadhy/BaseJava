/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.sio.java.dao;
/**
 *
 * @author bmunoz
 */
import com.co.sio.java.db.DBControl;
import com.co.sio.java.model.Usuarios;
import com.co.sio.java.utils.SeguridadUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;


   public class LoginDao {
     DBControl db = new DBControl();
     String sql;
     ResultSet datoSql;

    public LoginDao() {
       
    
    }
    
    public Usuarios validarLogin(String user, String pass) throws Exception {
        
        try {
            Usuarios usuarios  =  new Usuarios();
           
             //(cambiar)
            String sqlstate = "SELECT USUARIO FROM CMUSUARIOS WHERE USUARIO = ?"; //sentencia para validar si el usuario ingresado es correcto
            String[] paramstate = {user};
            ArrayList<HashMap<String, Object>> consultarstate = db.consultar(sqlstate, paramstate);
            //(cambiar)
            String sqlstate2 = "SELECT ESTADO FROM CMUSUARIOS WHERE CONTRASENA = ?";//sentencia para validar si la contraseña ingresada es correcta
            String[] paramstate2 = {SeguridadUtils.encripta(pass)};
            ArrayList<HashMap<String, Object>> consultarstate2 = db.consultar(sqlstate2, paramstate2);
            
            sql = "SELECT IDUSUARIO,USUARIO,NOMBRES,APELLIDOS,CORREO,TO_CHAR(FECHANACIMIENTO,'YYYY-MM-DD')AS FECHANACIMIENTO,"
                    + "ESTADO,IDROL"
                    + " FROM CMUSUARIOS WHERE USUARIO = ? and CONTRASENA = ? and ESTADO = 1";
            String[] params = {user,SeguridadUtils.encripta(pass)};
            ArrayList<HashMap<String, Object>> consultar = db.consultar(sql, params);
            
            if (consultarstate.size()==0) {
                usuarios.setMensajes("Usuario inválido");
            }
            else if (consultarstate2.size()==0) {
                usuarios.setMensajes("Contraseña Incorrecta");
            }
            else if (consultar.size()==0) {
                usuarios.setMensajes("Usuario Inactivo");
                
            }else{
                String idusuario = String.valueOf(consultar.get(0).get("IDUSUARIO"));
                String usuario = String.valueOf(consultar.get(0).get("USUARIO"));
                String nombres = String.valueOf(consultar.get(0).get("NOMBRES"));
                String apellidos = String.valueOf(consultar.get(0).get("APELLIDOS"));
                String correo = String.valueOf(consultar.get(0).get("CORREO"));
                String fechanacimiento = String.valueOf(consultar.get(0).get("FECHANACIMIENTO"));
                String estado = String.valueOf(consultar.get(0).get("ESTADO"));
                String idrol = String.valueOf(consultar.get(0).get("IDROL"));
                usuarios.setIdusuario(Integer.parseInt(idusuario));
                usuarios.setUsuario(usuario);
                usuarios.setNombres(nombres);
                usuarios.setApellidos(apellidos);
                usuarios.setCorreo(correo);
                usuarios.setFechanacimiento(fechanacimiento);
                usuarios.setEstado(Integer.parseInt(estado));
                usuarios.setIdrol(Integer.parseInt(idrol));
                
                sql="UPDATE CMUSUARIOS SET ULTIMOACCESO = SYSDATE WHERE IDUSUARIO = ?";
                db.conectar();
                db.callableStatement(sql);
                db.AsignarParametro(1, idusuario, 2);
                db.registrar();
                 
                JSONArray arr = new JSONArray(consultar);
            }
            return usuarios;
            
        } catch (SQLException ex) {
            System.out.println(ex);
           throw new Exception(ex.getMessage());
        }
         
    }    
}
