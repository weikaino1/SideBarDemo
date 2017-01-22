package com.example.x6.sidebardemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ISideBar {

    private TextView showTv;
    private SideBar bar;
    private SortAdapter adapter;
    private CityRes commentRes;
    private List<SortMode> data;
    private ListView list;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            data = initCityData(commentRes.getCitylist());
            //数据放在适配器里面之前要先排序
            Collections.sort(data, new PinyinComparator());
            adapter = new SortAdapter(MainActivity.this, data);
            list.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        bar = (SideBar) findViewById(R.id.slidBar);
        showTv = (TextView) findViewById(R.id.showTv);
        list = (ListView) findViewById(R.id.list);
    }

    private void initData() {
        adapter = new SortAdapter(this,data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson resultGson = new Gson();
                String jsonData = UtilsString.getStrFromAssets(MainActivity.this, "city.json");
                commentRes = resultGson.fromJson(jsonData, CityRes.class);

                handler.sendEmptyMessageDelayed(0, 100);
            }
        }).start();

    }

    private void initListener() {
        bar.setListener(this);
    }


    private List<SortMode> initCityData(List<CitylistDto> citylist) {
        List<SortMode> mLists = new ArrayList<SortMode>();

        for (int i = 0; i < citylist.size(); i++) {
            for (int n = 0; n < citylist.get(i).getCities().size(); n++) {
                SortMode mode = new SortMode();
                mode.setName(citylist.get(i).getCities().get(n).getName());
                mode.setSortLetter(citylist.get(i).getAZ());
                mode.setCityID(citylist.get(i).getCities().get(n).getCityID());
                mode.setCitycode(citylist.get(i).getCities().get(n).getCitycode());
                mode.setPyf(citylist.get(i).getCities().get(n).getPyf());
                mode.setPys(citylist.get(i).getCities().get(n).getPys());
                mLists.add(mode);
            }
        }
        return mLists;
    }


    @Override
    public void onTouchLetterChanged(String s) {
        showTv.setText(s);
        showTv.setVisibility(View.VISIBLE);

        int position = adapter.getPositionForSelection(s.charAt(0));
        if (position != -1) {
            list.setSelection(position);
        }

    }

    @Override
    public void onTouchUpInVisibility() {
        showTv.setText("");
        showTv.setVisibility(View.INVISIBLE);
    }
}
