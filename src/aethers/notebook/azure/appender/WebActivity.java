package aethers.notebook.azure.appender;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity
extends Activity
{
    public static final String LOGIN_URL = "login_url";
    
    private WebView webview;
    
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        webview = new WebView(this);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if(url.toLowerCase().startsWith("http://aethersnotebook.cloudapp.net/API/AuthenticateOpenID".toLowerCase()) 
                        && url.toLowerCase().contains("dnoa.userSuppliedIdentifier".toLowerCase()))
                {
                    Intent i = new Intent(WebActivity.this, LoginResultActivity.class);
                    i.putExtra(LOGIN_URL, url);
                    startActivity(i);
                    finish();
                    return true;
                }
                
                return false;
            }
            
            public void onPageFinished(WebView view, String url)
            {
                progressDialog.cancel();
            }
        });
        setContentView(webview);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        progressDialog = ProgressDialog.show(this, "", "Please wait whilst we contact your Open ID provider");
        String openID = getIntent().getStringExtra(OpenIdProviderListActivity.OPEN_ID_LOGIN);
        webview.postUrl("http://aethersnotebook.cloudapp.net/API/AuthenticateOpenID",
                EncodingUtils.getAsciiBytes("OpenID=" + Uri.encode(openID)));
    }    
}
