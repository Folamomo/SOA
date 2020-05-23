package pl.edu.agh.soa.soap;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.soap.exceptions.StudentNotFoundException;
import pl.edu.agh.soa.model.Student;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Collection;

@Stateless
//@WebService(wsdlLocation = "/soap-api/StudentController?wsdl")
@WebService
@SecurityDomain("my-security-domain")
@DeclareRoles({"testRole"})
@WebContext(contextRoot = "/soap-api", urlPattern = "/StudentController", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL )
public class StudentController {
    private StudentRepository studentRepository = new StudentRepository();

    @WebMethod(action = "find")
    @RolesAllowed("testRole")
    @WebResult(name = "student")
    public Student getStudentByAlbum(@WebParam(name = "album") String album)
            throws StudentNotFoundException {
        return studentRepository.getByAlbum(album).orElseThrow(StudentNotFoundException::new);
    }

    @WebMethod
    @RolesAllowed("testRole")
    @WebResult(name = "students")
    public Student[] getAllBy(@WebParam(name = "firstName") String firstName,
                                  @WebParam(name = "lastName") String lastName,
                                  @WebParam(name = "album") String album){
        Collection<Student> found = studentRepository.findAllBy(firstName, lastName, album);
        return found.toArray(new Student[found.size()]);
    }

    @WebMethod
    @RolesAllowed("testRole")
    public void addAvatar(
            @WebParam(name = "album") String album,
            @WebParam(name = "base64") String avatar)
    throws StudentNotFoundException{
        Student student = studentRepository.getByAlbum(album)
                .orElseThrow(StudentNotFoundException::new);
        student.setAvatar(avatar);
    }

    @WebMethod
    @RolesAllowed("testRole")
    public void addCourse(
            @WebParam(name = "album") String album,
            @WebParam(name = "course") String course)
            throws StudentNotFoundException{
        Student student = studentRepository.getByAlbum(album)
                .orElseThrow(StudentNotFoundException::new);
        student.addCourse(course);
    }

    @WebMethod
    @RolesAllowed("testRole")
    @WebResult(name = "album")
    public String addStudent(
            @WebParam(name = "firstName") String firstName,
            @WebParam(name = "lastName") String lastName,
            @WebParam(name = "album") String album){
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .album(album)
                .build();

        studentRepository.save(student);
        return student.getAlbum();
    }
}
