package zaudinov.testcase.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "service_hierarchy")
@PrimaryKeyJoinColumn(name = "service_id")
public class ServHierarchy extends Serv{
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id", referencedColumnName="service_id")
    private Set<Serv> children;

    @Column(name="parent_id", nullable = true)
    private Integer parent;
}
