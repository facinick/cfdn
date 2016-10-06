package com.inerrsia.myapplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
public class MainActivity extends AppCompatActivity
{
    static byte[] imgdata;
    static String JSON_DATA;
    public static final int MEDIA_TYPE_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    protected Camera mCamera;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "boom bitch", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // cuz 0 1 2.
        mViewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        String TAG = "TAG";
        CameraPreview mPreview;
        long startClickTime = 0;
        final long MAX_CLICK_DURATION = 400;
        final int MAX_CLICK_DISTANCE = 5;
        float x1;
        float y1;
        float x2;
        float y2;
        float dx;
        float dy;
        Camera mCamera;
        private static final int FOCUS_AREA_SIZE = 300;
        private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

            private Uri getOutputMediaFileUri(int type) {
                return Uri.fromFile(getOutputMediaFile(type));
            }

            private File getOutputMediaFile(int type) {
                // To be safe, you should check that the SDCard is mounted
                // using Environment.getExternalStorageState() before doing this.
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "MTN_Camera");
                // This location works best if you want the created images to be shared
                // between applications and persist after your app has been uninstalled.
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null;
                    }
                }
                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaFile;
                if (type == MEDIA_TYPE_IMAGE) {
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            "IMG_" + timeStamp + ".jpg");
                } else {
                    return null;
                }
                return mediaFile;
            }

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                imgdata = data;
                Log.e("XXXXXXXXXXXXXXXXXXX", "BYTE DATA SET");

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = "";
                            Log.e("XXXXXXXXXXXXXXXXXXX", "API call started");
                            try {
                                response = ApiCall.POST(new OkHttpClient(), imgdata);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("ERROR CALING API", "ERROR");
                            }
                            Log.e("XXXXXXXXXXXXXXXXXXX", "JSON DATA CAPTURED");
                            JSON_DATA = new String(response);
                            Log.d("Response", response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Log.e("XXXXXXXXXXXXXXXXXXX", "STARTING THREAD");
                thread.start();
                try {
                    thread.join();
                    Log.e("XXXXXXXXXXXXXXXXXXX", "THREAD COMPLETED");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("XXXXXXXXXXXXXXXXXXX", "ERROR IN THREAD JOIN");
                }
                Log.e("XXXXXXXXXXXXXXXXXXX", "SHIFTING SCREEN");
                Intent fuck = new Intent(getContext(), fuck.class);
                fuck.putExtra("json", JSON_DATA);
                startActivity(fuck);
                try {
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                    if (pictureFile == null) {
                        return;
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        fos.write(data);
                        fos.close();
                        Log.e("XXXXXXXXXXXXXXXXXXX", "DATA WRITTEN");
                    } catch (FileNotFoundException e) {
                        Log.d("TAG", "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d("TAG", "Error accessing file: " + e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        void focusOnTouch(MotionEvent event) {
            if (mCamera != null) {
                Camera.Parameters parameters = mCamera.getParameters();
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    Log.i(TAG, "fancy !");
                    Rect rect = calculateFocusArea(event.getX(), event.getY());
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                    meteringAreas.add(new Camera.Area(rect, 800));
                    parameters.setFocusAreas(meteringAreas);
                    mCamera.setParameters(parameters);
                    mCamera.autoFocus(mAutoFocusTakePictureCallback);
                } else {
                    mCamera.autoFocus(mAutoFocusTakePictureCallback);
                }
            } else {
                Toast.makeText(getContext(), "CAMERA NULL", Toast.LENGTH_SHORT).show();
            }
        }

        private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    Log.i("tap_to_focus", "success!");
                } else {

                    Log.i("tap_to_focus", "fail!");
                }
            }
        };

        private Rect calculateFocusArea(float x, float y) {
            int left = clamp(Float.valueOf((x / mPreview.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
            int top = clamp(Float.valueOf((y / mPreview.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);

            return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
        }

        private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
            int result;
            if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
                if (touchCoordinateInCameraReper > 0) {
                    result = 1000 - focusAreaSize / 2;
                } else {
                    result = -1000 + focusAreaSize / 2;
                }
            } else {
                result = touchCoordinateInCameraReper - focusAreaSize / 2;
            }
            return result;
        }

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

                CameraPreview mPreview;
                mCamera = Camera.open();
                mCamera.setDisplayOrientation(90);
                mPreview = new CameraPreview(getActivity().getBaseContext(), mCamera);
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                mPreview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                startClickTime = Calendar.getInstance().getTimeInMillis();
                                Log.d("StartTimeDown", Long.toString(startClickTime));
                                x1 = motionEvent.getX();
                                y1 = motionEvent.getY();
                                return true;
                            }
                            case MotionEvent.ACTION_UP: {
                                Log.d("StartTimeUp", Long.toString(startClickTime));
                                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                                x2 = motionEvent.getX();
                                y2 = motionEvent.getY();
                                dx = x2 - x1;
                                dy = y2 - y1;
                                Log.e("DX", Float.toString(dx));
                                Log.e("Dy", Float.toString(dy));
                                if (clickDuration < MAX_CLICK_DURATION && dx < MAX_CLICK_DISTANCE && dy < MAX_CLICK_DISTANCE) {
                                    Toast.makeText(getContext(), "FOCUSING, CHILL!", Toast.LENGTH_SHORT).show();
                                    focusOnTouch(motionEvent);
                                    mCamera.takePicture(null, null, mPicture);
                                } else {
                                    Toast.makeText(getContext(), "TAP AGAIN", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        return false;
                    }
                });
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.frame);
                preview.addView(mPreview);
                getFragmentManager().findFragmentById(R.id.frame);
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
//                final ListView wordsList = (ListView) rootView.findViewById(R.id.wordList);
//                wordsList.setTextAlignment(rootView.TEXT_ALIGNMENT_CENTER);
//                TextView title = (TextView) rootView.findViewById(R.id.textView4);
//              //  title.setVisibility(View.VISIBLE);
//                final ArrayList<String> words = new ArrayList<String>();
//                for (int i = 0; i < 15; i++)
//                {
//                    words.add("hello" + i);
//                }
//                ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, words);
//                wordsList.setAdapter(adp);
//                wordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getContext(), "show word meaning " + words.get(i), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getContext(), MeaningActivity.class);
//                        intent.putExtra("word", words.get(i));
//                        startActivity(intent);
//                    }
//                });
            }
//            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
//                Log.e("XXXXXXXXXXXXX", "LANDED AT 2");
//                if (JSON_DATA != null) {
//
//                    Log.e("XXXXXXXXXXXXXXXXXXX", JSON_DATA);
//                    ArrayList<String> ex = new ArrayList<>();
//                    try {
//                        JSONObject jsonRootObject = new JSONObject(JSON_DATA);
//                        JSONArray jsonArray = jsonRootObject.optJSONArray("words");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String name = jsonObject.optString("text").toString();
//                            ex.add(name);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    //           Log.e("XXXXXXXXXXXXXXXXXXX",ex.toString());
//                    //           ListView extracted = (ListView) rootView.findViewById(R.id.EXTRACTED);
//                    //           extracted.setVisibility(rootView.VISIBLE);
//                    //           ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ex);
//                    // extracted.setAdapter(adp);
//
//
//                } else {
//                    Log.e("XXXXXXXXXXXXXXXXXXX", "NULL JSON");
//                    TextView SEXYTEXT = (TextView) rootView.findViewById(R.id.SEXYTEXT);
//                    SEXYTEXT.setVisibility(rootView.VISIBLE);
                //}

                return rootView;
            }
        }

        public class SectionsPagerAdapter extends FragmentPagerAdapter {
            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "SECTION 1";
                    case 1:
                        return "SECTION 2";
                    case 2:
                        return "SECTION 3";
                }
                return null;
            }
        }

    }



