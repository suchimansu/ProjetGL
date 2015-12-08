package categorisation_image;

import java.io.*;

public class Parameter {
	
	private int sortParameter = 3600 * 24 ;
	private String destDir;
	
	public Parameter(String path) 
	{
		destDir = path;
		getOldParam();
	}
	
	public void getOldParam()
	{
		File f = new File ( destDir );
		
		if ( f.exists() )
		{
			if ( !f.isDirectory() )
			{
				try 
				{	
					FileReader doBack = new FileReader( destDir );
					sortParameter = doBack.read();
					doBack.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

	public int getSortParameter()
	{
		return sortParameter;
	}
	
	public void setSortParameter(int param)
	{
		sortParameter = param;
	}
	
	public void save(String path)
	{	
		try 
		{
			FileWriter doSave = new FileWriter( path );
			doSave.write( sortParameter );
			doSave.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
        
    public String getDestDir()
    {
        return destDir;
    }
}
