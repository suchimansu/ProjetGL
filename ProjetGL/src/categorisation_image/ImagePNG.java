package categorisation_image;

/**
 * Classe de stockage des informations d'un fichier image au format PNG.
 */
public class ImagePNG extends Image {

	/**
	 * Construit une nouvelle instance à partir d'un fichier PNG.
	 */
	public ImagePNG(String path) {
		super(path);
	}
	/**
	 * Récupère le temps de création de la photo à partir des métadonnées du format PNG.
	 * @return Le temps de création de la photo
	 */
/*	@Override
	protected String extactTime(String path) {
		return "";
	}
*/
}
