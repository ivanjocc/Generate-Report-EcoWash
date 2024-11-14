package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Obtener referencias a las vistas
        TextView txtFecha = findViewById(R.id.txt_fecha);
        TextView txtValorBruto = findViewById(R.id.txt_valor_bruto);
        TextView txtPorcentajeTrabajadores = findViewById(R.id.txt_porcentaje_trabajadores);
        TextView txtValorTrabajadores = findViewById(R.id.txt_valor_trabajadores);
        TextView txtGrafitos = findViewById(R.id.txt_grafitos);
        TextView txtTransferencias = findViewById(R.id.txt_transferencias);
        TextView txtTotalEgresos = findViewById(R.id.txt_total_egresos);
        TextView txtTotalFinal = findViewById(R.id.txt_total_final);

        // Crear un DecimalFormat para formatear sin ceros innecesarios
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Obtener los datos de MainActivity
        String fecha = getIntent().getStringExtra("fecha");
        double valorBruto = getIntent().getDoubleExtra("valorBruto", 0);
        double porcentajeTrabajadores = getIntent().getDoubleExtra("porcentajeTrabajadores", 0);
        double valorTrabajadores = getIntent().getDoubleExtra("valorTrabajadores", 0);
        double grafitos = getIntent().getDoubleExtra("grafitos", 0);
        double transferencias = getIntent().getDoubleExtra("transferencias", 0);
        double totalEgresos = getIntent().getDoubleExtra("totalEgresos", 0);
        double total = getIntent().getDoubleExtra("total", 0);

        // Asignar la fecha
        txtFecha.setText(fecha != null ? fecha : "Fecha no especificada");

        // Formatear y asignar cada valor
        txtValorBruto.setText("$ " + decimalFormat.format(valorBruto));
        txtPorcentajeTrabajadores.setText(decimalFormat.format(porcentajeTrabajadores) + "%");
        txtValorTrabajadores.setText("$ " + decimalFormat.format(valorTrabajadores));
        txtGrafitos.setText("$ " + decimalFormat.format(grafitos));
        txtTransferencias.setText("$ " + decimalFormat.format(transferencias));
        txtTotalEgresos.setText("$ " + decimalFormat.format(totalEgresos));
        txtTotalFinal.setText("$ " + decimalFormat.format(total));
    }
}