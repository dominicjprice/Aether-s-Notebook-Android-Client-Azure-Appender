package aethers.notebook.azure.appender;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

public class OpenIdProviderListActivity 
extends ListActivity
{
    public static final String OPEN_ID_LOGIN = "open_id_login";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ObjectMapper m = new ObjectMapper();
        try
        {
            List<OpenIdProvider> providers = 
                    m.readValue(
                            getString(R.string.configuration_openid_providers),
                            new TypeReference<List<OpenIdProvider>>() { });
            final OpenIdProviderListAdapter a = new OpenIdProviderListAdapter(this, providers); 
            setListAdapter(a);
            this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(
                        AdapterView<?> parent,
                        View view,
                        int position,
                        long id) 
                {
                    final OpenIdProvider p = (OpenIdProvider)a.getItem(position);
                    if(p instanceof IndirectOpenIdProvider)
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(OpenIdProviderListActivity.this);
                        alert.setTitle("Log In");
                        alert.setMessage("Please enter your Open ID");
                        final EditText input = new EditText(OpenIdProviderListActivity.this);
                        alert.setView(input);
                        alert.setPositiveButton("Log In", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) 
                            {
                                IndirectOpenIdProvider ip = (IndirectOpenIdProvider)p;
                                ip.setUserComponent(input.getText().toString());
                                Intent i = new Intent();
                                i.setClass(OpenIdProviderListActivity.this, WebActivity.class);
                                i.putExtra(OPEN_ID_LOGIN, ip.getOpenIdentifier());
                                startActivity(i);
                                finish();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        alert.show();
                    }
                    else
                    {
                        Intent i = new Intent();
                        i.setClass(OpenIdProviderListActivity.this, WebActivity.class);
                        i.putExtra(OPEN_ID_LOGIN, p.getOpenIdentifier());
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }        
    }
}
