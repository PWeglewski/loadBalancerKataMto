package edu.iis.mto.serverloadbalancer;

/**
 * Created by piotr on 22.06.16.
 */
public class Server {

    public static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;
    public int capacity;

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = (double) vm.size / capacity * MAXIMUM_LOAD;
    }
}
