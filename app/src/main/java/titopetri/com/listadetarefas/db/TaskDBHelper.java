package titopetri.com.listadetarefas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDBHelper extends SQLiteOpenHelper{

    public TaskDBHelper(Context context){
        super(context, titopetri.com.listadetarefas.db.TaskContract.DB_NAME, null, titopetri.com.listadetarefas.db.TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuerry = String.format("CREATE TABLE %s (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT)", titopetri.com.listadetarefas.db.TaskContract.TABLE,
                TaskContract.Columns.TAREFA);
        Log.d("TaskDBHelper","Query to form table: " +sqlQuerry);
        sqlDB.execSQL(sqlQuerry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS" + titopetri.com.listadetarefas.db.TaskContract.TABLE);
    }

}
