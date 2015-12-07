package categorisation_image;

public class ImageJPG extends Image {

	public ImageJPG(String path) {
		super(path);
	}

	@Override
	protected int extactTime(String path) {
		return 0;
	}

}
