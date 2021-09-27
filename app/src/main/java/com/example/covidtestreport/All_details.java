package com.example.covidtestreport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class All_details extends AppCompatActivity {

    private static final String TAG = "Display Name: ";
    TextView textView;
   Button generatePdf;
    int pageHeight = 1100;
    int pagewidth = 800;
    private ImageView qrCodeIV;
   TableRow tableRowState,tableRowDistric;

    //public static final Uri EXTERNAL_CONTENT_URI;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp,bmpQRCode,bmpQRCode1;


    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    private boolean readPermissionGranted = false;
    private boolean writePermissionGranted = false;
    private static final int CREATE_FILE = 1;
    private static final int PICK_PDF_FILE = 2;


    private String LOG_TAG = "GeneratedQRCode";
    private Context mContext;
    private int takeFlags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details);


        tableRowState = findViewById(R.id.staterowID);
        tableRowDistric = findViewById(R.id.districrowID);

        textView = (TextView) findViewById(R.id.cSRF_ID);
        String Tempholder = getIntent().getStringExtra("tvSRF");
        textView.setText(Tempholder);

        String Tempholder1 = getIntent().getStringExtra("tvName");
        textView = (TextView) findViewById(R.id.cname);
        textView.setText(Tempholder1);

        String Tempholder2 = getIntent().getStringExtra("tvname_of_lab");
        textView = (TextView) findViewById(R.id.cname_of_the_lab);
        textView.setText(Tempholder2);

        String Tempholder3 = getIntent().getStringExtra("tvicmr");
        textView = (TextView) findViewById(R.id.cICMR_ID);
        textView.setText(Tempholder3);

        String Tempholder4 = getIntent().getStringExtra("tvgender");
        textView = (TextView) findViewById(R.id.cgender);
        textView.setText(Tempholder4);

        String Tempholder5 = getIntent().getStringExtra("tvDistrict_NUMBER");
        textView = (TextView) findViewById(R.id.caddress);
        textView.setText(Tempholder5);

        String Tempholder6 = getIntent().getStringExtra("tvSwab_Collection");
        textView = (TextView) findViewById(R.id.csample_Collection_Date);
        textView.setText(Tempholder6);

        String Tempholder7 = getIntent().getStringExtra("tvresult_date");
        textView = (TextView) findViewById(R.id.cresult_date);
        textView.setText(Tempholder7);

        String Tempholder8 = getIntent().getStringExtra("tvState_Number");
        textView = (TextView) findViewById(R.id.ctState_num);
        textView.setText(Tempholder8);

        String Tempholder9 = getIntent().getStringExtra("tvAge");
        textView = (TextView) findViewById(R.id.cage);
        textView.setText(Tempholder9);

        String Tempholder10 = getIntent().getStringExtra("tvResult");
        textView = (TextView) findViewById(R.id.ctest_result);
        textView.setText(Tempholder10);

        if (Tempholder8 == null || Tempholder8.isEmpty())
        {
            tableRowState.setVisibility(View.GONE);
        } else {
            tableRowState.setVisibility(View.VISIBLE);
        }

        if (Tempholder5 == null || Tempholder5.isEmpty())
        {
            tableRowDistric.setVisibility(View.GONE);
        }else {
            tableRowDistric.setVisibility(View.VISIBLE);
        }

        // initializing our variables.
        generatePdf = findViewById(R.id.pdf);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.karnataka);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);



        qrCodeIV = findViewById(R.id.QR_code);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
           // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

       bmpQRCode1= generateQRcode(Tempholder,Tempholder1,Tempholder2,Tempholder3,Tempholder4,Tempholder5,Tempholder6,Tempholder7,Tempholder8,Tempholder9,Tempholder10);

        generatePdf.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // calling method to
                // generate our PDF file.
                generatePdf(bmpQRCode1,Tempholder,Tempholder1,Tempholder2,Tempholder3,Tempholder4,Tempholder5,Tempholder6,Tempholder7,Tempholder8,Tempholder9,Tempholder10);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) qrCodeIV.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                saveFile();

            }
        });

    }

//    private void displayfile() {
//        FileInputStream file = null;
//        String info = null;
//        StringBuilder data = new StringBuilder();
//        try {
//            File f = new File(Environment.getExternalStoragePublicDirectory("Download"),"Report.pdf");
//            file = new FileInputStream(f);
//            InputStreamReader input = new InputStreamReader(file);
//            BufferedReader br = new BufferedReader(input);
////            while (info == br.readLine())! = null)
////            {
////                data.append(info);
////            }
//            qrCodeIV.setImageBitmap(bmp);
//            file.close();
//
//        }catch (FileNotFoundException e)
//        {
//            Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_SHORT).show();
//        }
//        catch (IOException e) {
//
//            Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void saveFile() {
//
//        FileOutputStream file = null;
//        try {
//            File f = new File(Environment.getExternalStoragePublicDirectory("Download"),"Report.pdf");
//            f.createNewFile();
//            file = new FileOutputStream(f);
//            file.write(getApplicationInfo().flags);
//            //file.write(info.getBytes());
//            file.close();
//
//        }catch (FileNotFoundException e)
//        {
//            Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_SHORT).show();
//        }
//        catch (IOException e) {
//
//            Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_SHORT).show();
//        }
       // displayfile();

      // int pickerInitialUri =0 ;
      // int fileUri = 0;

        FileOutputStream fileOutputStream=null;
        OutputStream outputStream;

        try {
            if (SDK_INT >= Build.VERSION_CODES.Q)
            {
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"COVID19-TestReport"+".pdf");
//                Intent intent = getIntent();
//                intent.setType("application/pdf");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                Uri fileUri = intent.getData();
               // contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"application/pdf");
               // contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DOWNLOADS + File.separator+"TestReport");
              //  Uri fileUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
               // Uri fileUri1 = resolver.insert(Uri.parse(MediaStore.DownloadColumns.DOWNLOAD_URI),contentValues);

                fileOutputStream = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(fileUri));
//
//                //   bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);

                Objects.requireNonNull(fileOutputStream);

            }

        }catch (Exception e)
        {
            Toast.makeText(this,"file not saved\n"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "COVID19-TestReport");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
       // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, CREATE_FILE);
    }


    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private boolean isVirtualFile(Uri uri) {
        if (!DocumentsContract.isDocumentUri(this, uri)) {
            return false;
        }

        Cursor cursor = getContentResolver().query(
                uri,
                new String[] { DocumentsContract.Document.COLUMN_FLAGS },
                null, null, null);

        int flags = 0;
        if (cursor.moveToFirst()) {
            flags = cursor.getInt(0);
        }
        cursor.close();

        return (flags & DocumentsContract.Document.FLAG_VIRTUAL_DOCUMENT) != 0;
    }


    private InputStream getInputStreamForVirtualFile(Uri uri, String mimeTypeFilter)
            throws IOException {

        ContentResolver resolver = getContentResolver();

        String[] openableMimeTypes = resolver.getStreamTypes(uri, mimeTypeFilter);

        if (openableMimeTypes == null ||
                openableMimeTypes.length < 1) {
            throw new FileNotFoundException();
        }

        return resolver
                .openTypedAssetFileDescriptor(uri, openableMimeTypes[0], null)
                .createInputStream();
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException
    {
        ParcelFileDescriptor parcelFileDescriptor= getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }



    //@SuppressLint("WrongConstant")
    private void openFile(Uri pickerInitialUri) {
        Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent1.addCategory(Intent.ACTION_OPEN_DOCUMENT);
        intent1.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent1.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent1, PICK_PDF_FILE);
        final int takeFlags = intent1.getFlags()
                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
// Check for the freshest data.
        getContentResolver().takePersistableUriPermission(pickerInitialUri, takeFlags);
    }

    public void openDirectory(Uri uriToLoad)
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI,uriToLoad);

        startActivityForResult(intent,200);


    }

//    public void dumpImageMetaData(Uri uri) {
//
//        // The query, because it only applies to a single document, returns only
//        // one row. There's no need to filter, sort, or select fields,
//        // because we want all fields for one document.
//        Cursor cursor = new All_details().getContentResolver()
//                .query(uri, null, null, null, null, null);
//
//        try {
//            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
//            // "if there's anything to look at, look at it" conditionals.
//            if (cursor != null && cursor.moveToFirst()) {
//
//                // Note it's called "Display Name". This is
//                // provider-specific, and might not necessarily be the file name.
//                @SuppressLint("Range") String displayName = cursor.getString(
//                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                Log.i(TAG, "Display Name: " + displayName);
//
//                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
//                // If the size is unknown, the value stored is null. But because an
//                // int can't be null, the behavior is implementation-specific,
//                // and unpredictable. So as
//                // a rule, check if it's null before assigning to an int. This will
//                // happen often: The storage API allows for remote files, whose
//                // size might not be locally known.
//                String size = null;
//                if (!cursor.isNull(sizeIndex)) {
//                    // Technically the column stores an int, but cursor.getString()
//                    // will do the conversion automatically.
//                    size = cursor.getString(sizeIndex);
//                } else {
//                    size = "Unknown";
//                }
//                Log.i(TAG, "Size: " + size);
//            }
//        } finally {
//            cursor.close();
//        }
//    }


    private Bitmap generateQRcode(String Tempholder,String Tempholder1,String Tempholder2,String Tempholder3,String Tempholder4,String Tempholder5,String Tempholder6,String Tempholder7,String Tempholder8,String Tempholder9,String Tempholder10)
    {

            String sText = "Name Of the Lab : "+Tempholder2 +"\n \n"+"ICMR ID : "+ Tempholder3 +"\n \n"+"SFR ID : "+ Tempholder +"\n \n"+"Name of the Person : "+ Tempholder1 +"\n \n"+"Age : "+ Tempholder9 +"\n \n"+"Gender : "+ Tempholder4 +
                    "\n \n"+"Swab Collection : "+ Tempholder6 +"\n \n"+"Result Date : "+ Tempholder7 +"\n \n"+"Test Reslut : "+ Tempholder10 +"\n\n"+"State P Code : "+ Tempholder8 +"\n \n"+"District P Code : "+ Tempholder5;
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE,600,600) ;
                BarcodeEncoder encoder = new BarcodeEncoder();
                bmpQRCode = encoder.createBitmap(matrix);
                qrCodeIV.setImageBitmap(bmpQRCode);
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(textView.getApplicationWindowToken(),0);
                return bmpQRCode;

            }catch (WriterException e)
            {
                e.printStackTrace();
                return bmpQRCode;
            }
        }

    private void generatePdf(Bitmap bmpQRCode1,String Tempholder,String Tempholder1,String Tempholder2,String Tempholder3,String Tempholder4,String Tempholder5,String Tempholder6,String Tempholder7,String Tempholder8,String Tempholder9,String Tempholder10 ) {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        canvas.drawBitmap(bmpQRCode,100,500,paint);

        //canvas.drawBitmap(bmpQRCode,1,300,400,300,300,300,false,paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.default_color));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("COVID-19 TEST REPORT.", 209, 100, title);
       canvas.drawText("Government of Karnataka ", 209, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(15);

        // below line is used for setting
        // our text to center of PDF.
      // title.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Name Of the Lab  ", 100, 220, title);
        canvas.drawText(" : ",250,220,title);
        canvas.drawText(Tempholder2, 300, 220, title);

        canvas.drawText("ICMR_ID  ", 100, 250, title);
        canvas.drawText(" : ",250,250,title);
        canvas.drawText(Tempholder3, 300, 250, title);

        canvas.drawText("Name  ", 100, 280, title);
        canvas.drawText(" : ",250,280,title);
        canvas.drawText(Tempholder1, 300, 280, title);

        canvas.drawText("SRF-id  ", 100, 310, title);
        canvas.drawText(" : ",250,310,title);
        canvas.drawText(Tempholder, 300, 310, title);

        canvas.drawText("Age  ", 100, 340, title);
        canvas.drawText(" : ",250,340,title);
        canvas.drawText(Tempholder9, 300, 340, title);

        canvas.drawText("Gender  ", 100, 370, title);
        canvas.drawText(" : ",250,370,title);
        canvas.drawText(Tempholder4, 300, 370, title);


        canvas.drawText("Swab_collection  ", 100, 400, title);
        canvas.drawText(" : ",250,400,title);
        canvas.drawText(Tempholder6, 300, 400, title);

        canvas.drawText("Result_date  ", 100, 430, title);
        canvas.drawText(" : ",250,430,title);
        canvas.drawText(Tempholder7, 300, 430, title);

        canvas.drawText("Test Result  ", 100, 460, title);
        canvas.drawText(" : ",250,460,title);
        canvas.drawText(Tempholder10, 300, 460, title);

        canvas.drawText("State Number  ", 100, 490, title);
        canvas.drawText(" : ",250,490,title);
        canvas.drawText(Tempholder8, 300, 490, title);


        canvas.drawText("District_Number  ", 100, 520, title);
        canvas.drawText(" : ",250,520,title);
        canvas.drawText(Tempholder5, 300, 520, title);


        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "COVID19-TestReport.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(All_details.this, "PDF file generated successfully.And file is saved in File Manager ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {

        if ( SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();

        } else {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;

        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 200);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 200);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                if (Environment.isExternalStorageManager()) {
//                    // perform action when allow permission success
//                } else {
//                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
//                        // perform action when allow permission success
//                    } else {
//                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }
//    private String copyFileToExternalStorage(Uri uri, String newDirName) {
//        Uri returnUri = uri;
//
//        Cursor returnCursor = mContext.getContentResolver().query(returnUri, new String[]{
//                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
//        }, null, null, null);
//
//
//        /*
//         * Get the column indexes of the data in the Cursor,
//         *     * move to the first row in the Cursor, get the data,
//         *     * and display it.
//         * */
//        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//        returnCursor.moveToFirst();
//        String name = (returnCursor.getString(nameIndex));
//        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
//
//        File output;
//        if (!newDirName.equals("")) {
//            File dir = new File(mContext.getFilesDir() + "/" + newDirName);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            output = new File(mContext.getFilesDir() + "/" + newDirName + "/" + name);
//        } else {
//            output = new File(mContext.getFilesDir() + "/" + name);
//        }
//        try {
//            InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
//            FileOutputStream outputStream = new FileOutputStream(output);
//            int read = 0;
//            int bufferSize = 1024;
//            final byte[] buffers = new byte[bufferSize];
//            while ((read = inputStream.read(buffers)) != -1) {
//                outputStream.write(buffers, 0, read);
//            }
//
//            inputStream.close();
//            outputStream.close();
//
//        } catch (Exception e) {
//
//            Log.e("Exception", e.getMessage());
//        }
//
//        return output.getPath();
//    }


}