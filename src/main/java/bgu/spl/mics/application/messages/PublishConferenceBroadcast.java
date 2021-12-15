package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import java.util.*;

public class PublishConferenceBroadcast implements Broadcast{
	
	private Set<String> successfulModels;
	
	public PublishConferenceBroadcast(Set<String> _successfulModels) {
		successfulModels = _successfulModels;
	}

	public Set<String> getSuccessfulModels() {
		return successfulModels;
	}
	
}
