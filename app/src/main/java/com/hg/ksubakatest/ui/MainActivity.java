package com.hg.ksubakatest.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.hg.ksubakatest.R;
import com.hg.ksubakatest.model.FilmList;
import com.hg.ksubakatest.webservice.FilmService;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    FilmService mService;


    public final String TAG = getClass().getSimpleName();
    private ProgressDialog mDialog;
    private EditText mFieldMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFieldMovieName = (EditText) findViewById(R.id.field_movie_name);

        Observable<FilmList> filmListObservable = mService.getFilmList("Batman", "json", "movie");

        filmListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FilmList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                        e.getStackTrace();
                    }

                    @Override
                    public void onNext(FilmList filmList) {

                        Log.i(TAG, "size of list: " + filmList.getSearch().size());
                    }
                });
    }
}
