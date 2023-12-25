package dataAccess.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    int id;

    @Column(length=100)
    String name;

    @Column(nullable = false)
    int point;

}
