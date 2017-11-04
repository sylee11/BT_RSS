package com.gameloft.bt_rss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ListView lv;
//    ArrayList<String> arraytitle, arraylink, arrayimage;
//    ArrayAdapter adapter;
    custom_adapter customadapter ;
    ArrayList<docbao> mangdocbao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.lv);

        mangdocbao = new ArrayList<docbao>();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toobar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, News_activity.class);
                intent.putExtra("LinkTinTuc", mangdocbao.get(i).link);
                startActivity(intent);
            }
        });
    }
    private class ReadRSS extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);
            NodeList nodelist = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            NodeList nodepubDate = document.getElementsByTagName("pubDate");

            String image ="";
            String tittle = "";
            String link = "";
            String pubDate = "";
            for (int i =0; i<nodelist.getLength(); i++){
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+) ['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if(matcher.find()){
                    image = matcher.group(1);
                }

                Element element = (Element) nodelist.item(i);
                Element element2 = (Element) nodepubDate.item(i);
                tittle = parser.getValue(element, "title") ;
                link = parser.getValue(element, "link");
                image = parser.getValue(element, "image");
                pubDate = parser.getValue(element2, "pubDate");
                mangdocbao.add(new docbao(tittle,link,image,pubDate));

            }
            customadapter =new custom_adapter(MainActivity.this, android.R.layout.simple_list_item_1, mangdocbao);
            lv.setAdapter(customadapter);
            customadapter.notifyDataSetChanged();
            super.onPostExecute(s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
