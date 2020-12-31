package hello.world.recyclerviewtemplates;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// RecyclerView CRUD.
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<DictionaryEntity> mList = new ArrayList<>();
    ItemClickListener itemClickListener;
    private final DictionaryDatabase db;
    private int idx;

    // Save view by ViewHolder class.
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView english;
        protected TextView korean;
        protected TextView deleteItem;

        // (2)
        // RecyclerView need to make ViewHolder to yourself.
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.id = itemView.findViewById(R.id.id_listitem);
            this.english= itemView.findViewById(R.id.english_listitem);
            this.korean = itemView.findViewById(R.id.korean_listitem);
            this.deleteItem = itemView.findViewById(R.id.delete_item);
        }
    }

    // (3) => Call this constructor in MainActivity.
    // Constructor's parameter get List(ex. ArrayList) in MainActivity.
    public CustomAdapter(DictionaryDatabase db){
        this.db = db;
    }

    // Create item
    public void setItem(List<DictionaryEntity> data){
        mList = data;
        notifyDataSetChanged();
    }

    // Update item
    public void updateItem(DictionaryEntity dictionaryEntity){
        new Thread(() -> {
            mList.get(idx).setDictID(dictionaryEntity.getDictID());
            mList.get(idx).setEnglish(dictionaryEntity.getEnglish());
            mList.get(idx).setKorea(dictionaryEntity.getKorea());
            db.dictionaryDao().update(mList.get(idx));
        }).start();
    }

    // Delete all items
    public void deleteAll(){
        new Thread(()->{
            mList.clear();
            db.dictionaryDao().deleteAll();
        }).start();
    }

    public void orderASC() {
        new Thread(()-> {
            db.dictionaryDao().setASC();
        }).start();
    }

    public void orderDESC() {
        new Thread(()-> {
            db.dictionaryDao().setDESC();
        }).start();
    }

    // (1) => Callback method. Call at view != null
    // Create ViewHolder object.
    // C, U, D된 view(리스트) 반환. (Read)
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new CustomViewHolder(view);
    }

    // (4)
    // Display ViewHolder data(position) at view.
    // 리스트 추가 및 ViewHolder 구현. (Create)
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        viewHolder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        viewHolder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);

        viewHolder.id.setGravity(Gravity.CENTER);
        viewHolder.english.setGravity(Gravity.CENTER);
        viewHolder.korean.setGravity(Gravity.CENTER);

        viewHolder.id.setText(mList.get(position).getDictID());
        viewHolder.english.setText(mList.get(position).getEnglish());
        viewHolder.korean.setText(mList.get(position).getKorea());

        // Item click event.
        // Send list position and entity.
        final DictionaryEntity dictionary = mList.get(position);
        viewHolder.itemView.setOnClickListener(v -> {
            itemClickListener.OnItemClick(position, dictionary);
            idx = position;
        });

        // 리스트 삭제. (Delete)
        viewHolder.deleteItem.setOnClickListener(v -> {
            // mList.remove(position); => 제대로 동작하지 않음
//                mList.remove(viewHolder.getAdapterPosition());
//                notifyDataSetChanged();
            new Thread(() -> {
                DictionaryEntity dict = mList.remove(viewHolder.getAdapterPosition());
                db.dictionaryDao().delete(dict);
            }).start();
        });
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    // Return all item count.
    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
