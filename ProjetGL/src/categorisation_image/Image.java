package categorisation_image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

/**
 * Classe permettant de stocker les informations utiles d'un fichier image.
 * Elle permet entre autre de stocker le nom du fichier, son chemin et sa date (nécessaire au tri)
 */
public class Image {

	private String path;
	private Date dateCreation;
	private String filename;
	
        
	/**
	 * Constructeur permettant de récupérer le nom d'une photo et la
	 * date de création de celle-ci à partir du path.
	 * 
	 * @param path Chemin vers le fichier image
	 */
	public Image(String path) {
          try{
		this.path = path;
                
                //Obtention du filename
                File file = new File(path);
                this.filename = file.getName(); //file.getParent() pour avoir le directory name (sans le fichier)
                
                //Obtention de la date de cr�ation. getAttribute de creationTime retourne un FileTime
                // on le stocke dans un string et on extrait les champs pour les mettre dans type Date
                Path chemin = FileSystems.getDefault().getPath(path);
                String date1 = (Files.getAttribute(chemin, "creationTime" )).toString();

                int annee = Integer.parseInt(date1.substring(0, 4));
                int mois = Integer.parseInt(date1.substring(5,7));
                int jour = Integer.parseInt(date1.substring(8,10));
                int h = Integer.parseInt(date1.substring(11,13));
                int min = Integer.parseInt(date1.substring(14,16));
                int sec = Integer.parseInt(date1.substring(17,19));
                this.dateCreation = new Date(annee, mois, jour, h, min, sec);
                   
          }
          catch (IOException e){
            System.out.println("IOException: " + e);
          }
                
	}

        
	/**
	 * Retourne le nombre de secondes �coul�es entre la date de 
     * cr�ation d'une image et 01/01/1970.
     * 
	 * @return Nombre de secondes en Long
	 */
	public Long getTimeLong(){
            return this.dateCreation.getTime()/1000;
	}

        
    /**
	 * Retourne la date de cr�ation d'une image.
     * 
     * @return Date de cr�ation d'une image
	 */
    public Date getTimeDate(){
		return this.dateCreation;
	}
	
    /**
	 * Retourne le nom de l'image avec son extension.
     * 
     * @return String le nom de l'image et son extension
	 */
	public String getFileName(){
		return this.filename;
	}

    /**
	 * Retourne le path d'une image.
     * 
     * @return String le chemin d'une image
	 */
	public String getPath(){
		return this.path;
	}
}
