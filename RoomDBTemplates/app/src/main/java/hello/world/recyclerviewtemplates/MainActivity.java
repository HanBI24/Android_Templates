package hello.world.recyclerviewtemplates;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import hello.world.recyclerviewtemplates.DTO.Dictionary;
import hello.world.recyclerviewtemplates.adapters.CustomAdapter;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // Set LinearLayout horizontal
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mArrayList = new ArrayList<>();
        mAdapter = new CustomAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        // 구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button btnInsert = (Button)findViewById(R.id.button_main_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextID = (EditText) view.findViewById(R.id.edittext_dialog_id);
                final EditText editTextEnglish = (EditText) view.findViewById(R.id.edittext_dialog_endlish);
                final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);
                final AlertDialog dialog = builder.create();
                ButtonSubmit.setText("삽입");

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String strID = editTextID.getText().toString();
                        String strEnglish = editTextEnglish.getText().toString();
                        String strKorean = editTextKorean.getText().toString();

                        Dictionary dict = new Dictionary(strID, strEnglish, strKorean );
                        mArrayList.add(0, dict); //첫번째 줄에 삽입됨
                        //mArrayList.add(dict); //마지막 줄에 삽입됨
                        // notifyItemInserted(): 특정 position에 삽입하고 싶을 때 사용
                        mAdapter.notifyItemInserted(0);
                        //mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        // 1. 인터페이스로 구현
        // 2. CustomAdapter에 인터페이스의 Callback 메서드 Overriding 구현
        // 3. MainActivity에서 mAdapter 객체 생성 후 Callback method Overriding
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int position, Dictionary dictionary) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setTitle("Update User Info")
                       .setView(view);

                Button btnReplace = (Button) view.findViewById(R.id.button_dialog_submit);
                btnReplace.setText("수정");

                final EditText editTextID = (EditText) view.findViewById(R.id.edittext_dialog_id);
                final EditText editTextEnglish = (EditText) view.findViewById(R.id.edittext_dialog_endlish);
                final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);

                final AlertDialog dialog = builder.create();
                dialog.show();

                btnReplace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strID = editTextID.getText().toString();
                        String strEnglish = editTextEnglish.getText().toString();
                        String strKorean = editTextKorean.getText().toString();
                        Dictionary dict = new Dictionary(strID, strEnglish, strKorean );
                        mArrayList.set(position, dict);
                        mAdapter.notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

            }
        });
    }
}