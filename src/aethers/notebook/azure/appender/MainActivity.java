package aethers.notebook.azure.appender;

import aethers.notebook.core.AethersNotebook;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

public class MainActivity
extends Activity 
{
    private final ServiceConnection loggerConnection = new ServiceConnection()
    {
        @Override
        public void onServiceDisconnected(ComponentName name) 
        {
            aethersNotebook = null;
            installButton.setVisibility(View.GONE);
            uninstallButton.setVisibility(View.GONE);
        }
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) 
        {
            aethersNotebook = AethersNotebook.Stub.asInterface(service);
            progressDialog.cancel();
            try
            {
                if(aethersNotebook.isManagedAppenderInstalled(AzureAppenderService.IDENTIFIER))
                {
                    installButton.setVisibility(View.GONE);
                    uninstallButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    installButton.setVisibility(View.VISIBLE);
                    uninstallButton.setVisibility(View.GONE);
                }
            }
            catch(RemoteException e)
            {
                throw new RuntimeException(e);
            }
        }
    };
    
    private AethersNotebook aethersNotebook;
    
    private ProgressDialog progressDialog;
    
    private Button installButton;
    
    private Button uninstallButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    protected void onResume() 
    {
        super.onResume();
        installButton = (Button)findViewById(R.id.install_button);
        uninstallButton = (Button)findViewById(R.id.uninstall_button);
        progressDialog = ProgressDialog.show(this, "", "Connecting to Aether's Notebook");
        if(!bindService(new Intent("aethers.notebook.action.ACTION_CONNECT"),
                loggerConnection, BIND_AUTO_CREATE))
            progressDialog.cancel();
    }

    @Override
    protected void onPause() 
    {
        super.onPause();
        unbindService(loggerConnection);
        aethersNotebook = null;
    }
    
    public void install(View v)
    {
        installButton.setVisibility(View.GONE);
        uninstallButton.setVisibility(View.GONE);
        startActivity(new Intent(this, OpenIdProviderListActivity.class));
    }
    
    public void uninstall(View v)
    {
        try 
        {
            aethersNotebook.deregisterManagedAppender(AzureAppenderService.IDENTIFIER);
            installButton.setVisibility(View.VISIBLE);
            uninstallButton.setVisibility(View.GONE);
        }
        catch(RemoteException e)
        {
            throw new RuntimeException(e);
        }
    }
}