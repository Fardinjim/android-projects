package sathish.sjbitalumni.browsealumni;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import sathish.sjbitalumni.AppController;
import sathish.sjbitalumni.R;
import sathish.sjbitalumni.Supporting_Files.FadeInNetworkImageView;
import sathish.sjbitalumni.dto.BrowseAlumniDTO;

/**
 * Created by Sathish Mun on 18-08-2015.
 */
public class BrowseAlumniAdapter extends UltimateViewAdapter {

    Activity activity;
    BrowseAlumniDTO eachAlumniDetail;
    private List<BrowseAlumniDTO> oneRowAlumniDetail;
    onInsertContactListener insertContactListener;
    onCallListener callListener;


    // initialize the adapter
    public BrowseAlumniAdapter(Activity activity, List<BrowseAlumniDTO> oneRowAlumniDetail, onInsertContactListener insertContactListener, onCallListener callListener) {
        this.activity = activity;
        this.oneRowAlumniDetail = oneRowAlumniDetail;
        this.insertContactListener = insertContactListener;
        this.callListener = callListener;

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

                ((ViewHolder) holder).alumni.setImageUrl(eachAlumniDetail.getImageUrl(), imageLoader);


                ((ViewHolder) holder).alumniName.setText(eachAlumniDetail.getName());


                ((ViewHolder) holder).alumniRegisterNo.setText(eachAlumniDetail.getRegisterNo());


                ((ViewHolder) holder).alumniDept.setText(eachAlumniDetail.getDepartment());

                ((ViewHolder) holder).alumniYear.setText(eachAlumniDetail.getYear());

                ((ViewHolder) holder).alumniPhone.setText(eachAlumniDetail.getPhone());



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
        notifyDataSetChanged();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {
        FadeInNetworkImageView alumni;

        TextView  alumniName, alumniRegisterNo, alumniDept, alumniYear, alumniPhone;
        ImageView call, addToContact;

        //intialize the custom layout

        public ViewHolder(View itemView) {
            super(itemView);
            alumni = (FadeInNetworkImageView)
                    itemView.findViewById(R.id.alumni_image);

            alumniName = (TextView) itemView.findViewById(R.id.alumni_name);

            alumniRegisterNo = (TextView) itemView.findViewById(R.id.alumni_registerno);

            alumniDept = (TextView) itemView.findViewById(R.id.alumni_dept);

            alumniYear = (TextView) itemView.findViewById(R.id.alumni_year);

            alumniPhone = (TextView) itemView.findViewById(R.id.alumni_phone);

            addToContact = (ImageView) itemView.findViewById(R.id.addToContact);

            call = (ImageView) itemView.findViewById(R.id.call);

//            alumni.setOnClickListener(this);

            addToContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    insertContactListener.contact_info(
                            oneRowAlumniDetail.get(getLayoutPosition()-1).getName(),
                            oneRowAlumniDetail.get(getLayoutPosition()-1).getPhone() );
                }
            });

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String number = "tel:" + oneRowAlumniDetail.get(getLayoutPosition()-1).getPhone().toString().trim();
                    callListener.call_info(number);

                }
            });

        }


    }


    //set up interface
    public interface onInsertContactListener {
        public void contact_info(String name, String phone);
    }

    public interface onCallListener {
        public void call_info(String phone);
    }

}
