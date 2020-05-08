package com.example.meg.sqlitedb

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun SaveRecord(view: View) {
        val id=u_id.text.toString()
        val name=u_name.text.toString()
        val email=u_email.text.toString()

        val databaseHandler:DatabaseHandler = DatabaseHandler(this)


        if (id.trim()!="" && name.trim()!="" && email.trim()!=""){

            //save record
            val status=databaseHandler.addStudent(Student(Integer.parseInt(id),name,email))


            if (status >-1){
                Toast.makeText(this,"Record Saved",Toast.LENGTH_LONG).show()

                u_id.text.clear()
                u_name.text.clear()
                u_email.text.clear()


            }

        } else{

            Toast.makeText(this,"values cannot be blank",Toast.LENGTH_LONG).show()
        }



    }
    fun ViewRecord(view: View) {

        val databaseHandler:DatabaseHandler= DatabaseHandler(this)


        val std:List<Student> = databaseHandler.ViewStudent()

        val stdArrayId=Array(std.size){"0"}
        val stdArrayName=Array(std.size){"null"}
        val stdArrayEmail=Array(std.size){"null"}


        var index=0

        for (e in std){
            stdArrayId[index]=e.userId.toString()
            stdArrayName[index]=e.userName
            stdArrayEmail[index]=e.userEmail

            index++
        }

        val myListAdapter=MyListAdapter(this,stdArrayId,stdArrayName,stdArrayEmail)
        listView.adapter =myListAdapter

    }
    fun DeleteRecord(view: View) {

        val dialogBuilder= AlertDialog.Builder(this)
        val inflater=this.layoutInflater

        val dialogView = inflater.inflate(R.layout.delete_dialog,null)
        dialogBuilder.setView(dialogView)

        val dltId=dialogView.findViewById(R.id.deleteId) as EditText

        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter ID")

        dialogBuilder.setPositiveButton( "Delete", DialogInterface.OnClickListener { dialog, which ->

            val deleteId=dltId.text.toString()

            val databaseHandler:DatabaseHandler=DatabaseHandler(this)
            if (deleteId.trim()!=""){

                val status=databaseHandler.DeleteStudent(Student(Integer.parseInt(deleteId),"",""))

                if(status>-1){
                    Toast.makeText(this,"Record deleted",Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this,"Id cannot be empty",Toast.LENGTH_LONG).show()
            }


        })
        dialogBuilder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  })

        val b=dialogBuilder.create()
        b.show()

    }
    fun UpdateRecord(view: View) {

        val dialogBuilder= AlertDialog.Builder(this)
        val inflater=this.layoutInflater

        val dialogView = inflater.inflate(R.layout.update_dialog,null)
        dialogBuilder.setView(dialogView)


        val edId=dialogView.findViewById(R.id.updateId) as EditText
        val edName=dialogView.findViewById(R.id.updateName) as EditText
        val edEmail=dialogView.findViewById(R.id.updateEmail) as EditText


        dialogBuilder.setTitle("UPDATE RECORD")
        dialogBuilder.setMessage("Enter data")
        dialogBuilder.setPositiveButton("UPDATE", DialogInterface.OnClickListener { _, _ ->

            val updateId= edId.text.toString()
            val updateName=edName.text.toString()
            val updateEmail=edEmail.text.toString()


            val databaseHandler:DatabaseHandler = DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){


                val status =databaseHandler.UpdateStudent(Student(Integer.parseInt(updateId),updateName,updateEmail))
                if (status>-1){
                    Toast.makeText(this,"Record updated",Toast.LENGTH_LONG).show()

                }

            } else{
                Toast.makeText(this,"Values cannot be empty",Toast.LENGTH_LONG).show()

            }





        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })



        val b=dialogBuilder.create()
        b.show()


    }
}
