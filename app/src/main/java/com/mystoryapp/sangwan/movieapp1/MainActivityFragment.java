package com.mystoryapp.sangwan.movieapp1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridView;
    String moviesJsonStr = null;
    static String orderBy;
    ArrayList<String> movieIdArray = new ArrayList<String>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), android.graphics.Movie.class);
                intent.putExtra("id",movieIdArray.get(position));
                startActivity(intent);
            }
        });
        new FatchImage().execute();
        return rootView ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    public class FatchImage extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPostExecute(String str) {
            try {
                ArrayList<String> imageArray=getImageUrl(str);
                gridView.setAdapter(new ImageAdapter(getActivity(),imageArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(str);
        }

        public ArrayList<String> getImageUrl(String str) throws JSONException {
            ArrayList<String> urlArray = new ArrayList<String>();
            JSONObject moviesJson = new JSONObject(str);
            JSONArray moviesArray = moviesJson.getJSONArray("results");
            for(int i = 0; i < moviesArray.length(); i++) {
                JSONObject moviesCast = moviesArray.getJSONObject(i);
                urlArray.add(moviesCast.getString("poster_path"));
                Log.e("test", moviesCast.getString("poster_path"));
                movieIdArray.add(moviesCast.getString("id"));
            }
            return urlArray;
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            final String FATCH_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORY_BY = "sort_by";
            final String APPID_PARAM = "key";
            final String OPEN_MOVIE_API_KEY = "key";

            Uri builtUri = Uri.parse(FATCH_BASE_URL).buildUpon()
                    .appendQueryParameter(SORY_BY, orderBy)
                    .appendQueryParameter(APPID_PARAM, OPEN_MOVIE_API_KEY)
                    .build();

            Log.e("url", builtUri.toString());

            try {
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                } else {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }
                    return moviesJsonStr = buffer.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

    }
}
