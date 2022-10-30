package edu.sungshin.iam1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Community extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        TabHost tabHost = getTabHost();

        TabSpec tabSpecRecipe = tabHost.newTabSpec("RECIPE").setIndicator("레시피");
        tabSpecRecipe.setContent(R.id.tabRecipe);
        tabHost.addTab(tabSpecRecipe);

        TabSpec tabSpecTogether = tabHost.newTabSpec("TOGETHER").setIndicator("공구 및 배달");
        tabSpecTogether.setContent(R.id.tabTogehter);
        tabHost.addTab(tabSpecTogether);

        TabSpec tabSpecInfo = tabHost.newTabSpec("INFO").setIndicator("자취 정보");
        tabSpecInfo.setContent(R.id.tabInfo);
        tabHost.addTab(tabSpecInfo);

        tabHost.setCurrentTab(0);
    }
}