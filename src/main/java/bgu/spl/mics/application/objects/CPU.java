package bgu.spl.mics.application.objects;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.services.CPUService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    private int cores;
    private Queue<DataBatch> data=new LinkedList<>();//data the cpu works on-needs to be processed.
    private Cluster cluster = Cluster.getInstance();
    //TODO: maybe change process to use this field.
    private AtomicInteger currentTicksNumber=new AtomicInteger(0);


    public CPU(int _cores){
        cores=_cores;
    }

    public AtomicInteger getCurrentTicksNumber(){
        return currentTicksNumber;
    }

//    public void updateTime(){
//        currentTicksNumber++;
//    }

    private int timeToWait(){
        Data.Type type = data.peek().getData().getType();
        int time = 32/cores;
        switch(type){
            case Images:
                return time*4;
            case Text:
                return time*2;
            case Tabular:
                return time;
        }
        return time;
    }

    public void process(DataBatch dataBatch){
        int curr=currentTicksNumber.intValue();//the time when data starts processing
        int t=timeToWait();
        //TODO check if we can make sure data is sent the exact moment the right number of ticks passed
        while(currentTicksNumber.get()<curr+t) {//checks if t ticks have passed since start of process.
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        cluster.addProcessedData(dataBatch); //sends the processed data to the cluster
    }
}
