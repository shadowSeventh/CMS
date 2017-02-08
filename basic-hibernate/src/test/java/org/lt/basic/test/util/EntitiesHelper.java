package org.lt.basic.test.util;

import junit.framework.Assert;
import org.lt.basic.model.User;

public class EntitiesHelper {
	private static User baseUser = new User(1,"admin1");
	
	public static void assertUser(User expected,User actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
	}
	
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
}
