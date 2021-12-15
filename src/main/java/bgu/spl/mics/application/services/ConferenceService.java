package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.ConfrenceInformation;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConferenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    ConfrenceInformation info;
    int time=0;
    public ConferenceService(String name,int date) {
        super(name+"Svc");
        info=new ConfrenceInformation(name, date);
    }

    private Callback<TickBroadcast> updateTime=k->{
        if(time==info.getDate()) {
            sendBroadcast(new PublishConferenceBroadcast(info.getSuccessfulModel()));
            this.terminate();
        }

    };

    private Callback<PublishResultsEvent> publishToAll= k->{

    };


    @Override
    protected void initialize() {
        // TODO Implement this

    }
}
