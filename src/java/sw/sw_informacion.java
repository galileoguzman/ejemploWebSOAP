/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw;

import java.io.IOException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.lang.String;
import java.sql.Connection;

/**
 *
 * @author galileoguzman
 */
@WebService(serviceName = "sw_informacion")
public class sw_informacion {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "suma")
    public int suma(@WebParam(name = "elemento1") int elemento1, @WebParam(name = "elemento2") int elemento2) {
        //TODO write your implementation code here:
        return elemento1 + elemento2;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "conectar")
    public boolean conectar(@WebParam(name = "id") int id) {
        DAOWebService control = new DAOWebService();
        Establecimiento e = new Establecimiento();
        
        e.setId(id);
        control.conectar();
        
        return true;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "nombreEstablecimiento")
    public String nombreEstablecimiento(@WebParam(name = "id") int id) {
        //TODO write your implementation code here:
        DAOWebService control = new DAOWebService();
        Establecimiento e = new Establecimiento();
        Connection c = control.conectar();
        e.setId(id);
        e = control.consultarEstablecimiento(e);
        control.desconectar(c);
        return e.getNombreEstablecimiento();
    }
}
