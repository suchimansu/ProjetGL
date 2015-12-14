package categorisation_image.calendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

/**
 * Classe servant à stocker les évènements et leur hiérarchie.
 * Elle est majoritairement utilisée pour le tri des images.
 * @see	Event
 * @see Interval
 */
public class Calendar {

	/**
	 *  Evenement racine (qui contient tous les autres).
	 */
	private EventGlobal global;
	/**
	 *  Annuaire des évènements par leurs nom.
	 */
	private HashMap<String, Event> calMap;
	/**
	 *  Calendrier 
	 */
	private net.fortuna.ical4j.model.Calendar cal;
	
	/**
	 * Constructeur par defaut, initialise les attributs.
	 */
	public Calendar(){
		// Initialisation des attributs.
		global = new EventGlobal("Global", new ArrayList<Interval>());
		calMap = new HashMap<String,Event>();
		cal = new net.fortuna.ical4j.model.Calendar();
		cal.getProperties().add(new ProdId("iCal4j 1.0//EN"));
		cal.getProperties().add(Version.VERSION_2_0);
		cal.getProperties().add(CalScale.GREGORIAN);
	}
	
	/**
	 * Construit une instance à partir d'un fichier.
	 * Ce constructeur fabrique une nouvelle instance à partir des données situées dans le fichier
	 * path, au format ICal
	 * @param path chemin vers le fichier au format ICal contenant les catégories
	 */
	public Calendar(String path){
		// Initialisation des attributs.
		this();
		
		// Chargement du fichier iCalendar.
		FileInputStream fis = null;
		try {			
			fis = new FileInputStream(path);
			CalendarBuilder cbr = new CalendarBuilder();
			cal = cbr.build(fis);
			for (Iterator<?> i = cal.getComponents().iterator(); i.hasNext();) {
			    Component component = (Component) i.next();
			    Date start = new DateTime(component.getProperty(Property.DTSTART).getValue());
			    Date end = new DateTime(component.getProperty(Property.DTEND).getValue());
			    List<Interval> tmpList = new ArrayList<Interval>();
			    Interval tmpInterval = new Interval(start,end);
			    tmpList.add(tmpInterval);
			    
			    Event tmpe = new Event(component.getProperty(Property.SUMMARY).getValue(), tmpList);
		    	String tmps = tmpe.getName();
			    if(!calMap.containsKey(tmps)){
				    calMap.put(tmps, tmpe);
					global.addChild(tmpe);
			    }else{
			    	calMap.get(tmps).getIntervale().add(tmpInterval);
			    }
			}
		// Gestion des erreurs pouvant survenir.
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
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// Fermeture du fichier.
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				System.err.println("Erreur lors de la fermeture du fichier.");
				e.printStackTrace();
			};
		}
	}
	
	/**
	 * Retourne la catégorie se situant à la racine de l'arborescence des catégories
	 * @return La racine de l'arborescence des catégories
	 */
	public EventGlobal getGlobalEvent(){
		return global;
	}
	
	/**
	 * Ajoute un nouvel évènement au calendar avec son nom et sa liste d'{@link Interval}. 
	 * @param name Nom de la catégorie à ajouter
	 * @param dList Liste des intervalles de temps dans lesquels l'évènement prend place
	 */
	public void addEvent(String name, List<Interval> dList){
		Event tmp = new Event(name, dList);
		String tmps = name;
		if(!calMap.containsKey(tmps)){
			calMap.put(tmps, tmp);
			try {
				global.addChild(tmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			for(Interval i : dList){
				calMap.get(tmps).getIntervale().add(i);
			}
		}
		net.fortuna.ical4j.model.Date start;
		net.fortuna.ical4j.model.Date end; 
		VEvent event;
		for(Interval i : dList){
			start = new net.fortuna.ical4j.model.Date(i.getStart().getTime());
			end = new net.fortuna.ical4j.model.Date(i.getEnd().getTime());
			event = new VEvent(new DateTime(start),new DateTime(end),name);
			cal.getComponents().add(event);
		}
	}
	
	/**
	 * Change le nom de la catégorie de nom name par newName
	 * @param name Nom de la catégorie à renommer
	 * @param newName Nouveau nom de la catégorie 
	 */
	public void editEvent(String name, String newName) throws Exception{
		String tmps = name;
		if(!calMap.containsKey(tmps)){
			throw new Exception("Pas d'evenement portant ce nom.");
		}else{
			Event tmp = calMap.get(tmps);
			tmp.setName(newName);
			calMap.remove(tmps);
			calMap.put(newName, tmp);
			for (Iterator<?> i = cal.getComponents().iterator(); i.hasNext();){
				Component component = (Component)i.next();
				if(component.getProperty(Property.SUMMARY).equals(name)){
					cal.getComponents().remove(component);
					component.getProperty(Property.SUMMARY).setValue(newName);
					cal.getComponents().add(component);
				}
			}
		}
	}
	
	/**
	 * Supprime la catégorie de nom name.
	 * @param name Nom de la catégorie à supprimer
	 */
	public void remove(String name) throws Exception{
		String tmps = name;
		if(!name.equals("Global")){// On sait jamais...
			boolean test = !calMap.containsKey(tmps);
			if(test){
				throw new Exception("Pas d'evenement portant ce nom.");
			}else{
				calMap.remove(tmps);
				remove(name,global);
				for (Iterator<?> i = cal.getComponents().iterator(); i.hasNext();){
					Component component = (Component)i.next();
					if(component.getProperty(Property.SUMMARY).equals(name)){
						cal.getComponents().remove(component);
					}
				}
			}
		}
	}
	
	/**
	 * fonction récursive qui supprime l'évènement dans l'arbre des évènements
	 * @param name
	 * @param ev
	 */
	private boolean remove(String name, Events ev){
		if(ev.getName().equals(name)){
			((Event)ev).getParent().getChildren().remove(ev);
			for(Events e : ev.getChildren()){
				try {
					((Event)ev).getParent().addChild(e);
				} catch (Exception e1) {
					System.err.println("Cette exception ne devrait jamais etre levé."); // comme e est un fils de ev. l'ajouter dans le parent de ev ne devrait pas poser de probl�me au niveau de l'inclusion des intervalles
					e1.printStackTrace();
				}
			}
			return true;
		}else{
			for(Events e : ev.getChildren()){
				if(remove(name,e)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sauvegarde des évènements et de leur hiérachie dans un fichier au format ICal
	 * @param path Chemin vers le fichier de sauvegarde
	 */
	public void save(String path){
		FileOutputStream fout = null;
		CalendarOutputter cout = null;
		try {
			fout = new FileOutputStream(path);
			cout = new CalendarOutputter();
			cout.setValidating(false);
			cout.output(cal, fout);
		} catch (FileNotFoundException e) {
			System.err.println("Erreur lors de l'ouverture du flux de sortie pour la sauvegarde du calendrier.");
			e.printStackTrace();
		}catch (IOException | ValidationException e) {
			System.err.println("Erreur lors de la sauvegarde du calendrier.");
			e.printStackTrace();
		}finally {
			try {
				if(fout != null){
					fout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
