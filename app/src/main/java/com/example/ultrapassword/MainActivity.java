package com.example.ultrapassword;

import static com.example.ultrapassword.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultrapassword.models.LowerCaseGenerator;
import com.example.ultrapassword.models.NumericGenerator;
import com.example.ultrapassword.models.PasswordGenerator;
import com.example.ultrapassword.models.SpecialCharGenerator;
import com.example.ultrapassword.models.UpperCaseGenerator;

public class MainActivity extends AppCompatActivity {
    private EditText editPasswordSize;
    private TextView textPasswordGenerated,textErrorMassage;
    private CheckBox checkLower,checkUpper,checkNumeric,checkSpecial;
    private SeekBar seekBar;
    private Button btnGenerate, btnCopy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        initViews();
        clickListeners();
    }


    private void clickListeners(){
        btnGenerate.setOnClickListener(View -> {
           int passwordSize = Integer.parseInt(editPasswordSize.getText().toString());
           textErrorMassage.setText("");
           if (passwordSize<8 || passwordSize>60) {
                textErrorMassage.setText("Password size must be 8-60");
                return;
           }

           PasswordGenerator.clear();
            if (checkLower.isChecked()) PasswordGenerator.add(new LowerCaseGenerator());
            if (checkNumeric.isChecked()) PasswordGenerator.add(new NumericGenerator());
            if (checkUpper.isChecked()) PasswordGenerator.add(new UpperCaseGenerator());
            if (checkSpecial.isChecked()) PasswordGenerator.add(new SpecialCharGenerator());

            if(PasswordGenerator.isEmpty()){
                textErrorMassage.setText("Please select at least one password content type");
                return;
            }

            String password = PasswordGenerator.generatorPassword(passwordSize);
            textPasswordGenerated.setText(password);

        });
            btnCopy.setOnClickListener(view ->{
                ClipboardManager manager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText("password",textPasswordGenerated.getText().toString()));
                Toast.makeText(this,"Password Copied!",Toast.LENGTH_SHORT).show();

        });
    }
    private void  initViews(){
        editPasswordSize = findViewById(id.edit_pwd_size);
        textPasswordGenerated = findViewById(id.text_password_result);
        textErrorMassage = findViewById(id.text_error);
        checkLower = findViewById(id.check_lower);
        checkUpper = findViewById(id.check_upper);
        checkNumeric = findViewById(id.check_numeric);
        checkSpecial = findViewById(id.check_special);
        seekBar = findViewById(id.seek_Bar);
        btnGenerate = findViewById(id.btn_generate);
        btnCopy = findViewById(id.btn_copy);


    }
}