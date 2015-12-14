package categorisation_image;

import java.io.*;

/**
 * Classe permettant de stocker les paramètres généraux du programme.
 * Elle stocke en particulier la granularité du tri, ainsi que le dernier chemin utilisé comme destination
 * de tri
 */
public class Parameter {
	
	private double sortParameter  ;
	private String destDir;
	private String configDir;
	private String defaultPath ;
	
	/**
	 * Construit une nouvelle instance à partir du fichier de configuration donné.
	 * @param path Chemin vers le fichier de sauvegarde de la configuration
	 */
	public Parameter(String path) 
	{
		destDir = "";
		sortParameter = ListParameter.jour.getTime();
		defaultPath = System.getProperty("user.home" )+File.separator+"Categorizer"+File.separator;
		configDir = path;
		getOldParam();
	}

	/**
	 * Permet de récupérer les anciens paramètres à partir d'un fichier de configuration.
	 * Le fichier de configuration par défaut est initialisé à partir du main ( ../saveRessources.conf ) 
	 * @return void
	 */
	private void getOldParam()
	{
		File f = new File ( configDir );
		
		if ( f.exists() )
		{
			if ( !f.isDirectory() )
			{
				String str = "";
				boolean b = false;
				boolean t = false;
				
				try 
				{	
					FileReader doBack = new FileReader( configDir );
					char[] temp = new char[ 50 ];
					doBack.read( temp );
					
					for ( char c : temp )
					{
						if ( b )
						{
							str += c ;
							t = true;
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
				
				if ( t )
				{
					sortParameter = Double.parseDouble( str.trim() ) ; 
				}
			}
			
			
		}
	}

	public void setDestDir( String destDir )
	{
		this.destDir = destDir;
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
			if ( destDir.equals("") )
				doSave.write( defaultPath + "\n");
			else
				doSave.write( destDir + "\n");
			
			String str =  ""+sortParameter;
			doSave.write( str );
			doSave.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour la granularité du tri.
	 * @param param Nouvelle granularité
	 * @return void
	 */
	public void setSortParameter(double param)
	{
		sortParameter = param;
	}

	/**
	 * Retourne la granularité du tri.
	 * @return la granularité
	 */
	public double getSortParameter()
	{
		return sortParameter;
	}
	
	/**
	 * Retourne le path du dernier dossier utilisé comme destination de tri.
	 * @return le path du dernier dossier de destination de tri
	 */
    public String getDestDir()
    {
    	if ( destDir.equals("") )
    		return defaultPath;
    	
        return destDir;
    }
}
