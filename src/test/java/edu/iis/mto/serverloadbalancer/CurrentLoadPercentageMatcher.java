package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by piotr on 22.06.16.
 */
public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
    private double d1;

    public CurrentLoadPercentageMatcher(double d1) {
        this.d1 = d1;
    }

    public static CurrentLoadPercentageMatcher hasCurrentLoadOf(double expectedLoadPercentage) {
        return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
    }

    protected boolean matchesSafely(Server server) {
        double d1 = this.d1;
        double d2 = server.currentLoadPercentage;
        return doublesAreEqual(d1, d2);
    }

    private boolean doublesAreEqual(double d1, double d2) {
        return this.d1 == d2
                || Math.abs(this.d1 - d2) < 0.01d;
    }

    public void describeTo(Description description) {
        description.appendText("a server with load percentage of ").appendValue(d1);
    }
}
