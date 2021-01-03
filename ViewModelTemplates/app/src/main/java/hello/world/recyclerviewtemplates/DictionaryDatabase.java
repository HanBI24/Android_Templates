package hello.world.recyclerviewtemplates;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DictionaryEntity.class}, version = 1, exportSchema = false)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDao dictionaryDao();
    private static volatile DictionaryDatabase INSTANCE;

    // Singleton
    public static DictionaryDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DictionaryDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DictionaryDatabase.class, "dict_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyINSTANCE(){
        INSTANCE = null;
    }
}
