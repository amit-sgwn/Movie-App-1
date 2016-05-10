package com.mystoryapp.sangwan.movieapp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Movie_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__detail);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Movie.EXTRA_MOVIE)) {
            Movie movie = new Movie(intent.getBundleExtra(Movie.EXTRA_MOVIE));
            ((TextView)findViewById(R.id.movie_title)).setText(movie.title);
            ((TextView)findViewById(R.id.movie_rating)).setText(movie.getRating());
            ((TextView)findViewById(R.id.movie_overview)).setText(movie.overview);
            ((TextView)findViewById(R.id.movie_release_date)).setText(movie.release_date);


            Uri posterUri = movie.buildPosterUri(getString(R.string.api_poster_default_size));
            Picasso.with(this)
                    .load(posterUri)
                    .into((ImageView)findViewById(R.id.movie_poster));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie__detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Setting_Activity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
