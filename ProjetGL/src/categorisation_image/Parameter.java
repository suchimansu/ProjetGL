package categorisation_image;

import java.io.*;

/**
 * Classe permettant de stocker les paramètres généraux du programme.
 * Elle stocke en particulier la granularité du tri, ainsi que le dernier chemin utilisé comme destination
 * de tri
 */
public class Parameter {
	
	private int sortParameter = 3600 * 24 ;
	private String destDir;
	private String configDir;
	/**
	 * Construit une nouvelle instance à partir du fichier de configuration donné.
	 * @param path Chemin vers le fichier de sauvegarde de la configuration
	 */
	public Parameter(String path) 
	{
		configDir = path;
		getOldParam();
	}

	/**
	 * TODO je ne vois pas à quoi sert la fonction, sachant que destDir est censé etre le chemin
	 * du dernier dossier destination de tri utilisé
	 */
	private void getOldParam()
	{
		File f = new File ( configDir );
		
		if ( f.exists() )
		{
			if ( !f.isDirectory() )
			{
				try 
				{	
					FileReader doBack = new FileReader( configDir );
					char[] temp = new char[ 50 ];
					doBack.read( temp );
					boolean b = false;
					
					for ( char c : temp )
					{
						if ( b )
						{
							sortParameter = c ; 
						}
						else
						{
							if ( c == '\n' )
								b = true;
							else
								destDir += c;
						}
					}
					doBack.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Retourne la granularité du tri.
	 * @return la granularité
	 */
	public int getSortParameter()
	{
		return sortParameter;
	}

	/**
	 * Met à jour la granularité du tri.
	 * @param param Nouvelle granularité
	 * @return void
	 */
	public void setSortParameter(int param)
	{
		sortParameter = param;
	}

	/**
	 * Sauvegarde les paramètres dans le fichier de configuration situé dans path.
	 * @param path Chemin vers le fichier de configuration
	 * @return void
	 */
	public void save()
	{	
		try 
		{
			FileWriter doSave = new FileWriter( configDir );
			doSave.write( destDir );
			doSave.write( sortParameter );
			doSave.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	/**
	 * Retourne le dernier dossier utilisé comme destination de tri.
	 * @return le dernier dossier de destination de tri
	 */
    public String getDestDir()
    {
        return destDir;
    }
}
