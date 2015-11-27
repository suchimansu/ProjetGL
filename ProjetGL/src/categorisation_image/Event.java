package categorisation_image;

import java.util.Date;
import java.util.List;

public class Event {
	
	private Event parent;
	private List<Event> child;
	private List<Date> intervale;
	private String name;

	public Event(String name, List<Date> lDate) {
		
	}
	
	public Event getParent(){
		return parent;
	}

	public void setParent(Event parent){
		this.parent = parent;
	}
	
	public List<Date> getIntervale(){
		return intervale;
	}
	
	public String getNom(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
