package com.example.midtermtest_winter23;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements TasksRecyclerViewAdapter.ToDoClickListener,
        AddNewToDoFragment.FragmentClickListener
{

    ListView list;
    RecyclerView recyclerList;

    ArrayList<Task> tasks;
    ActivityResultLauncher<Intent> todoResultLauncher;
    TasksRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerList = findViewById(R.id.recycler_list);
//        list = findViewById(R.id.listoftasks);
   //     TasksBaseAdapter baseadapter = new TasksBaseAdapter(tasks,this);
//        list.setAdapter(adapter);

       tasks = FileStorageManager.getAllToDosFromTheFile(this);
        ((MyApp)getApplication()).taskArrayList = tasks;
        adapter = new TasksRecyclerViewAdapter(tasks, this);
        adapter.listener = this;

        recyclerList.setAdapter(adapter);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        todoResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Task newToDo = data.getParcelableExtra("newTodo");
                            tasks.add(newToDo);
                            FileStorageManager.writeNewToDoToTheFile(newToDo, MainActivity.this);

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.addNewTodoID:{
                Intent i = new Intent(this, AddNewToDo.class);
             //   startActivity(i);
                todoResultLauncher.launch(i);
                break;
            }
            case R.id.addfragment:
            {
                AddNewToDoFragment fragment = new AddNewToDoFragment();
                fragment.listener = this;
               fragment.show(getSupportFragmentManager().beginTransaction(), "1");
                // open a new fragment
                break;
            }
            case R.id.deleteAllTasks:{
               // FileStorageManager.deleteAllTodosFromTheFile(this);
                FileStorageManager.deleteOneTaskAt(2,this);

                tasks = FileStorageManager.getAllToDosFromTheFile(this);
                ((MyApp)getApplication()).taskArrayList = tasks;
                adapter.taskArrayList = tasks;
                adapter.notifyDataSetChanged();
                break;
            }

            case R.id.closeID:{
                finish();
                break;
            }
        }
        return true;
    }


    @Override
    public void todoSelected(int index) {
        Toast.makeText(this, "The selected todo is" + tasks.get(index).task, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fragmentSavedATask(Task newTask) {
        tasks.add(newTask);
        adapter.notifyDataSetChanged();
        FileStorageManager.writeNewToDoToTheFile(newTask, this);

    }

    @Override
    public void fragmentCanceled() {
        // no tasks to done here
    }
}
