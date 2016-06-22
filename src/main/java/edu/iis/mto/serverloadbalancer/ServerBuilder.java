package edu.iis.mto.serverloadbalancer;

/**
 * Created by piotr on 22.06.16.
 */
public class ServerBuilder {

    private int capacity;

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        return new Server();
    }
}
