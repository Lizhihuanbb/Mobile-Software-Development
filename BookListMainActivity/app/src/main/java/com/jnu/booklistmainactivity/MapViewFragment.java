package com.jnu.booklistmainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MapView mapView;
    public static final int WHAT_DATA_OK = 1000;

    public MapViewFragment() {
        // Required empty public constructor
    }


    public static MapViewFragment newInstance() {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =inflater.inflate(R.layout.fragment_map_view,container,false);
        mapView = rootview.findViewById(R.id.mapview_content);

        BaiduMap baiduMap = mapView.getMap();

        LatLng centerPoint = new LatLng(22.2559,113.541112);
        MapStatus mMapStatus = new MapStatus.Builder().target(centerPoint).zoom(17).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);


        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.jinan);


//        MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(centerPoint);
//        Marker marker = (Marker) baiduMap.addOverlay(markerOption);
//
//        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50).fontColor(0xFFFF00FF).text("????????????").rotate(0).position(centerPoint);
//        baiduMap.addOverlay(textOption);



        Handler handler=new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==WHAT_DATA_OK)
                {
                    String content= msg.getData().getString("data");
                    if(null!=content) {
                        try {
                            JSONObject jsonObject = new JSONObject(content);
                            JSONArray shops=jsonObject.getJSONArray("shops");
                            for(int index=0;index<shops.length();index++)
                            {
                                JSONObject shop=shops.getJSONObject(index);

                                LatLng centerPoint = new LatLng(shop.getDouble("latitude"),shop.getDouble("longitude"));
                                MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(centerPoint);
                                Marker marker = (Marker) mapView.getMap().addOverlay(markerOption);
                                OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                                        .fontColor(0xFFFF00FF).text(shop.getString("name")).rotate(0).position(centerPoint);
                                mapView.getMap().addOverlay(textOption);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        };
        Runnable runnable=new Runnable(){

            @Override
            public void run() {
                try {
                    URL url=new URL("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                    {
                        InputStream inputStream= httpURLConnection.getInputStream();
                        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                        String line="";
                        StringBuffer stringBuffer=new StringBuffer();
                        while( null!=(line=bufferedReader.readLine())){
                            stringBuffer.append(line);
                        }
                        Message message=new Message();
                        message.what= WHAT_DATA_OK;
                        Bundle bundle=new Bundle();
                        bundle.putString("data",stringBuffer.toString());
                        message.setData(bundle);

                        handler.sendMessage(message);
                        Log.i("test", "onCreateView: "+stringBuffer.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();




        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast.makeText(getContext(), "Marker???????????????", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return rootview;
    }
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}