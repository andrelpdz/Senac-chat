package alpdz.chat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity{
    private EditText codigo;
    private EditText nome;
    private Button btAdicionar;

    Gerenciador dbm;
    Cursor dados;
    ListarDados ld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define Banco
        dbm = new Gerenciador(getApplicationContext());

        // Define Elementos
        codigo = findViewById(R.id.codigo);
        nome = findViewById(R.id.nome);
        btAdicionar = findViewById(R.id.btAdicionar);

        // Evento Click
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = codigo.getText().toString();
                String n = nome.getText().toString();
                dbm.addUsuario(c,n);

                codigo.setText("Código");
                nome.setText("Nome");

                atualizarLista();
            }
        });

        // Listando Dados
        atualizarLista();
    }

    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Você clicou em: " + ld.getItem(position) + " N: " + position, Toast.LENGTH_SHORT).show();
    }

    public void atualizarLista(){
        // Criando Lista de nomes
        ArrayList<String> lNomes = new ArrayList<>();
        dados = dbm.listarUsuarios();

        while(!dados.isLast()) {
            String registro = "Cod: "+dados.getString(0);
            registro+= " - Nome:"+dados.getString(1);
            lNomes.add(registro);
            dados.moveToNext();
        }

        // Atualizando RecyclerView
        RecyclerView recyclerView = findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ld = new ListarDados(this, lNomes);
        ld.setClickListener(this::onItemClick);
        recyclerView.setAdapter(ld);
    }
}