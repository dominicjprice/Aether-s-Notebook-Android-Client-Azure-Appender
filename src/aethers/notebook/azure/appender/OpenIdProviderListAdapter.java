package aethers.notebook.azure.appender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class OpenIdProviderListAdapter
implements ListAdapter
{
    private final Activity activity;
    
    private final List<OpenIdProvider> list;
    
    public OpenIdProviderListAdapter(Activity activity, List<OpenIdProvider> list)
    {
        this.activity = activity;
        ArrayList<OpenIdProvider> ordered = new ArrayList<OpenIdProvider>();
        ordered.addAll(list);
        Collections.sort(ordered, new Comparator<OpenIdProvider>()
        {
            @Override
            public int compare(OpenIdProvider a, OpenIdProvider b) 
            {
                return a.getName().compareTo(b.getName());
            }
        });
        this.list = ordered;        
    }
    
    @Override
    public int getCount() 
    {
        return list.size();
    }

    @Override
    public Object getItem(int position) 
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) 
    {
        return list.get(position).hashCode();
    }

    @Override
    public int getItemViewType(int position) 
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        OpenIdProvider p = list.get(position);
        TextView t = new TextView(activity);
        t.setText(p.getName());
        t.setTextSize(40f);
        return t;
    }

    @Override
    public int getViewTypeCount() 
    {
        return 1;
    }

    @Override
    public boolean hasStableIds() 
    {
        return true;
    }

    @Override
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) { }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) { }

    @Override
    public boolean areAllItemsEnabled() 
    {
        return true;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

}
