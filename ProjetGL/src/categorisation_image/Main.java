package categorisation_image;

import java.io.File;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.NullArgumentException;

import categorisation_image.calendar.Calendar;
import categorisation_image.calendar.Interval;

/**
 * Classe principal du programme de tri des photos. Il contient,
 * entre autres, le point d'entree du programme
 */
public class Main {

	private static String pathParametre ;
	private static String pathEvent ;

	private static Parameter param;
	private static Calendar userCalendar ;
	
	private static ListParameter l;
	
	/**
	 * Affiche le menu principal de choix utilisateur.
	 * @param sc Flux d'entree utilise dans la communication avec l'utilisateur
	 * @return Le choix de l'utilisateur
	 */
	public static int afficheMenuPrincipal( Scanner sc ) 
	{
		boolean b = false;
		int saisie = -1 ;
		
		System.out.println("Configuration dossier sortie par defaut : "+param.getDestDir() );
		System.out.println("#############  BIENVENUE #############");
		System.out.println("          1 - Lancer le tri           ");
		System.out.println("            2 - Parametre             ");
		System.out.println("             3 - Quitter              ");
		System.out.println("######################################");

		while ( !b )
		{
			System.out.print("Votre choix : ");
			if ( sc.hasNextInt() )
			{
				saisie = sc.nextInt();
				if ( saisie > 0 && saisie <= 3 )
				{
					b = true;
				}
				else
				{
					System.out.println("Erreur dans la saisie. Le choix n'existe pas.");
				}
			}
			else
			{
				System.out.println("Merci d'entrer un des choix possibles ( 1, 2 .. )");
				sc.next();
			}
			
		}
		
		return saisie;		
	}
	
	/**
	 * Affiche le sous-menu de parametrage.
	 * @param sc Flux d'entree utilise dans la communication avec l'utilisateur
	 * @param  saisie : Le choix de l'utilisateur dans la premiere partie du menu. 
	 * @return Le choix de l'utilisateur
	 */
	public static int afficheMenuParametre ( int saisie , Scanner sc ) 
	{
		int saisieParam = -1;
		boolean b = false;
		
		if ( saisie == 1 )
		{
			System.out.println("Gestion des evenements");
			System.out.println("1 - Ajouter un evenement");
			System.out.println("2 - Supprimer un evenement");
			System.out.println("3 - Modifier un evenement");
		}
		else if ( saisie == 2 )
		{
			System.out.println("Modifier la finesse du tri");
			System.out.println("1 - Par jour");
			System.out.println("2 - Par mois");
			System.out.println("Modifier parametre de sortie logiciel");
			System.out.println("3 - Modifier le chemin de destination du tri");
		}
		
		System.out.println("0 - Retour au menu principal");

		while ( !b )
		{
			System.out.print("Votre choix : ");
			if ( sc.hasNextInt() )
			{
				saisieParam = sc.nextInt();
				if ( saisieParam > 3 || saisieParam < 0 )
				{
					System.out.println("Erreur dans la saisie. Le choix n'existe pas.");
				}
				else
				{
					if ( saisieParam != 0 )
					{
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
					}
					else
					{
						b = true;
					}
				}
			}
			else
			{
				System.out.println("Merci d'entrer un des choix possibles ( 1, 2 .. )");
				sc.next();
			}
		}
		
	
		return saisieParam;		
	}
	
	/**
	 * Affiche le sous-menu de gestion des categories.
	 * Appele a la suite du menu ( 1 - Parametrer evenement, 2 - Parametrer logiciel )
	 * @param sc Flux d'entree utilise dans la communication avec l'utilisateur
	 * @param saisie : La saisie provenant du sous-menu
	 * @param saisieParam : La saisie utilisateur provenant de afficheMenuParametre
	 * @return void
	 */
	public static void afficheSubMenuParam( int saisie , int saisieParam , Scanner sc )
	{
		String nomEvent = "";
		String dateDeb = "";
		String dateEnd = "";
		String newNom = "";
		boolean b = false;
		switch ( saisie )
		{
			case 1 : 
					switch ( saisieParam )
					{
						case 1 :   
							while ( !b )
							{
								   System.out.println("Nom evenement : ");
								   nomEvent = sc.next();
								   System.out.println("Date debut evenement ( Format : 00/00/2000 - Jour, mois, annee)");
								   dateDeb = sc.next();
								   System.out.println("Date fin evenement ( Format : 00/00/2000 - Jour, mois, annee )");
								   dateEnd = sc.next();
								   List < Interval > ar = new ArrayList<>();
								   Date dateDep = null;
								   Date dateFi = null;
								   try 
								   {
									   dateDep = new SimpleDateFormat("dd/MM/yyyy").parse( dateDeb.trim() );
									   dateFi = new SimpleDateFormat("dd/MM/yyyy").parse( dateEnd.trim() );
									   ar.add( new Interval( dateDep, dateFi ));
									   userCalendar.addEvent( nomEvent, ar );
									   b = true;
									} 
								   	catch (java.text.ParseException e) 
								   	{
										System.out.println("Erreur dans le format de date. Rappel : 00/00/0000");
									}
								   	catch ( NullArgumentException n)
								   	{
								   		System.out.println("Erreur dans le format de date. Rappel : 00/00/0000");
								   		b = false;
								   	}
								   	catch ( InvalidParameterException i )
								   	{
								   		System.out.println("La date de debut doit etre posterieur a la date de fin.");
								   		b = false;
								   	}		 						  
								}
								   break;
						case 2 :   
							b = false;
							while ( !b )
							{
								   System.out.println("Nom evenement : ");
								   nomEvent = sc.next();
								   try 
								   {
									   userCalendar.remove ( nomEvent );
									   b = true;
								   }
								   catch ( Exception e )
								   {
									   System.out.println("Le nom d'evenement n'existe pas");
								   }
							}
							      
								   break;
					    case 3 :   
					    	b = false;
					    	while ( !b )
					    	{
					    		   System.out.println("Nom evenement : ");
					     		   nomEvent = sc.next();
					     		   System.out.println("Nouveau nom : ");
					     		   newNom = sc.next();
					     		   
					     		   try
					     		   {
					     			   userCalendar.editEvent( nomEvent , newNom );
					     			   b = true;
					     		   }
					     		   catch ( Exception e )
					     		   {
					     			   System.out.println("L'evenement a modifier n'existe pas");
					     		   }
					    	}
					     		   break;
					} break;
			case 2 : 
					switch ( saisieParam )
					{
						case 1 :  
									param.setSortParameter( l.jour.getTime() );
									break;
						case 2 : 
									param.setSortParameter( l.mois.getTime() );
									break; 
						case 3 :
									System.out.print("Chemin du dossier de sortie : ");
									String pathTemp = sc.next();
									b = false;
									while ( !b )
									{
										if ( pathTemp.contains("/") || pathTemp.contains("\\") )
										{
											if ( !( pathTemp.lastIndexOf(File.separator) == pathTemp.length() - 1) )
											{
												pathTemp += File.separator;
											}
											
											b = true;
										}
										else
										{
											System.out.println("Le dossier de sortie ne comprends pas de"
																+"separateur de repertoire.");
											System.out.print("Chemin du dossier de sortie : ");
											pathTemp = sc.next();
										}
									}
										
									param.setDestDir( pathTemp);
									break;
					} break;
		}
	}

	/**
	 * Verifie si le fichier decrit par le chemin path existe.
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
	 * Verifie si le nombre de parametre entres par l'utilisateur est correct.
	 * @param saisieParam : Une saisie utilisateur
	 * @param nbParam : Le nombre de parametre qui ont ete donne ( ex : nbParam = 3 , car 3 choix possible )
	 * @return vrai si le nombre de parametre est correct, faux sinon
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
	 * Point d'entree du programme.
	 * @param args Tableau des parametres passes au programme via la ligne de commande
	 * @return void
	 */
	public static void main(String[] args) 
	{
		pathParametre = "ressource/saveRessource.conf";
		pathEvent = "ressource/ADECal(1).ics";
		param =  new Parameter( pathParametre );
		userCalendar = new Calendar( pathEvent );
		
		Scanner sc = new Scanner ( System.in );
		int menuPrincipal = afficheMenuPrincipal( sc );
		boolean b = false;


		while ( menuPrincipal != 3 )
		{
			// Lancer le tri
			if ( menuPrincipal == 1 )
			{
				System.out.println("1 - Entrer le chemin du dossier source d'images");
				System.out.println("0 - Retour au menu principal");
				
				int saisie = -1;
				b = false;
				while ( !b )
				{
					System.out.print("Votre choix : ");
					
					if ( sc.hasNextInt() )
					{
						saisie = sc.nextInt();

						if ( saisie == 0 || saisie == 1 )
						{
							b = true;
						}
						else
						{
							System.out.println("Erreur dans la saisie. Le choix n'existe pas.");
						}
					}
					else
					{
						System.out.println("Merci d'entrer un des choix possibles ( 1, 2 .. )");
						sc.next();
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
					
					System.out.println( saisiePath );
					Sorter S = new Sorter(userCalendar, param);
			    	try 
			    	{
						S.doTri( saisiePath );
					} 
			    	catch (Exception e) 
			    	{
			    		e.printStackTrace();
						System.out.println("Le systeme n'as pas reussi a effectuer le tri de vos photos.");
					}
				}
			}
			// Parametre
			else if ( menuPrincipal == 2 )
			{
				System.out.println(" 1 - Parametrer les evenements");
		    	System.out.println(" 2 - Parametrer le logiciel");
		    	System.out.println(" 0 - Retour menu principal");
		    	
		    	int saisieParam = -1;
		    	b = false;
		    	while ( !b )
		    	{
		    		System.out.print("Votre choix : ");
			    	if ( sc.hasNextInt() )
			    	{
			    		saisieParam = sc.nextInt();
			    		if ( verifyParam ( saisieParam , 3 ) )
			    		{
			    			b = true;
			    		}
			    		else
			    		{
			    			System.out.println("Erreur dans la saisie. Le choix n'existe pas.");
			    		}
			    	}
			    	else
			    	{
			    		System.out.println("Merci d'entrer un des choix possibles ( 1, 2 .. )");
			    		sc.next();
			    	}
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
		userCalendar.save(pathEvent);
		sc.close();
		System.out.println( "A bientot !");
	}
}