package br.com.ythalorossy.seriesy.adapters;

import java.util.List;

import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.model.Gender;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class GenderSpinnerAdapter implements SpinnerAdapter {

	private Context context;
	private List<Gender> genders;

	public GenderSpinnerAdapter(Context context, List<Gender> genders) {
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

	public boolean hasStableIds() {
		return false;
	}

	public int getItemViewType(int position) {
		return 0;
	}

	public int getViewTypeCount() {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}

	public void registerDataSetObserver(DataSetObserver observer) {

	}

	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.gender_item_list, null);

		View container = view.findViewById(R.id.item_container);

		((TextView) container.findViewById(R.id.name)).setText(genders.get(position).getName());

		return view;
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.gender_item_list, null);

		View container = view.findViewById(R.id.item_container);

		((TextView) container.findViewById(R.id.name)).setText(genders.get(position).getName());
		
		return view;
	}

}
