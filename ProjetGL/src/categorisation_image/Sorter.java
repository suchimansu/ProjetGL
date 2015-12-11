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

	/**
	 * Construit un nouveau trieur de photos utilisant la granularit� p et aucune cat�gorie pr�d�finie.
	 * @param p Granularit� du tri
	 */
    public Sorter(Parameter p)
    {
        tempEventCalendar = new Calendar();
        param = p;
    }

	/**
	 * Construit un nouveau trieur de photos utilisant la granularit� p et les cat�gories de userCal.
	 * @param p Granularit� du tri
	 * @param userCal Calendrier des cat�gories d�finies par l'utilisateur
	 */
    public Sorter(Calendar userCal, Parameter p)
    {
        this(p);
        this.tempEventCalendar = userCal;
    }

	/**
	 * Effectue le tri des photos se trouvant dans l'arborescence du dossier pathIn.
	 * @param pathIn Chemin du dossier contenant les photos � trier
	 * @return void
	 */
    public void doTri(String pathIn) throws Exception
    {
        TreeMap<Long, Image> images;
        Scan s = new Scan();
        EventGlobal globalEvent = tempEventCalendar.getGlobalEvent();
        // listeEvent = tempEventCalendar.getListEvent();
        images = s.doScan(new File(pathIn));

        userEventSort(globalEvent, images);
    }

	/**
	 * Tri les photos en fonction des cat�gories d�finies par l'utilisateur.
         * Dans le premier cas on est dans l'Events GlobalEvent
	 * @param globalEvent racine de l'arborescence des cat�gories
	 * @param mapImage Ensemble des fichiers images � trier
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
                            dest += "/" + children.getNom();
                            //l'image est dans une categorie de l'utilisateur
                            userEventSortBis(children, mapImage, key, dest);
                        }
                    }
                } 
            }
        } 
    }
    /**
	 * Tri les photos en fonction des cat�gories d�finies par l'utilisateur.
         * Dans les cas suivants on est dans les Events Event
	 * @param event racine de l'arborescence des cat�gories
	 * @param mapImage Ensemble des fichiers images � trier
	 * @param dest Chemin du dossier o� doivent se trouver les photos
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
                    userEventSortBis(children, mapImage, key, dest + "/" + children.getNom());
                    b = !b; //on a trouv� une sous categorie pour l'image
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
            move(srcImage, fileDest);
            
            mapImage.remove(key);//on enleve l'image du TreeMap
        }
    }
    
    /**
	 * Tri les photos ne correspondant � aucune cat�gorie d�finie par l'utilisateur.
	 * @param l List des images � trier
	 * @return void
	 */
    //pour le premier jet je tri par ann�e
    @SuppressWarnings("deprecation")
	private void unsortedSort(TreeMap<Long, Image> mapImage) throws Exception 
    {
    	long entreDeux = 0;
    	long key = 0;
    	while (!mapImage.isEmpty())
    	{
    		entreDeux = mapImage.get(key).getTimeLong()
    				- mapImage.get(key+1).getTimeLong();
    	}
        
    }

    
    /**
	 * Copy le contenu du fichier src dans dest.
	 * @param src Chemin du fichier � copier
	 * @param dest Chemin de destination de la copie
	 * @return Vrai si la copie s'est effectu�e sans probl�me, faux sinon
	 */
    // A priori copy ne doit pas �tre utilis�e, c'est une fonction utilis�e par move !
    @SuppressWarnings("resource") // chez moi ca ne voit pas que flux ferm�s dans le finally -> �vite warning
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
    	  ret = (count == size); // vrai ssi fichier copi� et copi� ENTIEREMENT
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
	 * D�place le fichier src vers dest. Le d�placement est effectu� � l'aide d'un renommage, 
	 * ou d'une copie si le renommage �choue
	 * @param src Chemin du fichier � d�placer
	 * @param dest Chemin de destination du fichier
	 * @return Vrai si le d�placement s'est effectu�e sans probl�me, faux sinon
	 */
    private boolean move(File src,File dest)
    {
        if (!dest.exists()) // pas de fichier existant avec le m�me nom
        {
	        boolean res = src.renameTo(dest); // d�placement par renommage plus rapide
	        if(!res) // si renommage �choue
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
