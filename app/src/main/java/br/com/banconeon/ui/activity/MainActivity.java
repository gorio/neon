package br.com.banconeon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.banconeon.API;
import br.com.banconeon.R;
import br.com.banconeon.utils.LocalStorage;
import br.com.banconeon.utils.Util;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.constraintLayout)
    ConstraintLayout mMainLayout;

    @ViewById(R.id.txtNome)
    TextView txtNome;

    @ViewById(R.id.txtEmail)
    TextView txtEmail;

    @AfterViews
    void init() {
        try {
            if (LocalStorage.getInstance(this).getString("_token") == null || LocalStorage.getInstance(this).getString("_token").isEmpty())
                getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://processoseletivoneon.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API neonAPI = retrofit.create(API.class);
        Call<ResponseBody> getToken = neonAPI.generateToken("Eduardo Gorio", "eduardo@gorio.com.br");
//        Call<ResponseBody> getToken = neonAPI.generateToken(txtNome.getText().toString(), txtEmail.getText().toString());
        getToken.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    try {
                        LocalStorage.getInstance(MainActivity.this).put("_token", response.body().string().replace("\"", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Util.notifyUser(mMainLayout, t.getMessage(), Color.RED);
            }
        });
    }

    @Click(R.id.btnEnviar)
    void buttomEnviarClicked() {
        startActivity(new Intent(this, UserListActivity_.class));
    }

    @Click(R.id.btnHistorico)
    void buttomHistoricoClicked() {
        startActivity(new Intent(this, HistoryActivity_.class));
    }
}