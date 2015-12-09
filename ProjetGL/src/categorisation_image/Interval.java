package categorisation_image;

import java.util.Date;

/**
 * Classe représentant un interval de temps.
 */
public class Interval {

	private Date start;
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

}
