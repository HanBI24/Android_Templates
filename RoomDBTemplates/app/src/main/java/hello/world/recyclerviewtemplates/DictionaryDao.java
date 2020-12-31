package hello.world.recyclerviewtemplates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DictionaryDao {
    @Query("SELECT * FROM dictTable")
    LiveData<List<DictionaryEntity>>getAll();

    @Insert
    void insert(DictionaryEntity dictionaryEntity);

    @Update
    void update(DictionaryEntity dictionaryEntity);

    @Delete
    void delete(DictionaryEntity dictionaryEntity);

    @Query("DELETE FROM dictTable")
    void deleteAll();

    @Query("SELECT * FROM dictTable ORDER BY dictID ASC")
    LiveData<List<DictionaryEntity>>setASC();

    @Query("SELECT * FROM dictTable ORDER BY dictID DESC")
    LiveData<List<DictionaryEntity>>setDESC();
}
