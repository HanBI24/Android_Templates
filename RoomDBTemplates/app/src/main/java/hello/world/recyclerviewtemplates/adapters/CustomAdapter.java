package hello.world.recyclerviewtemplates.Adapters;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hello.world.recyclerviewtemplates.DTO.Dictionary;
import hello.world.recyclerviewtemplates.ItemClickListener;
import hello.world.recyclerviewtemplates.R;

// RecyclerView CRUD.
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private final ArrayList<Dictionary> mList;
    ItemClickListener itemClickListener;

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

            this.id = (TextView) itemView.findViewById(R.id.id_listitem);
            this.english= (TextView) itemView.findViewById(R.id.english_listitem);
            this.korean = (TextView) itemView.findViewById(R.id.korean_listitem);
            this.deleteItem = (TextView) itemView.findViewById(R.id.delete_item);
        }

    }

    // (3) => Call this constructor in MainActivity.
    // Constructor's parameter get List(ex. ArrayList) in MainActivity.
    public CustomAdapter(ArrayList<Dictionary> list){
        this.mList = list;
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

        viewHolder.id.setText(mList.get(position).getId());
        viewHolder.english.setText(mList.get(position).getEnglish());
        viewHolder.korean.setText(mList.get(position).getKorean());

        // 리스트 수정. (Update)
        final Dictionary dictionary = mList.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClick(position, dictionary);
            }
        });

        // 리스트 삭제. (Delete)
        viewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mList.remove(position); => 제대로 동작하지 않음
                mList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
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
