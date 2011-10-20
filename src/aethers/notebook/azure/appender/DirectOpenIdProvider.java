package aethers.notebook.azure.appender;

public class DirectOpenIdProvider 
extends OpenIdProvider
{
    private String url;
    
    public String getUrl() { return url; }
    
    public void setUrl(String url) { this.url = url; }    

    @Override
    public String getOpenIdentifier() 
    {
        return url;
    }
}
