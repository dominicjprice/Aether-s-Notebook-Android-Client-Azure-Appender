package aethers.notebook.azure.appender;

public class IndirectOpenIdProvider
extends OpenIdProvider
{
    private String prefix;
    
    private String suffix;
    
    public String getPrefix() { return prefix; }

    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getSuffix() { return suffix; }

    public void setSuffix(String suffix) { this.suffix = suffix; }

    @Override
    public String getOpenIdentifier() 
    {
        return prefix + userComponent + suffix;
    }
}
