1.集成ShareSdk并独立
2.使用时需要在manifest中加入：
    <activity
        android:name="com.mob.tools.MobUIShell"
        android:screenOrientation="portrait"></activity>

    <!-- 微信 -->
    <activity
        android:name=".wxapi.WXEntryActivity"
        android:configChanges="keyboardHidden|orientation|screenSize"
        android:exported="true"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Translucent.NoTitleBar" />
    <!-- alipay callback -->
3.项目中使用的组件化开发，所以需要在壳工程中加入：
    <activity
        android:name="com.mob.tools.MobUIShell"
        android:configChanges="keyboardHidden|orientation|screenSize"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Translucent.NoTitleBar"
        android:windowSoftInputMode="stateHidden|adjustResize">

        <intent-filter>
            <data android:scheme="1104714153" />
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.BROWSABLE" />
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>

        <!-- 调用新浪原生SDK，需要注册的回调activity -->
        <intent-filter>
            <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
    </activity>
    修复merge的问题
4.在assets中ShareSDK.xml修改对应的AppKey、AppSecret
5.使用：AppShareMain.build(getContext()).withResponse(FragmentLogin.this)
                          .actionWhat(AppShareMain.Action.ILogin.LOGIN_QQ);
  具体参数看AppShareMain源码
6.如果有新版本jar中需要替换，在lib目录中更换相应的jar包