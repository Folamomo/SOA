package pl.edu.agh.soa.soap;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Stateless
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class HelloWorld {

    @WebMethod
    public String hello(@WebParam(name = "name") String name) {
        return "Hello " + name;
    }

    @WebMethod
    public String spierdalaj(@WebParam(name = "name") String name) {
        return "Spierdalaj " + name;
    }

}
