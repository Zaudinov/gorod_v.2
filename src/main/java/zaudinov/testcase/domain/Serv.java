package zaudinov.testcase.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "service")
public class Serv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id", referencedColumnName="service_id")
    private Set<Serv> children;

    @Column(name="parent_id", nullable = true)
    private Long parent;

    public Serv(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Serv() {
    }

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

    public Set<Serv> getChildren() {
        return children;
    }

    public void setChildren(Set<Serv> children) {
        this.children = children;
    }


    public void setParent(Long parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serv serv = (Serv) o;
        return Objects.equals(id, serv.id) &&
                Objects.equals(name, serv.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
