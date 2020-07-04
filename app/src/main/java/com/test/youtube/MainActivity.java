package com.test.youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyBLPkS9_ryvuyCeQ06DJWSnqXGBGNCnrsk";
    String nextPageToken="";

    EditText editSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        editSearch = findViewById(R.id.editSearch);

        String keyword= editSearch.getText().toString().trim();

        String searchKeyword ="q="+keyword;
        String url = URL+"&maxResults=20"+searchKeyword;
        getNetworkData(url);
    }

    public void getNetworkData(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nextPageToken = response.getString("nextPageToken");
                            JSONArray items = response.getJSONArray("items");
                            for(int i=0; i<items.length(); i++){
                                JSONObject object = items.getJSONObject(i);
                                JSONObject id = object.getJSONObject("id");
                                String videoId = id.getString("videoId");

                                JSONObject snippet = object.getJSONObject("snippet");
                                String title = snippet.getString("title");
                                String desc = snippet.getString("description");

                                Log.i("AAA",videoId+"    "+title+"     "+desc);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}