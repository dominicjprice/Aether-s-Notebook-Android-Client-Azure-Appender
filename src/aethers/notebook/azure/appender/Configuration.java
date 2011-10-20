package aethers.notebook.azure.appender;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import aethers.notebook.core.ConfigurationTemplate;
import android.content.Context;
import android.os.Environment;

public class Configuration
extends ConfigurationTemplate
{   
    public static final String SHARED_PREFERENCES_NAME 
            = "aethers.notebook.azure.appender.Configuration";
    
    public static enum ConnectionType
    {
        WifiAnd3G("Wifi and 3G"),
        Wifi("Wifi Only"),
        Manual("Manual");
        
        public final String friendlyName;
        
        private ConnectionType(String friendlyName)
        {
            this.friendlyName = friendlyName;
        }
    }
    
    private static final String defaultPathPrefix = 
            Environment.getExternalStorageDirectory().getAbsolutePath();
    
    public Configuration(Context context) 
    {
        super(context, SHARED_PREFERENCES_NAME);
    }
    
    public ConnectionType getConnectionType()
    {
        return ConnectionType.valueOf(getString(
                R.string.Preferences_connectionType,
                R.string.Preferences_connectionType_default));
    }
    
    public void setConnectionType(ConnectionType connectionType)
    {
        setString(
                R.string.Preferences_connectionType,
                connectionType.toString());
    }
    
    public URL getUrl()
    {
        try
        {
            return new URL(getString(
                    R.string.Preferences_url,
                    R.string.Preferences_url_default));
        }
        catch(MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public void setUrl(URL url)
    {
        setString(R.string.Preferences_url, url.toExternalForm());
    }
    
    public long getMaxFileSize()
    {
        return Long.parseLong(getString(
                R.string.Preferences_maxFileSize,
                R.string.Preferences_maxFileSize_default));
    }
    
    public void setMaxFileSize(long maxFileSize)
    {
        setString(R.string.Preferences_maxFileSize, String.valueOf(maxFileSize));
    }
    
    public File getLogDirectory()
    {
        String defaultDir = getContext().getString(
                R.string.Preferences_logDirectory_default);
        String configuredDir = getString(
                R.string.Preferences_logDirectory,
                R.string.Preferences_logDirectory_default);
        if(defaultDir.equals(configuredDir))
            return new File(defaultPathPrefix + configuredDir);
        return new File(configuredDir);
    }
    
    public void setLogDirectory(File logDirectory)
    {
        setString(
                R.string.Preferences_logDirectory,
                logDirectory.getAbsolutePath());
    }
    
    public boolean isDeleteUploadedFiles()
    {
        return getBoolean(
                R.string.Preferences_deleteUploadedFiles,
                R.string.Preferences_logDirectory_default);
    }
    
    public void setDeleteUploadedFiles(boolean deleteUploadedFiles)
    {
        setBoolean(
                R.string.Preferences_deleteUploadedFiles, 
                deleteUploadedFiles);
    }
    
    public int getMaxFiles()
    {
        
        String s = getString(
                R.string.Preferences_maxFiles,
                R.string.Preferences_maxFiles_default);
        return s == null || s.equals("")
                ? -1
                : Integer.parseInt(s);
    }
    
    public void setMaxFiles(int maxFiles)
    {
        setString(R.string.Preferences_maxFiles,
                maxFiles == -1
                        ? ""
                        : String.valueOf(maxFiles));
    }
    
    public String getOpenID()
    {
        return getString(R.string.Preferences_openID,
                R.string.Preferences_openID_default);
    }
    
    public void setOpenID(String openID)
    {
        setString(R.string.Preferences_openID, openID);
    }
    
    public String getSecret()
    {
        return getString(R.string.Preferences_secret,
                R.string.Preferences_secret_default);
    }
    
    public void setSecret(String secret)
    {
        setString(R.string.Preferences_secret, secret);
    }
}
