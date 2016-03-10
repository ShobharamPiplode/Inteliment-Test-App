package com.intedemoapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.intedemoapp.R;
import com.intedemoapp.controller.DataLoader;
import com.intedemoapp.model.SampleData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ScenarioTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScenarioTwoFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<SampleData>>
{
    private ProgressDialog progressDialog ;
    private View containerView;

    public ScenarioTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScenarioTwoFragment.
     */
    public static ScenarioTwoFragment newInstance() {
        ScenarioTwoFragment fragment = new ScenarioTwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setIndeterminate(true);

        containerView = inflater.inflate(R.layout.fragment_scenario_second, container, false);
        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(progressDialog!=null)
            progressDialog.show();

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<List<SampleData>> onCreateLoader(int arg0, Bundle arg1)
    {
        return new DataLoader(getActivity(), "http://express-it.optusnet.com.au/sample.json");
    }

    @Override
    public void onLoadFinished(Loader<List<SampleData>> arg0, final List<SampleData> data)
    {
        if(progressDialog!=null)
            progressDialog.dismiss();

        if(data!=null)
        {
            ArrayList<String> names = new ArrayList<>();
            for(SampleData item:data)
            {
                names.add(item.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            final Spinner spinner= (Spinner) containerView.findViewById(R.id.spinner);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    try {
                        SampleData item = data.get(position);
                        if (item != null) {
                            String textCar = item.getFromcentral().getCar();
                            TextView textViewCar = (TextView) containerView.findViewById(R.id.textViewCar);

                            if (textCar != null && !textCar.isEmpty()) {
                                textViewCar.setVisibility(View.VISIBLE);
                                textViewCar.setText("Car: "+textCar);
                            } else {
                                textViewCar.setVisibility(View.GONE);
                            }

                            String textTrain = item.getFromcentral().getTrain();
                            TextView textViewTrain = (TextView) containerView.findViewById(R.id.textViewTrain);
                            if (textTrain != null && !textTrain.isEmpty()) {
                                textViewTrain.setVisibility(View.VISIBLE);
                                textViewTrain.setText("Traine: "+textTrain);
                            } else {
                                textViewTrain.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button buttonNavigate= (Button) containerView.findViewById(R.id.buttonNavigate);
            buttonNavigate.setVisibility(View.VISIBLE);

            buttonNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int selectedIndex = spinner.getSelectedItemPosition();
                    SampleData item = data.get(selectedIndex);

                    double lat = item.getLocation().getLatitude();
                    double lon = item.getLocation().getLongitude();
                    String UriGeo = "geo:"+lat+","+lon;

                    Uri gmmIntentUri = Uri.parse(UriGeo);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<List<SampleData>> arg0)
    {

    }
}
