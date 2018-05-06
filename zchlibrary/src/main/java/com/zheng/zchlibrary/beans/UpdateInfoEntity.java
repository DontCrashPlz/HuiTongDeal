package com.zheng.zchlibrary.beans;

import java.io.Serializable;

/**
 * 更新信息实体
 * Created by Zheng on 2016/8/26.
 */
public class UpdateInfoEntity implements Serializable{
    private String version="";
    private String url="";
    private String description="";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateInfoEntity{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

//<android-app>
//<version>1.0</version>
//<description>测试版本</description>
//<url>
//http://192.168.1.62:8080/default/portal/appdownload/android-app/yicr-soa.apk
//</url>
//</android-app>