package com.example.aluno.cpf;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText cpf;
    boolean atualizando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpf = (EditText) findViewById(R.id.cpf);
        cpf.addTextChangedListener(mascara);

    }


    private final TextWatcher mascara = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            // se adicionamos a máscara, o Android chama o método onTextChange.
            // Para evitar o loop infinito usamos este controle
            if (atualizando) {
                atualizando = false;
                return;
            }
            // Ao apagar o texto, a máscara é removida,
            // então o posicionamento do cursor precisa
            // saber se o texto atual tinha ou não, máscara
            boolean temMascara =
                    s.toString().indexOf('.') > -1 ||
                            s.toString().indexOf('-') > -1;
            // Remove o '.' e '-' da String
            String str = s.toString()
                    .replaceAll("[.]", "")
                    .replaceAll("[-]", "");
            // os parâmetros before e after dizem o tamanho
            // anterior e atual da String digitada, se after > before é
            // porque está digitando, caso contrário, está apagando
            if (after > before) {
                // Se tem mais de 5 caracteres (sem máscara)
                // coloca o '.' e o '-'
                if(str.length() > 8){
                    str = str.substring(0, 3) + '.' +
                            str.substring(3, 6) + '.' +
                            str.substring(6, 9) + '-' +
                            str.substring(9);

                }else if (str.length() > 5) {
                    str = str.substring(0, 3) + '.' +
                            str.substring(3, 6) + '.' +
                            str.substring(6);
                    // Se tem mais de 2, coloca só o ponto
                } else if (str.length() > 3) {
                    str = str.substring(0, 3) + '.' +
                            str.substring(3);
                }
                // Seta a flag para evitar chamada infinita
                atualizando = true;
                // seta o novo texto
                cpf.setText(str);
                // seta a posição do cursor
                cpf.setSelection(cpf.getText().length());
            } else {
                atualizando = true;

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
}
