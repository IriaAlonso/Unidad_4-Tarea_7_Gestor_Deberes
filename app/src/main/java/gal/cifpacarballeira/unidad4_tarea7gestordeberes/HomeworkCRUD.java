package gal.cifpacarballeira.unidad4_tarea7gestordeberes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeworkCRUD {

    private final HomeworkDB homeworkDB;
    private SQLiteDatabase db; // Base de datos reutilizable

    public HomeworkCRUD(Context context) {
        homeworkDB = new HomeworkDB(context);
    }

    /**
     * Obtiene una instancia de la base de datos en modo escritura.
     * Abre la conexión solo si está cerrada o no existe.
     */
    private SQLiteDatabase getWritableDatabase() {
        if (db == null || !db.isOpen()) {
            db = homeworkDB.getWritableDatabase();
        }
        return db;
    }

    /**
     * Obtiene una instancia de la base de datos en modo lectura.
     * Abre la conexión solo si está cerrada o no existe.
     */
    private SQLiteDatabase getReadableDatabase() {
        if (db == null || !db.isOpen()) {
            db = homeworkDB.getReadableDatabase();
        }
        return db;
    }

    /**
     * Inserta una nueva tarea en la base de datos.
     *
     * @param homework Objeto Homework a insertar.
     * @return ID del registro insertado.
     */
    public long insertHomework(Homework homework) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = homework.toContentValues();
        long result = db.insert("homeworks", null, values);
        homework.setId(result);
        return result;
    }

    /**
     * Obtiene todas las tareas de la base de datos.
     *
     * @return Lista de objetos Homework.
     */
    public List<Homework> getAllHomework() {
        SQLiteDatabase db = getReadableDatabase();
        List<Homework> homeworkList = new ArrayList<>();
        try (Cursor cursor = db.query("homeworks", null, null, null, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    homeworkList.add(Homework.fromCursor(cursor));
                } while (cursor.moveToNext());
            }
        }
        return homeworkList;
    }

    /**
     * Actualiza una tarea existente en la base de datos.
     *
     * @param id ID de la tarea a actualizar.
     * @param homework Objeto Homework con los nuevos datos.
     * @return Número de filas actualizadas.
     */
    public int updateHomework(long id, Homework homework) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = homework.toContentValues();
        return db.update("homeworks", values, "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Elimina una tarea de la base de datos.
     *
     * @param id ID de la tarea a eliminar.
     * @return Número de filas eliminadas.
     */
    public int deleteHomework(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("homeworks", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
