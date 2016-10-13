package it.trumbl.ilprofeticoDaniele.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.trumbl.ilprofeticoDaniele.R;
import it.trumbl.ilprofeticoDaniele.SharedPreference;
import it.trumbl.ilprofeticoDaniele.models.Himno;

/**
 * Created by jhonlimaster on 07-12-15.
 */
public class HimnoAdapter extends BaseAdapter {

    private static final String TAG = "HimnoAdapter";
    private Context context;
    private ArrayList<Himno> himnos;
    private ArrayList<String> preferedList;

    public HimnoAdapter(Context context, ArrayList<Himno> himnos) {
        this.context = context;
        this.himnos = himnos;
        ReloadPrefered();
    }

    @Override
    public int getCount() {
        return himnos.size();
    }

    @Override
    public Object getItem(int i) {
        return himnos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return himnos.indexOf(himnos.get(i));
    }

    public void ReloadPrefered() {
        SharedPreference preference = new SharedPreference(context);
        preferedList = preference.loadFavorites();

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_himno, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Himno himno = himnos.get(i);

        viewHolder.numeroHimno.setText(String.valueOf(himno.getNumero()));
        viewHolder.titleHim.setText(himno.getTitle());

        if (!preferedList.contains(String.valueOf(himno.getNumero()))) {
            viewHolder.imageView.setImageResource(R.drawable.ic_star_border_white_24dp);
        } else {

            viewHolder.imageView.setImageResource(R.drawable.ic_star_white_24dp);
        }

        return view;
    }

    public void setData(ArrayList<Himno> data) {
        this.himnos = data;
    }

    public class ViewHolder {
        TextView numeroHimno;
        TextView titleHim;
        ImageView imageView;


        public ViewHolder(View itemView) {
            numeroHimno = (TextView) itemView.findViewById(R.id.numero_himno);
            titleHim = (TextView) itemView.findViewById(R.id.title_himno);
            imageView = (ImageView) itemView.findViewById(R.id.prefered_himn);

        }
    }
}