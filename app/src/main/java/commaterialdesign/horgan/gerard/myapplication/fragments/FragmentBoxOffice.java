package commaterialdesign.horgan.gerard.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import commaterialdesign.horgan.gerard.myapplication.MyApplication;
import commaterialdesign.horgan.gerard.myapplication.R;
import commaterialdesign.horgan.gerard.myapplication.adapters.AdapterBoxOffice;
import commaterialdesign.horgan.gerard.myapplication.extras.Keys;
import commaterialdesign.horgan.gerard.myapplication.logging.L;
import commaterialdesign.horgan.gerard.myapplication.network.VolleySingleton;
import commaterialdesign.horgan.gerard.myapplication.pojos.Movie;

import static commaterialdesign.horgan.gerard.myapplication.extras.Endpoints.URL_CHAR_QUESTION;
import static commaterialdesign.horgan.gerard.myapplication.extras.Endpoints.URL_PARAM_API_KEY;
import static commaterialdesign.horgan.gerard.myapplication.extras.Endpoints.URL_CHAR_AMEPERSAND;
import static commaterialdesign.horgan.gerard.myapplication.extras.Endpoints.URL_PARAM_LIMIT;
import static commaterialdesign.horgan.gerard.myapplication.extras.Endpoints.URL_POPULAR;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_ID;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static commaterialdesign.horgan.gerard.myapplication.extras.Keys.EndpointBoxOffice.KEY_TITLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String BNK = "BNK";
    String TAG = "ERROR";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits;
    private AdapterBoxOffice adapterBoxOffice;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
        //jyfuyf//
    }

    public static String getRequestUrl(int limit) {
        return URL_POPULAR
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();

    }


    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(10),
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listMovies=parseJSONResponse(response);
                        adapterBoxOffice.setMovieList(listMovies);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(request);

    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies= new ArrayList<>();
        if (response == null || response.length() == 0) {

        }
        try {
            StringBuilder data = new StringBuilder();
            JSONArray arrayMovies = response.getJSONArray(Keys.EndpointBoxOffice.KEY_MOVIES);

            for (int i = 0; i < arrayMovies.length(); i++) {
                JSONObject currentMovies = arrayMovies.getJSONObject(i);
                String id = currentMovies.getString(KEY_ID);
                String title = currentMovies.getString(KEY_TITLE);
                String releaseDate = currentMovies.getString(KEY_RELEASE_DATES);
                int score = currentMovies.getInt(KEY_AUDIENCE_SCORE);
                String review = currentMovies.getString(KEY_REVIEWS);
                String poster = "http://image.tmdb.org/t/p/w45" + currentMovies.getString(KEY_POSTERS);

                Movie movie = new Movie();
                movie.setId(id);
                movie.setTitle(title);

                Date date = dateFormat.parse(releaseDate);

                movie.setRelease_date(date);
                movie.setOverview(review);
                movie.setVote_count(score);
                movie.setPoster_path(poster);

                listMovies.add(movie);
            }

            L.T(getActivity(), listMovies.toString());


        } catch (JSONException e) {

        } catch (ParseException e) {

        }
        return listMovies;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice= new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;

    }


}








