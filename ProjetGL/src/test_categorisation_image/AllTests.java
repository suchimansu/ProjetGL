package test_categorisation_image;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test_categorisation_image.calendar.IntervalTest;
import test_categorisation_image.scan.ExtFilterTest;
import test_categorisation_image.scan.ScanTest;

@RunWith(Suite.class)
@SuiteClasses({ ExtFilterTest.class, IntervalTest.class, ImageTest.class, 
		ParameterTest.class, ScanTest.class })
public class AllTests {

}
