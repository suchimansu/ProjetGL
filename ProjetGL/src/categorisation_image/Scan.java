package categorisation_image;

import java.io.File;
import java.util.TreeMap;

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

	/**
	 * Construit un nouveau scanneur de fichier image. LEs fichiers filtrés sont les png, jpg et jpeg
	 */
	public Scan()
	{
		this.exts = new String[] {".png", ".jpg", ".jpeg"};
		this.filter = new ExtFilter(this.exts);
	}

	/**
	 * Lance un scan récursif sur un répertoire afin d'en extraire un arbre trié temporellement d'Image.
	 * @param path Dossier à explorer
	 * @return TreeMap d'Image trié selon la date de la photo
	 */
	public TreeMap<Long, Image> doScan(File path)
	{
		TreeMap<Long, Image> map = new TreeMap<Long, Image>();
		File[] contenu = path.listFiles(this.filter);
		if (contenu != null)
		{
			for (int i=0; i<contenu.length; i++)
			{
				if (!contenu[i].isDirectory()) // pas repertoire
				{
					String p = contenu[i].getAbsolutePath().toLowerCase();
					Image img;
					if (p.endsWith(this.exts[0]))
					{
						img = new ImagePNG(contenu[i].getAbsolutePath());
					}
					else
					{
						img = new ImageJPG(contenu[i].getAbsolutePath());
					}
					map.put(img.getTimeLong(), img);
				}
				else // recherche recursive dans le repertoire
				{
					map.putAll(doScan(contenu[i]));
				}
			}
		}
		return map;
	}
}
