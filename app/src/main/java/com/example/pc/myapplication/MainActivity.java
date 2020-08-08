package com.example.pc.myapplication;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button  btnAddData;
    Button  btnViewAll;
    Button  btnViewUpdate;
    Button  btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);


        editName =(EditText)findViewById(R.id.editText_name);
        editSurname =(EditText)findViewById(R.id.editText_surname);
        editMarks =(EditText)findViewById(R.id.editText_marks);
        editTextId =(EditText)findViewById(R.id.editText_id);

        btnAddData =(Button) findViewById(R.id.button_add);
        btnViewUpdate =(Button) findViewById(R.id.button_update);
        btnViewAll =(Button) findViewById(R.id.button_viewAll);
        btnDelete =(Button) findViewById(R.id.button_delete);


        addData();
        viewAll();
        updateData();
        deletedata();
    }

    //BOTON INSERTAR
    public void addData () {

        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted = myDb.insertarData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString() );

                       if(isInserted == true)
                           Toast.makeText(MainActivity.this,"Datos insertados",Toast.LENGTH_LONG).show();
                    else

                           Toast.makeText(MainActivity.this,"Datos no insertados",Toast.LENGTH_LONG).show();
                    }
                }

        );
    }
//BOTON MOSTRAR
    public void viewAll(){

        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() ==0){
                        //muestro mensaje
                        showMessage("Error", "Nada encontrado");
                             return;
                        }
                        StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                         buffer.append("Id :"+ res.getString(0)+"\n");
                         buffer.append("Name :"+ res.getString(1)+"\n");
                         buffer.append("Surname :"+ res.getString(2)+"\n");
                         buffer.append("Marks :"+ res.getString(3)+"\n\n");
                    }

                    //Mostrar todos los datos
                        showMessage("Datos", buffer.toString());
                    }
                }
        );
    }


    public  void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //BOTON ACTUALIZAR
    public void updateData(){
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb .updateData(editTextId.getText().toString(),editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
                   if(isUpdate ==true)

                       Toast.makeText(MainActivity.this,"Datos actualizados",Toast.LENGTH_LONG).show();
                   else

                       Toast.makeText(MainActivity.this,"Datos no actualizados",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }


    //BOTON ELIMINAR
    public void deletedata(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editTextId.getText().toString());

                        if(deleteRows >0)
                            Toast.makeText(MainActivity.this,"Datos eliminados",Toast.LENGTH_LONG).show();
                        else

                            Toast.makeText(MainActivity.this,"Datos no eliminados",Toast.LENGTH_LONG).show();

                    }
                }
        );

    }
}
