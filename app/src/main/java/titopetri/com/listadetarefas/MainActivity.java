package jorggelos.com.listadetarefas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import jorggelos.com.listadetarefas.db.TaskContract;
import jorggelos.com.listadetarefas.db.TaskDBHelper;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();
    }







    private void updateUI() {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TAREFA},
                null,null,null,null,null);

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.celula,
                cursor,
                new String[] { TaskContract.Columns.TAREFA},
                new int[] { R.id.textoCelula},
                0
        );
        ListView listView = (ListView) findViewById(R.id.listaTarefas);
        listView.setAdapter(listAdapter);
    }





    public void adicionarItem(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicione uma Tarefa");
        builder.setMessage("O que você precisa fazer?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);


        builder.setPositiveButton("Adicionar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tarefa = inputField.getText().toString();
                        Log.d("MainActivity", tarefa);

                        helper = new jorggelos.com.listadetarefas.db.TaskDBHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(jorggelos.com.listadetarefas.db.TaskContract.Columns.TAREFA, tarefa);

                        db.insertWithOnConflict(jorggelos.com.listadetarefas.db.TaskContract.TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);

                        updateUI();

                    }
                });

        builder.setNegativeButton("Cancelar",null);

        builder.create().show();
    }

    public void apagarItem(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.textoCelula);
        String tarefa = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TAREFA,
                tarefa);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

}