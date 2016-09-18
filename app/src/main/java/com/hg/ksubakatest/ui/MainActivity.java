package com.hg.ksubakatest.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hg.ksubakatest.MyApplication;
import com.hg.ksubakatest.R;
import com.hg.ksubakatest.model.FilmList;
import com.hg.ksubakatest.model.Search;
import com.hg.ksubakatest.webservice.FilmService;
import com.jakewharton.rxbinding.widget.RxTextView;

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
    private ProgressDialog progressDialog;
    private EditText mFieldMovieName;
    private RecyclerView mRecyclerView;
    private FilmListAdapter mFilmListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getmApiComponent().inject(this);

        mFieldMovieName = (EditText) findViewById(R.id.field_movie_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_film_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        showProgressDialog("", "Loading...");
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

                        hideProgressDialog();
                        Log.i(TAG, "size of list: " + filmList.getSearch().size());

                        mFilmListAdapter = new FilmListAdapter(MainActivity.this, filmList, R.layout.row_film_list);
                        mRecyclerView.setAdapter(mFilmListAdapter);

                        mFilmListAdapter.setOnItemClickListener(new FilmListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Toast.makeText(MainActivity.this, "you clicked: " + position, Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });


    }

    protected void showProgressDialog(String title, String description) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle(title);
        progressDialog.setMessage(description);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
