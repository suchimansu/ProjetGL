package categorisation_image.calendar;

import java.util.Date;

/**
 * Classe représantant un intervalle de temps (couple de date).
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
	 * Construit un nouvel interval de temps Ã  partir des dates de dÃ©but et fin.
	 * @param start Date de dÃ©but de l'interval
	 * @param end Date de fin de l'interval
	 */
	public Interval(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Retourne la date de dÃ©but de l'interval.
	 * @return Date de dÃ©but de l'interval
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
	 * Indique si la {@link Date} passé en paramètre est inclus dans l'intervalle.
	 * @param date {@link Date}
	 * @return boolean
	 */
	public boolean isInclude(Date date){
		return date.getTime()>=start.getTime() && date.getTime()<=end.getTime();
	}

}
