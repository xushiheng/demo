package com.xsh.util;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xsh.test.MyView;
import com.xsh.test.R;

public class MyAdapter extends BaseAdapter{
	public Context context;
	private List<Map<String,Object>> listItems;
	private LayoutInflater listContainer;
	public AsyncImageUtil asyncHttpUtil;

	public final class ViewHolder{
		public MyView myView;
		public TextView name;
		public TextView comment;
	}
	
	public MyAdapter(Context context,List<Map<String,Object>> listItems){
		this.context = context;
		this.listItems = listItems;
		listContainer = LayoutInflater.from(context);
		asyncHttpUtil = new AsyncImageUtil(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("method", "getView");
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.listitem, null);
			viewHolder.myView = (MyView) convertView.findViewById(R.id.myView);
			viewHolder.name = (TextView) convertView.findViewById(R.id.textView1);
			viewHolder.comment = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		asyncHttpUtil.getBitmap((String) listItems.get(position).get("logo"), viewHolder.myView);
		viewHolder.name.setText((CharSequence) listItems.get(position).get("name"));
		viewHolder.comment.setText((CharSequence) listItems.get(position).get("comment"));
		return convertView;
	}

}
