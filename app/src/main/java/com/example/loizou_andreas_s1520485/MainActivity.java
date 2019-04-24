package com.example.loizou_andreas_s1520485;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.Image;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SearchView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout lvRss;
    ArrayList<Earthquake> earthquake;
    ImageButton homeButton;
    ScrollView sv;
    ImageButton sortByButton;
    RadioGroup radioGroup;
    EditText searchView;
    ConstraintLayout mainLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortByButton = findViewById(R.id.sortByButton);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioButtonListener(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        searchView = findViewById(R.id.searchView);
        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchView.setText("");

                return false;
            }
        });
        searchView.setOnKeyListener(new SearchListener(this));
        lvRss = (LinearLayout) findViewById(R.id.ScrollView);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        sv = (ScrollView) findViewById(R.id.SV);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        sortByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getVisibility() == View.VISIBLE){
                    radioGroup.setVisibility(View.GONE);

                }else{
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        earthquake = new ArrayList<Earthquake>();

        /*lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        }); */

        new ProcessInBackground().execute();
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy loading rss feed...please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
                 * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
                 * We should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
                 *
                 * In order to achieve this, we will make use of a boolean variable called "insideItem".
                 */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control variable
                Earthquake earthquakeO = new Earthquake();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <title> and </title>
                                earthquakeO.setTitle(xpp.nextText());
                            }
                        }

                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <description> and </description>
                                earthquakeO.setDescription(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("puDate"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <pubDate> and </pubDate>
                               SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MM yyyy HH:mm:ss");

                               Date pdate = formatter.parse(xpp.nextText());
                                earthquakeO.setPubDate(pdate);
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("category"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <category> and </category>
                                earthquakeO.setCategory(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:Lat"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <geoLat> and </geoLat>
                                earthquakeO.setGeoLat(Double.parseDouble(xpp.nextText()));
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:Long"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <geoLong> and </geoLong>
                                earthquakeO.setGeoLong(Double.parseDouble(xpp.nextText()));
                            }
                        }
                    }

                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        earthquake.add(earthquakeO);
                        earthquakeO = new Earthquake();
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            for (Earthquake e: earthquake){
            earthquakeContainer earthquakeContainer = new earthquakeContainer(lvRss.getContext(), e.getTitle(), e.getDescription(), e.getGeoLong(), e.getGeoLat(), e.getMagnitute());
            lvRss.addView(earthquakeContainer);



            }


            progressDialog.dismiss();
        }
    }
    public void paintView(List<Earthquake> earthquakeList){

            lvRss.removeAllViews();


        for (Earthquake e: earthquakeList) {
            earthquakeContainer earthquakeContainer = new earthquakeContainer(lvRss.getContext(), e.getTitle(), e.getDescription(), e.getGeoLong(), e.getGeoLat(), e.getMagnitute());
            lvRss.addView(earthquakeContainer);
        }
lvRss.invalidate();
        }
    public ArrayList<Earthquake> getEarthquake() {
        return earthquake;
    }

    public void setEarthquake(ArrayList<Earthquake> earthquake) {
        this.earthquake = earthquake;
    }

}