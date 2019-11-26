package com.example.integrador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btEntrar;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inicializaComponentes();

        this.btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itLogin = new Intent(MainActivity.this, Login.class);

                startActivity(itLogin);
                finish();
            }
        });

        this.btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itCadastro = new Intent(MainActivity.this, Cadastro.class);

                startActivity(itCadastro);
                finish();
            }
        });
    }
    private void inicializaComponentes(){
        this.btEntrar = findViewById(R.id.bt_entrar);
        this.btCadastrar = findViewById(R.id.bt_cadastrar);
    }
}
