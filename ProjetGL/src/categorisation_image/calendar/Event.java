package categorisation_image.calendar;

import java.util.Date;
import java.util.List;

/**
 * Classe d√©crivant une cat√©gorie, avec son nom et les intervalles de temps la concernant.
 */

public class Event extends Events {	

	/**
	 * EvÈnement parent.
	 */
	private Events parent;
    
	/**
	 * Construit une nouvelle instance de nom name, et dont les intervalles de temps sont ceux de lDate.
	 * @param name Nom de la cat√©gorie
	 * @param lDate Liste des intervalles de temps dans lesquels l'√©v√®nement prend place
	 */
	public Event(String name, List<Interval> lInterval) {
		super(name,lInterval);
	}
	
	/**
	 * Retourne la cat√©gorie parente de cette instance.
	 * @return La cat√©gorie parente ou null si elle n'existe pas
	 */
	public Events getParent(){
		return parent;
	}

	/**
	 * Met √† jour la cat√©gorie parente de cette instance avec parent.
	 * @param parent Cat√©gorie parente de cette instance
	 * @return void
	 */
	public void setParent(Events parent){
		this.parent = parent;
	}
	
	/**
	 * Ajoute un ÈvËnement ‡ la liste des enfants si les intervalles de celui-ci sont inclusent dans l'ÈvËnement.
	 * Lance une erreur si ce n'est pas le cas.
	 * @param child {@link Event}
	 * @throws Exception
	 */
	public void addChild(Events child) throws Exception{
		if(this.include(child)){
			this.getChildren().add(child);
			((Event)child).setParent(this);
		}else{
			throw new Exception("L'intervalle de l'ÈvÈnement parent doit contenir celui de l'enfant.");
		}
	}
	        
}
