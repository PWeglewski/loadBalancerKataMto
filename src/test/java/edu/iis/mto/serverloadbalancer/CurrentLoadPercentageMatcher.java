package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by piotr on 22.06.16.
 */
public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
    public static final double EPSILON = 0.01d;
    private double expectedLoadPercentage;

    public CurrentLoadPercentageMatcher(double expectedLoad) {
        this.expectedLoadPercentage = expectedLoad;
    }

    public static CurrentLoadPercentageMatcher hasCurrentLoadOf(double expectedLoad) {
        return new CurrentLoadPercentageMatcher(expectedLoad);
    }

    protected boolean matchesSafely(Server server) {
        double d1 = this.expectedLoadPercentage;
        double d2 = server.currentLoadPercentage;
        return areDoublesEqual(d1, d2);
    }

    private boolean areDoublesEqual(double d1, double d2) {
        return d1 == d2
                || Math.abs(d1 - d2) < EPSILON;
    }

    public void describeTo(Description description) {
        description.appendText("a server with load percentage of ").appendValue(expectedLoadPercentage);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("a server with load percentage of ").appendValue(item.currentLoadPercentage);
    }
}
