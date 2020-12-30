package hello.world.recyclerviewtemplates;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private final ArrayList<Dictionary> mList;
    private final ArrayList<String> items = new ArrayList<>();

    // Save view by ViewHolder class.
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView english;
        protected TextView korean;
        protected CheckBox checkBox;
        protected ConstraintLayout constraintLayout;

        private boolean multiSelect = false;
        private final ArrayList<String> selectedItems = new ArrayList<String>();
        private final ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                multiSelect = true;
                menu.add("Delete");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                for (Dictionary intItem : mList) {
                    mList.remove(intItem);
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                multiSelect = false;
                selectedItems.clear();
                notifyDataSetChanged();
            }
        };

        // (2)
        // RecyclerView need to make ViewHolder to yourself.
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.id = (TextView) itemView.findViewById(R.id.id_listitem);
            this.english= (TextView) itemView.findViewById(R.id.english_listitem);
            this.korean = (TextView) itemView.findViewById(R.id.korean_listitem);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.check_box);
            this.constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.constraint);
            for(int i=0; i<mList.size(); i++){
                items.add(String.valueOf(i));
            }
        }

        void update(String value) {
            korean.setText(value + "");
            if (selectedItems.contains(value)) {
                constraintLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                constraintLayout.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity) view.getContext()).startActionMode(actionModeCallbacks);
                    selectItem(value);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItem(value);
                }
            });
        }

        void selectItem(String item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    constraintLayout.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    constraintLayout.setBackgroundColor(Color.LTGRAY);
                }
            }
        }
    }

    // (3) => Call this constructor in MainActivity.
    // Constructor's parameter get List(ex. ArrayList) in MainActivity.
    public CustomAdapter(ArrayList<Dictionary> list){
        this.mList = list;
    }

    // (1) => Callback method. Call at view != null
    // Create ViewHolder object.
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new CustomViewHolder(view);
    }

    // (4)
    // Display ViewHolder data(position) at view.
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
        viewHolder.update(items.get(position));
    }


    // Return all item count.
    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
