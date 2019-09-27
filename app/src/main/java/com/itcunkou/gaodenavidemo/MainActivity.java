package com.itcunkou.gaodenavidemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AMapLocationListener {
    private Button button;
    private double start_lng;
    private double start_lat;
    public AMapLocationClient mLocationClient = null;
    /**
     * 声明AMapLocationClientOption对象
     */
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        performCodeWithPermission("权限", new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                    }

                    @Override
                    public void noPermission() {
                    }
                }, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH);


        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("start_lng", start_lng);
                bundle.putDouble("start_lat", start_lat);
//                23.433996, 113.172854
                bundle.putDouble("end_lng", 118.540852);
                bundle.putDouble("end_lat", 24.904488);
//                bundle.putDouble("end_lng", 113.172854);
//                bundle.putDouble("end_lat", 23.433996);
//                ,24.904488
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NaviActivity.class);
                intent.putExtras(bundle);
                startActivity(new Intent(intent));
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                start_lat = aMapLocation.getLatitude();
                start_lng = aMapLocation.getLongitude();

                LatLng my_latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());

                Toast.makeText(this,"位置改变："+start_lat+"，"+start_lng, Toast.LENGTH_LONG).show();

                ArrayList<LatLng> latLngList = new ArrayList<>();
                LatLng latLng1 = new LatLng( 23.431667,113.171196);
                LatLng latLng2 = new LatLng(23.43146,113.170724);
                LatLng latLng3 = new LatLng(23.431942,113.17095);
                LatLng latLng4 = new LatLng(23.431942,113.170435);

                latLngList.add(latLng1);
                latLngList.add(latLng2);
                latLngList.add(latLng3);
                latLngList.add(latLng4);

                Utils.polygonCon(null,latLngList,my_latLng);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
}
