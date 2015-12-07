package categorisation_image;

import java.util.Date;

public abstract class Image {

	private String path;
	private int dateCreation;
	private String filename;
	
	public Image(String path) {
		
	}
	
	public int getTime(){
		return dateCreation;
	}
	
	public String getFileName(){
		return filename;
	}

	public String getPath(){
		return path;
	}
	
	abstract protected int extactTime(String path);
}
