package com.ezz.bytourism1;


import java.io.File;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MySimpleAdapter extends SimpleAdapter {
	private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private List<? extends Map<String, ?>> mData;
    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;
	public MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	 /**
     * @see android.widget.Adapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }
    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);

            final int[] to = mTo;
            final int count = to.length;
            final View[] holder = new View[count];

            for (int i = 0; i < count; i++) {
                holder[i] = v.findViewById(to[i]);
            }

            v.setTag(holder);
        } else {
            v = convertView;
        }
        bindView(position, v);

        return v;
    }
    
    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = holder[i];       
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }
                if (!bound) {
                	if(v instanceof RatingBar){
                    	float score = Float.parseFloat(data.toString());  //±¸×¢2
                    //	Log.i("ratingBar", score+" ");
                    	((RatingBar)v).setRating(score);
                    }else  if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " + data.getClass());
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        //setViewText((TextView) v, text);
                        ((TextView) v).setText(text);
                    } else if (v instanceof ImageView) {  
                    	/*if(data instanceof String){
						//	File file = new File(data.toString());
							File file = new File("E://image/xiamen_three.jpg");
                    		if(file.exists()){
								Bitmap bm = BitmapFactory.decodeFile("/image/xiamen_three.jpg");
								((ImageView)v).setImageBitmap(bm);
							}
							else{
								file = new File("E://image//xiamen_three.jpg");
								
								Log.i("file.exists = ", ""+file.exists());
								}
                    	}*/
                    	if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);                            
                        } 
                    	else if(data instanceof byte[]) {      //±¸×¢1
								Bitmap bmp;
								byte[] image = (byte[])data;
								if(image.length!=0){
								bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
								((ImageView) v).setImageBitmap(bmp);
								}
							}
                        }
                    
                    else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
            }
   }
   
 
    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }
}