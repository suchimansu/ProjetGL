package categorisation_image;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class Sorter
{

    private Calendar tempEventCalendar;
    private Parameter param;

    public Sorter(Parameter p)
    {
        tempEventCalendar = new Calendar();
        param = p;
    }

    public Sorter(Calendar userCal, Parameter p)
    {
        this(p);
        this.tempEventCalendar = userCal;
    }

    public void doTri(String pathIn) throws Exception
    {
        List<Event> listeEvent;
        TreeMap<Long, Image> images;
        Scan s = new Scan();
        Event globalEvent = tempEventCalendar.getGlobalEvent();
        // listeEvent = tempEventCalendar.getListEvent();
        images = s.doScan(new File(pathIn));

        userEventSort(globalEvent, images, pathIn);
    }

    private void userEventSort(Event globalEvent, TreeMap<Long, Image> mapImage, String pathIn) throws Exception
    {
        Date dateImage;
        if (globalEvent.hasChild()) 
        {
            for (Long key : mapImage.keySet())
            {
                dateImage = mapImage.get(key).getTimeDate();
                if (globalEvent.isInclude(dateImage))
                {
                    for (Event childs : globalEvent.getChild())
                    {
                        if (childs.isInclude(dateImage))
                        {
                            pathIn += childs.getNom();
                            //l'image est dans une categorie de l'utilisateur
                            userEventSortBis(globalEvent, mapImage, key, pathIn);
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

    private void userEventSortBis(Event event, TreeMap<Long, Image> mapImage, Long key, String pathIn)
    {
        boolean b = true;
        Date dateImage = mapImage.get(key).getTimeDate();
        if (event.hasChild())
        {
            for (Event childs : event.getChild())
            {
                if (childs.isInclude(dateImage))
                {
                    pathIn += "\\" + childs.getNom(); //on agrandi l'arborescence du chemin
                    //l'image est dans une categorie de l'utilisateur
                    userEventSortBis(childs, mapImage, key, pathIn);
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
            //deplacerImage(pathImage, pathDest)
            move(srcImage, dest);
            
            mapImage.remove(key);//on enleve l'image du TreeMap
        }
        else
        {
            //normalement on ne rentre pas ici
        }
    }

    private void unsortedSort(List l) throws Exception 
    {
        int affinage = param.getSortParameter();
    }

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
