package categorisation_image;

public class ImagePNG extends Image {

	public ImagePNG(String path) {
		super(path);
	}

	@Override
	protected int extactTime(String path) {
		return 0;
	}

}
