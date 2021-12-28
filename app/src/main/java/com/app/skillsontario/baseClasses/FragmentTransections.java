package com.app.skillsontario.baseClasses;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class FragmentTransections {

    public static void replaceFragmnet(Context context, Fragment fragment, String tag, int layout, Boolean isAddFrag) {

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        if (!isAddFrag)
            ft.add(layout, fragment, tag);
        else
            ft.replace(layout, fragment, tag);

        ft.addToBackStack(null);

        if (!isAddFrag) {
            while (fragmentManager.getBackStackEntryCount() > 0) {
                ((AppCompatActivity) context).getSupportFragmentManager().popBackStackImmediate();
            }
        }
        //Log.e("FragmentCount", "" + ((AppCompatActivity) context).getSupportFragmentManager().getBackStackEntryCount() + " Tag " + tag);
        try {
            ft.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


 /*   public static String getCurrentFragmentTag(Activity context, int id) {
        String tag = "";

        tag = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragment_container).getTag();

        return tag;
    }*/

    public static Fragment getCurrentFragment(Activity context, int id) {
        Fragment fragment;

        fragment = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(id);

        return fragment;
    }

    public static Fragment getCurrentFragment(Activity context, String id) {
        Fragment fragment;

        fragment = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(id);

        return fragment;
    }

    public static void removeAllFragments(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
    }

    public static void removePreviousFragment(Context context, int rremoveFragNo) {
        if (((AppCompatActivity) context).getSupportFragmentManager().getBackStackEntryCount() > 1) {
            int backStackCount = ((AppCompatActivity) context).getSupportFragmentManager().getBackStackEntryCount() - 2;
            for (int i = backStackCount; i > backStackCount - rremoveFragNo; i--) {
                ((AppCompatActivity) context).getSupportFragmentManager().popBackStack();
            }
        }
    }
}