package categorisation_image.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Events {

	/**
	 * Nom de l'�v�nement.
	 */
	protected String name;
	/**
	 * Liste des �v�nement enfants.
	 */
	private List<Events> child;
	/**
	 * Liste des intervalles de l'�v�nement.
	 */
	private List<Interval> intervale;

	public Events(String name, List<Interval> lInterval) {
		this.child = new ArrayList<Events>();
		this.name = name;
		List<Interval> list = new ArrayList<Interval>();
		if(lInterval.size() > 0){
			list.add(lInterval.get(0));
			Interval tmp;
			boolean include;
			try {
				//nettoyage de la liste d'intervalle en fusionnant les intervalles qui se chevauche.
				for(int i = 1 ; i < lInterval.size() ; i++ ){
					tmp = lInterval.get(i);
					include = false;
					for(Interval interval : list){
						if(interval.isInclude(tmp.getStart()) && !interval.isInclude(tmp.getEnd())){
							interval = new Interval(interval.getStart(), tmp.getEnd());
							include = true;
							break;
						}else if(interval.isInclude(tmp.getEnd()) && !interval.isInclude(tmp.getStart())){
							interval = new Interval(tmp.getStart(),interval.getEnd());
							include = true;
							break;
						}else if(interval.isInclude(tmp.getStart()) && interval.isInclude(tmp.getEnd())){
							include = true;
							break;
						}
					}
					if(!include){
						list.add(tmp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.intervale = lInterval;
	}
	
	public List<Interval> getIntervale(){
		return intervale;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Retourne le nom de la catégorie décrite par cette instance.
	 * @return le nom de la catégorie
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Indique si la date est dans l'ev�nement.
	 * @param date {@link Date}
	 * @return boolean
	 */
	public boolean isInclude(Date date){
		for(Interval i : intervale){
			if(i.isInclude(date)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Indique si l'�v�nement param�tre est compris dans l'�v�nement.
	 * @param child {@link Event}
	 * @return boolean
	 */
	public boolean include(Events child){
		boolean tmp = false;
		boolean r = true;
		for(Interval i : child.getIntervale()){
			for(Interval j : intervale){
				tmp = tmp || (j.isInclude(i.getStart())&&j.isInclude(i.getEnd()));
			}
			r = r && tmp;
			tmp = false;
		}
		return r;
	}

	/**
	 * Retourne la liste des �v�nements enfants.
	 * @return List<{@link Event}>
	 */
	public List<Events> getChildren(){
		return child;
	}
	
	/**
	 * Indique la pr�sence d'�v�nement enfant.
	 * @return boolean
	 */
	public boolean hadChildren(){
		return !child.isEmpty();
	}
	
	public abstract void addChild(Events child) throws Exception;

}
