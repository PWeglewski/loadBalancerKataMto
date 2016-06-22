package edu.iis.mto.serverloadbalancer;

/**
 * Created by piotr on 22.06.16.
 */
public class Server {

    public double currentLoadPercentage;
    public int capacity;

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return true;
    }
}
