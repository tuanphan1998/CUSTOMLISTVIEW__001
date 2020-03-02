package com.example.customlistviewhoconlineanhquan;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private ListView lv1;
    // tao du lieu cho doi tuong la dang mang rong
    private List<School> listSchool = new ArrayList<>();
    // tao adapter
    private ArrayAdapter<School> adt;
    // tao editext
    private EditText ed1;
    private EditText ed2;
    private Button tbnADD;
    private  Button tbnEdit;
    // lay chi so
    int vitri = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = (ListView)findViewById(R.id.listviewDanhsach);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        tbnADD = findViewById(R.id.btn1);
        tbnEdit = findViewById(R.id.btn2);
        // enable click button add
        tbnADD.setEnabled(false);
        // enable click button edit
        tbnEdit.setEnabled(false);
        // ontextChange
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)
                {
                    tbnADD.setEnabled(true);
                }
                else if(s.length() == 0)
                {
                    tbnADD.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(vitri >= 0)
                    {
                        tbnEdit.setEnabled(true);
                    }
                    else if(vitri == -1)
                    {
                        tbnEdit.setEnabled(false);
                    }
                    else if(s.length() == 0)
                    {
                        tbnEdit.setEnabled(false);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        // them moi thong tin
        tbnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemNew();
            }
        });
        // get data o vi tru position
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    School s = listSchool.get(position);
                    ed1.setText(s.getName());
                    ed2.setText(s.getAddress());
                    vitri = position;
            }
        });
        // xoa thong tin
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listSchool.remove(position);
               adt.notifyDataSetChanged();
                return false;
            }
        });
        // sua thon tin
        tbnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaThongTin();
            }
        });


        // khoi tao data vao item
        adt = new ArrayAdapter<School>(MainActivity.this , 0 , listSchool)
        {
            @NonNull
            @Override
            // goi item ra de doi du lieu vao va dan vao textview
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.schollitem , null);

                // anh xa ra di cac txt1 va txt2
                TextView txt1 = convertView.findViewById(R.id.txt1);
                TextView txt2 = convertView.findViewById(R.id.txt2);

                // lay gai tri
                 School s = listSchool.get(position);
                 txt1.setText(s.getName());
                 txt2.setText(s.getAddress());

                return convertView;
            }
        };
        // nap rap item vao listview
        lv1.setAdapter(adt);
    }
    //Phuong thuc them moi
    private void addItemNew()
    {
       try {
           String name = ed1.getText().toString();
           String address = ed2.getText().toString();
           if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address))
           {
               Toast.makeText(this, "nhap thieu thong tin", Toast.LENGTH_SHORT).show();
               return;
           }
           School s = new School();
           s.setName(name);
           s.setAddress(address);
           listSchool.add(s);
           adt.notifyDataSetChanged();
       }
       catch (Exception e)
       {
           Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
       }
    }
    // phuong thuc sua doi
    private void suaThongTin()
    {
        try
        {
            String name = ed1.getText().toString();
            String tuoi = ed2.getText().toString();
            School s = listSchool.get(vitri);
            s.setName(name);
            s.setAddress(tuoi);
            adt.notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "" + ex, Toast.LENGTH_SHORT).show();
        }
    }

}
