package categorisation_image;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
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

	/**
	 * Construit un nouveau trieur de photos utilisant la granularité p et aucune catégorie prédéfinie.
	 * @param p Granularité du tri
	 */
    public Sorter(Parameter p)
    {
        tempEventCalendar = new Calendar();
        param = p;
    }

	/**
	 * Construit un nouveau trieur de photos utilisant la granularité p et les catégories de userCal.
	 * @param p Granularité du tri
	 * @param userCal Calendrier des catégories définies par l'utilisateur
	 */
    public Sorter(Calendar userCal, Parameter p)
    {
        this(p);
        this.tempEventCalendar = userCal;
    }

	/**
	 * Effectue le tri des photos se trouvant dans l'arborescence du dossier pathIn.
	 * @param pathIn Chemin du dossier contenant les photos à trier
	 * @return void
	 */
    public void doTri(String pathIn) throws Exception
    {
        List<Events> listeEvent;
        TreeMap<Long, Image> images;
        Scan s = new Scan();
        EventGlobal globalEvent = tempEventCalendar.getGlobalEvent();
        // listeEvent = tempEventCalendar.getListEvent();
        images = s.doScan(new File(pathIn));

        userEventSort(globalEvent, images, pathIn);
    }

	/**
	 * Tri les photos en fonction des catégories définies par l'utilisateur.
         * Dans le premier cas on est dans l'Events GlobalEvent
	 * @param globalEvent racine de l'arborescence des catégories
	 * @param mapImage Ensemble des fichiers images à trier
	 * @param pathIn Chemin du dossier contenant les photos à trier
	 * @return void
	 * @see Events 
         * @see EventGlobal
	 */
    private void userEventSort(Events globalEvent, TreeMap<Long, Image> mapImage, String pathIn) throws Exception
    {
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
                            pathIn += children.getNom();
                            //l'image est dans une categorie de l'utilisateur
                            userEventSortBis(children, mapImage, key, pathIn);
                        }
                    }
                } 
                else 
                {
                    //on ne fait rien : l'image n'est pas dans un parametre utilisateur
                }
            }
        } 
        else
        {
            //on ne fait rien : pas de parametre utilisateur
        }
    }
    /**
	 * Tri les photos en fonction des catégories définies par l'utilisateur.
         * Dans les cas suivants on est dans les Events Event
	 * @param event racine de l'arborescence des catégories
	 * @param mapImage Ensemble des fichiers images à trier
	 * @param pathIn Chemin du dossier contenant les photos à trier
	 * @return void
	 * @see Events
         * @see Event
	 */
    private void userEventSortBis(Events event, TreeMap<Long, Image> mapImage, Long key, String pathIn)
    {
        boolean b = true;
        Date dateImage = mapImage.get(key).getTimeDate();
        if (event.hadChildren())
        {
            for (Events children : event.getChildren())
            {
                if (children.isInclude(dateImage))
                {
                    pathIn += "\\" + children.getNom(); //on agrandi l'arborescence du chemin
                    //l'image est dans une categorie de l'utilisateur
                    userEventSortBis(children, mapImage, key, pathIn);
                    b = !b; //on a trouvé une sous categorie pour l'image
                }
            }
        }

        if (b) 
        {//on est dans le dossier ou doit etre la photo
            String pathImageS;
            
            pathImageS = mapImage.get(key).getPath();
            Path pathImage = Paths.get(pathImageS);
            File srcImage = pathImage.toFile();
            Path pathDest = Paths.get(pathIn);
            File dest = pathDest.toFile();
            move(srcImage, dest);
            
            mapImage.remove(key);//on enleve l'image du TreeMap
        }
        else
        {
            //normalement on ne rentre pas ici
        }
    }
    
    /**
	 * Tri les photos ne correspondant à aucune catégorie définie par l'utilisateur.
	 * @param l List des images à trier
	 * @return void
	 */
    private void unsortedSort(List l) throws Exception 
    {
        int affinage = param.getSortParameter();
    }

    /**
	 * Copy le contenu du fichier src dans dest.
	 * @param src Chemin du fichier à copier
	 * @param dest Chemin de destination de la copie
	 * @return Vrai si la copie s'est effectuée sans problème, faux sinon
	 */
    // A priori copy ne doit pas être utilisée, c'est une fonction utilisée par move !
    @SuppressWarnings("resource") // chez moi ca ne voit pas que flux fermés dans le finally -> évite warning
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
    	  ret = (count == size); // vrai ssi fichier copié et copié ENTIEREMENT
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
	 * Déplace le fichier src vers dest. Le déplacement est effectué à l'aide d'un renommage, 
	 * ou d'une copie si le renommage échoue
	 * @param src Chemin du fichier à déplacer
	 * @param dest Chemin de destination du fichier
	 * @return Vrai si le déplacement s'est effectuée sans problème, faux sinon
	 */
    private boolean move(File src,File dest)
    {
        if (!dest.exists()) // pas de fichier existant avec le même nom
        {
	        boolean res = src.renameTo(dest); // déplacement par renommage plus rapide
	        if(!res) // si renommage échoue
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
}
