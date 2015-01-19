package br.com.ythalorossy.seriesy.adapters;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.model.Serie;
import br.com.ythalorossy.seriesy.utils.FileUtil;

public class SerieListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Serie> series;

	public SerieListAdapter(Context context, List<Serie> series) {
		this.context = context;
		this.series = series;
	}

	public int getCount() {
		return series.size();
	}

	public Object getItem(int position) {
		return series.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.serie_item_list, null);
		
		View container = view.findViewById(R.id.item_container);
		
		ImageView image = (ImageView) container.findViewById(R.id.image);
		image.setImageResource(R.drawable.draw);
		
		if (series.get(position).getImageName() != null) {
			
			File root = FileUtil.getRootDirectory();
			File imageFile = FileUtil.getFile(root, series.get(position).getImageName(), FileUtil.IMG_EXTENSION);
			
			Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			image.setImageBitmap(bitmap);
		}
		
		
		TextView title = (TextView) container.findViewById(R.id.title);
		title.setText(series.get(position).getTitle());

		return view;
	}

}
