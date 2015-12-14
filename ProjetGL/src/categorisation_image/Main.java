package categorisation_image;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import categorisation_image.calendar.Calendar;
import categorisation_image.calendar.Interval;

/**
 * Classe principal du programme de tri des photos. Il contient,
 * entre autres, le point d'entrée du programme
 */
public class Main {

	private static String pathParametre ;
	private static String pathEvent ;

	private static Parameter param;
	private static Calendar userCalendar ;
	
	/**
	 * Affiche le menu principal de choix utilisateur.
	 * @param sc Flux d'entrée utilisé dans la communication avec l'utilisateur
	 * @return Le choix de l'utilisateur
	 */
	public static int afficheMenuPrincipal( Scanner sc ) 
	{
		boolean b = false;
		int saisie = -1 ;
		
		System.out.println("Configuration dossier sortie par défaut : "+param.getDestDir() );
		System.out.println("#############  BIENVENUE #############");
		System.out.println("          1 - Lancer le tri           ");
		System.out.println("            2 - Paramètre             ");
		System.out.println("             3 - Quitter              ");
		System.out.println("######################################");
		System.out.print("Votre choix : ");
		saisie = sc.nextInt();
		
		while ( !b )
		{
			if ( saisie > 0 && saisie <= 3 )
			{
				b = true;
			}
			else
			{
				System.out.println("Erreur .. ");
				System.out.println("Merci de recommencer votre saisie : ");
				saisie = sc.nextInt();
			}
		}
		
		return saisie;		
	}
	
	/**
	 * Affiche le sous-menu de paramétrage.
	 * @param sc Flux d'entrée utilisé dans la communication avec l'utilisateur
	 * @param  saisie : Le choix de l'utilisateur dans la première partie du menu. 
	 * @return Le choix de l'utilisateur
	 */
	public static int afficheMenuParametre ( int saisie , Scanner sc ) 
	{
		int saisieParam = -1;
		boolean b = false;
		
		if ( saisie == 1 )
		{
			System.out.println("Gestion des événements");
			System.out.println("1 - Ajouter un événement");
			System.out.println("2 - Supprimer un événement");
			System.out.println("3 - Modifier un événement");
		}
		else if ( saisie == 2 )
		{
			System.out.println("Modifier la finesse du tri");
			System.out.println("1 - Par jour");
			System.out.println("2 - Par heure");
			System.out.println("Modifier paramètre de sortie logiciel");
			System.out.println("3 - Modifier le chemin de destination du tri");
		}
		
		System.out.println("0 - Retour au menu principal");

		while ( !b )
		{
			System.out.print("Votre choix : ");
			saisieParam = sc.nextInt();
			if ( saisie == 1 )
			{
				if ( saisieParam == 1 || saisieParam == 2 || saisieParam == 3 )
				{
					b = true;
				}
			}
			else if ( saisie == 2 )
			{
				if ( saisieParam == 1 || saisieParam == 2 || saisieParam == 3)
				{
					b = true;
				}
			}
			else if ( saisie == 0 )
			{
				b = true;
			}
			else
			{
				System.out.println("Le choix séléctionné n'existe pas. Veuillez recommencer votre saisie");
			}
		}
		
	
		return saisieParam;		
	}
	
	/**
	 * Affiche le sous-menu de gestion des catégories.
	 * Appelé à la suite du menu ( 1 - Paramétrer événement, 2 - Paramétrer logiciel )
	 * @param sc Flux d'entrée utilisé dans la communication avec l'utilisateur
	 * @param saisie : La saisie provenant du sous-menu
	 * @param saisieParam : La saisie utilisateur provenant de afficheMenuParametre
	 * @return void
	 */
	public static void afficheSubMenuParam( int saisie , int saisieParam , Scanner sc )
	{
		String nomEvent = "";
		String dateDeb = "";
		String dateEnd = "";
		SimpleDateFormat dateDebut = new SimpleDateFormat( "dd/MM/yy ");
		SimpleDateFormat dateFin = new SimpleDateFormat( "dd/MM/yy" );
		String newNom = "";

		switch ( saisie )
		{
			case 1 : 
					switch ( saisieParam )
					{
						case 1 :   System.out.println("Nom événements : ");
								   nomEvent = sc.next();
								   System.out.println("Date début événement : ");
								   dateDeb = sc.next();
								   System.out.println("Date fin événement : ");
								   dateEnd = sc.next();
								   List < Interval > ar = new ArrayList<>();
								   Date dateDep = null;
								   Date dateFi = null;
								   try 
								   {
									   dateDep = dateDebut.parse( dateDeb );
									   dateFi = dateFin.parse( dateEnd );
			
									} 
								   	catch (java.text.ParseException e) 
								   	{
										e.printStackTrace();
									}
								   ar.add( new Interval( dateDep, dateFi ));
								  
								   userCalendar.addEvent( nomEvent, ar );
								   break;
						case 2 : System.out.println("Nom événements : ");
								   nomEvent = sc.next();
								   userCalendar.remove ( nomEvent );
								   break;
					    case 3 : System.out.println("Nom événements : ");
					     		   nomEvent = sc.next();
					     		   System.out.println("Nouveau nom : ");
					     		   newNom = sc.next();
					     		   userCalendar.editEvent( nomEvent , newNom );
					     		   break;
					}
			case 2 : 
					switch ( saisieParam )
					{
						case 1 :  
									param.setSortParameter( 3600 * 24 );
									break;
						case 2 : 
									param.setSortParameter( 3600 );
									break; 
						case 3 :
									System.out.print("Chemin du dossier de sortie : ");
									String pathTemp = sc.next();
									boolean b = false;
									
									while ( !b )
									{
										if ( pathTemp.contains("/") || pathTemp.contains("\\") )
										{
											if ( !( pathTemp.lastIndexOf(File.separator) == pathTemp.length() ) )
											{
												pathTemp += File.separator;
											}
											
											b = true;
										}
										else
										{
											System.out.println("Erreur .. ");
											System.out.print("Chemin du dossier de sortie : ");
											pathTemp = sc.next();
										}
									}
										
									param.setDestDir( pathTemp);
									break;
					}
		}
	}

	/**
	 * Vérifie si le fichier décrit par le chemin path existe.
	 * @param path chemin vers un fichier
	 * @return vrai si le fichier existe, faux sinon
	 */
	public static boolean verify( String path )
	{
		File f = new File ( path );

		if ( f.exists() )
		{
			return true;
		}

		return false;
	}

	/**
	 * Vérifie si le nombre de paramètre entrés par l'utilisateur est correct.
	 * @param saisieParam : Une saisie utilisateur
	 * @param nbParam : Le nombre de paramètre qui ont été donné ( ex : nbParam = 3 , car 3 choix possible )
	 * @return vrai si le nombre de paramètre est correct, faux sinon
	 */
	public static boolean verifyParam ( int saisieParam , int nbParam )
	{
		if ( saisieParam <= nbParam && saisieParam >= 0 )
		{
			return true;
		}

		return false;
	}

	/**
	 * Point d'entrée du programme.
	 * @param args Tableau des paramètres passés au programme via la ligne de commande
	 * @return void
	 */
	public static void main(String[] args) 
	{
		pathParametre = "../saveRessource.conf";
		pathEvent = "ressource/ADECal(1).ics";
		param =  new Parameter( pathParametre );
		userCalendar = new Calendar( pathEvent );
		
		Scanner sc = new Scanner ( System.in );
		int menuPrincipal = afficheMenuPrincipal( sc );


		while ( menuPrincipal != 3 )
		{
			// Lancer le tri
			if ( menuPrincipal == 1 )
			{
				System.out.println("1 - Entrer le chemin du dossier de destination");
				System.out.println("0 - Retour au menu principal");
				
				boolean b = false;
				int saisie = -1;
				while ( !b )
				{
					System.out.print("Votre choix : ");
					saisie = sc.nextInt();

					if ( saisie == 0 || saisie == 1 )
					{
						b = true;
					}
					else
					{
						System.out.println("Erreur dans la saisie");
					}
				}
				
				if ( saisie != 0 )
				{
					System.out.println("Chemin : ");
					String saisiePath = sc.next();
					while ( !verify ( saisiePath ) )
					{
						System.out.println("Erreur .. ");
						System.out.println("Chemin : ");	
						saisiePath = sc.next();
					}
					
					if ( !( saisiePath.lastIndexOf(File.separator) == saisiePath.length() - 1 ) )
					{
						saisiePath+=File.separator;
					}
					
					Sorter S = new Sorter(userCalendar, param);
			    	try 
			    	{
						S.doTri( saisiePath );
					} 
			    	catch (Exception e) 
			    	{
						e.printStackTrace();
					}
				}
			}
			// Paramètre
			else if ( menuPrincipal == 2 )
			{
				System.out.println(" 1 - Paramètrer les évenements");
		    	System.out.println(" 2 - Paramètrer le logiciel");
		    	System.out.println(" 0 - Retour menu principal");
		    	int saisieParam = sc.nextInt();

		    	while ( !verifyParam ( saisieParam , 3 ) )
		    	{
		    		System.out.println("Erreur .. ");
		    		System.out.println(" 1 - Paramètrer les évenements");
		    		System.out.println(" 2 - Paramètrer le logiciel");
		    		System.out.println(" 0 - Retour menu principal");
		    		saisieParam = sc.nextInt();
		    	}
		    	
		    	if ( saisieParam != 0 )
		    	{
		    		int recupMenuParam = afficheMenuParametre( saisieParam, sc );
		    		if ( recupMenuParam != 0)
		    		{
			    		afficheSubMenuParam( saisieParam , recupMenuParam , sc);
		    		}
		    	}
			}

			menuPrincipal = afficheMenuPrincipal( sc );
		}
		param.save();
		sc.close();
		System.out.println( "A bientôt !");
	}
}