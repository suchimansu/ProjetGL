package categorisation_image;

import java.io.File;
import java.io.FileFilter;

public class ExtFilter implements FileFilter
{
	private String[] exts;

	/**
	 * \fn ExtFilter(String[] exts)
	 * \brief Construit un filtre de fichier à partir des extensions fournies
	 *
	 * \param ext Tableau des extensions autorisés
	 */
	public ExtFilter(String[] exts)
	{
		this.exts = exts;
	}
	
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
			for (int i=0; i<this.exts.length; i++)
			{
				if (path.endsWith(this.exts[i]))
				{
					return true;
				}
			}
			return false;
		}
	}
}