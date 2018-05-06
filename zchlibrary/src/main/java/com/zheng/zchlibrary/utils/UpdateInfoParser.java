package com.zheng.zchlibrary.utils;

import android.util.Xml;

import com.zheng.zchlibrary.beans.UpdateInfoEntity;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * 更新信息解析器
 * Created by Zheng on 2016/8/26.
 */
public class UpdateInfoParser {

    /**
     * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
     * @param is 服务器读取输入流
     * @return 版本更新信息实体
     * @throws Exception
     */
    public static UpdateInfoEntity getUpdataInfo(InputStream is) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源
        int type = parser.getEventType();
        UpdateInfoEntity info = new UpdateInfoEntity();//实体
        while(type != XmlPullParser.END_DOCUMENT ){
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("version".equals(parser.getName())){
                        info.setVersion(parser.nextText());	//获取版本号
                    }else if ("description".equals(parser.getName())){
                        info.setDescription(parser.nextText());	//获取该文件的信息
                    }else if ("url".equals(parser.getName())){
                        info.setUrl(parser.nextText());	//获取要升级的APK文件
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

}
