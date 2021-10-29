package zaudinov.testcase.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "service")
@DiscriminatorValue("2")
public class ServHierarchy extends Serv{
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id", referencedColumnName="service_id")
    private Set<Serv> children;

    @Column(name="parent_id", nullable = true)
    private Integer parent;

    public Set<Serv> getChildren() {
        return children;
    }

    public void setChildren(Set<Serv> children) {
        this.children = children;
    }

    public Integer getParent() {
        return parent;
    }
}
