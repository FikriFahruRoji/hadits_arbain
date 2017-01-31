package id.barkost.haditsarbain.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.barkost.haditsarbain.model.ModelMenu;
import id.barkost.haditsarbain.R;

/**
 * Created by fikri on 20/01/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<ModelMenu> menuList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView no, latin, arabic;

        public MyViewHolder(View view) {
            super(view);
            no = (TextView) view.findViewById(R.id.tv_no);
            latin = (TextView) view.findViewById(R.id.tv_latin);
            arabic = (TextView) view.findViewById(R.id.tv_arabic);
        }
    }


    public MenuAdapter(List<ModelMenu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelMenu menu = menuList.get(position);
        holder.no.setText(Integer.toString(menu.getNo()));
        holder.latin.setText(menu.getLatin());
        holder.arabic.setText(menu.getArabic());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}