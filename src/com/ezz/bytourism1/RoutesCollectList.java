package com.ezz.bytourism1;

/**
 * Created by 37492 on 2016/4/7.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
public class RoutesCollectList extends Activity implements OnItemClickListener,OnScrollListener{
    private ListView listView;
    private ArrayAdapter< String> arr_adapter;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routescollect);
        listView = (ListView)findViewById(R.id.routeslist);
        //1.创建一个数据适配器
        //ArrayAdapter(上下文，当前ListView加载每一个列表项所对应的布局文件，数据源)
        //2.适配器加载数据源
        //3.视图（ListView）加载适配器
		/*SimpleAdapter
		 * 1.创建一个适配器
		 * 2.适配器加载数据源
		 * 3.视图(ListView)加载适配器
		 * listView.setAapter(arr_adapter)
		 * */
        String[] arr_data = {"慕课网1","慕课网2","慕课网3","慕课网4","慕课网5"};
        dataList = new ArrayList<Map<String,Object>>();
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr_data);
        //	listView.setAdapter(arr_adapter);

        /**
         * SimpleAdapter:
         * context：上下文
         * data：数据源(List< ?extends Map<String,?>>data)一个Map所组成的List集合
         * 			  每一个Map都会去对应ListView列表的一行
         * 			 每一个Map<键-值对>中的键必须包含所有from所指定的键
         * resource：列表项的布局文件ID
         * from：Map中的键名
         * to：绑定数据源视图的ID，与from成对应关系
         * */
        simp_adapter = new SimpleAdapter(this, getData(), R.layout.routescollect_item,new String[]{"pic","text"} ,new int[]{R.id.image,R.id.title});

        listView.setAdapter(simp_adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }
    private List<Map<String, Object>> getData(){
        for(int i = 0;i<20;i++){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("pic", R.drawable.ic_launcher);
            map.put("text", "慕课网"+i);
            dataList.add(map);

        }
        return dataList;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO 自动生成的方法存根
        String text = listView.getItemAtPosition(position)+"";
        Toast.makeText(this, "position="+position +"text = "+text, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO 自动生成的方法存根
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("pic", R.drawable.ic_launcher);
                map.put("text", "增加项");
                dataList.add(map);
                simp_adapter.notifyDataSetChanged();
                break;
            case SCROLL_STATE_IDLE:
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                break;
            default:
                break;
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO 自动生成的方法存根

    }
}
