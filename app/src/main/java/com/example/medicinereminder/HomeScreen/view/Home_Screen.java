package com.example.medicinereminder.HomeScreen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.medicinereminder.HomeScreen.HomeFragment;
import com.example.medicinereminder.HomeScreen.MoreFragment;
import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_screen.view.MedicationsFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class Home_Screen extends AppCompatActivity {

    BubbleNavigationLinearView bNaviView;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initUI();
//        bNaviView.setBadgeValue(0,"" );
//        bNaviView.setBadgeValue(1,"" );
//        bNaviView.setBadgeValue(2,"" );

        //set home first screen....
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragmentcontainer,new HomeFragment());
        fragmentTransaction.commit();

        bNaviView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position == 0)
                {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.home_fragmentcontainer,new HomeFragment());
                    fragmentTransaction.commit();
                }
                else if(position == 1)
                {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.home_fragmentcontainer,new MedicationsFragment());
                    fragmentTransaction.commit();
                }
                else{

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.home_fragmentcontainer,new MoreFragment());
                    fragmentTransaction.commit();

                }
            }
        });


    }

    void initUI()
    {
        bNaviView = findViewById(R.id.home_navigationBar);
    }
}