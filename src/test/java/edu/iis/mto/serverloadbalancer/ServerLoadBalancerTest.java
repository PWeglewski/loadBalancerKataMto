package edu.iis.mto.serverloadbalancer;

import org.junit.Test;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmsCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ServerLoadBalancerTest {
    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void balancingAServer_noVms_serverStaysEmpty() {
        Server theServer = a(server().withCapacity(1));

        balancing(aListOfServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, hasLoadPercentageOf(0.0d));
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));
        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(100.0d));
        assertThat("the server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
        Server theServer = a(server().withCapacity(10));
        Vm theVm = a(vm().ofSize(1));
        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(10.0d));
        assertThat("the server should contain vm", theServer.contains(theVm));

    }

    @Test
    public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
        Server theServer = a(server().withCapacity(100));
        Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));
        balancing(aListOfServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasVmsCountOf(2));
        assertThat("the server should contain vm", theServer.contains(theFirstVm));
        assertThat("the server should contain vm", theServer.contains(theSecondVm));

    }

    @Test
    public void vmShouldBeBalancedOnLessLoadedServer() {
        Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
        Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
        Vm theFirstVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(moreLoadedServer, lessLoadedServer), aListOfVmsWith(theFirstVm));

        assertThat("less loaded server should contain vm", lessLoadedServer.contains(theFirstVm));
        assertThat("more loaded server should not contain vm", !moreLoadedServer.contains(theFirstVm));
    }

    @Test
    public void balancingServerWithNotEnoughRoom_shouldNotBeFilledWithTheVm() {
        Server theServer = a(server().withCapacity(10).withCurrentLoadOf(90.0d));
        Vm theVm = a(vm().ofSize(2));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat("the server should not contain the vm", !theServer.contains(theVm));
    }

    @Test
    public void balancingserversAndVms() {
        Server server1 = a(server().withCapacity(4));
        Server server2 = a(server().withCapacity(6));

        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));

        balancing(aListOfServersWith(server1, server2), aListOfVmsWith(vm1, vm2, vm3));

        assertThat("server 1 should contain vm1", server1.contains(vm1));
        assertThat("server 2 should contain vm2", server2.contains(vm2));
        assertThat("server 1 should contain vm3", server1.contains(vm3));

        assertThat(server1, hasLoadPercentageOf(75.0d));
        assertThat(server2, hasLoadPercentageOf(66.66d));
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] aListOfVmsWith(Vm... vms) {
        return vms;
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server[] aListOfServersWith(Server... servers) {
        return servers;
    }

    private <T> T a(Builder<T> builder) {
        return builder.build();
    }
}
