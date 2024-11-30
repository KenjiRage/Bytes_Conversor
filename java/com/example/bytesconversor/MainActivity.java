package com.example.bytesconversor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

        private Spinner spinnerUnidadOrigen_MJCS;
        private Spinner spinnerUnidadDestino_MJCS;
        private EditText editTextCantidad_MJCS;
        private Button buttonConvertirOrigenDestino_MJCS;
        private Button buttonConvertirDestinoOrigen_MJCS;
        private TextView textViewResultado_MJCS;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // Inicialización de vistas
                spinnerUnidadOrigen_MJCS = findViewById(R.id.spinnerUnidadOrigen_MJCS);
                spinnerUnidadDestino_MJCS = findViewById(R.id.spinnerUnidadDestino_MJCS);
                editTextCantidad_MJCS = findViewById(R.id.editTextCantidad_MJCS);
                buttonConvertirOrigenDestino_MJCS = findViewById(R.id.buttonConvertirOrigenDestino_MJCS);
                buttonConvertirDestinoOrigen_MJCS = findViewById(R.id.buttonConvertirDestinoOrigen_MJCS);
                textViewResultado_MJCS = findViewById(R.id.textViewResultado_MJCS);

                // Adaptador para Spinner
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.unidades_byte_MJCS, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnidadOrigen_MJCS.setAdapter(adapter);
                spinnerUnidadDestino_MJCS.setAdapter(adapter);

                // Configuración del botón para convertir de origen a destino
                buttonConvertirOrigenDestino_MJCS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                convertir(true);
                        }
                });

                // Configuración del botón para convertir de destino a origen
                buttonConvertirDestinoOrigen_MJCS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                convertir(false);
                        }
                });
        }

        // Función para convertir unidades
        private void convertir(boolean origenADestino) {
                String cantidadStr = editTextCantidad_MJCS.getText().toString();
                if (TextUtils.isEmpty(cantidadStr)) {
                        Toast.makeText(this, "Introduce un valor válido", Toast.LENGTH_SHORT).show();
                        return;
                }

                double cantidad = Double.parseDouble(cantidadStr);
                String unidadOrigen = spinnerUnidadOrigen_MJCS.getSelectedItem().toString();
                String unidadDestino = spinnerUnidadDestino_MJCS.getSelectedItem().toString();

                if (unidadOrigen.isEmpty() || unidadDestino.isEmpty()) {
                        Toast.makeText(this, "Selecciona las unidades de origen y destino", Toast.LENGTH_SHORT).show();
                        return;
                }

                double resultado = 0;
                if (origenADestino) {
                        resultado = convertirUnidades(cantidad, unidadOrigen, unidadDestino);
                } else {
                        resultado = convertirUnidades(cantidad, unidadDestino, unidadOrigen);
                }

                textViewResultado_MJCS.setText(formatearResultado(resultado));
        }

        // Método para la lógica de conversión
        private double convertirUnidades(double cantidad, String origen, String destino) {
                double factorOrigen = obtenerFactorConversion(origen);
                double factorDestino = obtenerFactorConversion(destino);
                return cantidad * factorOrigen / factorDestino;
        }

        // Método para obtener el factor de conversión en relación a Bytes
        private double obtenerFactorConversion(String unidad) {
                switch (unidad) {
                        case "byte":
                                return 1;
                        case "kilobyte (Kb)":
                                return Math.pow(1024, 1);  // 1 KB = 1024 Bytes
                        case "megabyte (MB)":
                                return Math.pow(1024, 2);  // 1 MB = 1024 KB
                        case "gigabyte (GB)":
                                return Math.pow(1024, 3);  // 1 GB = 1024 MB
                        case "terabyte (TB)":
                                return Math.pow(1024, 4);  // 1 TB = 1024 GB
                        case "petabyte (PB)":
                                return Math.pow(1024, 5);  // 1 PB = 1024 TB
                        case "exabyte (EB)":
                                return Math.pow(1024, 6);  // 1 EB = 1024 PB
                        case "zetabyte (ZB)":
                                return Math.pow(1024, 7);  // 1 ZB = 1024 EB
                        case "yottabyte (YB)":
                                return Math.pow(1024, 8);  // 1 YB = 1024 ZB
                        case "brontobyte (BB)":
                                return Math.pow(1024, 9);  // 1 BB = 1024 YB
                        case "geopbyte (GB)":
                                return Math.pow(1024, 10); // 1 GB = 1024 BB
                        default:
                                return 1;
                }
        }


        // Método para formatear el resultado con dos decimales o notación científica si es necesario
        private String formatearResultado(double resultado) {
                if (resultado >= Math.pow(10, 6) || resultado <= Math.pow(10, -3)) {
                        return String.format("%.2E", resultado); // Notación científica
                } else {
                        return String.format("%.2f", resultado); // Dos decimales
                }
        }
}
