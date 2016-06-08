package edu.iis.mto.serverloadbalancer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest {

    @Parameterized.Parameter // first data value (0) is default
    public int serverCapacity;
    @Parameterized.Parameter(value = 1)
    public int vmSize;
    @Parameterized.Parameter(value = 2)
    public double expectedLoad;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 100.0d}, {10, 1, 10.0d}, {10, 5, 50.0d}, {1000, 20, 2.0d}, {16, 14, 87.5d}, {5, 5, 100.0d}, {8, 6, 75.0d}
        });
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(serverCapacity));
        Vm theVm = a(vm().ofSize(vmSize));
        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(expectedLoad));
        assertThat("the server should contain vm", theServer.contains(theVm));
    }


}
