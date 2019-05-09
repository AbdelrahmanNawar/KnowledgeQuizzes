package com.example.nawar.quizes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nawar.quizes.model.Category;
import com.example.nawar.quizes.model.NetWorkUtilities;
import com.example.nawar.quizes.model.Result;
import com.example.nawar.quizes.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Categories extends AppCompatActivity {
    @BindView(R.id.gridview)
    GridView gridView;
    GetData getData;

    @SuppressWarnings("unused")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        final Category[] categories = new Category[7];

        categories[0] = new Category(R.drawable.history, NetWorkUtilities.HISTORY_QUESTIONS);
        categories[1] = new Category(R.drawable.sports, NetWorkUtilities.SPORTS_QUESTIONS);
        categories[2] = new Category(R.drawable.general_knowldge, NetWorkUtilities.GENERAL_KNOWLDGE_QUESTIONS);
        categories[3] = new Category(R.drawable.art, NetWorkUtilities.ART_QUESTIONS);
        categories[4] = new Category(R.drawable.math, NetWorkUtilities.MATH_QUESTIONS);
        categories[5] = new Category(R.drawable.geography, NetWorkUtilities.GEOGRAPHY_QUESTIONS);
        //dummy row to handle gridview last row cut
        categories[6] = new Category(0, "");

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categories);

        gridView.setAdapter(categoriesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!categories[i].getName().equals("") && getData == null) {
                    getData = new GetData();
                    getData.execute(categories[i].getName());
                }
            }
        });
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            try {
                ProviderInstaller.installIfNeeded(getApplicationContext());
                SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, null, null);
                SSLEngine engine = sslContext.createSSLEngine();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }
    }


    public class GetData extends AsyncTask<String, Void, ArrayList<Result>> {
        ProgressDialog dialog;

        @Override
        protected ArrayList<Result> doInBackground(String... urlStr) {
            HttpURLConnection httpURLConnection;
            BufferedReader reader;
            int responseCode;

            if (checkInternet()) {

                try {
                    ArrayList<Result> results = new ArrayList<>();

                    URL url = new URL(urlStr[0]);
                    URLConnection urlConnection = url.openConnection();
                    httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);

                    responseCode = httpURLConnection.getResponseCode();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    if (inputStream != null) {
                        if (responseCode == 200) {
                            reader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder buffer = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                buffer.append(line);
                            }
                            try {

                                Parcel p = Parcel.obtain();
                                JSONObject obj = new JSONObject(buffer.toString());
                                if (obj.has(getString(R.string.result))) {
                                    for (int i = 0; i < obj.getJSONArray(getString(R.string.result)).length(); i++) {
                                        Result myObject = new Result(p);

                                        JSONObject questionObj = obj.getJSONArray(getString(R.string.result)).getJSONObject(i);
                                        String questionStr = questionObj.getString(getString(R.string.question_key));
                                        String correctStr = questionObj.getString(getString(R.string.correct));
                                        String category = questionObj.getString(getString(R.string.category));

                                        JSONArray incorrect = questionObj.getJSONArray(getString(R.string.incorrect));
                                        List<String> answers = new ArrayList<>();
                                        for (int j = 0; j < incorrect.length(); j++) {
                                            answers.add(incorrect.getString(j));
                                        }
                                        answers.add(correctStr);
                                        myObject.setQuestion(questionStr);
                                        myObject.setCorrectAnswer(correctStr);
                                        myObject.setAnswers(answers);
                                        myObject.setCategory(category);

                                        results.add(myObject);
                                    }
                                    p.recycle();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            return results;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


        @Override
        protected void onPreExecute() {
            //showProgressDialog
            if (dialog == null) {
                dialog = new ProgressDialog(Categories.this);
                dialog.setMessage("Loading........");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
            }
        }


        @Override
        protected void onPostExecute(ArrayList<Result> results) {
            dialog.dismiss();
            getData = null;
            if (results == null) {
                Toast.makeText(Categories.this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            } else {


                if (results.size() > 0) {


                    Intent intent = new Intent(Categories.this, Questions_Activity.class);
                    intent.putParcelableArrayListExtra(getString(R.string.result), results);
                    startActivity(intent);

                }
            }

        }
    }

    public boolean checkInternet() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getData = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getData != null) {
            if (getData.getStatus().equals(AsyncTask.Status.RUNNING))

                getData.cancel(true);
        }
    }
}
