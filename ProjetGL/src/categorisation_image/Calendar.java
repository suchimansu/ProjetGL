package categorisation_image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;

public class Calendar {

	// Type a valider
	private Event global;
	private HashMap<String, Event> calMap;
	private net.fortuna.ical4j.model.Calendar cal;
	
	public Calendar(){
		calMap = new HashMap<String,Event>();
		cal = new net.fortuna.ical4j.model.Calendar();
	}
	
	public Calendar(String path){
		calMap = new HashMap<String,Event>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			CalendarBuilder cbr = new CalendarBuilder();
			cal = cbr.build(fis);
			for (Iterator<?> i = cal.getComponents().iterator(); i.hasNext();) {
			    Component component = (Component) i.next();
			    //System.out.println("Component [" + component.getName() + "]");
			    Date start = new DateTime(component.getProperty(Property.DTSTART).getValue());
			    //System.out.println("start : " + start.getHours() + ":" + start.getMinutes());
			    Date end = new DateTime(component.getProperty(Property.DTEND).getValue());
			    //System.out.println("end : " + end.getHours() + ":" + end.getMinutes());
			    List<Interval> tmpList = new ArrayList<Interval>();
			    Interval tmpInterval = new Interval(start,end);
			    
			    // TODO checker si le SUMMARY est toujours présent
			    Event tmpe = new Event(component.getProperty(Property.SUMMARY).getValue(), tmpList);
			    calMap.put(tmpe.getNom(), tmpe);

			    /*for (Iterator<?> j = component.getProperties().iterator(); j.hasNext();) {
			        Property property = (Property) j.next();
			        System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
			    }*/
			}
		} catch (FileNotFoundException e) {
			System.err.println("Erreur lors de l'ouverture du fichier iCalendar dans le constructeur Calendar(String path)");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier iCalendar dans le constructeur Calendar(String path).");
			e.printStackTrace();
		} catch (ParserException e) {
			System.err.println("Erreur lors de la création de l'object net.fortuna.ical4j.model.Calendar dans le constructeur Calendar(String path).");
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				System.err.println("Erreur lors de la fermeture du fichier iCalendar.");
				e.printStackTrace();
			};
		}
		
		
	}
	
	public Event getGlobalEvent(){
		return global;
	}
	
	public void importCal(){
		
	}
	
	public void addEvent(String name, List<Interval> dList){
		Event tmp = new Event(name, dList);
		calMap.put(name, tmp);
	}
	
	public void editEvent(String name, String newName){
		
	}
	
	public void remove(String name){
		
	}
	
	public void save(String path){
		
	}
	
}
