package br.com.banconeon;

import java.util.ArrayList;
import java.util.List;

import br.com.banconeon.model.User;

public class MockUsers {

    private static MockUsers mInstance = null;
    private List<User> mUser = new ArrayList<>();

    private String[] mockNames = {"Reshmi Franco", "Vespasien Finley", "Felizitas Zeru", "Nikolai Selene", "Ingram Hamid", "Ljuba Luisa",
            "Cormac Marina", "Johano Hovsep", "Patrik Sólveig", "Benigna Radomira", "Trista Vasu", "Gunnar Garden", "Keren Happukh Adolf",
            "Domitian Ariane", "Martina Sati", "Mário José"};

    private String[] mockTelefones = {"(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234",
            "(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234",
            "(11)99876-1234", "(11)99876-1234", "(11)99876-1234", "(11)99876-1234","(11)99876-1234"};

    private String[] mockFotos = {"http://www.gorio.com.br/neon/image1.png", "http://www.gorio.com.br/neon/image2.png",
            "http://www.gorio.com.br/neon/image3.png", "http://www.gorio.com.br/neon/image13.png", "http://www.gorio.com.br/neon/image12.png",
            "http://www.gorio.com.br/neon/image4.png", null, "http://www.gorio.com.br/neon/image10.png",
            "http://www.gorio.com.br/neon/image5.png", "http://www.gorio.com.br/neon/image9.png", "http://www.gorio.com.br/neon/image11.png",
            "http://www.gorio.com.br/neon/image6.png", null, "http://www.gorio.com.br/neon/image8.png",
            "http://www.gorio.com.br/neon/image7.png", "http://www.gorio.com.br/neon/image15.png"};

    private MockUsers() {
    }

    public static MockUsers getInstance() {
        if (mInstance == null) {
            mInstance = new MockUsers();
        }
        return mInstance;
    }

    public List<User> getUsers() {

        User user;

        for (int i = 0; i < mockNames.length-1; i++) {
            user = new User(String.valueOf(i), String.valueOf(i), mockNames[i], mockTelefones[i], mockFotos[i]);
            mUser.add(user);
        }

        user = new User(String.valueOf(199), String.valueOf(199), mockNames[15], mockTelefones[15], mockFotos[15]);
        mUser.add(user);

        return this.mUser;
    }

}
