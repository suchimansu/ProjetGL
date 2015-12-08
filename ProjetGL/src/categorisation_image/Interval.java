package categorisation_image;

import java.util.Date;

public class Interval {

	private Date start;
	private Date end;
	
	public Interval(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	public Date getStart(){
		return start;
	}
	
	public Date getEnd(){
		return end;
	}

}
