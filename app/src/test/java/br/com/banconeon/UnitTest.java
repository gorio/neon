package br.com.banconeon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import br.com.banconeon.ui.activity.UserListActivity_;
import br.com.banconeon.utils.Util;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UnitTest {

    private UserListActivity_ userListActivity;

    @Before
    public void setup() {
        ActivityController<UserListActivity_> activityController = Robolectric.buildActivity(UserListActivity_.class);
        userListActivity = activityController.get();
    }

    @Test
    public void testOnlineMethod() {
        assertEquals(true, Util.isOnline(userListActivity.getApplicationContext()));
    }
}
