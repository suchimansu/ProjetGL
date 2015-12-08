package categorisation_image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
//
public abstract class Image {

	private String path;
	// TODO dateCreation doit etre une date
	private String dateCreation;
	private String filename;
	
	public Image(String path) {
          try{
		this.path = path;
                
                //Obtention du filename
                File file = new File(path);
                this.filename = file.getName(); //file.getParent() pour avoir le directory name (sans le fichier)
                
                //Obtention de la date de création. getAttribute de creationTime retourne un FileTime
                Path chemin = FileSystems.getDefault().getPath(path);
                // TODO ici creer un date avec gregorian calendar à patir du string
                String date = (Files.getAttribute(chemin, "creationTime" )).toString();
                this.dateCreation = date.substring(0, 10); //on prend que la date de l'horodatage
                // Files.getAttribute(chemin, "size","") pour avoir la taille du fichier
                   
          }
          catch (IOException e){
            System.out.println("IOException: " + e);
          }
                
	}
	
	public Long getTime(){
		// TODO ici modifier car dateCreation est sensé etre une date
		return Long.parseLong(dateCreation);
	}
	
	public String getFileName(){
		return filename;
	}

	public String getPath(){
		return path;
	}
	
	abstract protected String extactTime(String path);
}
