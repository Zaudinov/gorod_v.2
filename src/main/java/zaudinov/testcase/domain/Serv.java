package zaudinov.testcase.domain;

import javax.persistence.*;

@Entity
@Table(name = "service")
@Inheritance(strategy = InheritanceType.JOINED)
public class Serv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private Long id;

    private String name;
}
