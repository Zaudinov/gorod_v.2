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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Serv getService() {
        return service;
    }

    public void setService(Serv service) {
        this.service = service;
    }
}
