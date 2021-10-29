package zaudinov.testcase.repository.projections;

public interface UserView {
    Long getId();
    String getFio();
    String getAccount();
    ServWithoutChildrenView getService();
}
