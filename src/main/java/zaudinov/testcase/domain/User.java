package zaudinov.testcase.domain;

import javax.persistence.*;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fio;

    @Column
    private String account;

    @ManyToOne
    @JoinColumn(name="service_id")
    private Serv service;


}
