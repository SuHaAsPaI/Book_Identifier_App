package suhaas.charlton.bookidentifier;

import android.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	

}
