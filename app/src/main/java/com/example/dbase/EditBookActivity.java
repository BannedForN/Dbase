package com.example.dbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextName, editTextAuthor;
    private Button saveButton;
    private DataBase_Helper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        saveButton = findViewById(R.id.save_button);

        dbHelper = new DataBase_Helper(this);

        // Получаем данные о книге из Intent
        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);
        String bookName = intent.getStringExtra("book_name");
        String bookAuthor = intent.getStringExtra("book_author");

        // Устанавливаем данные в EditText
        editTextName.setText(bookName);
        editTextAuthor.setText(bookAuthor);

        // Обработка нажатия на кнопку сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBookName = editTextName.getText().toString().trim();
                String newBookAuthor = editTextAuthor.getText().toString().trim();

                if (newBookName.isEmpty() || newBookAuthor.isEmpty()) {
                    Toast.makeText(EditBookActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                int result = dbHelper.updateBook(bookId, newBookName, newBookAuthor);

                if (result > 0) {
                    Toast.makeText(EditBookActivity.this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditBookActivity.this, "Ошибка обновления книги", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}