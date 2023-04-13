package com.example.mydatabaseapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber: EditText
    lateinit var SaveB: Button
    lateinit var ViewB: Button
    lateinit var DeleteB: Button
    lateinit var DataBase: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.NameText)
        edtEmail = findViewById(R.id.EmailText)
        edtIdNumber = findViewById(R.id.IdNoText)
        SaveB = findViewById(R.id.SaveButton)
        DeleteB = findViewById(R.id.Deletebutton)
        ViewB = findViewById(R.id.Viewbutton)

        //Create a database called eMobilis
        DataBase = openOrCreateDatabase("eMobilis", Context.MODE_PRIVATE, null)
        //Create a table called users in the database
        DataBase.execSQL("CREATE TABLE IF NOT EXISTS users(jina VARCHAR,arafa VARCHAR,kitabulisho VARCHAR)")

        SaveB.setOnClickListener {
            //Receive data from the user
            var name = edtName.text.toString().trim()
            var email = edtName.text.toString().trim()
            var IdNumber = edtName.text.toString().trim()
            //check if the user is subbmitting empty fields
            if (name.isEmpty()||email.isEmpty()||IdNumber.isEmpty()){
                //Display an error message using the defined message function
                message("EMPTY FIELDS!!!","Please fill in all inputs")
            }
            else{

                DataBase.execSQL("INSERT INTO users  VALUES('"+name+"','"+email+"','"+IdNumber+"')")
                clear()
                message("SUCCESS","User saved successfully")
            }




        }
        ViewB.setOnClickListener {
            //use cursor to select all the records
            var cursor = DataBase.rawQuery("SELECT * FROM  users",null)
            // check if  there is any record found
            if (cursor.count == 0){
                message("NO RECORDS !!!","sorry,no users found!!!")
            }
            else{
                //use tring buffer to append all records to be displyed in loop
                var buffer = StringBuffer()
                while (cursor.moveToNext()){
                    var retreivedName = cursor.getString(0)
                    var retreivedEmail = cursor.getString(1)
                    var retreivedIdNumber = cursor.getString(2)

                    buffer.append(retreivedName+"\n")
                    buffer.append(retreivedEmail+"\n")
                    buffer.append(retreivedIdNumber+"\n\n")
                }
                message("USERS",buffer.toString())

            }
            DeleteB.setOnClickListener {
                val IdNumber = edtIdNumber.text.toString().trim()
                if ( IdNumber.isEmpty()){
                    message("EMPTY FIELD!!!","Please fill in the field")

                }
                else{
                    var cursor = DataBase.rawQuery("SELECT * FROM users WHERE kitambulisho= '"+IdNumber+"'", null)
                    if( cursor.count == 0){
                        message("NO RECORDS!!","Sorry,there's no user with provided id")
                    }
                    else{
                        DataBase.execSQL("DELETE FROM user WHERE kitambulisho='"+IdNumber+"'",null)
                        clear()
                        message("SUCCESS!!!","user deleted successfully")
                    }
                }
            }
        }




    }

    fun message(title: String, message: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setPositiveButton("cancel",null)
        alertDialog.create().show()
    }

    fun clear (){
        edtName.setText("")
        edtEmail.setText("")
        edtIdNumber.setText("")

    }


}