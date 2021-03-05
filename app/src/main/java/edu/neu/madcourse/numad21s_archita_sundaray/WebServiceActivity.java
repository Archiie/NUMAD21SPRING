package edu.neu.madcourse.numad21s_archita_sundaray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "WebServiceActivity";
    private TextView mPlaceTextView;
    private TextView mStateTextView;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private EditText mURLEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        mURLEditText = (EditText)findViewById(R.id.URL_editText);
        mPlaceTextView = (TextView)findViewById(R.id.result_textview1);
        mStateTextView = (TextView)findViewById(R.id.result_textview2);
        mLatitudeTextView = (TextView)findViewById(R.id.result_textview3);
        mLongitudeTextView = (TextView)findViewById(R.id.result_textview4);
    }

    public void callWebserviceButtonHandler(View view){
        PingWebServiceTask task = new PingWebServiceTask();
        try {
            String url = NetworkUtil.validInput(mURLEditText.getText().toString());
            task.execute(url); // This is a security risk.  Don't let your user enter the URL in a real app.
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    // Google is deprecating Android AsyncTask API in Android 11 and suggesting to use java.util.concurrent
    // But it is still important to learn & manage how it works
    private class PingWebServiceTask  extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                String resp = NetworkUtil.httpResponse(url); // Get String response from the url address

                // Transform String into JSONObject
                jObject = new JSONObject(resp);

                return jObject;

            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }
            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            TextView result_view1 = (TextView)findViewById(R.id.result_textview1);
            TextView result_view2 = (TextView)findViewById(R.id.result_textview2);
            TextView result_view3 = (TextView)findViewById(R.id.result_textview3);
            TextView result_view4 = (TextView)findViewById(R.id.result_textview4);

            try {
                JSONArray places = jObject.getJSONArray("places");
                JSONObject placeObj = places.getJSONObject(0);

                //shows the city
                String placeName = placeObj.getString("place name");
                result_view1.setText(placeName);

                //shows the state
                String state = placeObj.getString("state");
                result_view2.setText(state);

                //shows the latitude
                String latitude = placeObj.getString("latitude");
                result_view3.setText(latitude);

                //shows the longitude
                String longitude = placeObj.getString("longitude");
                result_view4.setText(longitude);

            } catch (JSONException e) {
                e.printStackTrace();
                result_view1.setText("Something went wrong!");
            }
        }

    }
}