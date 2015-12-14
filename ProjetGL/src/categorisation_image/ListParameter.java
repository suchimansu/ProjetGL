package categorisation_image;

public enum ListParameter {
	mois( "mois" , 3600 * 24 * 7 * 4.34524 ),
	jour( "jour", 3600 * 24 );
	
	private String name;
	private double time;
	
	private ListParameter( String name , double duree )
	{
		this.name= name;
		this.time = duree;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public double getTime()
	{
		return this.time;
	}
}
