package suhaas.charlton.bookidentifier;

import java.io.IOException;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    String sd,sp=" ";
    int l=1,k=1,j=1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
    protected void onResume() {
        super.onResume();
        enableNfcWrite();
    }
	
     
    @Override
    protected void onPause() {
        super.onPause();
        disableNfcWrite();
    }
     
    private PendingIntent getPendingIntent() {
    	  return PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    	}
    
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi")
    private void enableNfcWrite(){
    	  NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    	  IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
    	  IntentFilter[] writeTagFilters = new IntentFilter[] { tagDetected };
    	  adapter.enableForegroundDispatch(this, getPendingIntent(), writeTagFilters, null);
    	}

    	@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1) 
    	private void disableNfcWrite(){
    	  NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    	  adapter.disableForegroundDispatch(this);
    	}
   
    	
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1) @SuppressLint("NewApi") @Override
    protected void onNewIntent(Intent intent) {
    	if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
    	    Tag discoveredTag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    	    
    	    Ndef ndef= Ndef.get(discoveredTag);
    	    Log.d(TAG, ndef.getType().toString());
    	    
    	    Log.d(TAG, ndef.isWritable() ?"true":"false");
    	   
    	  
		try{
    		   ndef.connect();
    		   NdefMessage ndefMessage=ndef.getNdefMessage();
    		   Log.d(TAG, ndefMessage.toString());
    		   Log.d(TAG, Integer.toString(ndef.getMaxSize()));
    		   NdefRecord record= ndefMessage.getRecords()[0];
    		   byte[] payload =record.getPayload();
    		   Log.d(TAG, new String(payload));
    		   sd = new String(payload);
    		   setContentView(R.layout.read);
    		   TextView e=(TextView)findViewById(R.id.tv);
    		  
    		   if(sd.equals(sp)){
    			   e.setText("Tag is Empty");
    		   }
    		   else{
    		   e.setText(sd);
    		   }
    		   Toast.makeText(this, "NFC Tag Read Successful", Toast.LENGTH_SHORT).show();
    		   ndef.close();
    	   }catch(IOException e)
    	   {
    		   Log.e(TAG, "IOException");
    	   }
    	   catch(FormatException e){
    		   
    	   }
    	  }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent sread1=new Intent("suhaas.charlton.bookidentifier.ABOUT");
    		startActivity(sread1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
