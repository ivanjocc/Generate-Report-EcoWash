package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Declarar el nuevo campo para transferencias
    private EditText inputValue, inputGrafitos, inputDate, inputTransferencias;
    private CheckBox checkboxHoliday;
    private LinearLayout egresosContainer;
    private ArrayList<Map<String, EditText>> egresosFields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los elementos
        inputValue = findViewById(R.id.input_value);
        inputGrafitos = findViewById(R.id.input_grafitos);
        inputTransferencias = findViewById(R.id.input_transferencias);
        inputDate = findViewById(R.id.input_date);
        checkboxHoliday = findViewById(R.id.checkbox_holiday);
        egresosContainer = findViewById(R.id.egresos_container);
        Button btnAddEgreso = findViewById(R.id.btn_add_egreso);
        Button btnCalculate = findViewById(R.id.btn_calculate);

        // Inicializar la lista de campos de egresos
        egresosFields = new ArrayList<>();

        // Configurar el selector de fecha
        inputDate.setOnClickListener(v -> showDatePickerDialog());

        // Configurar el botón para agregar un nuevo campo de egreso (con descripción y valor)
        btnAddEgreso.setOnClickListener(v -> addEgresoField());

        // Configurar el botón para calcular y mostrar el resumen
        btnCalculate.setOnClickListener(v -> calculateAndShowSummary());
    }

    private void showDatePickerDialog() {
        // Obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar el diálogo de selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            (view, selectedYear, selectedMonth, selectedDay) -> {
                // Mostrar la fecha seleccionada en el campo de texto
                inputDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }, year, month, day);

        datePickerDialog.show();
    }

    private void addEgresoField() {
        // Crear un nuevo LinearLayout para los campos de descripción y valor del egreso
        LinearLayout egresoLayout = new LinearLayout(this);
        egresoLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Crear campo para la descripción del egreso
        EditText descriptionField = new EditText(this);
        descriptionField.setHint("Descripción");
        descriptionField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        // Crear campo para el valor del egreso
        EditText valueField = new EditText(this);
        valueField.setHint("Valor");
        valueField.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        valueField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        // Agregar los campos al LinearLayout
        egresoLayout.addView(descriptionField);
        egresoLayout.addView(valueField);

        // Añadir el layout de egreso al contenedor de egresos
        egresosContainer.addView(egresoLayout);

        // Guardar el par de campos en el ArrayList de egresos
        Map<String, EditText> egresoMap = new HashMap<>();
        egresoMap.put("descripcion", descriptionField);
        egresoMap.put("valor", valueField);
        egresosFields.add(egresoMap);
    }

    private void calculateAndShowSummary() {
        String valueText = inputValue.getText().toString();
        String grafitosText = inputGrafitos.getText().toString();
        String transferenciasText = inputTransferencias.getText().toString();
        String fechaSeleccionada = inputDate.getText().toString();

        if (valueText.isEmpty() || grafitosText.isEmpty() || transferenciasText.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorBruto = Double.parseDouble(valueText);
        double grafitos = Double.parseDouble(grafitosText);
        double transferencias = Double.parseDouble(transferenciasText);

        // Obtener el porcentaje para trabajadores según el día
        double porcentajeTrabajadores = checkboxHoliday.isChecked() ? 0.40 : 0.35;
        double valorTrabajadores = valorBruto * porcentajeTrabajadores;

        // Sumar todos los valores de egresos ingresados
        double totalEgresos = 0;
        for (Map<String, EditText> egresoField : egresosFields) {
            EditText valueField = egresoField.get("valor");
            String egresoText = valueField.getText().toString();
            if (!egresoText.isEmpty()) {
                totalEgresos += Double.parseDouble(egresoText);
            }
        }

        // Calcular el total neto
        double total = valorBruto + grafitos - valorTrabajadores - transferencias - totalEgresos;

        // Enviar los datos a SummaryActivity
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("fecha", fechaSeleccionada);
        intent.putExtra("valorBruto", valorBruto);
        intent.putExtra("grafitos", grafitos);
        intent.putExtra("transferencias", transferencias);
        intent.putExtra("porcentajeTrabajadores", porcentajeTrabajadores * 100);
        intent.putExtra("valorTrabajadores", valorTrabajadores);
        intent.putExtra("totalEgresos", totalEgresos);
        intent.putExtra("total", total);
        startActivity(intent);
    }
}