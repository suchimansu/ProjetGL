package test_categorisation_image;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import categorisation_image.Image;

public class ImageTest
{
	@Test
	public void testGetTimeLong()
	{
		Image img = new Image("ressource/TestFiles/Photo-0010.jpg");
		assertTrue(img.getTimeLong().equals(1314200828000L));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetTimeDate()
	{
		Image img = new Image("ressource/TestFiles/Photo-0010.jpg");
		assertTrue(img.getTimeDate().equals(new Date(111,7,24,17,47,8)));
	}

	@Test
	public void testGetFileName()
	{
		Image img = new Image("ressource/TestFiles/Photo-0010.jpg");
		assertTrue(img.getFileName().equals("Photo-0010.jpg"));
	}

	@Test
	public void testGetPath()
	{
		Image img = new Image("ressource/TestFiles/Photo-0010.jpg");
		assertTrue(img.getPath().equals("ressource/TestFiles/Photo-0010.jpg"));
	}
}