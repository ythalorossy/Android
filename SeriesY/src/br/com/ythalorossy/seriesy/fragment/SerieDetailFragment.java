package br.com.ythalorossy.seriesy.fragment;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.activity.OnNavigate;
import br.com.ythalorossy.seriesy.activity.SerieDetailActivity;
import br.com.ythalorossy.seriesy.activity.SerieFormActivity;
import br.com.ythalorossy.seriesy.activity.SerieListActivity;
import br.com.ythalorossy.seriesy.model.Serie;
import br.com.ythalorossy.seriesy.model.repository.Repository;
import br.com.ythalorossy.seriesy.model.repository.SerieRepository;
import br.com.ythalorossy.seriesy.utils.FileUtil;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

/**
 * A fragment representing a single Serie detail screen. This fragment is either
 * contained in a {@link SerieListActivity} in two-pane mode (on tablets) or a
 * {@link SerieDetailActivity} on handsets.
 */
public class SerieDetailFragment extends Fragment implements OnClickListener {

	private OnNavigate onNavigate;
	
	private Serie serie;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SerieDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Serie.SerieContract.COLUMN_ID)) {
			
			SerieRepository repository = SerieRepository.getInstance(getActivity());
			
			serie = repository.findById(Long.valueOf(getArguments().getString(Serie.SerieContract.COLUMN_ID)));
		}
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onNavigate = (OnNavigate) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_serie_detail, container, false);

		if (serie != null) {
			
			((Button) rootView.findViewById(R.id.btn_edit)).setOnClickListener(this);
			((Button) rootView.findViewById(R.id.btn_delete)).setOnClickListener(this);
			
			((TextView) rootView.findViewById(R.id.title)).setText(serie.getTitle());
			((TextView) rootView.findViewById(R.id.gender)).setText(serie.getGender().getName());
			((TextView) rootView.findViewById(R.id.synopsis)).setText(serie.getSynopsis());
			
			if (serie.getImageName() != null) {
				
				File root = FileUtil.getRootDirectory();
				File imageFile = FileUtil.getFile(root, serie.getImageName(), FileUtil.IMG_EXTENSION);
				
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				
				View container_image_data = rootView.findViewById(R.id.container_image_data);
				
				((ImageView) container_image_data.findViewById(R.id.image)).setImageBitmap(bitmap);
			}
			
		}

		return rootView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_edit:	
				Bundle arguments = new Bundle();
				arguments.putString(Serie.SerieContract.COLUMN_ID, String.valueOf(serie.getId()));
				arguments.putBoolean(SeriesYUtil.IS_UPDATE, true);
				onNavigate.navigateTo(SerieFormActivity.class, arguments);
				break;
			case 
				R.id.btn_delete:
					
					Repository<Serie> repository = SerieRepository.getInstance(getActivity());
					repository.delete(serie);
					
					onNavigate.navigateTo(SerieListActivity.class, null);
					
				break;
		}

		
	}
}
