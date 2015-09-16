package sathish.alumnijson.alumnijson;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import sathish.alumnijson.AppController;
import sathish.alumnijson.R;
import sathish.alumnijson.Supporting_Files.FadeInNetworkImageView;
import sathish.alumnijson.dto.BrowseAlumniJsonDTO;

/**
 * Created by Sathish Mun on 13-08-2015.
 */
public class AlumniJsonAdapter extends UltimateViewAdapter {

    Activity activity;
    BrowseAlumniJsonDTO eachAlumniDetail;
    private List<BrowseAlumniJsonDTO> oneRowAlumniDetail;


    // initialize the adapter
    public AlumniJsonAdapter(Activity activity, List<BrowseAlumniJsonDTO> oneRowAlumniDetail) {
        this.activity = activity;
        this.oneRowAlumniDetail = oneRowAlumniDetail;
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_alumni, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return oneRowAlumniDetail.size();
    }

    @Override
    public long generateHeaderId(int position) {
        if (position == 0) {
            return position;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if ((customHeaderView != null ? position <= getAdapterItemCount() : position < getAdapterItemCount())
                && (customHeaderView == null || position > 0)) {
            try {

                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                eachAlumniDetail = oneRowAlumniDetail.get(position - 1);

                ((ViewHolder) holder).firstAlumni.setImageUrl(eachAlumniDetail.getFirstItemInfo().getImageUrl(), imageLoader);
                ((ViewHolder) holder).secondAlumni.setImageUrl(eachAlumniDetail.getSecondItemInfo().getImageUrl(), imageLoader);

                ((ViewHolder) holder).firstAlumniName.setText(eachAlumniDetail.getFirstItemInfo().getName());
                ((ViewHolder) holder).secondAlumniName.setText(eachAlumniDetail.getSecondItemInfo().getName());

                ((ViewHolder) holder).firstAlumniRegisterNo.setText(eachAlumniDetail.getFirstItemInfo().getRegisterNo());
                ((ViewHolder) holder).secondAlumniRegisterNo.setText(eachAlumniDetail.getSecondItemInfo().getRegisterNo());

                ((ViewHolder) holder).firstAlumniDept.setText(eachAlumniDetail.getFirstItemInfo().getDepartment());
                ((ViewHolder) holder).secondAlumniDept.setText(eachAlumniDetail.getSecondItemInfo().getDepartment());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_alumni, parent, false);
        return new RecyclerView.ViewHolder(v) {
        };
    }



    @Override
    public <T> void insert(List<T> list, T object, int position) {
        list.add(object);
        notifyItemInserted(position);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
        FadeInNetworkImageView firstAlumni;
        FadeInNetworkImageView secondAlumni;
        TextView firstAlumniName, secondAlumniName;
        TextView firstAlumniRegisterNo, secondAlumniRegisterNo;
        TextView firstAlumniDept, secondAlumniDept;

        //intialize the custom layout

        public ViewHolder(View itemView) {
            super(itemView);
            firstAlumni = (FadeInNetworkImageView)
                    itemView.findViewById(R.id.first_alumni_image);
            secondAlumni = (FadeInNetworkImageView) itemView
                    .findViewById(R.id.second_alumni_image);
            firstAlumniName = (TextView) itemView.findViewById(R.id.first_alumni_name);
            secondAlumniName = (TextView) itemView.findViewById(R.id.second_alumni_name);
            firstAlumniRegisterNo = (TextView) itemView.findViewById(R.id.first_alumni_registerno);
            secondAlumniRegisterNo = (TextView) itemView.findViewById(R.id.second_alumni_registerno);
            firstAlumniDept = (TextView) itemView.findViewById(R.id.first_alumni_dept);
            secondAlumniDept = (TextView) itemView.findViewById(R.id.second_alumni_dept);
            firstAlumni.setOnClickListener(this);
            secondAlumni.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

}
