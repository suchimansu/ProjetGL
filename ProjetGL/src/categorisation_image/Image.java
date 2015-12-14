package categorisation_image;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Classe permettant de stocker les informations utiles d'un fichier image.
 * Elle permet entre autres de stocker le nom du fichier, son chemin et sa date (necessaire au tri)
 */
public class Image {

	private String path;
	private Date dateCreation;
	private String filename;
	
        
	/**
	 * Constructeur permettant de recuperer le nom d'une photo et la
	 * date de creation de celle-ci a partir du path.
	 * 
	 * @param path Chemin vers le fichier image
	 */
	public Image(String path) {
          try{
              // Affectation du path
            this.path = path;
            
              // Recuperation du nom de fichier 
            File file = new File( path );
            this.filename = file.getName();
            
              // Recuperation de la date a partir des metadonnees
            Metadata metadata = null;
              try{
                  // On recupere les metadatas
                metadata = ImageMetadataReader.readMetadata( file );	
              } 
              catch (ImageProcessingException e){
                System.out.println("Levee de l'exception ImageProcessingException");
		e.printStackTrace();
              }
			
              // On itere sur les metadatas recuperees
              for ( Directory direct : metadata.getDirectories() ){
            	 // On itere sur chaque valeur recuperee
                for ( Tag tag : direct.getTags() ){
                  // Si le champ contient "Modified" c'est qu'on est sur le champ dernierre modif. 
                  
                	if ( tag.toString().contains("Date/Time Original") ){
                		// On affecte notre date de creation
                       this.dateCreation = direct.getDate( tag.getTagType() );
                       break;
                    }
                }
              }
              
              if ( dateCreation == null )
              {
            	  for ( Directory direct : metadata.getDirectories() ){
                 	 // On itere sur chaque valeur recuperee
                     for ( Tag tag : direct.getTags() ){
                       // Si le champ contient "Modified" c'est qu'on est sur le champ dernierre modif. 
                       
                     	if ( tag.toString().contains("Modified") ){
                     		// On affecte notre date de creation
                            this.dateCreation = direct.getDate( tag.getTagType() );
                            break;
                         }
                     }
                   }
              }
          }
          catch (IOException e){
            System.out.println("IOException: " + e);
          }
                
	}

        
	/**
	 * Retourne le nombre de secondes ecoulees entre la date de 
         * creation d'une image et 01/01/1970.
         * 
	 * @return Nombre de secondes en Long
	 */
	public Long getTimeLong(){
            return this.dateCreation.getTime();
	}

        
        /**
	* Retourne la date de creation d'une image.
        * 
        * @return Date de creation d'une image
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
