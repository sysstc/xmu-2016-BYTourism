package com.ezz.view;

import java.util.ArrayList;
import java.util.List;

import com.ezz.bytourism1.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LeftMenuFragment extends Fragment {

	private List<LeftMenu> list = null;
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		list = new ArrayList<LeftMenu>();
		list.add(new LeftMenu(R.drawable.svip, "��ͨ��Ա"));
		list.add(new LeftMenu(R.drawable.qianbao, "QQǮ��"));
		list.add(new LeftMenu(R.drawable.zhuangban, "����װ��"));
		list.add(new LeftMenu(R.drawable.shoucang, "�ҵ��ղ�"));
		list.add(new LeftMenu(R.drawable.xiangce, "�ҵ����"));
		list.add(new LeftMenu(R.drawable.wenjian, "�ҵ��ļ�"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_menu, container, false);
		lv = (ListView) view.findViewById(R.id.left_menu_lv);
		LeftMenuAdapter adapter = new LeftMenuAdapter(list);
		lv.setAdapter(adapter);
		return view;
	}
}
