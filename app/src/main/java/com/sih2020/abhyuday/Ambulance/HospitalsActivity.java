package com.sih2020.abhyuday.Ambulance;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.sih2020.abhyuday.R;
import com.sih2020.abhyuday.Util.Utils;
import com.sih2020.abhyuday.api.ApiInterface;
import com.sih2020.abhyuday.api.Client;
import com.sih2020.abhyuday.models.Places;
import com.sih2020.abhyuday.models.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Location location;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Results> results = new ArrayList<>();
    private HospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        recyclerView = findViewById(R.id.hospitals_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent, null));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        swipeRefreshLayout.post(this::loadHospitals);
    }

    public void loadHospitals() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = Client.getClient().create(ApiInterface.class);
        location = new Location("");
        location.setLatitude(Double.parseDouble("15.7962748"));
        location.setLongitude(Double.parseDouble("74.4716944"));

        Call<Places> call = apiInterface.getHospitals("15.7962748,74.4716944", 15000, "hospital", "distance", Utils.API_KEY);
        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {

                    results = response.body().getResults();
                    adapter = new HospitalAdapter(results, location);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener((view, position) -> {
                        TextView tvContact = view.findViewById(R.id.tvContact);
                        Results result = results.get(position);

                        String uri = "https://www.google.com/maps/place/"+result.getGeometry().getLocation().getLat()+","+result.getGeometry().getLocation().getLng();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        getApplicationContext().startActivity(intent);


                    });
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void onRefresh() {
        loadHospitals();
    }
}