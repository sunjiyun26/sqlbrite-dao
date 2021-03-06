package com.hannesdorfmann.sqlbrite.dao;

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) @Config(manifest = Config.NONE) public class DaoTest {

  private DaoManager manager;
  private UserDao userDao;

  @Before public void init() {
    Context c = RuntimeEnvironment.application;
    userDao = new UserDao();
    manager = DaoManager.with(c).databaseName("test.db").version(1).add(userDao).build();
  }

  @Test public void testDao() throws Exception {
    for (int i = 0; i < 10; i++) {
      String name = "Name" + i;
      int age = i;
      double weight = i + 10 / 3;
      byte[] blob = Integer.toString(i).getBytes("UTF-8");

      User u = userDao.insert(name, age, weight, blob);
      assertEquals(name, u.name);
      assertEquals(age, u.age);
      assertEquals(weight, u.weight, 0.1);
      assertEquals(blob, u.blob);

      User qUser = userDao.getById(u.id);

      assertEquals(qUser.age, u.age);
      assertArrayEquals(qUser.blob, u.blob);
      assertEquals(qUser.id, u.id);
      assertEquals(qUser.name, u.name);
      assertEquals(qUser.weight, u.weight, 0.1);
    }
  }
}
