package categorisation_image;

public class Parametre {
	
	private int sortParameter;
	private String destDir;

	public Parametre() {

	}
	
	public Parametre(String path) {
		destDir = path;
	}

	public int getSortParameter(){
		return sortParameter;
	}
	
	public void setSortParameter(int param){
		sortParameter = param;
	}
	
	public void save(String path){
		
	}
        
        public String getDestDir()
        {
            return destDir;
        }
}
