package categorisation_image.calendar;

import java.util.Date;
import java.util.List;

public class EventGlobal extends Events {

	public EventGlobal(String name, List<Interval> lInterval) {
		super(name, lInterval);
	}
	
	public void addChild(Events child) {
		if(this.include(child)){
			try {
				((Events)this).addChild(child);
			} catch (Exception e) {
				System.err.println("Cette exception ne devrait jamais etre levé.");//L'intervalle est agrandi si il n'inclut pas l'évènement enfant.
				e.printStackTrace();
			}
		}else{
			List<Interval> childIts = child.getIntervale();
			List<Interval> globalIts = this.getIntervale();
			Interval globalIt = globalIts.get(0);
			Date earlier = globalIt.getStart();
			Date older = globalIt.getEnd();
			for(Interval i : childIts){
				if(i.getEnd().after(earlier)){
					earlier = i.getEnd();
				}
				if(i.getStart().before(older)){
					older = i.getEnd();
				}
			}
			globalIts.remove(globalIt);
			try {
				globalIt = new Interval(older, earlier);
			} catch (Exception e) {
				System.err.println("Cette exception ne devrait jamais etre levé.");//Par la définition de earlier et older plus haut, older doit bien etre antérieur a earlier et les deux non null.
				e.printStackTrace();
			}
			globalIts.add(globalIt);
			this.addChild(child);
		}
	}
}
