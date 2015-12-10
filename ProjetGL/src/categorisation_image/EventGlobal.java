package categorisation_image;

import java.util.Date;
import java.util.List;

public class EventGlobal extends Events {

	public EventGlobal(String name, List<Interval> lInterval) {
		super(name, lInterval);
	}
	
	public void addChild(Events child) {
		if(this.include(child)){
			try {
				this.addChild(child);
			} catch (Exception e) {
				System.err.println("Cette erreur ne devrait jamais arriver");
				e.printStackTrace();
			}
		}else{
			// TODO gérer l'agrandissement de l'intervalle global.
		}
	}
}
