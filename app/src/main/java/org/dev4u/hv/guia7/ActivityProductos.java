package org.dev4u.hv.guia7;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Guard;
import java.util.ArrayList;

import modelo.AdaptadorProducto;
import modelo.Categoria;
import modelo.DB;
import modelo.Producto;

public class ActivityProductos extends AppCompatActivity {

    private TextView lbltxtNombreCat, lblId_ProductoMain;
    private EditText txtProducto, txtDescripcion;
    private Button btnGuardar, btnEliminar;
    private ListView ListViewProducto;

    //lista de datos (categoria)
    private ArrayList<Producto> lstProducto;
    //sirve para manejar la eliminacion
    private Producto producto_temp=null;
    private DB db;
    private AdaptadorProducto adaptadorProducto;
    private String idCategoriaIntent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Inicializamos los controles
        lblId_ProductoMain=findViewById(R.id.lblId_ProductoMain);
        lbltxtNombreCat=findViewById(R.id.lbltxtNombreCat);
        txtProducto=findViewById(R.id.txtProducto);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        btnGuardar=findViewById(R.id.btnGuardar);
        btnEliminar=findViewById(R.id.btnEliminar);
        ListViewProducto=findViewById(R.id.lstProductos);
        lblId_ProductoMain.setText(null);
        idCategoriaIntent=getIntent().getStringExtra("IDCategoria");
        lbltxtNombreCat.setText(getIntent().getStringExtra("NombreCat"));

        //Inicializamos la BD
        db                      = new DB(this);
        lstProducto            = db.getArrayProducto(
                db.getCursorProducto(idCategoriaIntent)
        );
        if(lstProducto==null)//si no hay datos
            lstProducto = new ArrayList<>();
        adaptadorProducto      = new AdaptadorProducto(this,lstProducto);
        ListViewProducto.setAdapter(adaptadorProducto);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });
        ListViewProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seleccionar(lstProducto.get(i));
            }
        });
    }
    private void guardar(){
        Producto producto = new Producto(lblId_ProductoMain.getText().toString(),txtProducto.getText().toString(), txtDescripcion.getText().toString(),idCategoriaIntent);
        producto_temp=null;
        if(db.guardar_O_ActualizarProducto(producto)){
            Toast.makeText(this,"Se guardo el producto",Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstProducto.clear();
            lstProducto.addAll(db.getArrayProducto(
                    db.getCursorProducto(idCategoriaIntent)
            ));

            adaptadorProducto.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(producto_temp!=null){
            db.borrarProducto(producto_temp.getId_producto());
            lstProducto.remove(producto_temp);
            adaptadorProducto.notifyDataSetChanged();
            producto_temp=null;
            Toast.makeText(this,"Se elimino el producto",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Producto producto){
        producto_temp = producto;
        lblId_ProductoMain.setText(producto.getId_categoria());
        txtProducto.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
    }
    private void limpiar(){
        lblId_ProductoMain.setText(null);
        txtProducto.setText(null);
        txtDescripcion.setText(null);
    }

}
