package com.skocur.pkpseatfinder;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CompartmentVH extends RecyclerView.ViewHolder {

    private ViewGroup mViewGroup;
    private TextView mSeatsRange;
    private TextView mEmptySeats;
    private Handler mHandler;

    private MutableLiveData<Integer> _data = new MutableLiveData<>();
    private LiveData<Integer> mData = _data;

    public CompartmentVH(@NonNull View itemView, LifecycleOwner owner) {
        super(itemView);

        mViewGroup = itemView.findViewById(R.id.item_container);
        mSeatsRange = itemView.findViewById(R.id.item_compartment_name);
        mEmptySeats = itemView.findViewById(R.id.item_compartment_empty_seats);

        mHandler = new Handler(itemView.getContext().getMainLooper());

        final View dot = itemView.findViewById(R.id.dot);

        mData.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mEmptySeats.setText("" + integer);

                if (integer > 0) {
                    dot.setBackgroundResource(R.drawable.dot_green);
                } else {
                    dot.setBackgroundResource(R.drawable.dot_red);
                }
                Log.e(">>>>>>", "" +integer);
            }
        });
    }

    public void setName(String text) {
        mSeatsRange.setText(text);
    }

    public void setEmptySeatsFor(String seatsGroupId) throws IOException {
        if (seatsGroupId.equals("1")) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        downloadAndDisplay(this);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            AsyncTask.execute(r);
        }
    }

    private void downloadAndDisplay(final Runnable r) {
        try {
            final URL url = new URL("https://find-seats.tomaszmularczyk.now.sh/api/seats");

            InputStreamReader reader = new InputStreamReader(url.openStream());
            final SeatsGroup dto = new Gson().fromJson(reader, SeatsGroup.class);

            _data.postValue(dto.emptySeats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runOnUiThread(Runnable r) {
        mHandler.post(r);
    }

    class SeatsGroup {
        int seatsTaken;
        int emptySeats;
    }
}
