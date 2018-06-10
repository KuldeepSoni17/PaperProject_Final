package com.example.kuldeep.promisepaperproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    public ListView listView;
    public int currlistid;
    public int DBID;
    public sqlite SQLDB;
    private boolean isOffline;
    private Menu menu;
    ProgressDialog progressDialog;
    private static final int REQUEST_CODE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    int downloadedSize = 0;
    boolean paperDownloaded = true;
    int totalSize = 0;
//  String domain = "http://192.168.1.6/PromisePaper/";
    String domain = "http://192.168.43.100/promise/";
    public ListItem currlistItem;
    public ArrayList<ListItem> mainlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        SQLDB = new sqlite(this);
        isOffline = true;
        if(getIntent().hasExtra("isoffline"))
        {
            isOffline = getIntent().getExtras().getBoolean("isoffline");
            Log.d("DEBUGGING",isOffline + "");
        }
        verifyStoragePermissions(this);
        if(isOffline)
        gooffline();
        else
            goonline();
    }
    private void updateMenuTitles()
    {
        MenuItem offon = menu.findItem(R.id.menuoffon);
        if(isOffline)
        {
            offon.setTitle("Online");
            offon.setIcon(getResources().getDrawable(R.drawable.ic_wifi_white_24dp));
        }
        else
        {
            offon.setTitle("Offline");
            offon.setIcon(getResources().getDrawable(R.drawable.ic_phone_android_white_24dp));
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("PERMISSION",permission + "");

        if (permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_CODE
            );
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        this.menu = menu;
        updateMenuTitles();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menurefreshmenu:
                if(isOffline)
                {
                    refreshOffline(currlistItem);
                }
                else
                {
                    refreshOnline(currlistItem);
                }
                break;
            case R.id.menuoffon:
                offonchng();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gooffline()
    {
        listView = (ListView) findViewById(R.id.sellistview);
        currlistid = getResources().getInteger(R.integer.selcourse);
        isOffline = true;
        mainlist = new ArrayList<>();
        currlistItem = new ListItem("NULL");
        refreshOffline(currlistItem);
    }
    public void goonline()
    {
        listView = (ListView) findViewById(R.id.sellistview);
        currlistid = getResources().getInteger(R.integer.selcourse);
        isOffline = false;
        mainlist = new ArrayList<>();
        currlistItem = new ListItem("NULL");
        DBID = getResources().getInteger(R.integer.fakedbid);
        refreshOnline(currlistItem);
    }
    public void offonchng()
    {
     if(isOffline)
     {
         isOffline = false;
         updateMenuTitles();
         goonline();
     }
        else
     {
         isOffline = true;
         updateMenuTitles();
         gooffline();
     }
    }

    public void emptyListView()
    {
        mainlist.clear();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitemtext, (ArrayList)mainlist);
        listView.setAdapter(adapter);
    }
    public void refreshOffline(ListItem listItem)
    {   emptyListView();
        currlistItem = listItem;
        findViewById(R.id.offdbempty).setVisibility(View.GONE);
        if(SQLDB==null)
        {
            SQLDB = new sqlite(this);
        }
        Log.d("DEBUGGING",listItem + " ");
        ArrayList<ListItem> mainlist = SQLDB.getlist(listItem, currlistid, getResources());
        if(!mainlist.isEmpty())
        LoadList(mainlist);
        else
        findViewById(R.id.offdbempty).setVisibility(View.VISIBLE);
    }
    public void refreshOnline(ListItem listItem)
    {   emptyListView();
        findViewById(R.id.offdbempty).setVisibility(View.GONE);
        Log.d("HERE","HERE");
        new DatabaseHelperClass().getList(listItem);
    }
    public void LoadList(final ArrayList mainlist)
    {

        Log.d("mainlist",mainlist.size() +"");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitemtext, (ArrayList)mainlist);
        listView.setAdapter(adapter);
        Log.d("success","loadlistsuccess " + currlistid + " " + DBID);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currlistid == getResources().getInteger(R.integer.selcourse))
                {
                currlistid = getResources().getInteger(R.integer.selsemester);
                Log.d("QUERY", currlistid + " " + DBID);
                    if(isOffline)
                    {
                        refreshOffline((ListItem) mainlist.get((int)id));
                    }
                    else
                    {
                        refreshOnline((ListItem) mainlist.get((int)id));
                    }
                }
                else if(currlistid == getResources().getInteger(R.integer.selsemester))
                {
                currlistid = getResources().getInteger(R.integer.selsubject);
                    Log.d("QUERY", currlistid + " " + DBID);
                    if(isOffline)
                    {
                        refreshOffline((ListItem) mainlist.get((int)id));
                    }
                    else
                    {
                        refreshOnline((ListItem) mainlist.get((int)id));
                    }
                }
                else if(currlistid == getResources().getInteger(R.integer.selsubject))
                {
                currlistid = getResources().getInteger(R.integer.selyear);
                    Log.d("QUERY", currlistid + " " + DBID);
                    if(isOffline)
                    {
                        refreshOffline((ListItem) mainlist.get((int)id));
                    }
                    else
                    {
                        refreshOnline((ListItem) mainlist.get((int)id));
                    }
                }
                else if(currlistid == getResources().getInteger(R.integer.selyear))
                {
                    if(isOffline)
                    {
                        openOffPdf((ListItem) mainlist.get((int)id));
                    }
                    else
                    {
                        openpdf((ListItem) mainlist.get((int)id));
                    }
                }
                else
                {}
            }
        });


    }

    public void openOffPdf(final ListItem listItem)
    {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + listItem.pathoffile);
            Log.d("File",file + "");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        catch(ActivityNotFoundException e)
        {
            Toast.makeText(this, "No Application find to open pdf", Toast.LENGTH_LONG).show();
        }
    }
    public void openpdf(final ListItem listItem)
    {
        final String path = domain + listItem.pathoffile;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                downloadFile(path, listItem.pathoffile, listItem.month,listItem.year, listItem.paperID, listItem.subjectID, listItem.subjectname, listItem.courseID, listItem.semval, listItem.coursename, listItem.semstart, listItem.semend);
            }
        });
       thread.start();
        try {
            ///thread.join();
            Log.d("HERE", "ADDPAPER");
            Toast.makeText(this, "Paper Downloaded", Toast.LENGTH_SHORT).show();
            if (SQLDB == null) {
                SQLDB = new sqlite(SelectionActivity.this);
            }
            SQLDB.addPaper(listItem.month, listItem.year, listItem.paperID, listItem.subjectID, listItem.pathoffile, listItem.subjectname, listItem.courseID, listItem.semval, listItem.coursename, listItem.semstart, listItem.semend);
//            if (paperDownloaded) {
//                Log.d("HERE", "ADDPAPER");
//                Toast.makeText(this, "Paper Downloaded", Toast.LENGTH_SHORT).show();
//                if (SQLDB == null) {
//                    SQLDB = new sqlite(SelectionActivity.this);
//                }
//                SQLDB.addPaper(listItem.month, listItem.year, listItem.paperID, listItem.subjectID, listItem.pathoffile, listItem.subjectname, listItem.courseID, listItem.semval, listItem.coursename, listItem.semstart, listItem.semend);
//            }
        }
        catch (Exception e)
        {

        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,SelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isoffline",isOffline);
        i.putExtras(bundle);
        startActivity(i);
   }
    public void postcurrlistid()
    {
        if(currlistid == getResources().getInteger(R.integer.selcourse))
        {
            currlistid = getResources().getInteger(R.integer.selsemester);
        }
        else if(currlistid == getResources().getInteger(R.integer.selsemester))
        {
            currlistid = getResources().getInteger(R.integer.selsubject);
        }
        else if(currlistid == getResources().getInteger(R.integer.selsubject))
        {
            currlistid = getResources().getInteger(R.integer.selyear);
        }
        else if(currlistid == getResources().getInteger(R.integer.selyear))
        {
            //view  PDF
        }
        else
        {}

    }
    public void precurrlistid()
    {
        if(currlistid == getResources().getInteger(R.integer.selsemester))
        {
            this.currlistid = getResources().getInteger(R.integer.selcourse);
        }
        else if(currlistid == getResources().getInteger(R.integer.selsubject))
        {
            this.currlistid = getResources().getInteger(R.integer.selsemester);
        }
        else if(currlistid == getResources().getInteger(R.integer.selyear))
        {
            //viewPDF
        }
        else if(currlistid==88661)
        {
            currlistid = 88661;
        }
    }
    void downloadFile(final String path, final String filename,final int month, final int year, final int paperID, final int subjectID, final String subjectName, final int courseID, final int semVal, final String courseName, final int semStart, final int semEnd){

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(SelectionActivity.this);
                    progressDialog.setTitle("Downloading");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setProgress(0);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setIndeterminate(false);
                    progressDialog.show();
                }
            });


            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setRequestMethod("GET");
            //connect
            urlConnection.connect();
            Log.d("CONNECTION",urlConnection.getResponseCode() + "");
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "papers");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
                Log.d("CREATED","DIRECTORY CREATED");
            }
            if (success) {

            } else {
                // Do something else on failure
            }
            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,filename);
            FileOutputStream fileOutput = new FileOutputStream(file);
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            paperDownloaded = false;
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                runOnUiThread(new Runnable() {
                    public void run() {
                        int per = (int)((float)downloadedSize/totalSize * 100);
                        progressDialog.setProgress(per);
                        //Log.d("Progress",per+"");
                        //Log.d("Downloadedsize",downloadedSize + "");
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int per = (int)((float)downloadedSize/totalSize * 100);
                        if(per%100==0)
                        {
                   //         Log.d("HERE","TERMINATE");
                            progressDialog.dismiss();
                            paperDownloaded = true;
                        }
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    // pb.dismiss(); // if you want close it..
//                }
//            });

        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }
    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(SelectionActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

//    void showProgress(String file_path){
//        dialog = new Dialog(DownloadFileDemo1.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.myprogressdialog);
//        dialog.setTitle("Download Progress");
//
//        TextView text = (TextView) dialog.findViewById(R.id.tv1);
//        text.setText("Downloading file from ... " + file_path);
//        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
//        cur_val.setText("Starting download...");
//        dialog.show();
//
//        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
//        pb.setProgress(0);
//        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
//    }

    private class DatabaseHelperClass {

        //CommonForGettingListsLikeCourses,Subjects,Semesters
    /*public static ArrayList<ListItem> getList(int idToGet , ArrayList<ListItem> listToReturn)
    {
        return listToReturn;
    }*/

        public void getList(ListItem listItem)
        {
            currlistItem = listItem;
            String path = domain + "getlist.php";
            Log.d("LOG",path);
            Log.d("HERE","HERE");
            if(currlistid == getResources().getInteger(R.integer.selcourse))
            {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("TABLEID",Integer.toString(currlistid));
                new DBthread().execute(path,builder.build().getEncodedQuery());
            }
            else if(currlistid == getResources().getInteger(R.integer.selsemester))
            {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("TABLEID",Integer.toString(currlistid)).appendQueryParameter("COURSEID",Integer.toString(listItem.courseID));
                new DBthread().execute(path,builder.build().getEncodedQuery());
            }
            else if(currlistid == getResources().getInteger(R.integer.selsubject))
            {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("TABLEID",Integer.toString(currlistid)).appendQueryParameter("COURSEID",Integer.toString(listItem.courseID)).appendQueryParameter("SEMVAL",Integer.toString(listItem.semval));
                new DBthread().execute(path,builder.build().getEncodedQuery());
            }
            else if(currlistid == getResources().getInteger(R.integer.selyear))
            {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("TABLEID",Integer.toString(currlistid)).appendQueryParameter("SUBJECTID",Integer.toString(listItem.subjectID));
                new DBthread().execute(path,builder.build().getEncodedQuery());
            }
            else
            {}
        }
        //OnClickOfDownloadButton_ReturnsDemandedPaper
//        public PdfDocument download()
//        {
//
//            return null;
//        }

        private class DBthread extends AsyncTask<String,String,String>
        {
            HttpURLConnection conn;
            URL url = null;

            public DBthread() {
                super();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    url = new URL(params[0]);
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params[1]);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                    int response_code = conn.getResponseCode();
                    if (response_code == HttpURLConnection.HTTP_OK) {
                        Log.d("HTTPOK","HTTPOK");
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            Log.d("HTTPOK",line);
                            result.append(line);
                        }
                        return(result.toString());

                    }else{

                        return("unsuccessful");
                    }
                }
                catch (Exception e)
                {   e.printStackTrace();
                    return "Exception";
                }
                finally {
                    conn.disconnect();
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Log.d("STR",s);
                    if (s.contains("list_fetch_success_88661")) {
                        String str = s.substring(24);
                        Log.d("STR",str);
                        JSONArray jsonArray = new JSONArray(str);
                        mainlist = new ArrayList<ListItem>();
                        Log.d("length",jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            mainlist.add(new ListItem(jsonObject.getInt("courseid"), jsonObject.getString("coursename"), jsonObject.getInt("semstart"), jsonObject.getInt("semend"), jsonObject.getString("coursename")));
                            Log.d("courses", jsonObject.getString("coursename"));
                        }
                        LoadList(mainlist);
                    }
                    else if(s.contains("list_fetch_success_88662"))
                    {   String str = s.substring(24);
                        Log.d("STR",str);
                        JSONArray jsonArray = new JSONArray(str);
                        mainlist = new ArrayList<ListItem>();
                        Log.d("length",jsonArray.length() + "");
                        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                        int semstr = jsonObject.getInt("semstart");
                        int semend = jsonObject.getInt("semend");
                        for (int i = semstr; i <= semend; i++) {
                            mainlist.add(new ListItem(i, jsonObject.getInt("courseid"), jsonObject.getString("coursename"), "Semester " + i ));
                        }
                        LoadList(mainlist);
                    }
                    else if (s.contains("list_fetch_success_88663")) {
                        String str = s.substring(24);
                        Log.d("STR",str);
                        JSONArray jsonArray = new JSONArray(str);
                        mainlist = new ArrayList<ListItem>();
                        Log.d("length",jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            mainlist.add(new ListItem(jsonObject.getString("subjectname"), jsonObject.getInt("subjectid"), jsonObject.getInt("semval"), jsonObject.getInt("courseid"), jsonObject.getString("subjectname")));
                        }
                        LoadList(mainlist);
                    }
                    else if (s.contains("list_fetch_success_88664")) {
                        String str = s.substring(24);
                        Log.d("STR",str);
                        JSONArray jsonArray = new JSONArray(str);
                        mainlist = new ArrayList<ListItem>();
                        Log.d("length",jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("ARRAY",i +"");
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Log.d("CHECK", jsonObject.getInt("paperid") + " " + jsonObject.getInt("year") + " "  +jsonObject.getInt("month") + " " + jsonObject.getInt("subjectid") + " " + jsonObject.getString("pathoffile"));
                            mainlist.add(new ListItem(jsonObject.getInt("paperid"), jsonObject.getInt("year"), jsonObject.getInt("month"), jsonObject.getInt("subjectid"), jsonObject.getString("pathoffile"), jsonObject.getString("subjectname"), jsonObject.getInt("semval"), jsonObject.getInt("courseid"), jsonObject.getString("coursename"), jsonObject.getInt("semstart"), jsonObject.getInt("semend"), " HELLO"));
                        }
                        Log.d("mainlistPostExecute","CHECK");
                        LoadList(mainlist);
                    }
                    else if (s.contains("listempty")) {
                        String str = s.substring(9);
                        Log.d("STR",str);
                        precurrlistid();
                        TextView textView = (TextView) findViewById(R.id.offdbempty);
                        textView.setText("No data available, Please go back");
                        textView.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        Log.d("Unsuccessful","Unsuccessful");
                    }
                }
                catch (Exception e)
                {

                }
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }



    }

}
