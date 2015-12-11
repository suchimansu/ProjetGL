package categorisation_image.calendar;

import java.security.InvalidParameterException;
import java.util.Date;

import org.apache.commons.lang.NullArgumentException;

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
	 * @throws Exception 
	 */
	public Interval(Date start, Date end) throws Exception {
		// TODO Creer deux exceptions � remonter pour un traitement plus explicite.
		if(start == null || end == null){
			throw new NullArgumentException("Impossible de cr�er un intervalle avec une date de d�but ou de fin nulle.");
		}else if (start.after(end)) {
			throw new InvalidParameterException("La date de fin doit être postérieure à la date de début");
		}else{
			this.start = start;
			this.end = end;
		}
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
