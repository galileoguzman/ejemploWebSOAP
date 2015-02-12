/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author galileoguzman
 */
public class DAOWebService {
    private Connection con;

    public Connection conectar() {
        /*
         * Metodo conectar que no recibe parametros
         * el cual regresara un objeto de tipo conexion
         * 
         *
         */
        System.out.println("Hola conectar");
        try {
            if (this.con == null) {
                Context ctx = new InitialContext();

                DataSource mds = (DataSource) ctx.lookup("java:comp/env/jdbc/JavaDB");

                this.con = mds.getConnection();

                System.out.println("Conectado");
            }
        } catch (NamingException ne) {
            ne.printStackTrace();

            System.out.println("Error en el naming " + ne.getMessage());
        } catch (SQLException sqle) {
            sqle.printStackTrace();

            System.out.println("Error en el SQL e " + sqle.getMessage());

        }

        return this.con;
    }

    public void desconectar(Connection conexion) {
        /*
         * Metodo que se encarga de desconectar la aplicacion de la
         * base de datos
         **/
        try {
            if (!conexion.isClosed()) {
                conexion.close();
                this.con = null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }
    
    public boolean altaEstablecimiento(Establecimiento establecimiento) {
        /**
         * Metodo altaUsuario recibe como parametro un obj de la clase Usuario
         * el cual a traves de sus metodos de extracion de datos se pasaran como
         * parametros al query SQL. Retorna 1 exito -1 fallido de la operacion
         *
         */
        try {
            if (!this.con.isClosed()) {
                String query = "INSERT INTO establecimiento(id, nombre, direccion, latitud, longitud, telefono) values (?, ?, ?, ?, ?, ?)";

                PreparedStatement pstm = this.con.prepareStatement(query);
                pstm.setInt(1, establecimiento.getId());
                pstm.setString(2, establecimiento.getNombreEstablecimiento());
                pstm.setString(3, establecimiento.getDireccion());
                pstm.setString(4, establecimiento.getLatitud());
                pstm.setString(5, establecimiento.getLongitud());
                pstm.setString(6, establecimiento.getTelefono());
                pstm.execute();
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    public Establecimiento consultarEstablecimiento(Establecimiento establecimiento){

        try{
            if (!this.con.isClosed()) {
                String query = "SELECT * FROM establecimiento where id = ?";

                PreparedStatement pstm = this.con.prepareStatement(query);
                pstm.setInt(1, establecimiento.getId());
                
                ResultSet resultado = pstm.executeQuery();

                if (resultado.next()) {
                    establecimiento.setId(resultado.getInt("id"));
                    establecimiento.setNombreEstablecimiento(resultado.getString("nombre"));
                    establecimiento.setDireccion(resultado.getString("direccion"));
                    establecimiento.setLatitud(resultado.getString("latitud"));
                    establecimiento.setLongitud(resultado.getString("longitud"));
                    establecimiento.setTelefono(resultado.getString("telefono"));
                }
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

            return establecimiento;
        }

    

    public Vector consultarEstablecimientos() {
        /**
         * Metodo consultarUsuarios el cual regresara todos los usuarios
         * agregados a la Base de Datos hasta el momento. Regresa un vector de
         * la clase Vector
         *
         *
         */

        Vector vector = new Vector(10, 1);
        try {
            if (!this.con.isClosed()) {
                String query = "SELECT * FROM establecimiento";

                PreparedStatement pstm = this.con.prepareStatement(query);
                ResultSet resultado = pstm.executeQuery();

                while (resultado.next()) {
                    Establecimiento establecimiento = new Establecimiento();

                    establecimiento.setId(resultado.getInt("id"));
                    establecimiento.setNombreEstablecimiento(resultado.getString("nombre"));
                    establecimiento.setDireccion(resultado.getString("direccion"));
                    establecimiento.setLatitud(resultado.getString("latitud"));
                    establecimiento.setLongitud(resultado.getString("longitud"));
                    establecimiento.setTelefono(resultado.getString("telefono"));

                    vector.addElement(establecimiento);
                }

            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return vector;
    }
    public boolean eliminarEstablecimiento(Establecimiento establecimiento)
    {
        
    	try
    	{
    		if(!this.con.isClosed())
    		{
    			String query = "DELETE FROM establecimiento where id = "+ establecimiento.getId();

    			PreparedStatement pstm = this.con.prepareStatement(query);
    			pstm.execute();
    			return true;
    		}
    	}catch (SQLException sqle)
    	{
    		sqle.printStackTrace();
    	}
    	return false;
    }

}
