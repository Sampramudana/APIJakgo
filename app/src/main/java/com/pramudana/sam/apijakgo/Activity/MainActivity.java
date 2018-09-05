package com.pramudana.sam.apijakgo.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pramudana.sam.apijakgo.Model.DataItem;
import com.pramudana.sam.apijakgo.Model.ResponseJakgo;
import com.pramudana.sam.apijakgo.Network.ApiService;
import com.pramudana.sam.apijakgo.Network.InstanceRetrofit;
import com.pramudana.sam.apijakgo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcJakgo;
    CustomAdapter adapter;
    String token = "EHEDFkkTW8/I1JP+niBRmoruaOqpev79AI8O+MRjvCCMSaxuzdcdVo/WorRtyUao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Inlitialize Widget to Variable
        rcJakgo = findViewById(R.id.rcJakgo);
        getData();
    }

    private void getData() {
        final ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseJakgo> call = apiService.readJakgoApi(token);
        call.enqueue(new Callback<ResponseJakgo>() {
            @Override
            public void onResponse(Call<ResponseJakgo> call, Response<ResponseJakgo> response) {
                String status = response.body().getStatus();
                if (response.body().getStatus().equals("success")) {

                    List<DataItem> dataJakgo = response.body().getData();
                    adapter = new CustomAdapter(rcJakgo, MainActivity.this, dataJakgo);
                    Log.e("Data",response.body().getStatus());
                    rcJakgo.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rcJakgo.setAdapter(adapter);

                }
                else{
                    Log.e("Gagal","Broken Dude");
                }
            }

            @Override
            public void onFailure(Call<ResponseJakgo> call, Throwable t) {
                Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        Context context;
        List<DataItem> dataItems;

        public CustomAdapter(RecyclerView rcNews, Context context, List<DataItem> articlesItems) {
            this.context = context;
            this.dataItems = articlesItems;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.name.setText(dataItems.get(position).getNama());
            holder.phone.setText(dataItems.get(position).getPhone());
            holder.wilayah.setText(dataItems.get(position).getWilayah());
        }

        @Override
        public int getItemCount() {
            return dataItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name, phone, wilayah;

            public MyViewHolder(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                phone = itemView.findViewById(R.id.phone);
                wilayah = itemView.findViewById(R.id.wilayah);
            }
        }
    }
}
