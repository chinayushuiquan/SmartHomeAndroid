package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class ChoseCountryNumActivity extends  BaseActivity{
    private static final int RESULT_OK = 100;
    private String countryNames[] ;
    private String countryNumbers[];

    private String lines[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q",
                              "r" ,"s","t","u","v","w","x","y","z"};

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_country_chose_num);
        countryNames = getResources().getStringArray(R.array.country_names);
        countryNumbers = getResources().getStringArray(R.array.country_number);

        mListView = (ListView)findViewById(R.id.country_list);
        mListView.setAdapter(new MyNumAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("result_country_number", countryNumbers[position]);
                bundle.putString("result_country_name", countryNames[position]);
                intent.putExtras(bundle);
                ChoseCountryNumActivity.this.setResult(RESULT_OK, intent);
                ChoseCountryNumActivity.this.finish();
            }
        });
    }
    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                .setTitleText(R.string.chose_local_num)
                .setTitleColor(R.color.white)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }



   class MyNumAdapter  extends BaseAdapter {

       public MyNumAdapter() {
       }

       @Override
       public int getCount() {
           return countryNames.length;
       }

       @Override
       public Object getItem(int position) {
           return null;
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           MyViewHold holder = null;
           if(convertView == null){
               convertView = LayoutInflater.from(ChoseCountryNumActivity.this).inflate(R.layout.show_country_chose_list_item, null);
               holder = new MyViewHold();
               holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
               holder.mNameTv = (TextView) convertView.findViewById(R.id.country_name);
               holder.mNumberTv = (TextView) convertView.findViewById(R.id.country_num);
               holder.letterLineTv = (TextView) convertView.findViewById(R.id.letter_line);
               convertView.setTag(holder);
           }else{
               holder = (MyViewHold) convertView.getTag();
           }
           String name = countryNames[position];
           String number = countryNumbers[position];

           holder.mNameTv.setText(name);
           holder.mNumberTv.setText("+"+number);
           return convertView;
       }
   }


   class  MyViewHold {
       RelativeLayout rl;
       TextView mNameTv;
       TextView mNumberTv;
       TextView letterLineTv;
   }


}
