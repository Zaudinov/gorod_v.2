package zaudinov.testcase.repository.projection;

public interface UserView {
    Long getId();
    String getFio();
    String getAccount();
    ServWithoutChildrenView getService();
}
