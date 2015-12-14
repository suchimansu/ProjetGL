package test_categorisation_image.scan;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import categorisation_image.scan.ExtFilter;

public class ExtFilterTest
{
	@Test
	public void testAccept()
	{
		String[] exts = new String[] {".png", ".jpg", ".jpeg", ".bmp"};
		ExtFilter filter = new ExtFilter(exts);
		assertTrue(filter.accept(new File("ressource/TestFiles/")));
		assertTrue(filter.accept(new File("ressource/TestFiles/07082011419.jpg")));
		assertFalse(filter.accept(new File("ressource/TestFiles/truc.txt")));
	}
}
