package hello.world.recyclerviewtemplates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter mAdapter;
    private DictionaryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // Set LinearLayout horizontal
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        mArrayList = new ArrayList<>();
//        mAdapter = new CustomAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        // 구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        db = DictionaryDatabase.getDatabase(MainActivity.this);
        mAdapter = new CustomAdapter(db);
        mRecyclerView.setAdapter(mAdapter);

        db.dictionaryDao().getAll().observe(this, dictionaryEntities -> mAdapter.setItem(dictionaryEntities));

        Button btnInsert = findViewById(R.id.button_main_insert);
        btnInsert.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.edit_box, null, false);
            builder.setView(view);

            final Button ButtonSubmit = view.findViewById(R.id.button_dialog_submit);
            final EditText editTextID = view.findViewById(R.id.edittext_dialog_id);
            final EditText editTextEnglish = view.findViewById(R.id.edittext_dialog_endlish);
            final EditText editTextKorean = view.findViewById(R.id.edittext_dialog_korean);
            final AlertDialog dialog = builder.create();
            ButtonSubmit.setText("삽입");

            ButtonSubmit.setOnClickListener(v1 -> {
                String strID = editTextID.getText().toString();
                String strEnglish = editTextEnglish.getText().toString();
                String strKorean = editTextKorean.getText().toString();
                new Thread(()-> {
                    DictionaryEntity dict = new DictionaryEntity(strID, strEnglish, strKorean );
                    db.dictionaryDao().insert(dict);
                }).start();
                //mArrayList.add(0, dict); //첫번째 줄에 삽입됨
                //mArrayList.add(dict); //마지막 줄에 삽입됨
                //notifyItemInserted(): 특정 position에 삽입하고 싶을 때 사용
                //mAdapter.notifyItemInserted(0);
                //mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.show();
        });

        // 1. 인터페이스로 구현
        // 2. CustomAdapter에 인터페이스의 Callback 메서드 Overriding 구현
        // 3. MainActivity에서 mAdapter 객체 생성 후 Callback method Overriding
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int position, DictionaryEntity dictionary) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setTitle("Update User Info")
                       .setView(view);

                Button btnReplace = view.findViewById(R.id.button_dialog_submit);
                btnReplace.setText("수정");

                final EditText editTextID = view.findViewById(R.id.edittext_dialog_id);
                final EditText editTextEnglish = view.findViewById(R.id.edittext_dialog_endlish);
                final EditText editTextKorean = view.findViewById(R.id.edittext_dialog_korean);

                final AlertDialog dialog = builder.create();
                dialog.show();

                btnReplace.setOnClickListener(v -> {
                    String strID = editTextID.getText().toString();
                    String strEnglish = editTextEnglish.getText().toString();
                    String strKorean = editTextKorean.getText().toString();
                    DictionaryEntity dict = new DictionaryEntity(strID, strEnglish, strKorean );
                    //mArrayList.set(position, dict);
//                        mAdapter.notifyItemChanged(position);
                    mAdapter.updateItem(dict);
                    dialog.dismiss();
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) mAdapter.deleteAll();
        if(item.getItemId() == R.id.order_asc) mAdapter.orderASC();
        if(item.getItemId() == R.id.order_desc) mAdapter.orderDESC();
        return super.onOptionsItemSelected(item);
    }
}