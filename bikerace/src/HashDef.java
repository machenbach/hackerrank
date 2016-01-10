import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class HashDef {

	@Test
	public void test() {
		Map<String, Integer> m = new HashDefault<>(Integer.MAX_VALUE);
		String s = "S";
		assertTrue(m.get(s).equals(Integer.MAX_VALUE));
		m.put(s, 1);
		assertTrue(m.get(s) == 1);
	}

}
