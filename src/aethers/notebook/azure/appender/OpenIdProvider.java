package aethers.notebook.azure.appender;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="type")
public abstract class OpenIdProvider
{
    protected String name;
    
    protected String userComponent;
    
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }
    
    public String getUserComponent() { return userComponent; }
    
    public void setUserComponent(String userComponent) 
            { this.userComponent = userComponent; }
    
    public abstract String getOpenIdentifier();
}
