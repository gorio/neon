package br.com.banconeon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.banconeon.R;
import br.com.banconeon.model.User;
import br.com.banconeon.ui.custom.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> mUserList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView mFoto;
        public TextView mNome;
        public TextView mTelefone;
        public TextView mInitials;

        public ViewHolder(View v) {
            super(v);
            mFoto = (CircleImageView) v.getRootView().findViewById(R.id.foto);
            mNome = (TextView) v.getRootView().findViewById(R.id.nome);
            mTelefone = (TextView) v.getRootView().findViewById(R.id.telefone);
            mInitials = (TextView) v.getRootView().findViewById(R.id.initials);
        }
    }

    public UserAdapter(List<User> usersList) {
        mUserList = usersList;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNome.setText(mUserList.get(position).getNome());
        holder.mTelefone.setText(mUserList.get(position).getTelefone());

        if (mUserList.get(position).getFoto() == null) {
            holder.mFoto.setImageAlpha(0);
            Glide.with(holder.mFoto.getContext()).load(R.mipmap.ic_launcher).into(holder.mFoto);

            StringBuilder builder = new StringBuilder(3);

            for (int i = 0; i < 2; i++) {
                builder.append(mUserList.get(position).getNome().split(" ")[i].substring(0, 1));
            }

            holder.mInitials.setText(builder.toString());
            holder.mInitials.setVisibility(View.VISIBLE);
        } else {
            holder.mFoto.setImageAlpha(255);
            holder.mInitials.setVisibility(View.INVISIBLE);
            Glide.with(holder.mFoto.getContext()).load(mUserList.get(position).getFoto()).into(holder.mFoto);
        }

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}