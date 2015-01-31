
package com.prasad.viewpager;



import com.appFactory.SensU.AppDataStore;
import com.prasad.SensU.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class LayoutFour extends Fragment {


	public static Fragment newInstance(Context context) {
		LayoutFour f = new LayoutFour();	
		
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.layout_four, null);	
		 
		  final CheckBox c=(CheckBox)root.findViewById(R.id.checkBox1);
		Button b=(Button)root.findViewById(R.id.button1);
		  b.setOnClickListener(new View.OnClickListener() {

		        @Override
		        public void onClick(View v) {
		        	if(c.isChecked())
		             	new AppDataStore(getActivity()).setFirstUsed(true);
		            getActivity().finish();
		        }
		    });
		 
		  c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
			
			}
		});
		return root;
	}
	
}
