package com.mobileapp.localnews;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobileapp.localnews.adapter.NewsAdapter;
import com.mobileapp.localnews.apiinterface.ApiInterface;
import com.mobileapp.localnews.data.NewsPojo;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    GridView gridView;

    //Creating a rest adapter
    RestAdapter reviewsAdapter;
    //Creating an object of our api interface
    ApiInterface apiInterface;

    NewsAdapter adapter;
    int id;

    private ProgressDialog progDialog;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = (GridView) view.findViewById(R.id.movies_grid);
        gridView.setOnItemClickListener(this);

        adapter = new NewsAdapter(getActivity());
        gridView.setAdapter(adapter);

        reviewsAdapter = new RestAdapter.Builder().setEndpoint(Constants.NEWS_URL).build();
        apiInterface = reviewsAdapter.create(ApiInterface.class);

        Bundle bundle = getArguments();
        id = bundle.getInt("key");

//        loadNews();
        getLoaderManager().initLoader(0, null, this);

    }


    private void loadNewsFromService(){

        new MyAsyncTask().execute();

    }

    @Override
    public void onResume() {
        super.onResume();

        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }


    private void showTopNews() {

        //Defining the method
        apiInterface.getTopNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }

    private void showSportsNews() {

        //Defining the method
        apiInterface.getSportsNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }


    private void showBusinessNews() {

        //Defining the method
        apiInterface.getBusinessNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }

    private void showEnerainmentNews() {

        //Defining the method
        apiInterface.getEnertainmentNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }

    private void showLifestyleNews() {

        //Defining the method
        apiInterface.getLifestyleNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }

    private void showTechNews() {

        //Defining the method
        apiInterface.getTechNews(new Callback<NewsPojo>() {
            @Override
            public void success(NewsPojo data, Response response) {
                showNews(data);
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMsg(Constants.ERROR);
            }
        });

    }

    public void showNews(NewsPojo data) {
        dismissProgressDialog();

       String formatedData= new Gson().toJson(data);

        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_CODE, id);
        values.put(MyDatabaseHelper.KEY_NAME,formatedData);
        // insert a record
        getActivity().getContentResolver().insert(MyContentProvider.NEWS_URI, values);

        List<NewsPojo.Inner> list = data.getArticles();
        adapter.refresh(list);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        NewsPojo.Inner data = (NewsPojo.Inner) parent.getItemAtPosition(position);

        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("movieData", data);
        startActivity(intent);

    }


    public void showToastMsg(String msg) {
        dismissProgressDialog();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MyDatabaseHelper.KEY_ROWID,
                MyDatabaseHelper.KEY_CODE,
                MyDatabaseHelper.KEY_NAME,
        };
        Uri uri = Uri.parse(MyContentProvider.NEWS_URI + "/" + id);
        CursorLoader cursorLoader = new CursorLoader(getActivity(), uri, projection, null, null, null);


        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String myCode = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_CODE));
                String myName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_NAME));
                NewsPojo data = new Gson().fromJson(myName, NewsPojo.class);
                adapter.refresh(data.getArticles());
            } else {
                loadNewsFromService();
            }
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.refresh(null);

    }

    private void showProgressDialog() {
        progDialog = new ProgressDialog(getActivity());
        progDialog.setMessage("Loading Please Wait...");
        progDialog.setIndeterminate(true);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    private void dismissProgressDialog() {
        if ((progDialog != null) && progDialog.isShowing()) {
            try {
                progDialog.dismiss();
                progDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        public MyAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (id == R.id.id_top) {
                showTopNews();
            } else if (id == R.id.id_business) {
                showBusinessNews();
            } else if (id == R.id.id_entertainment) {
                showEnerainmentNews();
            } else if (id == R.id.id_lifestyle) {
                showLifestyleNews();
            } else if (id == R.id.id_sports) {
                showSportsNews();
            } else if (id == R.id.id_tech) {
                showTechNews();
            }


            return null;
        }

    }

}



//    private void loadNews(){
//
//        String[] projection = {
//                MyDatabaseHelper.KEY_ROWID,
//                MyDatabaseHelper.KEY_CODE,
//                MyDatabaseHelper.KEY_NAME, };
//
//        Uri uri = Uri.parse(MyContentProvider.NEWS_URI + "/" + id);
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null,null);
//
//        if (cursor != null) {
//            if(cursor.moveToFirst()) {
//                String myCode = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_CODE));
//                String myName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_NAME));
//                NewsPojo data=new Gson().fromJson(myName,NewsPojo.class);
//                adapter.refresh(data.getArticles());
//            }else{
//                loadNewsFromService();
//            }
//
//        }
//
//        else{
//
//            loadNewsFromService();
//
//        }
//
//    }