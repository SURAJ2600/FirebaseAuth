package androidmasterminds.com.firebaseauth.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidmasterminds.com.firebaseauth.Model.pdfData;
import androidmasterminds.com.firebaseauth.R;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {

    private ArrayList<pdfData> mDatalist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_pdfs, txt_eventname;

        public MyViewHolder(View view) {
            super(view);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_eventname = (TextView) view.findViewById(R.id.txt_eventname);
            txt_pdfs = (TextView) view.findViewById(R.id.txt_pdfs);
        }
    }


    public GeneralAdapter(ArrayList<pdfData> moviesList) {
        this.mDatalist = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        pdfData mData = mDatalist.get(position);
        holder.txt_name.setText(mData.getFEUILLET());
        holder.txt_eventname.setText(mData.getName());
        holder.txt_pdfs.setText(mData.getLink());

    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

}