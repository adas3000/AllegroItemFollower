package com.example.observer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.observer.fragment.AddItemFragment
import com.example.observer.fragment.ItemListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val supportFragmentManager :FragmentManager= supportFragmentManager
        val fragmentTransition :FragmentTransaction= supportFragmentManager.beginTransaction()

        fragmentTransition.add(R.id.frameLayout_fragmentKeeper,ItemListFragment())
        fragmentTransition.commit()

        floatingActionButton3.setOnClickListener {
            val fab_fragmentTransition = supportFragmentManager.beginTransaction()
            fab_fragmentTransition.replace(R.id.frameLayout_fragmentKeeper,AddItemFragment())
            fab_fragmentTransition.addToBackStack(null)
            fab_fragmentTransition.commit()
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if(count==0){
            super.onBackPressed()
        }
        else{
            supportFragmentManager.popBackStack()
        }

    }


}
