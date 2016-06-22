package edu.iis.mto.serverloadbalancer;

/**
 * Created by piotr on 22.06.16.
 */
public class ServerBuilder implements Builder<Server> {

    private int capacity;

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        return new Server(capacity);
    }
}
