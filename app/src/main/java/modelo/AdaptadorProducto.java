package modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.dev4u.hv.guia7.R;

import java.util.List;

public class AdaptadorProducto extends ArrayAdapter<Producto> {

    public AdaptadorProducto(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Producto obj = getItem(position);
        //TODO inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblDescripcion=(TextView)convertView.findViewById(R.id.lblDescripcion_product);
        TextView lblNombre = (TextView) convertView.findViewById(R.id.lblNombre_product);
        TextView lblID_Product = (TextView) convertView.findViewById(R.id.lblId_Product);
        // mostrar los datos
        lblNombre.setText("Nombre: "+obj.getNombre());
        lblID_Product.setText(obj.getId_producto());
        lblDescripcion.setText("Descripcion: "+obj.getDescripcion());
        // Return la convertView ya con los datos
        return convertView;
    }
}
