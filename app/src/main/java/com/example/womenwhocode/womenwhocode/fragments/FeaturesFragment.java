package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.FeaturesAdapter;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class FeaturesFragment extends Fragment {
    FeaturesAdapter aFeatures;
    ArrayList<Feature> features;
    ListView lvFeatures;

    private OnFeatureItemClickListener listener;

    public interface OnFeatureItemClickListener {
        public void onFeatureClickListener(Feature feature);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        features = new ArrayList<>();
        aFeatures = new FeaturesAdapter(getActivity(), features);
        populateFeaturesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        lvFeatures = (ListView) view.findViewById(R.id.lvFeatures);
        lvFeatures.setAdapter(aFeatures);

        lvFeatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feature feature = aFeatures.getItem(position);
                listener.onFeatureClickListener(feature);
            }
        });


        return view;
    }

    void addAll(List<Feature> features) {
        aFeatures.addAll(features);
    }

    void populateFeaturesList() {
        ParseQuery<Feature> query = ParseQuery.getQuery(Feature.class);
        query.orderByAscending("title");
        query.findInBackground(new FindCallback<Feature>() {
            public void done(List<Feature> lFeatures, ParseException e) {
                if (e == null) {
                    aFeatures.clear();
                    addAll(lFeatures);
                    aFeatures.notifyDataSetChanged();
                } else {
                    Log.d("Message", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFeatureItemClickListener) {
            listener = (OnFeatureItemClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement FeaturesFragment.OnFeaureItemClickListener");
        }
    }
}