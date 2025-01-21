package gal.cifpacarballeira.unidad4_tarea7gestordeberes;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Homework implements Parcelable {

    private long id;
    private String subject; // Asignatura (PMDM, AD, etc.)
    private String description; // Descripción del deber
    private String dueDate; // Fecha de entrega en formato dd/MM/yyyy
    private boolean isCompleted; // Estado del deber

    // Constructor completo
    public Homework(long id, String subject, String description, String dueDate, boolean isCompleted) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // Constructor sin ID (útil para nuevos registros)
    public Homework(String subject, String description, String dueDate, boolean isCompleted) {
        this(0, subject, description, dueDate, isCompleted);
    }

    // Getters y Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Implementación de Parcelable
    protected Homework(Parcel in) {
        id = in.readLong();
        subject = in.readString();
        description = in.readString();
        dueDate = in.readString();
        isCompleted = in.readByte() != 0;
    }

    public static final Creator<Homework> CREATOR = new Creator<Homework>() {
        @Override
        public Homework createFromParcel(Parcel in) {
            return new Homework(in);
        }

        @Override
        public Homework[] newArray(int size) {
            return new Homework[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(subject);
        dest.writeString(description);
        dest.writeString(dueDate);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
    }

    // Convierte el objeto a ContentValues para operaciones en la base de datos
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id); // Opcional, puede omitirse si el ID es autogenerado
        values.put("subject", subject);
        values.put("description", description);
        values.put("due_date", dueDate);
        values.put("is_completed", isCompleted ? 1 : 0);
        return values;
    }

    // Crea un objeto Homework a partir de un Cursor
    public static Homework fromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        String subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
        boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("is_completed")) == 1;

        return new Homework(id, subject, description, dueDate, isCompleted);
    }
}
