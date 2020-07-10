package pl.edu.agh.soa.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Task {
    @Id @GeneratedValue
    private Long id;
    private String data;
    private Timestamp startedAt;
    private Timestamp finishedAt;
}
