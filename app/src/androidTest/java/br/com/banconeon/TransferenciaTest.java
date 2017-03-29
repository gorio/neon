package br.com.banconeon;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.banconeon.ui.activity.UserListActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TransferenciaTest {

    @Rule
    public ActivityTestRule<UserListActivity_> mActivityRule = new ActivityTestRule<>(
            UserListActivity_.class);


    @Test
    public void executarTesteLoginContaErrada() {

        //Clica na posicão 3 da lista
        onView(withId(R.id.userlist)).perform(actionOnItemAtPosition(3, click()));

        //Adiciona R$ 10,00 no campo de valor
        onView(withId(R.id.edtValor)).perform(typeText("1000"), closeSoftKeyboard());

        //Realiza o Swype para realizar a transferência
        onView(withId(R.id.img_thumb)).perform(swipeRight());
    }
}
