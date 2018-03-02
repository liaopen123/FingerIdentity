package com.qidongyinqing.fingeridentity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qidongyinqing.fingerprintlib.FingerprintIdentify;
import com.qidongyinqing.fingerprintlib.base.BaseFingerprint;

public class MainActivity extends AppCompatActivity {

    private FingerprintIdentify mFingerprintIdentify;
    private static final int MAX_AVAILABLE_TIMES = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         mFingerprintIdentify = new FingerprintIdentify(this, new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {

            }
        });


        if(mFingerprintIdentify!=null&&mFingerprintIdentify.isHardwareEnable()){
            //硬件支持
            if(!mFingerprintIdentify.isRegisteredFingerprint()){
                Toast.makeText(this, "你还没设置指纹", Toast.LENGTH_SHORT).show();
            }else{

            }
        }else{
            //不支持
            Toast.makeText(this, "系统不支持", Toast.LENGTH_SHORT).show();
        }

        start();
    }

    public void start() {

        mFingerprintIdentify.startIdentify(MAX_AVAILABLE_TIMES, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                Toast.makeText(MainActivity.this, "成功！！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotMatch(int availableTimes) {
                if (availableTimes == 0) {
                    Toast.makeText(MainActivity.this, "请使用手势解锁" , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "你还有"+availableTimes+"次机会", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                if (isDeviceLocked) {
                    Toast.makeText(MainActivity.this, "指纹验证失败"+isDeviceLocked, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "指纹验证失败"+isDeviceLocked, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                Toast.makeText(MainActivity.this, "指纹验证太过频繁，请稍后重试,转密码支付", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
