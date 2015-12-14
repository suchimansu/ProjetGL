package categorisation_image.calendar;

import java.util.Date;
import java.util.List;

public class EventGlobal extends Events {

	public EventGlobal(String name, List<Interval> lInterval) {
		super(name, lInterval);
	}
	
	public void addChild(Events child) {
		if(this.getIntervale().size() == 0){
			for(Interval i : child.getIntervale()){
				this.getIntervale().add(i);
			}
			this.addChild(child);
		}else if(this.include(child)){
			this.getChildren().add(child);
			((Event)child).setParent(this);
		}else{
			List<Interval> childIts = child.getIntervale();
			List<Interval> globalIts = this.getIntervale();
			Interval globalIt = globalIts.get(0);
			Date earlier = globalIt.getStart();
			Date older = globalIt.getEnd();
			for(Interval i : childIts){
				if(i.getEnd().getTime() >= earlier.getTime()){
					earlier = i.getEnd();
				}
				if(i.getStart().getTime() <= older.getTime()){
					older = i.getStart();
				}
			}
			globalIts.remove(globalIt);
			try {
				globalIt = new Interval(older, earlier);
			} catch (Exception e) {
				System.err.println("Cette exception ne devrait jamais etre levée.");//Par la définition de earlier et older plus haut, older doit bien etre ant�rieur a earlier et les deux non null.
				e.printStackTrace();
			}
			globalIts.add(globalIt);
			this.addChild(child);
		}
	}
}
