package categorisation_image;

import java.util.Date;
import java.util.List;

public class Event {
	
	private Event parent;
	private List<Event> child;
	private List<Interval> intervale;
	private String name;
        
	public Event(String name, List<Interval> lDate) {
		this.name = name;
		this.intervale = lDate;
	}
	
	public Event getParent(){
		return parent;
	}

	public void setParent(Event parent){
		this.parent = parent;
	}
	
	public List<Interval> getIntervale(){
		return intervale;
	}
	
	public String getNom(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void addChild(Event child){
		this.child.add(child);
		child.setParent(this);
	}
    
	public boolean isInclude(Date date){
		return true;
	}
        
}
