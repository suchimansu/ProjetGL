package categorisation_image;

import java.io.File;
import java.io.FileFilter;
import java.util.TreeMap;

public class Scan
{
	static String[] ext = {".png", ".jpg", ".jpeg"};
	/**
	 * \class ExtFilter
	 * \brief Classe implémentant un filtrage de fichier image
	 */
	private class ExtFilter implements FileFilter
	{
		/**
		 * \fn ExtFilter(String[] ext)
		 * \brief Construit un filtre de fichier à partir des extensions fournies
		 *
		 * \param ext Tableau des extensions autorisés
		 */
		public ExtFilter() { }
		
		/**
		 * \fn boolean accept(File pathname)
		 * \brief Retourne true si le fichier est un répertoire ou possède une
		 * extension autorisée
		 *
		 * \param pathname Chemin et nom du fichier
		 * \return True si pathname est un répertoire, ou un fichier d'extension autorisée
		 * false sinon
		 */
		@Override
		public boolean accept(File pathname)
		{
			if (pathname.isDirectory())
			{
				return true;
			}
			else
			{
				String path = pathname.getAbsolutePath().toLowerCase();
				for (int i=0; i<Scan.ext.length; i++)
				{
					if (path.endsWith(Scan.ext[i]))
					{
						return true;
					}
				}
				return false;
			}
		}
	}
	
	private ExtFilter filter;

	public Scan()
	{
		this.filter = new ExtFilter();
	}

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
					if (p.endsWith(Scan.ext[0]))
					{
						img = new ImagePNG(contenu[i].getAbsolutePath());
					}
					else
					{
						img = new ImageJPG(contenu[i].getAbsolutePath());
					}
					map.put(img.getTime(), img);
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