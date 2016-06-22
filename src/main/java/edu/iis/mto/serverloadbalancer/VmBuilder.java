package edu.iis.mto.serverloadbalancer;

/**
 * Created by piotr on 22.06.16.
 */
public class VmBuilder implements Builder<Vm> {
    private int size;

    public static VmBuilder vm() {
        return new VmBuilder();
    }

    public VmBuilder ofSize(int size) {
        this.size = size;
        return this;
    }

    public Vm build() {
        return new Vm();
    }
}
