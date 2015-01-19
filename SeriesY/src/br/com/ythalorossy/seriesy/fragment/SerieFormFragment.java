package br.com.ythalorossy.seriesy.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.activity.HomeActivity;
import br.com.ythalorossy.seriesy.activity.OnNavigate;
import br.com.ythalorossy.seriesy.activity.SerieListActivity;
import br.com.ythalorossy.seriesy.adapters.GenderSpinnerAdapter;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.Serie;
import br.com.ythalorossy.seriesy.model.repository.GenderRepository;
import br.com.ythalorossy.seriesy.model.repository.Repository;
import br.com.ythalorossy.seriesy.model.repository.SerieRepository;
import br.com.ythalorossy.seriesy.utils.FileUtil;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class SerieFormFragment extends Fragment implements OnItemSelectedListener {

	private OnNavigate onNavigate;

	private Serie serie;
	private boolean isUpdate = false;
	private boolean changedImage = false;

	private List<Gender> genders;

	public SerieFormFragment() {
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		
		if (bundle != null && bundle.getBoolean(SeriesYUtil.IS_UPDATE, false)) {

			isUpdate = bundle.getBoolean(SeriesYUtil.IS_UPDATE);

			SerieRepository repository = SerieRepository.getInstance(getActivity());

			this.serie = repository.findById(Long.valueOf(bundle.getString(Serie.SerieContract.COLUMN_ID)));

		} else {

			this.serie = new Serie();
		}
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		this.onNavigate = (OnNavigate) activity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_serie_form, container, false);

		ImageView image = (ImageView) rootView.findViewById(R.id.image);
		image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(intent, FileUtil.IMG_RESULT_LOAD);
			}
		});
		image.setDrawingCacheEnabled(true);

		Spinner gender = (Spinner) rootView.findViewById(R.id.spinnerGender);
		gender.setOnItemSelectedListener(this);
		
		genders = GenderRepository.getInstance(getActivity()).findAll();
		GenderSpinnerAdapter adapter = new GenderSpinnerAdapter(getActivity(), genders);
		gender.setAdapter(adapter);
		
		((Button) rootView.findViewById(R.id.btnSave))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						save(rootView);
					}
				});
		((Button) rootView.findViewById(R.id.btnCancel))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						onNavigate.navigateTo(SerieListActivity.class, null);
					}
				});

		if (isUpdate) {

			((EditText) rootView.findViewById(R.id.editTextTitle)).setText(this.serie.getTitle());
//			((EditText) rootView.findViewById(R.id.editTextGender)).setText(this.serie.getGender().getName());
			((EditText) rootView.findViewById(R.id.editTextSynopsis)).setText(this.serie.getSynopsis());
			
			if (this.serie.getImageName() != null) {
				
				File root = FileUtil.getRootDirectory();
				File imageFile = FileUtil.getFile(root, this.serie.getImageName(), FileUtil.IMG_EXTENSION);
				
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				((ImageView) rootView.findViewById(R.id.image)).setImageBitmap(bitmap);
			}
		}

		return rootView;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == FileUtil.IMG_RESULT_LOAD
				&& resultCode == Activity.RESULT_OK 
				&& data != null) {

			String picturePath = FileUtil.getImagePath(getActivity(), data);

			ImageView image = (ImageView) getView().findViewById(R.id.image);
			image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			this.changedImage = true;
		}
	}

	public void save(View view) {

		EditText title = (EditText) view.findViewById(R.id.editTextTitle);
//		EditText gender = (EditText) view.findViewById(R.id.editTextGender);
		EditText synopsis = (EditText) view.findViewById(R.id.editTextSynopsis);

		this.serie.setTitle(title.getText().toString());
//		this.serie.setGender(gender.getText().toString());
		this.serie.setSynopsis(synopsis.getText().toString());

		Repository<Serie> repository = SerieRepository.getInstance(getActivity());

		if (isUpdate) {

			this.serie = repository.update(this.serie);

		} else {

			this.serie = repository.save(this.serie);
		}

		if (changedImage) {
			
			this.serie.setImageName(FileUtil.IMG_NAME_PREFIX + this.serie.getId());

			try {
				saveImage(view, this.serie);
				
				SerieRepository.getInstance(getActivity()).update(serie);
				
			} catch (Exception e) {

				Toast.makeText(getActivity(), "Error saving the image", Toast.LENGTH_SHORT).show();
			}
		}
		
		this.onNavigate.navigateTo(HomeActivity.class, null);
	}

	private void saveImage(View view, Serie serie) throws Exception {

		ImageView image = (ImageView) view.findViewById(R.id.image);
		Bitmap bitmap = image.getDrawingCache();
		if (bitmap != null) {

			File root = FileUtil.getRootDirectory();

			root.mkdirs();

			File imageFile = new File(root, serie.getImageName() + "." + FileUtil.IMG_EXTENSION);
			
			OutputStream outputStream = new FileOutputStream(imageFile);

			bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

			outputStream.flush();
			outputStream.close();
		}

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		Gender gender = this.genders.get(position);
		
		this.serie.setGender(gender);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		
	}

}
