package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public class TestModelEvent implements Event<Model>{
	
	private Model model;
	
	public TestModelEvent(Model _model) {
		model = _model;
	}
	
	public Model getModel() {
		return model;
	}
	
}
