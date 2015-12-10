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
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;

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
	 *  Annuaire des �v�nement par leurs nom.
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
		global = new EventGlobal("Global", null);
		calMap = new HashMap<String,Event>();
		cal = new net.fortuna.ical4j.model.Calendar();
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
			    calMap.put(tmpe.getNom(), tmpe);
				global.addChild(tmpe);
			}
		// Gestion des erreurs pouvant survenir.
		} catch (FileNotFoundException e) {
			System.err.println("Erreur lors de l'ouverture du fichier iCalendar dans le constructeur Calendar(String path)");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier iCalendar dans le constructeur Calendar(String path).");
			e.printStackTrace();
		} catch (ParserException e) {
			System.err.println("Erreur lors de la cr�ation de l'object net.fortuna.ical4j.model.Calendar dans le constructeur Calendar(String path).");
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
	 * Importe le calendrier
	 * @return void
	 */
	public void importCal(){
		
	}
	
	/**
	 * Ajoute un nouvel �v�nement au calendar avec son nom et sa liste d'{@link Interval}. 
	 * @param name Nom de la catégorie à ajouter
	 * @param dList Liste des intervalles de temps dans lesquels l'évènement prend place
	 * @return void
	 */
	public void addEvent(String name, List<Interval> dList){
		Event tmp = new Event(name, dList);
		calMap.put(name, tmp);
		try {
			global.addChild(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Change le nom de la catégorie de nom name par newName
	 * @param name Nom de la catégorie à renommer
	 * @param newName Nouveau nom de la catégorie 
	 * @return void
	 */
	public void editEvent(String name, String newName){
		Event tmp = calMap.get(name);
		tmp.setName(newName);
		calMap.remove(name);
		calMap.put(newName, tmp);
	}
	
	/**
	 * Supprime la catégorie de nom name.
	 * @param name Nom de la catégorie à supprimer
	 * @return void
	 */
	public void remove(String name){
		
	}
	
	/**
	 * Sauvegarde des évènements et de leur hiérachie dans un fichier au format ICal
	 * @param path Chemin vers le fichier de sauvegarde
	 * @return void
	 */
	public void save(String path){
		
	}
	
}
