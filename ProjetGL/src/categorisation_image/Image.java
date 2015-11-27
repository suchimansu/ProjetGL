package categorisation_image;

import java.util.Date;

public class Image {

	private String path;
	private Date dateCreation;
	private String filename;
	
	public Image(String path) {
		
	}
	
	public Date getDateCreation(){
		return dateCreation;
	}
	
	public String getFileName(){
		return filename;
	}

	public String getPath(){
		return path;
	}
}
