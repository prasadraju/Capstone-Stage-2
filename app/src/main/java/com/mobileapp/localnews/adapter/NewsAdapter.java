package com.mobileapp.localnews.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileapp.localnews.R;
import com.mobileapp.localnews.data.NewsPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import com.prasad.poupularmoviesstage1.popularmoviesstage1.data.MoviesData;

public class NewsAdapter extends BaseAdapter {

	private Context context;
	List<NewsPojo.Inner> list;

	private class ViewHolder {
		ImageView gridIcon;
		TextView movieTitle;
	}


	public NewsAdapter(Context context) {

		list=new ArrayList<NewsPojo.Inner>();
		this.context = context;


	}

	public void refresh(List<NewsPojo.Inner> list){

		if(list!=null){
			this.list=list;
			notifyDataSetChanged();
		}

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		NewsPojo.Inner moviesData=list.get(position);

		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.adapter_news,  parent,false);
			holder.gridIcon = (ImageView) view.findViewById(R.id.grid_icon);
			holder.movieTitle = (TextView) view.findViewById(R.id.movieTitle);

			view.setTag(holder);

		} else
			holder = (ViewHolder) view.getTag();

//		Logger.log("poster path::" + Constants.POSTERMAIN_PATH + moviesData.getPoster_path());

		holder.movieTitle.setText(moviesData.getTitle());
		Picasso.with(context).load(moviesData.getUrlToImage())
				.placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holder.gridIcon);


//		Picasso.with(context).load("http://i.imgur.com/DvpvklR.png")
//				.placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holder.gridIcon);


		return view;
	}
}
