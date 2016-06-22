package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by piotr on 23.06.16.
 */
public class ServerVmsCountMatcher extends TypeSafeMatcher<Server> {
    private int expectedVmsCount;

    public ServerVmsCountMatcher(int expectedVmsCount) {
        this.expectedVmsCount = expectedVmsCount;
    }

    public static ServerVmsCountMatcher hasAVmsCountOf(int expectedVmsCount) {
        return new ServerVmsCountMatcher(expectedVmsCount);
    }

    protected boolean matchesSafely(Server server) {
        return expectedVmsCount == server.countVms();
    }

    public void describeTo(Description description) {
        description.appendText("a server with vms count of ").appendValue(expectedVmsCount);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("a server with vms count of").appendValue(item.countVms());
    }
}
