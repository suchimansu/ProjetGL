package categorisation_image.scan;

import java.io.File;
import java.util.TreeMap;

import categorisation_image.Image;

/**
 * Classe permettant de pouvoir lister les fichiers images d'une arborescence de fichier.
 * Les fichiers sont filtrés par extension
 * @see ExtFilter
 * @see Image
 */
public class Scan
{	
	private ExtFilter filter;
	private String[] exts;
	private TreeMap<Long, Image> map;

	/**
	 * Construit un nouveau scanneur de fichier image. Les fichiers filtrés sont les png, jpg et jpeg
	 */
	public Scan()
	{
		this.exts = new String[] {".png", ".jpg", ".jpe", ".jif", ".jfif", ".jpeg", ".tif", ".tiff"};
		this.filter = new ExtFilter(this.exts);
		this.map = new TreeMap<Long, Image>();
	}

	/**
	 * Retourne l'ensemble des images scannees.
	 * @return TreeMap d'Image trié selon la date de la photo
	 */
	public TreeMap<Long, Image> getMap()
	{
		return this.map;
	}

	/**
	 * Lance un scan récursif sur un répertoire afin d'en extraire un arbre trié temporellement d'Image.
	 * @param path Dossier à explorer
	 */
	public void doScan(File path)
	{
		File[] contenu = path.listFiles(this.filter);
		if (contenu != null)
		{
			for (int i=0; i<contenu.length; i++)
			{
				if (!contenu[i].isDirectory()) // pas repertoire
				{
					Image img = new Image(contenu[i].getAbsolutePath());
					Long time = img.getTimeLong();
					// In case some image have same modification time value
					// Just moving a few millis to have all file
					while (this.map.containsKey(time))
					{
						time++;
					}
					this.map.put(time, img);
				}
				else // recherche recursive dans le repertoire
				{
					doScan(contenu[i]);
				}
			}
		}
	}
}
