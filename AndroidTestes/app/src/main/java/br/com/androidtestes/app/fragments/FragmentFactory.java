package br.com.androidtestes.app.fragments;

import android.os.Bundle;

public class FragmentFactory {

    public static AbstractFragment get(Class clazz, int sectionNumber){

        AbstractFragment fragment = null;

        try {
            fragment = (AbstractFragment) clazz.newInstance();
            Bundle args = new Bundle();
            args.putInt(AbstractFragment.ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fragment;
    }
}
