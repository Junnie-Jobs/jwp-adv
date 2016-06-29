package core.test;

import org.junit.Test;

public class RandomIdTest {
	@Test
	public void generate() throws Exception {
		RandomString random = new RandomString(10);
		System.out.println(random.nextString());
	}
}
