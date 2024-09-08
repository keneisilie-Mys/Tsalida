package com.example.tsalida;
//For the one that uses the card_view

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class SongTitleAdapter extends RecyclerView.Adapter<SongTitleAdapter.ViewHolder> {
    String[] angamiTitle;
    String[] englishTitle;

    SongTitleAdapter(String[] angamiTitle, String[] englishTitle){
        this.angamiTitle = angamiTitle;
        this.englishTitle = englishTitle;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }



    @Override
    public int getItemCount() {
        return angamiTitle.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Getting the views
        CardView cardView = holder.cardView;
        TextView angamiText = cardView.findViewById(R.id.angami_title);
        TextView englishText = cardView.findViewById(R.id.english_title);
        TextView numbering = cardView.findViewById(R.id.index);

        //Setting the views
        angamiText.setText(angamiTitle[position]);
        englishText.setText(englishTitle[position]);
        numbering.setText(String.valueOf(position+1));

        int positionFinal = position;
        int positionEdited = adjustPosition(positionFinal);
        //Adding on click to the viewHolder
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClickViewHolder(positionEdited);
                }
            }
        });
    }


    //Making an interface so that the adapter can execute a code in the listener(the activity/fragment)
    interface Listener{
        void onClickViewHolder(int position);
    }
    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public int adjustPosition(int pos){
        if(pos >11 && pos <17){
            return pos-1;
        } else if (pos >16 && pos <30) {
            return pos-2;
        }else if (pos >29 && pos <39) {
            return pos-3;
        }else if (pos >38 && pos <56) {
            return pos-4;
        }else if (pos >55 && pos <75) {
            return pos-5;
        }else if (pos >74 && pos <88) {
            return pos-6;
        }else if (pos >87 && pos <97) {
            return pos-7;
            //////
        } else if (pos > 96 && pos <104) {
            return pos - 6;
        }else if (pos > 103 && pos <113) {
            return pos-7;
        }else if (pos > 112 && pos <126 || pos == 127) {
            return pos-8;
        }//////////////////
        else if (pos ==126 || pos > 127 && pos<135 ) {
            return pos - 9;
        } else if (pos > 134 && pos <146) {
            return pos - 10;
        }else if (pos > 145 && pos <160) {
            return pos - 11;
        }else if (pos > 159 && pos <166) {
            return pos - 12;
            ///////////////////
        }else if (pos > 165 && pos <203) {
            return pos - 11;////correct
        }else if (pos > 202 && pos <209) {
            return pos - 12;
        }else if (pos > 208 && pos <219) {
            return pos - 13;
        }else if (pos > 218 && pos <224) {
            return pos - 14;
        }else if (pos >223  && pos <239) {
            return pos - 13;///////////
        }else if (pos > 238 && pos <244) {
            return pos - 12;
        }else if (pos > 243 && pos <249) {
            return pos - 11;
        }else if (pos > 248 && pos <254) {
            return pos - 10;
        }else if (pos > 253 && pos <261) {
            return pos - 11;
        }else if (pos > 260 && pos <264) {
            return pos - 10;
        }else if (pos > 263 && pos <267) {
            return pos - 11;
        }else if (pos > 266 && pos <270) {
            return pos - 10;
        }else if (pos > 269 && pos <275) {
            return pos - 11;
        }else if (pos > 274 && pos <292) {
            return pos - 12;
        }else if (pos > 291 && pos <305) {
            return pos - 13;
        }else if (pos > 304 && pos <313) {
            return pos - 14;
        }else if (pos > 312 && pos <323) {
            return pos - 15;
        }else if (pos > 322 && pos <374) {
            return pos - 14;
        }else if (pos > 373 && pos <381) {
            return pos - 15;
        }else if (pos > 380 && pos <383) {
            return pos - 16;
        }else if (pos > 382 && pos <385) {
            return pos - 17;
        }else if (pos > 384 && pos <387) {
            return pos - 18;
        }else if (pos > 386 && pos <389) {
            return pos - 19;
        }else if (pos > 388 && pos <394) {
            return pos - 20;
        }else if (pos > 393 && pos <398) {
            return pos - 21;
        }else if (pos > 397 && pos <400) {
            return pos - 22;
        }else if (pos > 399 && pos <402) {
            return pos - 23;
        }else if (pos > 401 && pos <405) {
            return pos - 24;
        }else if (pos > 404 && pos <408) {
            return pos - 25;
        }else if (pos > 407 && pos <411) {
            return pos - 26;
        }else if (pos > 410 && pos <413) {
            return pos - 27;
        }else if (pos > 412 && pos <418) {
            return pos - 28;
        }else if (pos > 417 && pos <422) {
            return pos - 27;
        }
        return pos;
    }
}
