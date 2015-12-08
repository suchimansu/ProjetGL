package test_categorisation_image;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ImageJPGTest.class, ImagePNGTest.class, IntervalTest.class,
		ParameterTest.class, ScanTest.class })
public class AllTests {

}
