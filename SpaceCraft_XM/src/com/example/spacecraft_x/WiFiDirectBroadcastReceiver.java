package com.example.spacecraft_x;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.provider.Settings;
import android.widget.Toast;


public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

	private WifiP2pDeviceList peers;
    private WifiP2pManager mManager;
    private Channel mChannel;
    private MultiPlayerInicio mActivity;
    private PeerListListener myPeerListListener;
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
    		MultiPlayerInicio activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
        	int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            	//mActivity.aux.setText("Wi-Fi Ligado");
            	Toast.makeText(mActivity,"Wifi ligado e P2P Acessivel", Toast.LENGTH_LONG).show();
            } else {
                // Wi-Fi P2P is not enabled
            	final WifiManager wifiManager = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
            	if(wifiManager.isWifiEnabled())
            	{Toast.makeText(mActivity,"Wifi ligado mas conexão P2P indisponivel", Toast.LENGTH_LONG).show();
            		//mActivity.aux.setText("Aguardando Por Outro Jogador...");
            	}
            	else{
            		Toast.makeText(mActivity,"DesLigado", Toast.LENGTH_LONG).show();
            		mActivity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            	}
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
        	if (mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
                myPeerListListener.onPeersAvailable(peers);
                
                
            }
        	else
        	{
                mActivity.aux.setText("Aguardando Por Outro Jogador...");
        	}
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
}
