package aethers.notebook.azure.appender;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class LoginResultActivity
extends Activity
{    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        String url = getIntent().getStringExtra(WebActivity.LOGIN_URL);
        Toast.makeText(this, url, Toast.LENGTH_LONG);
        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        try
        {
            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            {
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Login Failed: " + response.getStatusLine().getReasonPhrase())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) 
                            {
                                LoginResultActivity.this.finish();
                            }
                        });
                b.create().show();
            }
            else
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                ObjectMapper m = new ObjectMapper();
                JsonNode n = m.readTree(new ByteArrayInputStream(out.toByteArray()));
                Configuration config = new Configuration(this);
                config.setOpenID(n.get("openID").getValueAsText());
                config.setSecret(n.get("secret").getValueAsText());
                finish();
            }       
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
