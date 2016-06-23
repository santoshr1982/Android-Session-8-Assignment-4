package com.writefiletoexternalstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileExternalStorage extends AppCompatActivity implements View.OnClickListener {

    private EditText mFileInput;
    private TextView mFileContent;

    private Button mAddData;
    private Button mDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_external_storage);

        mFileInput = (EditText) findViewById(R.id.file_input);
        mFileContent = (TextView) findViewById(R.id.file_content);

        mAddData = (Button) findViewById(R.id.add_data);
        mDeleteData = (Button) findViewById(R.id.delete_file);

        mAddData.setOnClickListener(this);

        mDeleteData.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.add_data:

                WriteToFile();

                break;

            case R.id.delete_file:

                deleteFile();

                break;

        }

    }

    private void deleteFile() {

        String Status = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(Status)){

            File root = Environment.getExternalStorageDirectory();

            File dir = new File(root.getAbsolutePath()+"/TextFile");

            if(dir.exists()){

                File file = new File(dir,"Test.txt");

                if(file.exists()){

                    file.delete();
                    mFileContent.setText("");
                    Toast.makeText(getApplicationContext(),"File Deleted.", Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(getApplicationContext(),"The file does not exist.", Toast.LENGTH_LONG).show();
                }

            }else{

                Toast.makeText(getApplicationContext(), "The file or folder does not exist.", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void WriteToFile() {

        String Status = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(Status)){

            File root = Environment.getExternalStorageDirectory();

            File dir = new File(root.getAbsolutePath()+"/TextFile");

            if(!dir.exists()){

                dir.mkdir();

            }

            File file = new File(dir,"Test.txt");
            String Content = mFileInput.getText().toString();

            try {

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Content.getBytes());
                fileOutputStream.close();
                mFileInput.setText("");
                Toast.makeText(getApplicationContext(), "File Saved.", Toast.LENGTH_SHORT).show();

                Content = "";

                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();

                while((Content=bufferedReader.readLine())!=null){

                    stringBuffer.append(Content + "\n");

                }

                mFileContent.setText(stringBuffer.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            Toast.makeText(getApplicationContext(),"External Memory Not Available.",Toast.LENGTH_LONG).show();
        }

    }
}
