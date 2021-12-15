package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Cluster;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CPU service is responsible for handling the DataPreProcessEvent.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    Cluster cluster=Cluster.getInstance();
    CPU cpu;
    int time=0;

    public CPUService(String name) {
        super(name+"Svc");
        cpu= (CPU) cluster.getCPUS().get(name);
    }

    //this callback handles getting a tickBroadcast and updates the cpu accordingly.
    private Callback<TickBroadcast> tickUpdateCallBack= k->{
        time++;
        cpu.getCurrentTicksNumber().set(time);
    };

    @Override
    protected void initialize() {
        // TODO Implement this

    }
}
