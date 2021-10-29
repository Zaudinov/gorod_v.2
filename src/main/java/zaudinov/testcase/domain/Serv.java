package zaudinov.testcase.domain;

import javax.persistence.*;

@Entity
@Table(name = "service")
@Inheritance(strategy = InheritanceType.JOINED)
public class Serv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    Long id;

    String name;

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
