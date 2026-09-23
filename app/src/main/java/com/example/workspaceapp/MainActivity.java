package com.example.workspaceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content; SharedPreferences sp; TextView clock, title;
    int blue=Color.rgb(37,99,235), text=Color.rgb(15,23,42), muted=Color.rgb(100,116,139);
    @Override public void onCreate(Bundle b){super.onCreate(b); sp=getSharedPreferences("session",0); if(sp.getBoolean("login",false)) home(); else login();}
    TextView tv(String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(text);t.setPadding(0,8,0,8);return t;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setTextSize(15);return b;}
    void base(){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(24,22,24,12);root.setBackgroundColor(Color.rgb(248,250,252));setContentView(root);}
    void login(){base(); Space top=new Space(this);root.addView(top,new LinearLayout.LayoutParams(1,70)); TextView h=tv("工作空间",30);h.setGravity(Gravity.CENTER);h.setTypeface(null,1);root.addView(h); TextView sub=tv("Workspace",16);sub.setGravity(Gravity.CENTER);sub.setTextColor(muted);root.addView(sub); LinearLayout box=new LinearLayout(this);box.setOrientation(LinearLayout.VERTICAL);box.setPadding(18,30,18,18); EditText id=new EditText(this);id.setHint("工号");id.setSingleLine(); EditText pw=new EditText(this);pw.setHint("密码");pw.setSingleLine();pw.setInputType(0x81); Button go=btn("登录"); go.setOnClickListener(v->{ if(id.getText().length()==0||pw.getText().length()==0){toast("请输入工号和密码");return;} sp.edit().putBoolean("login",true).putString("name",id.getText().toString()).apply();home();}); box.addView(id);box.addView(pw);box.addView(go); root.addView(box); TextView hint=tv("测试版本：登录信息仅保存在本机",13);hint.setTextColor(muted);hint.setGravity(Gravity.CENTER);root.addView(hint);}
    void home(){base(); LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER_VERTICAL); title=tv("首页",24);title.setTypeface(null,1);bar.addView(title,new LinearLayout.LayoutParams(0,-2,1)); Button logout=btn("退出");logout.setOnClickListener(v->{sp.edit().clear().apply();login();});bar.addView(logout);root.addView(bar); clock=tv("",14);clock.setTextColor(muted);root.addView(clock); Timer timer=new Timer();timer.scheduleAtFixedRate(new TimerTask(){public void run(){runOnUiThread(()->clock.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(new Date())));}},0,1000); content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);root.addView(content,new LinearLayout.LayoutParams(-1,0,1)); card("👤 个人信息","工号："+sp.getString("name","User"),v->profile()); card("📱 设备信息","查看本机 Android / 网络信息",v->device()); card("📷 扫描 QR","打开相机进行二维码扫描",v->scan()); card("⚙ 设置","应用设置与版本信息",v->settings());}
    void card(String a,String b,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(18,14,18,14);c.setBackgroundColor(Color.WHITE);TextView x=tv(a,18);x.setTypeface(null,1);TextView y=tv(b,13);y.setTextColor(muted);c.addView(x);c.addView(y);c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,10,0,0);content.addView(c,p);}
    void sub(String name){base();TextView h=tv(name,24);h.setTypeface(null,1);root.addView(h);Button back=btn("← 返回");back.setOnClickListener(v->home());root.addView(back);}
    void profile(){sub("个人信息");root.addView(tv("工号\n"+sp.getString("name",""),18));root.addView(tv("登录状态\n已登录",18));}
    void device(){sub("设备信息");root.addView(tv("Android\n"+android.os.Build.VERSION.RELEASE,18));root.addView(tv("设备\n"+android.os.Build.MANUFACTURER+" "+android.os.Build.MODEL,18));root.addView(tv("SDK\n"+android.os.Build.VERSION.SDK_INT,18));}
    void scan(){sub("二维码扫描");root.addView(tv("测试版本暂使用系统相机入口。下一版可接入真正 QR 解码器。",16));Button b=btn("打开相机");b.setOnClickListener(v->{if(android.os.Build.VERSION.SDK_INT>=23&&checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)requestPermissions(new String[]{Manifest.permission.CAMERA},10);else launchCamera();});root.addView(b);}
    void launchCamera(){try{Intent i=new Intent("android.media.action.IMAGE_CAPTURE");startActivityForResult(i,11);}catch(Exception e){toast("设备没有可用相机");}}
    void settings(){sub("设置");root.addView(tv("版本：1.0.0\n参考原 APK 的 Workspace 结构重新实现。",16));Button b=btn("应用设置");b.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()))));root.addView(b);}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
