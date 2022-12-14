package com.example.uridongnefc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegionResetActivity extends Activity {

    private Button location_button;
    private Button region_next_btn;
    private TextView location_text;

    /** 사용자 현재 위치 주소 **/
    private String region;
    private double longitude;
    private double latitude;


    /** ReverseGeoCoding API KEY **/

    String APIKEY_ID = "hgdo31f2qg";
    String APIKEY = "cMNNoOKZKpqez7CHzDWHJ80PTjQbuE4whqLIItmP";

    /** Retrofit **/

    Retrofit retrofit = NaverApiClient.getNaverApiClient();
    ReverseGeocodingInterface reverseGeocodingInterface = retrofit.create(ReverseGeocodingInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.region_choose_activity);

        location_button = (Button) findViewById(R.id.location_button);
        location_text = (TextView) findViewById(R.id.location_text);
        region_next_btn = (Button) findViewById(R.id.region_next_btn);


        /** 위치 관리자 객체 생성 **/
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( RegionResetActivity.this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 0 );
                }
                else{
                    /** 사용자 마지막 위치 받아오기 **/
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();


                        /** 좌표 -> 주소로 변경 **/
                        String coord = longitude +","+ latitude ;

                        Call<ReverseGeocoding> call = reverseGeocodingInterface.getGeo(APIKEY_ID,APIKEY, coord,"epsg:4326", "addr","json");

                        call.enqueue(new Callback<ReverseGeocoding>() {
                            @Override
                            public void onResponse(Call<ReverseGeocoding> call, Response<ReverseGeocoding> response) {

                                ReverseGeocoding tmp = response.body();
                                Result result= tmp.results.get(0);

                                region = result.getRegion().getArea1().getName() + " " +
                                        result.getRegion().getArea2().getName() + " " +
                                        result.getRegion().getArea3().getName();

                                location_text.setText(region);

                                region = result.getRegion().getArea3().getName();


                            }

                            @Override
                            public void onFailure(Call<ReverseGeocoding> call, Throwable t) {

                            }
                        });


                    }
                    }

                }
            });

        /** 메인화면으로 넘기기 **/
        region_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegionResetActivity.this, MainActivity.class);
                intent.putExtra("region", region);
                startActivity(intent);
            }
        });

    }

}
