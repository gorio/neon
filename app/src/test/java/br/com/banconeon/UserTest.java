package br.com.banconeon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.banconeon.model.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    private User mUser = new User("1", "1", "Eduardo Gorio", "11111", "http://");

    @Test
    public void testGetId() {
        assertEquals("1", mUser.getId());
    }
}
