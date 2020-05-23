package pl.edu.agh.soa.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.edu.agh.soa.model.Student;
import pl.edu.agh.soa.soap.StudentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
@Path("/Student")
@Api(value = "StudentRESTController")
public class StudentRESTController {
    private StudentRepository studentRepository = new StudentRepository();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Gets all students")
    @ApiResponses({@ApiResponse(code=200, message="Success")})
    public Response getAllStudents(){
        Collection<Student> students = studentRepository.getAll();
        return Response
                .ok()
                .entity(students)
                .build();
    }

    @GET
    @Path("/{album}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Gets student by album")
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code =404, message="No student found")})
    public Response getByAlbum(@PathParam("album") String album){
        Optional<Student> student = studentRepository.getByAlbum(album);
        if (student.isPresent()) {
            return Response
                    .ok()
                    .entity(student.get())
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/{album}/avatar")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Gets student by album")
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code =404, message="No student found")})
    public Response getAvatarByAlbum(@PathParam("album") String album){
        Optional<Student> student = studentRepository.getByAlbum(album);
        if (student.isPresent()) {
            String avatar = student.get().getAvatar();
            System.out.println(avatar);
            return Response
                    .ok()
                    .entity(avatar)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds students by parameters")
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code =404, message="No student found")})
    public Response find(@QueryParam("firstName") String firstName,
                         @QueryParam("lastName") String lastName,
                         @QueryParam("album") String album){
        Collection<Student> found = studentRepository.findAllBy(firstName, lastName, album);
        if (found.isEmpty()){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(found)
                    .build();
        }
    }

    @POST
    @Path("/{album}/setAvatar")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Set avatar for student")
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code =404, message="No student found")})
    public Response setAvatar(@PathParam("album") String album, @QueryParam("base64Image") String base64){
        Optional<Student> student = studentRepository.getByAlbum(album);
        if (student.isPresent()) {
            student.get().setAvatar(base64);
            studentRepository.save(student.get());
            return Response
                    .ok()
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/{album}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Add or edit student")
    @ApiResponses({@ApiResponse(code=201, message="Created")})
    public Response putStudent(@PathParam("album") String album,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName){
        Student student = studentRepository.getByAlbum(album).orElse(new Student());
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentRepository.save(student);
        return Response
                .ok()
                .status(Response.Status.CREATED)
                .build();
    }

}
