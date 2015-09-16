package mun.sathish.sjbitfest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Home on 18-04-2015.
 */
public class RecylerAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ClickListener clickListener;
    private LayoutInflater inflator;
    private Context context;
    List<DrawerItem> data = Collections.emptyList();

    public RecylerAdaptor(Context context, List<DrawerItem> data) {
        this.context = context;

        inflator = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER)
        {
            View view = inflator.inflate(R.layout.drawer_header, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }
        else
        {
            View view = inflator.inflate(R.layout.custom_row, parent, false);
            ItemHolder  holder= new ItemHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){return TYPE_HEADER;}
        else{ return TYPE_ITEM; }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderHolder)
        {

        }
        else
        {
            ItemHolder itemHolder = (ItemHolder) holder;
            DrawerItem current = data.get(position-1);
            itemHolder.title.setText(current.title);
            itemHolder.icon.setImageResource(current.iconId);
        }



    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listicon);


        }

        @Override
        public void onClick(View v) {
            //  context.startActivity(new Intent(context, Subactivity.class));




            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder  {

        public HeaderHolder(View itemView) {
            super(itemView);


        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);

    }
}
