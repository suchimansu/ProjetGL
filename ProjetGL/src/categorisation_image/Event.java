package categorisation_image;

import java.util.Date;
import java.util.List;

/**
 * Classe décrivant une catégorie, avec son nom et les intervalles de temps la concernant.
 */
public class Event {
	
	private Event parent;
	private List<Event> child;
	private List<Interval> intervale;
	private String name;

	/**
	 * Construit une nouvelle instance de nom name, et dont les intervalles de temps sont ceux de lDate.
	 * @param name Nom de la catégorie
	 * @param lDate Liste des intervalles de temps dans lesquels l'évènement prend place
	 */
	public Event(String name, List<Interval> lDate) {
		this.name = name;
		this.intervale = lDate;
	}

	/**
	 * Retourne la catégorie parente de cette instance.
	 * @return La catégorie parente ou null si elle n'existe pas
	 */
	public Event getParent(){
		return parent;
	}

	/**
	 * Met à jour la catégorie parente de cette instance avec parent.
	 * @param parent Catégorie parente de cette instance
	 * @return void
	 */
	public void setParent(Event parent){
		this.parent = parent;
	}

	/**
	 * Retourne les intervalles de temps dans lesquels l'évènement prend place.
	 * @return La liste des intervalles de temps
	 */
	public List<Interval> getIntervale(){
		return intervale;
	}

	/**
	 * Retourne le nom de la catégorie décrite par cette instance.
	 * @return le nom de la catégorie
	 */
	public String getNom(){
		return name;
	}

	/**
	 * Met à jour le nom de la catégorie décrite par cette instance avec name.
	 * @param name Nouveau nom de la catégorie
	 * @return void
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Ajoute une sous-catégorie à cette instance
	 * @param child sous-catégorie à ajouter
	 * @return void
	 */
	public void addChild(Event child){
		this.child.add(child);
		child.setParent(this);
	}

	/**
	 * Retourne vrai si l'évènement prend entièrement place durant la période date.
	 * @return vrai si l'évènement prend entièrement place durant Date, faux sinon
	 */
	public boolean isInclude(Date date){
		return true;
	}
        
}
