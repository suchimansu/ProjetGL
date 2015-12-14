package categorisation_image;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.TreeMap;

import categorisation_image.calendar.Calendar;
import categorisation_image.calendar.EventGlobal;
import categorisation_image.calendar.Events;
import categorisation_image.scan.Scan;

/**
 * Classe permettant de trier un ensemble de fichiers images.
 * @see Scan
 */
public class Sorter
{

    private Calendar tempEventCalendar;
    private Parameter param;
    private int nbPhoto;

	/**
	 * Construit un nouveau trieur de photos utilisant la granularite p et aucune categorie predefinie.
	 * @param p Granularite du tri
	 */
    public Sorter(Parameter p)
    {
        tempEventCalendar = new Calendar();
        param = p;
        System.out.println(param.getSortParameter());
        this.nbPhoto = 0;
    }

	/**
	 * Construit un nouveau trieur de photos utilisant la granularite p et les categories de userCal.
	 * @param p Granularite du tri
	 * @param userCal Calendrier des categories definies par l'utilisateur
	 */
    public Sorter(Calendar userCal, Parameter p)
    {
        this(p);
        this.tempEventCalendar = userCal;
    }

	/**
	 * Effectue le tri des photos se trouvant dans l'arborescence du dossier pathIn.
	 * @param pathIn Chemin du dossier contenant les photos a trier
	 * @return void
	 */
    public void doTri(String pathIn) throws Exception
    {
        TreeMap<Long, Image> images;
        Scan s = new Scan();
        EventGlobal globalEvent = tempEventCalendar.getGlobalEvent();
        // listeEvent = tempEventCalendar.getListEvent();
        s.doScan(new File(pathIn));
        images = s.getMap();
        nbPhoto = images.size();
        userEventSort(globalEvent, images);
        unsortedSort(images);
    }

	/**
	 * Tri les photos en fonction des categories definies par l'utilisateur.
     * Dans le premier cas on est dans l'Events GlobalEvent
	 * @param globalEvent racine de l'arborescence des categories
	 * @param mapImage Ensemble des fichiers images a trier
	 * @return void
	 * @see Events 
     * @see EventGlobal
	 */
    private void userEventSort(Events globalEvent, TreeMap<Long, Image> mapImage) throws Exception
    {
    	String dest = this.param.getDestDir();
        Date dateImage;
        if (globalEvent.hadChildren()) 
        {
            for (Long key : mapImage.keySet())
            {
                dateImage = mapImage.get(key).getTimeDate();
                if (globalEvent.isInclude(dateImage))
                {
                    for (Events children : globalEvent.getChildren())
                    {
                        if (children.isInclude(dateImage))
                        {
                            dest += File.separator + children.getName();
                            //l'image est dans une categorie de l'utilisateur
                            userEventSortBis(children, mapImage, key, dest);
                        }
                    }
                } 
            }
        } 
    }
    /**
	 * Tri les photos en fonction des categories definies par l'utilisateur.
     * Dans les cas suivants on est dans les Events Event
	 * @param event racine de l'arborescence des categories
	 * @param mapImage Ensemble des fichiers images a trier
	 * @param dest Chemin du dossier ou doivent se trouver les photos
	 * @return void
	 * @see Events
     * @see Event
	 */
    private void userEventSortBis(Events event, TreeMap<Long, Image> mapImage, Long key, String dest)
    {
        boolean b = true;
        Date dateImage = mapImage.get(key).getTimeDate();
        if (event.hadChildren())
        {
            for (Events children : event.getChildren())
            {
                if (children.isInclude(dateImage))
                {
                    //dest += "/" + children.getNom(); //on agrandi l'arborescence du chemin
                    //l'image est dans une categorie de l'utilisateur
                    userEventSortBis(children, mapImage, key, dest + "/" + children.getName());
                    b = !b; //on a trouve une sous categorie pour l'image
                }
            }
        }

        if (b) 
        {//on est dans le dossier ou doit etre la photo
        	
            String pathImageS = mapImage.get(key).getPath();
            Path pathImage = Paths.get(pathImageS);
            File srcImage = pathImage.toFile();
            
            Path pathDest = Paths.get(dest);
            File fileDest = pathDest.toFile();
            fileDest.mkdirs();
            fileDest = new File(fileDest.getAbsolutePath() + File.separator + mapImage.get(key).getFileName());

            move(srcImage, fileDest);
            
            mapImage.remove(key);//on enleve l'image du TreeMap
            System.out.println(mapImage.get(mapImage.firstKey()).getFileName() + " : " + (nbPhoto-mapImage.size()) + "/" + nbPhoto );
        }
    }
    
    /**
	 * Tri les photos ne correspondant a aucune categorie definie par l'utilisateur.
	 * @param l List des images a trier
	 * @return void
	 */
    @SuppressWarnings("deprecation")
	private void unsortedSort(TreeMap<Long, Image> mapImage) throws Exception 
    {
    	String nomDossierDest = param.getDestDir();
    	long entreDeux = 0;
    	boolean sameDossier = true;
    	TreeMap<Long, Image> l = new TreeMap<Long, Image>();
    	if(mapImage.size() > 0)
    	{
	    	Long temps1 = mapImage.get(mapImage.firstKey()).getTimeLong();
	    	Long temps2;
	    	l.put(mapImage.firstKey(), mapImage.get(mapImage.firstKey()));
	    	System.out.println(mapImage.get(mapImage.firstKey()).getFileName() + " : " 
					+ (nbPhoto-mapImage.size()) + "/" + nbPhoto );
	    	mapImage.remove(mapImage.firstKey());
	    	
//System.console().flush();
	    	
	    	Date d = l.get(l.lastKey()).getTimeDate();
	    	/*nomDossierDest += convertMonth(d.getMonth());
	    			d.getDate() + "-"
					+ (d.getMonth() + 1) + "-"
					+ (d.getYear()+1900);
			if(param.getSortParameter() == ListParameter.mois.getTime())
			{
				nomDossierDest += "_at_" + d.getHours() + "h";
			}*/
				if(param.getSortParameter() == ListParameter.mois.getTime())
				{
					nomDossierDest += convertMonth(d.getMonth());
				}
				else
				{
					nomDossierDest += d.getDay() + " " + convertMonth(d.getMonth());
				}
	    	
	    	while (!mapImage.isEmpty() && sameDossier)
	    	{	
	    		temps2 = mapImage.get(mapImage.firstKey()).getTimeLong();
	    		entreDeux =  temps2 - temps1;
	    		
	    		if(entreDeux < param.getSortParameter())
	    		{
	    			temps1 = temps2;
	    			l.put(mapImage.firstKey(), mapImage.get(mapImage.firstKey()));
	    			mapImage.remove(mapImage.firstKey());
	    		}
	    		else
	    		{
	    			sameDossier = false;
	    			unsortedSort(mapImage);
	    		}
	    		
	    	}
	    	
	    	//on deplace les images contenues dans l dans le bon dossier
	    	for(Long key : l.keySet())
	    	{
	    		String pathImageS = l.get(key).getPath();
	            Path pathImage = Paths.get(pathImageS);
	            File srcImage = pathImage.toFile();
	            
	            Path pathDest = Paths.get(nomDossierDest);
	            File fileDest = pathDest.toFile();
	            fileDest.mkdirs();
	            fileDest = new File(fileDest.getAbsolutePath() + File.separator + l.get(key).getFileName());
	            move(srcImage, fileDest);
	    	}
    	}
    }
    
    
    /**
	 * Copy le contenu du fichier src dans dest.
	 * @param src Chemin du fichier a copier
	 * @param dest Chemin de destination de la copie
	 * @return Vrai si la copie s'est effectuee sans probleme, faux sinon
	 */
    // A priori copy ne doit pas etre utilisee, c'est une fonction utilisee par move !
    @SuppressWarnings("resource") // chez moi ca ne voit pas que flux ferme dans le finally -> evite warning
	private boolean copy(File src, File dest)
    {
    	FileChannel in = null; // entree
    	FileChannel out = null; // sortie
    	boolean ret;
    	try // initialisation des flux
    	{
    	  in = new FileInputStream(src).getChannel();
    	  out = new FileOutputStream(dest).getChannel();
    	 
    	  // Copie du fichier
    	  long size = in.size();
    	  long count = in.transferTo(0, size, out);
    	  ret = (count == size); // vrai ssi fichier copie est copie ENTIEREMENT
    	}
    	catch (Exception e)
    	{
    	  e.printStackTrace(); // erreur dans ouverture/lecture flux, transfert, etc. pleins d'erreurs possibles voir doc
    	  ret = false;
    	}
    	finally // fermeture des flux ouverts dans tous les cas
    	{
    	  if(in != null)
    	  {
    	  	try
    	  	{
    		  in.close();
    		}
    	  	catch (IOException e) {}
    	  }
    	  if(out != null)
    	  {
    	  	try
    	  	{
    		  out.close();
    		} catch (IOException e) {}
    	  }
    	}
		return ret;
    }

    /**
	 * Deplace le fichier src vers dest. Le deplacement est effectue a l'aide d'un renommage, 
	 * ou d'une copie si le renommage echoue
	 * @param src Chemin du fichier a deplacer
	 * @param dest Chemin de destination du fichier
	 * @return Vrai si le deplacement s'est effectuee sans probleme, faux sinon
	 */
    private boolean move(File src,File dest) 
    {
        if (!dest.exists()) // pas de fichier existant avec le meme nom
        {
	        boolean res = src.renameTo(dest); // deplacement par renommage plus rapide
	        if(!res) // si renommage echoue
	        {
	            // copie du fichier puis effacement de l'ancien (plus long que renommage)
	            res = true;
	            res &= this.copy(src,dest);
	            if(res) // copie reussie, on peut effacer l'ancien
	        	{
	        		res &= src.delete();
	        	}
	        }
	        return(res);
        }
        else // fichier de destination existe deja
        {
        	return false;
        }
    }

	private String convertMonth(int m)
	{
		switch(m)
		{
			case 0 : return "Janvier";
			case 1 : return "Fevrier";
			case 2 : return "Mars";
			case 3 : return "Avril";
			case 4 : return "Mai";
			case 5 : return "Juin";
			case 6 : return "Juillet";
			case 7 : return "Aout";
			case 8 : return "Septembre";
			case 9 : return "Octobre";
			case 10 : return "Novembre";
			case 11 : return "Decembre";
			
		}
		return "";
	}
}
