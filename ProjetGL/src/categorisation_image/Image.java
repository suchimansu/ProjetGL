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

	
	public Long getTimeLong(){
            return this.dateCreation.getTime();
	}

        public Date getTimeDate(){
		return this.dateCreation;
	}
	
	public String getFileName(){
		return this.filename;
	}

	public String getPath(){
		return this.path;
	}
}
