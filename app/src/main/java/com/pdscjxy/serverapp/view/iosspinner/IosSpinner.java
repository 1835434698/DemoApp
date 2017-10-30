package com.pdscjxy.serverapp.view.iosspinner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;


/**
 * Created by dell on 2017/8/28.
 */

public class IosSpinner extends LinearLayout {
    private SpinnerPop pop;
    private Object data = null;
    private int select = -1;
    private String text = "";
    private TextView title;
    private Context mContext;
    public IosSpinner(Context context) {
        super(context);
        init(context);
    }

    public IosSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IosSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_ios_spinner, this);
        title = (TextView) findViewById(R.id.title);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pop!=null){
                    pop.showPopupWindow();
                }
            }
        });
    }

    public void setAdapter(final BaseSpinnerAdapter adapter){
        pop = new SpinnerPop(mContext,adapter,new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pop.hidePopupWindow();
                data = adapter.getItem(i);
                select = i;
                text = adapter.getTitle(i);
                title.setText(text);
                if(listener!=null){
                    listener.onItemClick(adapterView,view,i,l);
                }
            }
        });
    }

    private AdapterView.OnItemClickListener listener;
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        this.listener = listener;
    }

    public Object getSelect(){
        return data;
    }

    public int getSelectIndex(){
        return select;
    }

    public String getSelectText(){
        return text;
    }
}
