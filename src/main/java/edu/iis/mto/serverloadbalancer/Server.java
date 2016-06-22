package edu.iis.mto.serverloadbalancer;


public class Server {

    public static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPecentage;
    public int capacity;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPecentage = (double) vm.size / capacity * MAXIMUM_LOAD;
    }

    public int countVms() {
        return 0;
    }
}
