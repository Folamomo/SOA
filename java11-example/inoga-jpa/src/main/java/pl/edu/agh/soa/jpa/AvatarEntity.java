package pl.edu.agh.soa.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class AvatarEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AvatarId;
    String avatar;
}
