package categorisation_image;

import java.io.*;
import java.utils.*;

public class Main {

	private String pathParametre = " .. ";
	private String pathEvent = " .. ";

	private String pathrep;
	private static Parametre param = new Parametre( pathParametre ); 
	private static Calendar userCalendar = new Calendar( pathEvent );
	

	
	public static void afficheMenuPrincipal() 
	{
		Scanner sc = new Scanner ( System.in );
		String saisie = "" ;
		System.out.pritnln("#############  BIENVENUE #############");
		System.out.println("          1 - Lancer le tri           ");
		System.out.println("            2 - Paramètre             ");
		System.out.println("             3 - Quitter              ");
		System.out.println("######################################");
		saisie = in.nextLine();

		afficheSubMenu( saisie );
	}

	public static void afficheSubMenu( String saisie )
	{
		Scanner sc = new Scanner ( System.in );
		String saisieSub = "";

		switch ( saisie )
		{
			case "1" :  System.out.println("Merci de rentrer le chemin du dossier ( Ex : /home/ .. )");
					    saisieSub = sc.nextLine();
					    break;
		    case "2" :  System.out.println(" 1 - Paramètrer les évenements");
		    		    System.out.println(" 2 - Paramètrer le logiciel");
		    		    saisieSub = sc.nextLine();
		    		     break;
		}

		switch ( saisieSub )
		{
			case ( saisieSub.contains("/") || saisieSub.contains("\\") ): 
						Sorter S = new Sorter( eventCalendar );
						S.doTri();
						break;
			default:
					afficheMenuParametre( saisieSub );
					break;
		}
	}
	
	public static void afficheMenuParametre ( String saisie ) 
	{
		Scanner sc = new Scanner ( System.in );
		String saisieParam = "";

		switch ( saisie )
		{
			case "1" :  System.out.println("1 - Ajouter un événement");
						System.out.println("2 - Supprimer un événement");
						System.out.println("3 - Modifier un événement");
						saisieParam = sc.nextLine();
						break;
			case "2" :  System.out.println("Modifier la finesse du tri");
						System.out.println("1 - Par jour");
						System.out.println("2 - Par heure");
						saisieParam = sc.nextLine();
						break;
		}

		afficheSubMenuParam( saisie, saisieParam );
	}
	
	public static void afficheSubMenuParam( String saisie , String saisieParam )
	{
		String nomEvent = "";
		String dateDebut = "";
		String dateFin = "";
		String newNom = "";

		Scanner sc = new Scanner ( System.in );
		switch ( saisie )
		{
			case "1" : 
					switch ( saisieParam )
					{
						case "1" : System.out.println("Nom événements : ");
								   nomEvent = sc.nextLine();
								   System.out.println("Date début événement : ");
								   dateDebut = sc.nextLine();
								   System.out.println("Date fin événement : ");
								   dateFin = sc.nextLine();
								   userCalendar.addEvent( nomEvent, dateDebut, dateFin );
								   break;
						case "2" : System.out.println("Nom événements : ");
								   nomEvent = sc.nextLine();
								   userCalendar.remove ( nomEvent );
								   break;
					    case "3" : System.out.println("Nom événements : ");
					     		   nomEvent = sc.nextLine();
					     		   System.out.println("Nouveau nom : ");
					     		   newNom = sc.nextLine();
					     		   userCalendar.addEvent( nomEvent , newNom );
					     		   break;
					}
			case "2" : 
					switch ( saisieParam )
					{
						case "1" :  param.setFinesse( "jour" );
									break;
						case "2" :  param.setFinesse( "heure");
									break; 

					}
		}
	}


	public static void main(String[] args) 
	{
			afficheMenuPrincipal();
	}

}
