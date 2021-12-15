package bgu.spl.mics.application.objects;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.mics.application.objects.*;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	//This is the only instance of the cluster
	private static Cluster c = new Cluster();

	//We need this map to know to which GPU to send the processed data
	private ConcurrentHashMap<Data, GPU> map = new ConcurrentHashMap<>();
	//A list of all CPUs
	private Map<String,CPU> cpus;
	//A list of all GPUs
	private List<GPU> gpus;
	//An object that holds stats
	private Statistics stats;
	//a list of processed data.
	private Queue<DataBatch> proc=new LinkedList<>();


	/**
	 * Retrieves the single instance of this class.
	 */
	public static Cluster getInstance() {
		return c;
	}

	public ConcurrentHashMap getMap(){return map;};

	public void addProcessedData(DataBatch dataBatch){
		proc.add(dataBatch);
	}

	public Map getCPUS(){return cpus;}

	private class Statistics{
		//The names of all fully trained models
		public Set<String> trainedModelNames = new HashSet<>();
		//The amount of data batches processed so far
		public int processedBatches = 0;
		//The number of CPU time units used
		public int cpuTimeUsed = 0;
		//The number of GPU time units used
		public int gpuTimeUsed = 0;
	}

}
