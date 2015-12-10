package categorisation_image.calendar;

import java.util.Date;
import java.util.List;

/**
 * Classe décrivant une catégorie, avec son nom et les intervalles de temps la concernant.
 */

public class Event extends Events {	

	/**
	 * Ev�nement parent.
	 */
	private Events parent;
    
	/**
	 * Construit une nouvelle instance de nom name, et dont les intervalles de temps sont ceux de lDate.
	 * @param name Nom de la catégorie
	 * @param lDate Liste des intervalles de temps dans lesquels l'évènement prend place
	 */
	public Event(String name, List<Interval> lInterval) {
		super(name,lInterval);
	}
	
	/**
	 * Retourne la catégorie parente de cette instance.
	 * @return La catégorie parente ou null si elle n'existe pas
	 */
	public Events getParent(){
		return parent;
	}

	/**
	 * Met à jour la catégorie parente de cette instance avec parent.
	 * @param parent Catégorie parente de cette instance
	 * @return void
	 */
	public void setParent(Events parent){
		this.parent = parent;
	}
	
	/**
	 * Ajoute un �v�nement � la liste des enfants si les intervalles de celui-ci sont inclusent dans l'�v�nement.
	 * Lance une erreur si ce n'est pas le cas.
	 * @param child {@link Event}
	 * @throws Exception
	 */
	public void addChild(Events child) throws Exception{
		if(this.include(child)){
			this.getChildren().add(child);
			((Event)child).setParent(this);
		}else{
			throw new Exception("L'intervalle de l'�v�nement parent doit contenir celui de l'enfant.");
		}
	}
	        
}
