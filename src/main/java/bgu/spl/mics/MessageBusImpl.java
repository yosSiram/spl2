package bgu.spl.mics;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//singleton reference:
	private static MessageBusImpl msgBus=new MessageBusImpl();
	/* fields: */
	//queue for events the microservice will handle:
	private ConcurrentHashMap<MicroService,Queue<Message>> msEvents;
	//for each broadcast type holds all the microservices that are interested in it:
	private ConcurrentHashMap<Class<? extends Broadcast>,LinkedList<MicroService>> broadcastLog;
	//for each event type holds the microservices that are interested in it:
	private ConcurrentHashMap<Class<? extends Event<?>>,LinkedList<MicroService>> eventLog;
	//for each event holds the future obj:
	private ConcurrentHashMap<Event<?>,Future<?>> Futures;

	public MessageBusImpl() {
		msEvents=new ConcurrentHashMap<>();
		broadcastLog=new ConcurrentHashMap<>();
		eventLog=new ConcurrentHashMap<>();
		Futures=new ConcurrentHashMap<>();
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		//TODO check if there is a way to implement using atomics.
		//synchronize the eventLog- changes occur exclusively in this object.
		synchronized (eventLog) {
			eventLog.computeIfAbsent(type, k -> new LinkedList<MicroService>());
			eventLog.get(type).add(m);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		//synchronize the broadcastLog- changes occur exclusively in this object.
		synchronized (broadcastLog) {
			broadcastLog.computeIfAbsent(type, k -> new LinkedList<MicroService>());
			eventLog.get(type).add(m);
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> fut= (Future<T>) Futures.remove(e);
		if(fut!=null) fut.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		//TODO check if broadcastLog good-pretty sure is good. if not change to this.
		synchronized (broadcastLog){
			LinkedList<MicroService> listeners=broadcastLog.get(b.getClass());
			if(b==null) return; //checks there are listeners
			for(MicroService m:listeners){
				msEvents.get(m).add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		//TODO check if can change this to something better-maybe dont need synchro, concur map after all?
		//we dont want to be in a situation where we check if there are subscribers and get no but at the same
		//time we get a subscriber. so we lock check there are actually subscribers then if there are we can
		//get a microservice to handle this event.
		synchronized (eventLog) {
			LinkedList<MicroService> l = eventLog.get(e.getClass());
			if (l == null) return null; //checks there are subscribers to this kind of event.
			MicroService m = l.pollFirst();
			msEvents.get(m).add(e);
			l.addLast(m);

			Future<T> f = new Future<>();
			Futures.put(e, f);
			return f;

		}
	}

	@Override
	public synchronized void register(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
