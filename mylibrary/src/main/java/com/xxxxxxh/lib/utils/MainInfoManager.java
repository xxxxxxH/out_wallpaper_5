package com.xxxxxxh.lib.utils;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.facebook.applinks.AppLinkData;
import com.kingfisher.easy_sharedpreference_library.SharedPreferencesManager;
import com.xxxxxxh.lib.entity.FacebookEntity;
import com.xxxxxxh.lib.entity.GoogleEntity;

public class MainInfoManager {
    private  static   MainInfoManager instance = null;
    private String appLink="Applink is empty";
    private String installReferrer="Referrer is empty";

    private MainInfoManager() {
    }

    public static MainInfoManager getInstance() {
        if (instance == null) {
            instance = new MainInfoManager();
        }
        return instance;
    }

    public FacebookEntity getFacebookInfo(Context context) {
        appLink = SharedPreferencesManager.getInstance().getValue("appLink", String.class);
        if (TextUtils.isEmpty(appLink)) {
            AppLinkData.fetchDeferredAppLinkData(context, new AppLinkData.CompletionHandler() {
                @Override
                public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                    if (appLinkData != null) {
                        appLink = appLinkData.getTargetUri().toString();
                        SharedPreferencesManager.getInstance().putValue("appLink", appLink);
                    } else {
                        appLink = "Applink is empty";
                    }
                }
            });
        }
        FacebookEntity entity = new FacebookEntity();
        entity.setAppLink(appLink);
        return entity;
    }

    public GoogleEntity getGoogleInfo(Context context) {
        installReferrer = SharedPreferencesManager.getInstance().getValue("installReferrer", String.class);
        if (TextUtils.isEmpty(installReferrer)) {
            InstallReferrerClient client = InstallReferrerClient.newBuilder(context).build();
            client.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                        try {
                            installReferrer = client.getInstallReferrer().getInstallReferrer();
                            SharedPreferencesManager.getInstance().putValue("installReferrer", installReferrer);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        installReferrer = "Referrer is empty";
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    installReferrer = "Referrer is empty";
                }
            });
        }
        GoogleEntity entity = new GoogleEntity();
        entity.setInstallReferrer(installReferrer);
        return entity;
    }
}
