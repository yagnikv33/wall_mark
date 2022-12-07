package com.wallmark;

import static android.view.KeyEvent.KEYCODE_DEL;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.utils.Helpers;

public class PwdAct extends AppCompatActivity {

    EditText[] otpETs = new EditText[6];
    MaterialButton btnVerify = findViewById(R.id.btn_verify);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);

        otpETs[0] = findViewById(R.id.otpET1);
        otpETs[1] = findViewById(R.id.otpET2);
        otpETs[2] = findViewById(R.id.otpET3);
        otpETs[3] = findViewById(R.id.otpET4);
        otpETs[4] = findViewById(R.id.otpET5);
        otpETs[5] = findViewById(R.id.otpET6);

        btnVerify.setOnClickListener(v -> {
            String code = Helpers.rS(otpETs[0]) + Helpers.rS(otpETs[1]) +
                    Helpers.rS(otpETs[2]) + Helpers.rS(otpETs[3]) + Helpers.rS(otpETs[4])
                    + Helpers.rS(otpETs[5]);

            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 7 || keyCode == 8 ||
                keyCode == 9 || keyCode == 10 ||
                keyCode == 11 || keyCode == 12 ||
                keyCode == 13 || keyCode == 14 ||
                keyCode == 15 || keyCode == 16 ||
                keyCode == 67) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                int index = checkWhoHasFocus();
                if (keyCode == KEYCODE_DEL) {
                    if (index != 123) {
                        if (Helpers.rS(otpETs[index]).equals("")) {
                            if (index != 0) {
                                otpETs[index - 1].requestFocus();
                            }
                        } else {
                            return super.dispatchKeyEvent(event);
                        }
                    }
                } else {
                    if (index != 123) {
                        if (Helpers.rS(otpETs[index]).equals("")) {
                            return super.dispatchKeyEvent(event);
                        } else {
                            if (index != 5) {
                                otpETs[index + 1].requestFocus();
                            }
                        }
                    }
                    return super.dispatchKeyEvent(event);
                }
            }
        } else {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    private int checkWhoHasFocus() {
        for (int i = 0; i < otpETs.length; i++) {
            EditText tempET = otpETs[i];
            if (tempET.hasFocus()) {
                return i;
            }
        }
        return 123;
    }
}