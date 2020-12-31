package hello.world.recyclerviewtemplates;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dictTable")
public class DictionaryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dictID;
    private String english;
    private String korea;

    public DictionaryEntity(String dictID, String english, String korea) {
        this.dictID = dictID;
        this.english = english;
        this.korea = korea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDictID() {
        return dictID;
    }

    public void setDictID(String dictID) {
        this.dictID = dictID;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorea() {
        return korea;
    }

    public void setKorea(String korea) {
        this.korea = korea;
    }

    @NonNull
    @Override
    public String toString() {
        return "DictionaryDao{" +
                "id=" + id +
                ", dictID='" + dictID + '\'' +
                ", english='" + english + '\'' +
                ", korea='" + korea + '\'' +
                '}';
    }
}
