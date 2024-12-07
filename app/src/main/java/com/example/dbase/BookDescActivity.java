package com.example.dbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookDescActivity extends AppCompatActivity {

    private TextView bookNameTextView, bookAuthorTextView;
    private Button deleteButton, editButton;
    private DataBase_Helper dbHelper;
    private int bookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_desc);

        bookNameTextView = findViewById(R.id.book_name_detail);
        bookAuthorTextView = findViewById(R.id.book_author_detail);
        deleteButton = findViewById(R.id.delete_button);
        editButton = findViewById(R.id.edit_button);

        dbHelper = new DataBase_Helper(this);

        // Получаем данные о книге из Intent
        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);
        String bookName = intent.getStringExtra("book_name");
        String bookAuthor = intent.getStringExtra("book_author");

        // Устанавливаем данные в TextView
        bookNameTextView.setText(bookName);
        bookAuthorTextView.setText(bookAuthor);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(BookDescActivity.this, EditBookActivity.class);
                editIntent.putExtra("book_id", bookId);
                editIntent.putExtra("book_name", bookName);
                editIntent.putExtra("book_author", bookAuthor);
                startActivity(editIntent);
            }
        });

        // Обработка нажатия на кнопку удаления
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = dbHelper.deleteBookById(bookId);
                if (result > 0) {
                    Toast.makeText(BookDescActivity.this, "Книга удалена", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookDescActivity.this, "Ошибка удаления книги", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}