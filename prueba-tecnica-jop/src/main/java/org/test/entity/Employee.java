package org.test.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import java.util.Date;

@Entity
@Table(name = "EMPLOYEES")

public class Employee extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "GENDER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_GENDER"))
    private Gender gener;
    @ManyToOne
    @JoinColumn(name = "JOB_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_JOBS"))
    @NotFound(action = NotFoundAction.IGNORE)
    private Job job;
    @Column(name ="NAME")
    private String name;
    @Column(name ="LAST_NAME")
    private String lastName;
    @Column(name ="BIRTHDATE")
    private Date birthDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGener() {
        return gener;
    }

    public void setGener(Gender gener) {
        this.gener = gener;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
