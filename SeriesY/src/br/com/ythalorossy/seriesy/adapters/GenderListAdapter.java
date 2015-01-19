package br.com.ythalorossy.seriesy.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.model.Gender;

public class GenderListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Gender> genders;

	public GenderListAdapter(Context context, List<Gender> genders) {
		this.context = context;
		this.genders = genders;
	}

	public int getCount() {
		return genders.size();
	}

	public Object getItem(int position) {
		return genders.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.gender_item_list, null);
		
		View container = view.findViewById(R.id.item_container);
		
		TextView name = (TextView) container.findViewById(R.id.name);
		name.setText(genders.get(position).getName());

		return view;
	}

}
