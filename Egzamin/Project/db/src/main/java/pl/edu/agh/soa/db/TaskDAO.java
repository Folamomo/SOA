package pl.edu.agh.soa.db;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;

import static java.time.Instant.now;

@LocalBean
@Stateless
public class TaskDAO {
    @PersistenceContext(unitName = "Task")
    private EntityManager entityManager;

    public Long createTask(String data){
        Task task = new Task();
        task.setData(data);
        task.setStartedAt(Timestamp.from(now()));
        entityManager.persist(task);
        entityManager.flush();
        return task.getId();
    }

    public boolean checkById(Long id){
        Task task = entityManager.find(Task.class, id);
        if (task == null) throw new TaskNotFoundException("No task with id = " + id);
        else return task.getFinishedAt() != null;
    }

    public void setFinishedAt(Long id, Timestamp timestamp){
        Task task = entityManager.find(Task.class, id);
        if (task == null) throw new TaskNotFoundException("No task with id = " + id);
        task.setFinishedAt(timestamp);
    }
}
