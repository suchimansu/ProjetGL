package categorisation_image;

import java.util.TreeMap;

public class Scan {

	public Scan() {
		
	}
	
	public TreeMap<Integer, Image> doScan(String path){
		return null;		
	}
	
	private String getExtension(String path){
		String[] r = path.split(".");
		return r[r.length-1];
	}

}
