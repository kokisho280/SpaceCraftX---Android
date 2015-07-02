package com.example.spacecraft_x;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

enum Ligacao{Blue,wifi};
public class MultiPlayerInicio extends Activity
{
	Ligacao myLigacao;
	Button wifi,bluetooth,cancelar,procurar;
	TextView aux;
	WifiP2pManager mManager;
	Channel mChannel;
	IntentFilter mIntentFilter;
	BroadcastReceiver mReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	    this.setContentView(R.layout.muiltinicio);
		aux = (TextView)findViewById(R.id.textViewMulti);
		wifi = (Button)findViewById(R.id.buttonWifi);
		cancelar = (Button)findViewById(R.id.buttonCancelarLigacao);
		bluetooth = (Button)findViewById(R.id.buttonBlue);
	    procurar = (Button)findViewById(R.id.buttonProcurar);
	    
	    procurar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(MultiPlayerInicio.this, "Discovery Initiated",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(MultiPlayerInicio.this, "Discovery Failed : " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
				
			}
		});
		cancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(myLigacao == Ligacao.wifi)
				{
					unregisterReceiver(mReceiver);
				}
				
			}
		});
	    wifi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myLigacao = Ligacao.wifi;
				mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
			    mChannel = mManager.initialize(MultiPlayerInicio.this, getMainLooper(), null);
			    mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, MultiPlayerInicio.this);
			    mIntentFilter = new IntentFilter();
			    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
			    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
			    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
			    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
			    registerReceiver(mReceiver, mIntentFilter);
			    sendBroadcast(new Intent().setAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION));
			}
		});
		
//	    mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
//	        @Override
//	        public void onSuccess() {
//	            //...
//	        }
//	        @Override
//	        public void onFailure(int reasonCode) {
//	            //...
//	        }
//	    });

	}
	@Override
	protected void onResume() {
	    super.onResume();
	    Music.play(this, R.raw.main_music);
	}
	/* unregister the broadcast receiver */
	@Override
	protected void onPause() {
	    super.onPause();
	    Music.stop(this);
	    unregisterReceiver(mReceiver);
	}
	
	

}

