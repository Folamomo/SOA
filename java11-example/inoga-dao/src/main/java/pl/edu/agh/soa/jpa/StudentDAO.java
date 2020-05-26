package pl.edu.agh.soa.jpa;

import pl.edu.agh.soa.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StudentDAO {
    @PersistenceContext(unitName = "Student")
    EntityManager entityManager;

    public Student entityToStudent(StudentEntity entity){
        Student result = new Student();
        result.setLastName(entity.getLastName());
        result.setFirstName(entity.getFirstName());
        result.setAlbum(entity.getAlbum());
        result.setAvatar(entity.getAvatar() == null ? null : entity.getAvatar().getAvatar());
        result.setCourses(entity.getCourses().stream().map(CourseEntity::getName).collect(Collectors.toList()));
        return result;
    }

    public void save(Student student) {
        StudentEntity entity = byAlbum(student.getAlbum());
        if (entity == null) entity = new StudentEntity();
        if (entity.getCourses() == null) entity.setCourses(new ArrayList<>());
        if (entity.getAvatar() == null){
            if (student.getAvatar() != null) {
                entity.setAvatar(new AvatarEntity());
                entity.getAvatar().setAvatar(student.getAvatar());
            }
        } else {
            if (student.getAvatar() != null) {
                entity.getAvatar().setAvatar(student.getAvatar());
            } else {
                entity.setAvatar(null);
            }
        }
        entity.setAlbum(student.getAlbum());
        entity.setFirstName(student.getFirstName());
        entity.setLastName(student.getLastName());
        entityManager.persist(entity);
    }

    public Collection<Student> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = builder.createQuery(StudentEntity.class);
        Root<StudentEntity> legoSetEntityRoot = query.from(StudentEntity.class);
        query.select(legoSetEntityRoot);

        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(this::entityToStudent)
                .collect(Collectors.toList());
    }

    private StudentEntity byAlbum(String album){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = builder.createQuery(StudentEntity.class);
        Root<StudentEntity> root = query.from(StudentEntity.class);
        query.where(builder.equal(root.get("album"), album));
        List<StudentEntity> result = entityManager.createQuery(query).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Student get(String album) {
        StudentEntity entity = byAlbum(album);
        return entity == null ? null : entityToStudent(entity);
    }

    public Optional<Student> getByAlbum(String album){
        return Optional.ofNullable(byAlbum(album)).map(this::entityToStudent);
    }
}
