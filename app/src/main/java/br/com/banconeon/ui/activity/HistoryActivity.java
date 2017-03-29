package br.com.banconeon.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;
import java.util.List;

import br.com.banconeon.API;
import br.com.banconeon.MockUsers;
import br.com.banconeon.R;
import br.com.banconeon.adapter.HistoryAdapter;
import br.com.banconeon.adapter.HistoryGraphAdapter;
import br.com.banconeon.model.TransferHistory;
import br.com.banconeon.model.User;
import br.com.banconeon.ui.custom.SimpleDividerItemDecoration;
import br.com.banconeon.utils.LocalStorage;
import br.com.banconeon.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EActivity(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity {

    @ViewById(R.id.relativeLayout)
    RelativeLayout mMainLayout;

    @ViewById(R.id.historylist)
    RecyclerView mRecyclerView;

    @ViewById(R.id.graphList)
    RecyclerView mRecyclerViewGraph;

    @ViewById(R.id.progress)
    ProgressBar mProgress;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterGraph;
    private List<TransferHistory> transfers;
    private List<User> mUserList;


    @AfterViews
    void init() {
        mUserList = MockUsers.getInstance().getUsers();

        if (Util.isOnline(this)) {
            try {
                getTransfers(LocalStorage.getInstance(HistoryActivity.this).getString("_token"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            mRecyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

            mRecyclerView.setNestedScrollingEnabled(false);

            // Gráfico

            mRecyclerViewGraph.setHasFixedSize(true);

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewGraph.setLayoutManager(horizontalLayoutManagaer);
            mRecyclerViewGraph.addItemDecoration(new SimpleDividerItemDecoration(this));

            mRecyclerViewGraph.setNestedScrollingEnabled(false);
        } else {
            showProgress(false);
            Util.notifyUser(mMainLayout, "Verifique sua conexão e tente novamente.", Color.RED);
        }
    }

    private void getTransfers(final String token) {
        showProgress(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://processoseletivoneon.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API buscaliAPI = retrofit.create(API.class);
        Call<List<TransferHistory>> loadFaqs = buscaliAPI.getTransfers(token);
        loadFaqs.enqueue(new Callback<List<TransferHistory>>() {

            @Override
            public void onResponse(Call<List<TransferHistory>> call, Response<List<TransferHistory>> response) {
                if (response != null && response.code() == 200) {
                    transfers = response.body();

                    Collections.reverse(transfers);

                    for (TransferHistory example : transfers) {
                        for (User user :
                                mUserList) {
                            if (user.getClienteId().equals(example.getClienteId())) {
                                example.setNome(user.getNome());
                                example.setFoto(user.getFoto());
                                example.setTelefone(user.getTelefone());
                            }
                        }
                    }

                    mAdapter = new HistoryAdapter(transfers);

                    mRecyclerView.setAdapter(mAdapter);

                    mAdapterGraph = new HistoryGraphAdapter(transfers);

                    mRecyclerViewGraph.setAdapter(mAdapterGraph);

                } else {
                    if (response != null) {
                        Util.notifyUser(mMainLayout, String.valueOf(response.code() + " " + response.message()), Color.RED);
                    }
                }

                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<TransferHistory>> call, Throwable t) {
                showProgress(false);
                Util.notifyUser(mMainLayout, t.getMessage(), Color.RED);
            }
        });
    }

    private void showProgress(boolean enable) {

        if (enable) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.INVISIBLE);
        }
    }
}
