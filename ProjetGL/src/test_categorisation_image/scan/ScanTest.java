package test_categorisation_image.scan;

import java.io.File;
import java.util.TreeMap;

import org.junit.Test;
import static org.junit.Assert.*;

import categorisation_image.scan.Scan;
import categorisation_image.Image;

public class ScanTest
{
	@Test
	public void testDoScan()
	{
		Scan s = new Scan();
		s.doScan(new File("ressource/TestFiles"));
		TreeMap<Long,Image> map = s.getMap();
		assertEquals(10, map.size());
	}
}
