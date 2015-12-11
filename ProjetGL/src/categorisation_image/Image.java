package categorisation_image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

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
		try
		{
			// Affectation du path
    	  	this.path = path;
    	  	// Création du fichier
            File file = new File( path );
            // Récupération du nom de fichier
            this.filename = file.getName();
          
            // Metadata null..
            Metadata metadata = null;
			try 
			{
				// On récupére les metadata
				metadata = ImageMetadataReader.readMetadata( file );
				
			} 
			catch (ImageProcessingException e) 
			{
				System.out.println("error..");
				e.printStackTrace();
			}
			
			// On itère sur les metadata récupérer
			for ( Directory direct : metadata.getDirectories() )
			{
				// On itère sur chaque valeur récupérer
				for ( Tag tag : direct.getTags() )
				{
					// Si le champ contiens "Modified" c'est qu'on est sur le champ dernière modif. 
					// du fichier
					if ( tag.toString().contains("Modified") )
					{
						// On affecte notre date de création
						this.dateCreation = direct.getDate( tag.getTagType() );
					}
				}
			}
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
