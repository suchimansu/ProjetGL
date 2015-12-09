package categorisation_image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class Image {

	private String path;
	private Date dateCreation;
	private String filename;
	
        
        /**
	 * \fn Image(String path)
	 * \brief Constructeur permettant de récupérer le nom d'une photo et la
         * date de création de celle-ci à partir du path
         * 
	 * \param path String
	 */
	public Image(String path) {
          try{
		this.path = path;
                
                //Obtention du filename
                File file = new File(path);
                this.filename = file.getName(); //file.getParent() pour avoir le directory name (sans le fichier)
                
                //Obtention de la date de création. getAttribute de creationTime retourne un FileTime
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
	 * \fn getTimeLong()
	 * \brief Retourne le nombre de secondes écoulées entre la date de 
         * création d'une image et 01/01/1970
         * 
	 * \return Long
	 */
	public Long getTimeLong(){
            return this.dateCreation.getTime()/1000;
	}

        
        /**
	 * \fn getTimeDate()
	 * \brief Retourne la date de création d'une image
         * 
         * \return Date
	 */
        public Date getTimeDate(){
		return this.dateCreation;
	}
	
        /**
	 * \fn getFileName()
	 * \brief Retourne le nom de l'image avec son extension
         * 
         * \return String
	 */
	public String getFileName(){
		return this.filename;
	}

        /**
	 * \fn getPath()
	 * \brief Retourne le path d'une image
         * 
         * \return String
	 */
	public String getPath(){
		return this.path;
	}
}
