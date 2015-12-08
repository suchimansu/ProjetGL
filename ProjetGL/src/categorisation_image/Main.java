package categorisation_image;

import java.io.*;
import java.util.*;

public class Main {

	private static String pathParametre = " .. ";
	private static String pathEvent = " .. ";

	private String pathrep;
	private static Parametre param = new Parametre( pathParametre ); 
	private static Calendar userCalendar = new Calendar( pathEvent );
	

	
	public static void afficheMenuPrincipal() 
	{
		boolean b = false;
		int saisie = -1 ;
		System.out.println("#############  BIENVENUE #############");
		System.out.println("          1 - Lancer le tri           ");
		System.out.println("            2 - Paramètre             ");
		System.out.println("             3 - Quitter              ");
		System.out.println("######################################");
		System.out.print("Votre choix : ");
		Scanner sc = new Scanner ( System.in );

		while ( !b )
		{
			if ( sc.hasNextInt() )
			{
				saisie = sc.nextInt();
				b = true;
			}
			else
			{
				System.out.println("\nErreur sur la saisie");
				System.out.print("Votre choix : ");
				sc.next();
			}
		}
		sc.close();
		
		afficheSubMenu( saisie );
	}

	public static void afficheSubMenu( int saisie )
	{
		Scanner sc = new Scanner ( System.in );
		String saisieSub = "";
		boolean b = false;
		
		if ( saisie == 1 )
		{
			System.out.println("Merci de rentrer le chemin du dossier ( Ex : /home/ .. )");
		}
		else if ( saisie == 2 )
		{
			System.out.println(" 1 - Paramètrer les évenements");
		    System.out.println(" 2 - Paramètrer le logiciel");
		}
		
		while ( !b )
		{
			if ( sc.hasNextLine() )
			{
				saisieSub = sc.nextLine();
				b = true;
			}
			else
			{
				System.out.println("\nErreur sur la saisie");
				System.out.print("Votre choix : ");
				sc.next();
			}
		}
		sc.close();

		if ( saisieSub.charAt(0) == '1' || saisieSub.charAt(0) == '2' )
		{
			afficheMenuParametre( Integer.parseInt( saisieSub ) );
		}
		else
		{
			Sorter S = new Sorter( userCalendar );
			S.doTri();
		}
		
	}
	
	public static void afficheMenuParametre ( int saisie ) 
	{
		int saisieParam = -1;
		boolean b = false;
		
		if ( saisie == 1 )
		{
			System.out.println("1 - Ajouter un événement");
			System.out.println("2 - Supprimer un événement");
			System.out.println("3 - Modifier un événement");
		}
		else if ( saisie == 2 )
		{
			System.out.println("Modifier la finesse du tri");
			System.out.println("1 - Par jour");
			System.out.println("2 - Par heure");
		}

		Scanner sc = new Scanner ( System.in );
		while ( !b )
		{
			if ( sc.hasNextInt() )
			{
				saisieParam = sc.nextInt();
				b = true;
			}
			else
			{
				System.out.println("\nErreur sur la saisie");
				System.out.print("Votre choix : ");
				sc.next();
			}
		}
		sc.close();
		
		afficheSubMenuParam( saisie, saisieParam );
	}
	
	public static void afficheSubMenuParam( int saisie , int saisieParam )
	{
		String nomEvent = "";
		String dateDebut = "";
		String dateFin = "";
		String newNom = "";

		Scanner sc = new Scanner ( System.in );
		switch ( saisie )
		{
			case 1 : 
					switch ( saisieParam )
					{
						case 1 :   System.out.println("Nom événements : ");
								   nomEvent = sc.nextLine();
								   System.out.println("Date début événement : ");
								   dateDebut = sc.nextLine();
								   System.out.println("Date fin événement : ");
								   dateFin = sc.nextLine();
								   userCalendar.addEvent( nomEvent, dateDebut, dateFin );
								   break;
						case 2 : System.out.println("Nom événements : ");
								   nomEvent = sc.nextLine();
								   userCalendar.remove ( nomEvent );
								   break;
					    case 3 : System.out.println("Nom événements : ");
					     		   nomEvent = sc.nextLine();
					     		   System.out.println("Nouveau nom : ");
					     		   newNom = sc.nextLine();
					     		   userCalendar.renameEvent( nomEvent , newNom );
					     		   break;
					}
			case 2 : 
					switch ( saisieParam )
					{
						case 1 :  param.setFinesse( "jour" );
									break;
						case 2 :  param.setFinesse( "heure");
									break; 

					}
		}
	}


	public static void main(String[] args) 
	{
			afficheMenuPrincipal();
	}

}
