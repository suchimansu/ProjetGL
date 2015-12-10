package categorisation_image;

import java.util.Date;

/**
 * Classe repr�santant un intervalle de temps (couple de date).
 *
 */
public class Interval {

	/**
	 * Date de debut d'intervalle.
	 */
	private Date start;
	/**
	 * Date de fin d'intervalle.
	 */
	private Date end;
	
	/**
	 * Construit un nouvel interval de temps à partir des dates de début et fin.
	 * @param start Date de début de l'interval
	 * @param end Date de fin de l'interval
	 */
	public Interval(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Retourne la date de début de l'interval.
	 * @return Date de début de l'interval
	 */
	public Date getStart(){
		return start;
	}
	
	/**
	 * Retourne la date de fin de l'interval.
	 * @return Date de fin de l'interval
	 */
	public Date getEnd(){
		return end;
	}
	
	/**
	 * Indique si la {@link Date} pass� en param�tre est inclus dans l'intervalle.
	 * @param date {@link Date}
	 * @return boolean
	 */
	public boolean isInclude(Date date){
		return date.getTime()>=start.getTime() && date.getTime()<=end.getTime();
	}

}
