package br.com.ythalorossy.seriesy.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class YesNoDialogFragment extends DialogFragment {

	public static YesNoDialogFragment newInstance(int title){
		
		YesNoDialogFragment yesNoDialogFragment = new YesNoDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		
		yesNoDialogFragment.setArguments(args);
		
		return yesNoDialogFragment;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(title)
			.setPositiveButton("Yes", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					onActivityResult(1, Activity.RESULT_OK, null);
//					getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
				}
			})
			.setNegativeButton("No", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			})
			
		.create();
	}
	
}
