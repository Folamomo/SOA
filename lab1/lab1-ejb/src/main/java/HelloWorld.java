import model.Student;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.logging.Logger;

/**
 * Created by student on 2020-02-25.
 */

@Stateless
@WebService(name = "HelloWorldServiceType", portName = "HelloWorldPort",
        targetNamespace = "https://soa.agh.edu.pl/lab1/ws")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class HelloWorld {
    public static final Logger LOGGER = Logger.getLogger(HelloWorld.class.getName());

    @WebMethod
    public String hello(String name){
        LOGGER.info("Greeting " + name);
        return "Hello, " + name;
    }


    public Student getStudent(@WebParam(name = "album") String album){
        LOGGER.info("getStudent invoked with " + album);

        Student student = new Student("Jan", "Nowak", album);
        student.addCourse("SOA");
        student.addCourse("Bazy Danych");

        return student;

    }
}
