package categorisation_image;

/**
 * Classe enumere permettant de definir un espace de temps
 */
public enum ListParameter {
	mois( "mois" , 3600 * 24 * 7 * 4.34524 ),
	jour( "jour", 3600 * 24 );
	
	private String name;
	private double time;
	
	/**
	 * Construit un nouvel objet de type enumere
	 * @param name nom de l'objet
	 * @param duree le temps associe
	 */
	private ListParameter( String name , double duree )
	{
		this.name= name;
		this.time = duree;
	}
	
	/**
	 * Permet de recuperer le nom de l'objet enumere
	 * @return le nom de l'objet
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Permet de recuperer le temps de l'objet enumere
	 * @return le temps associe a l'objet
	 */
	public double getTime()
	{
		return this.time;
	}
}
