package com.example.integrador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import android.annotation.SuppressLint;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.integrador.R.id.img_perfil;

public class Cadastro extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 40, message = "O nome deve ter entre 3 e 40 caracteres")
    private EditText etNome;

    @NotEmpty(message = "Campo obrigatório!")
    @Email(message = "Email inválido!")
    private EditText etEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Senha deve conter no mínimo 6 números!")
    private EditText etSenha;

    @NotEmpty(message = "Campo obrigatório!")
    @ConfirmPassword(message = "Senha não confirmado!")
    private EditText etConfSenha;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 10, max = 10, message = "Deve ser digitado a sua matrícula")
    private EditText etMatricula;


    private Button btCadastro;
    private Bitmap bitmap;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.inicializaComponentes();
        this.btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.cb_cadastrar:
                if (checked) {
                } else
                    break;
        }
    }

    private void inicializaComponentes() {
        this.etNome = findViewById(R.id.et_nome_completo);
        this.etEmail = findViewById(R.id.et_cadastro_email);
        this.etSenha = findViewById(R.id.et_cadastrar_senha);
        this.etConfSenha = findViewById(R.id.et_conf_senha);
        this.btCadastro = findViewById(R.id.bt_cadastro);
        this.etMatricula = findViewById(R.id.et_matricula);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "PASSOU NA VALIDAÇÃO!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "ERRO NA VALIDAÇÃO!", Toast.LENGTH_LONG).show();
        for (ValidationError erro : errors) {
            View componente = erro.getView();
            String mensagemErro = erro.getCollatedErrorMessage(this);
            if (componente instanceof EditText) {
                ((TextView) componente).setError(mensagemErro);
            }
        }


    }

    public class Camera extends Activity {
        ImageView img;

        @Override
        public void onCreate(Bundle savedInstanteState) {
            super.onCreate(savedInstanteState);
            setContentView(R.layout.activity_cadastro);

            img = findViewById(img_perfil);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirCamera();
                }
            });
        }

        public void abrirCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }

        protected void onActivityResul(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            InputStream stream = null;
            if (requestCode == 0 && resultCode == RESULT_OK) {
                try {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (stream != null)
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }}
        }

        private Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeigth) {
            Bitmap novoBmp = null;

            int w = bmpOriginal.getWidth();
            int h = bmpOriginal.getHeight();

            float destinyFactor = context.getResources().getDisplayMetrics().density;
            float novoW = newWidth * destinyFactor;
            float novoH = newHeigth * destinyFactor;

            float scalaW = novoW / w;
            float scalaH = novoH / h;

            Matrix matrix = new Matrix();
            matrix.postScale(scalaW, scalaH);

            novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

            return novoBmp;
        }
    }}