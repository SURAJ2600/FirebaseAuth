package androidmasterminds.com.firebaseauth;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidmasterminds.com.firebaseauth.Adapters.GeneralAdapter;
import androidmasterminds.com.firebaseauth.Model.pdfData;
import androidmasterminds.com.firebaseauth.RestClient.APIIterface;
import androidmasterminds.com.firebaseauth.RestClient.RetrofitInstance;
import androidmasterminds.com.firebaseauth.Utilities.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeneralListingActivity extends AppCompatActivity {


    private APIIterface interfaces;

    private Context mContext;
    ArrayList<pdfData> mArraylist=new ArrayList<>();

    private GeneralAdapter mAdapter;

    @BindView(R.id.recyleview)
    RecyclerView mRecyerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_listing);
        ButterKnife.bind(this);
        mContext=GeneralListingActivity.this;


        GetListFromResponce();

        mAdapter = new GeneralAdapter(mArraylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyerview.setLayoutManager(mLayoutManager);
        mRecyerview.setItemAnimator(new DefaultItemAnimator());
        mRecyerview.setAdapter(mAdapter);




    }

    private void GetListFromResponce() {

        if(Util.isNetworkConnected(GeneralListingActivity.this)) {

            HashMap<String, String> mMap = new HashMap<>();

            mMap.put("date", "2018-06-10");
            mMap.put("limit", "21");
            mMap.put("page", "1");


            interfaces = RetrofitInstance.getRetrofitInstance().create(APIIterface.class);


            Call<JsonElement> call = interfaces.loadChanges(mMap);

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    try {


                        Gson gson = new Gson();

                        pdfData mdata;


                        try {
                            JSONObject obj = new JSONObject(response.body().toString());

                            JSONArray data = obj.getJSONArray("data");

                            if (data.length() != 0) {

                                mArraylist.clear();

                                for (int i = 0; i < data.length() - 1; i++) {

                                    mdata = gson.fromJson(data.getJSONObject(i).toString(), pdfData.class);
                                    mArraylist.add(mdata);


                                }

                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(mContext, mContext.getString(R.string.internet),Toast.LENGTH_SHORT).show();

        }


    }
}
