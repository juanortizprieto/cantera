package org.test.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;


@Entity
@Table(name = "GENDER")

public class Gender extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="ID")
    private Long id;
    @Column(name ="NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
