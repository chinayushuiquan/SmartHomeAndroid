package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/10/20 0020.
 *
 * 按照
 *
 */

public class RemoteControlTemplateActivity extends  BaseActivity{

    private Gallery gallery;
    int subtype, subTypeNameResid;
    int[] imgResIds;
    int selectPos = 0;
    Devices device;
    Button next_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_template_activity);

       /* subtype = getIntent().getIntExtra(Device_ir_add_three.DEVICE_ADD_SUBTYPE, HiDevice.DEVICE_IR_SUBTYPE_TV);
        subTypeNameResid = getIntent().getIntExtra(Device_ir_add_three.DEVICE_ADD_SUBTYPE_NAME, R.string.tv);*/

        /*if(subtype == HiDevice.DEVICE_IR_SUBTYPE_TV){
            imgResIds = new int[] { R.drawable.tv_temp_1, R.drawable.tv_temp_2, R.drawable.tv_temp_3 };
        }
        if(subtype == HiDevice.DEVICE_IR_SUBTYPE_PLAY){
            imgResIds = new int[] { R.drawable.bf_temp_1, R.drawable.bf_temp_2, R.drawable.bf_temp_3 };
        }

        if(subtype == HiDevice.DEVICE_IR_SUBTYPE_GONGF){
            imgResIds = new int[] { R.drawable.gf_temp_1, R.drawable.gf_temp_2 };
        }

        if(subtype == HiDevice.DEVICE_IR_SUBTYPE_AC){
            imgResIds = new int[] { R.drawable.ac_temp_1, R.drawable.ac_temp_2 };
        }

        if(subtype == HiDevice.DEVICE_IR_SUBTYPE_TOUYJ){
            imgResIds = new int[] { R.drawable.tyy_temp_1, R.drawable.tyy_temp_2 };
        }

        if(subtype == HiDevice.DEVICE_IR_SUBTYPE_QIT){
            imgResIds= new int[] { R.drawable.qit_temp_1, R.drawable.qit_temp_2 };
        }

        if(subtype == HiDevice.DEVICE_TYPE_DAJING_AIRCONDITION){
            imgResIds = new int[] {R.drawable.daikin_temp_1};
        }*/

        //device = (HiDevice) getIntent().getSerializableExtra(IntentExtraName.DEVICE);
        init();
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleBgRes(R.color.white)
                .setTitleText(R.string.choose_rc_template)
                .setRightText(R.string.next_tip)
                .setRightTextColor(R.color.orange)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void init() {
        next_bt=(Button) findViewById(R.id.next_bt);
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(subtype == HiDevice.DEVICE_TYPE_DAJING_AIRCONDITION){
                    DataUtil.insertOtherDevices(Device_ir_add_two.this, getResources().getString(R.string.dajing_air_condition), HiDevice.DEVICE_TYPE_DAJING_AIRCONDITION);
                    Device_ir_add_two.this.finish();
                }else {
                    Intent intent = new Intent(Device_ir_add_two.this, Device_ir_add_three.class);
                    intent.putExtra(Device_ir_add_three.DEVICE_ADD_SUBTYPE_NAME, subTypeNameResid);
                    intent.putExtra(Device_ir_add_three.DEVICE_ADD_SUBTYPE, subtype);
                    intent.putExtra(Device_ir_add_three.DEVICE_ADD_TEMPLATE, selectPos + 1);
                    Device_ir_add_two.this.startActivity(intent);
                    finish();
                }*/

            }
        });
        gallery = (Gallery) findViewById(R.id.gallery);
        // 这段代码是杨丰盛老师的《android开发揭秘》中这样写的
        // myGallery.setBackgroundResource(R.drawable.bg0);
        ImageAdapter adapter = new ImageAdapter();
      //  gallery.setSpacing(Utils.dip2px(Device_ir_add_two.this, 10));
        gallery.setAdapter(adapter);
        gallery.setSelection(selectPos);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectPos = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }





    class ImageAdapter extends BaseAdapter {
        // 用来设置ImageView的风格

        // 构造函数
        public ImageAdapter() {

        }

        // 返回所有图片的个数
        @Override
        public int getCount() {

            return imgResIds.length;
        }

        // 返回图片在资源的位置
        @Override
        public Object getItem(int position) {

            return position;
        }

        // 返回图片在资源的位置
        @Override
        public long getItemId(int position) {

            return position;
        }

        // 此方法是最主要的，他设置好的ImageView对象返回给Gallery
        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*ImageView imageView = new ImageView(Device_ir_add_two.this);
            // 通过索引获得图片并设置给ImageView
            imageView.setImageResource(imgResIds[position]);
            // 设置ImageView的伸缩规格，用了自带的属性值
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // 设置布局参数
            imageView.setPadding(Utils.dip2px(Device_ir_add_two.this, 10), 0,
                    Utils.dip2px(Device_ir_add_two.this, 10), 0);
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    Parameter.windowsW
                            - Utils.dip2px(Device_ir_add_two.this, 100),
                    Parameter.windowsH));*/
            // 设置风格，此风格的配置是在xml中
            return null;
        }

    }





}
