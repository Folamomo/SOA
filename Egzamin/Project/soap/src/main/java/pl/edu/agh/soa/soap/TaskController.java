package pl.edu.agh.soa.soap;

import javax.annotation.security.DeclareRoles;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.db.TaskDAO;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Stateless
@WebService
@SecurityDomain("my-security-domain")
@DeclareRoles({"TaskGroup"})
@WebContext(contextRoot = "/soap", urlPattern = "/TaskController", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class TaskController {
    @EJB
    TaskDAO taskDAO;

    @WebMethod(action = "push")
    @RolesAllowed("TaskGroup")
    @WebResult(name = "Id")
    public Long push(@WebParam(name = "data") String data) {
        return taskDAO.createTask(data);
    }

    @WebMethod(action = "check")
    @RolesAllowed("TaskGroup")
    @WebResult(name = "Finished")
    public boolean check(@WebParam(name= "id") Long id) {
        return taskDAO.checkById(id);
    }

}
