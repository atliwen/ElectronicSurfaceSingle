package com.example.electronic.surface.single;

import lombok.Data;

/**
 * @author 李文
 * @create 2019-11-14 10:46
 **/
@Data
public class ZjsPrintOrderParam
{

    /**
     * 产品类型  L
     */
    private String productType;

    /**
     * 分拣编码  311A-CA08-C001
     */
    private String sortCode;

    /**
     * 运单号    A100010111111
     */
    private String theAwb;


    /**
     * 收件人 省    河北
     */
    private String sProvince;

    /**
     * 收件人 市    石家庄
     */
    private String sCity;

    /**
     * 收件人 区    无名
     */
    private String sArea;

    /**
     * 收件人 地址   天天阿里中文路为大帝滴滴aaaaaa为大帝滴滴aass大帝
     */
    private String sAddress;


    /**
     * 收件人 姓名   李先生
     */
    private String sName;


    /**
     * 收件人 手机    19210001200
     */
    private String sMobilePhone;


    /**
     * 收件人 电话    1230-15151-8481
     */
    private String sPhone;


    /**
     * 发件人 省    河北
     */
    private String fProvince;

    /**
     * 发件人 市    石家庄
     */
    private String fCity;

    /**
     * 发件人 区    无名
     */
    private String fArea;

    /**
     * 发件人 地址   天天阿里中文路为大帝滴滴aaaaaa为大帝滴滴aass大帝
     */
    private String fAddress;


    /**
     * 发件人 姓名   李先生
     */
    private String fName;


    /**
     * 发件人 手机    19210001200
     */
    private String fMobilePhone;


    /**
     * 发件人 电话    1230-15151-8481
     */
    private String fPhone;


    /**
     * 总共代收款  39000.00
     */
    private String cod;

    /**
     * 件数 999
     */
    private String number;

    /**
     * 计费重量 200.05
     */
    private String chargeableWeight;

    /**
     * 打印单位  中国移动
     */
    private String printUnit;

    /**
     * 打印时间 2018-05-17
     */
    private String printTime;


    /**
     * 条码号 A11000225225155-999-1
     */
    private String barCode;

    /**
     * 客户单号  1212151215151
     */
    private String mailNo;


    /**
     * 商品名称  服服服服服服服服服服服服服服服服服服服服服服QWW啊啊啊啊啊啊啊啊啊啊啊啊
     */
    private String itemName;

}
