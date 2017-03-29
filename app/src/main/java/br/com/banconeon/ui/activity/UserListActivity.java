package br.com.banconeon.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.banconeon.API;
import br.com.banconeon.MockUsers;
import br.com.banconeon.R;
import br.com.banconeon.adapter.UserAdapter;
import br.com.banconeon.model.User;
import br.com.banconeon.ui.custom.CircleImageView;
import br.com.banconeon.ui.custom.RecyclerItemClickListener;
import br.com.banconeon.ui.custom.SimpleDividerItemDecoration;
import br.com.banconeon.ui.custom.UnlockMoney;
import br.com.banconeon.utils.LocalStorage;
import br.com.banconeon.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EActivity(R.layout.activity_userlist)
public class UserListActivity extends AppCompatActivity {

    @ViewById(R.id.constraintLayout)
    ConstraintLayout mMainLayout;

    @ViewById(R.id.userlist)
    RecyclerView mRecyclerView;

    @ViewById(R.id.progress)
    ProgressBar mProgress;

    private List<User> mUserList;
    private Dialog dialog;
    private String current = "";

    @AfterViews
    void init() {
        mUserList = MockUsers.getInstance().getUsers();

        dialog = new Dialog(UserListActivity.this);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        RecyclerView.Adapter mAdapter = new UserAdapter(mUserList);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (!dialog.isShowing()) {
                            if (Util.isOnline(UserListActivity.this)) {
                                showDialog(mUserList.get(position));
                            } else {
                                showProgress(false);
                                Util.notifyUser(mMainLayout, "Verifique sua conexão e tente novamente.", Color.RED);
                            }

                        }
                    }
                })
        );

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void showProgress(boolean enable) {

        if (enable) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.INVISIBLE);
        }
    }

    private void showDialog(final User user) {
        // custom dialog
        dialog = new Dialog(UserListActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        // set the custom dialog components - text, image and button
        CircleImageView foto = (CircleImageView) dialog.findViewById(R.id.foto);
        TextView nome = (TextView) dialog.findViewById(R.id.nome);
        TextView telefone = (TextView) dialog.findViewById(R.id.telefone);
        TextView initials = (TextView) dialog.findViewById(R.id.initials);
        final EditText valor = (EditText) dialog.findViewById(R.id.edtValor);

        // Máscara valor

        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    valor.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));

                    current = formatted;
                    valor.setText(formatted);
                    valor.setSelection(formatted.length());

                    valor.addTextChangedListener(this);
                }
            }
        });

        nome.setText(user.getNome());
        telefone.setText(user.getTelefone());

        if (user.getFoto() == null) {
            foto.setImageAlpha(0);
            Glide.with(foto.getContext()).load(R.mipmap.ic_launcher).into(foto);

            StringBuilder builder = new StringBuilder(3);

            for (int i = 0; i < 2; i++) {
                builder.append(user.getNome().split(" ")[i].substring(0, 1));
            }

            initials.setText(builder.toString());
            initials.setVisibility(View.VISIBLE);
        } else {
            foto.setImageAlpha(255);
            initials.setVisibility(View.INVISIBLE);
            Glide.with(foto.getContext()).load(user.getFoto()).into(foto);
        }

        final UnlockMoney unlockButton = (UnlockMoney) dialog.findViewById(R.id.btnEnviar);
        TextView dialogButtonNao = (TextView) dialog.findViewById(R.id.btnFechar);

        unlockButton.setOnUnlockListener(new UnlockMoney.OnUnlockListener() {
            @Override
            public void onUnlock() {

                try {
                    if (!current.isEmpty()) {
                        sendMoney(user.getClienteId(), LocalStorage.getInstance(UserListActivity.this).getString("_token"), NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).parse(current).doubleValue());
                        dialog.dismiss();
                    } else {
                        valor.setError("Digite um valor");
                        unlockButton.reset();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Nao
        dialogButtonNao.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }

    private void sendMoney(String id, String token, Double valor) {
        showProgress(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://processoseletivoneon.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API buscaliAPI = retrofit.create(API.class);
        Call<Boolean> loadFaqs = buscaliAPI.sendMoney(id, token, valor);
        loadFaqs.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response != null && response.code() == 200) {
                    boolean transfered = response.body();

                    if (transfered)
                        Util.notifyUser(mMainLayout, "Transferência realizada com Sucesso", Color.GREEN);
                    else
                        Util.notifyUser(mMainLayout, "Não foi possível realizar a transferência.", Color.RED);
                } else {
                    if (response != null) {
                        Util.notifyUser(mMainLayout, String.valueOf(response.code() + " " + response.message()), Color.RED);
                    }
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                showProgress(false);
                Util.notifyUser(mMainLayout, t.getMessage(), Color.RED);
            }
        });
    }


}
