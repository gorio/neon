package br.com.banconeon.adapter;

import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.banconeon.R;
import br.com.banconeon.model.TransferHistory;
import br.com.banconeon.ui.custom.CircleImageView;

public class HistoryGraphAdapter extends RecyclerView.Adapter<HistoryGraphAdapter.ViewHolder> {
    private List<TransferHistory> mTransferHistoryList;
    private Double mValorMax;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mBall;
        CircleImageView mFoto;
        TextView mValor;
        TextView mInitials;
        View mBarGraph;

        ViewHolder(View v) {
            super(v);
            mBarGraph = v.getRootView().findViewById(R.id.bargraph);
            mBall = (CircleImageView) v.getRootView().findViewById(R.id.ball);
            mFoto = (CircleImageView) v.getRootView().findViewById(R.id.foto);
            mValor = (TextView) v.getRootView().findViewById(R.id.valor);
            mInitials = (TextView) v.getRootView().findViewById(R.id.initials);
        }
    }

    public HistoryGraphAdapter(List<TransferHistory> usersList) {
        mTransferHistoryList = usersList;
        mValorMax = getMaxValue(mTransferHistoryList);
    }

    @Override
    public HistoryGraphAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_graphic, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mValor.setText(NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(mTransferHistoryList.get(position).getValor()));
//        Glide.with(holder.mBall.getContext()).load(R.mipmap.ic_launcher).into(holder.mBall);

        if (mTransferHistoryList.get(position).getFoto() == null) {
            holder.mFoto.setImageAlpha(0);
            Glide.with(holder.mFoto.getContext()).load(R.mipmap.ic_launcher).into(holder.mFoto);

            StringBuilder builder = new StringBuilder(3);

            for (int i = 0; i < 2; i++) {
                builder.append(mTransferHistoryList.get(position).getNome().split(" ")[i].substring(0, 1));
            }

            holder.mInitials.setText(builder.toString());
            holder.mInitials.setVisibility(View.VISIBLE);
        } else {
            holder.mFoto.setImageAlpha(255);
            holder.mInitials.setVisibility(View.INVISIBLE);
            Glide.with(holder.mFoto.getContext()).load(mTransferHistoryList.get(position).getFoto()).into(holder.mFoto);
        }

        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) holder.mBarGraph.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.heightPercent = getSizeGraph(mValorMax, mTransferHistoryList.get(position).getValor()) * 0.01f;
        holder.mBarGraph.requestLayout();

    }

    private Double getMaxValue(List<TransferHistory> list) {
        Double valor = 0.0;

        for (TransferHistory transfer :
                list) {
            if (transfer.getValor() != null && transfer.getValor() > valor)
                valor = transfer.getValor();
        }

        return valor;
    }

    private int getSizeGraph(Double maxValue, Double valor) {

        if ((((valor * 50) / maxValue)) < 1)
            return 1;
        else
            return (int) (((valor * 50) / maxValue));
    }

    @Override
    public int getItemCount() {
        return mTransferHistoryList.size();
    }
}