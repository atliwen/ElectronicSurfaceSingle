package com.example.electronic.surface.single;


import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import javax.print.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李文
 * @create 2019-11-13 11:12
 **/
public class ElectronicSurfaceSingle
{
    //初始的宽度
    private static final int START_WIDTH = 0;

    //初始的高度
    private static final int START_HEIGHT = 0;

    //图片的宽度
    private static final int IMG_WIDTH = (int) UnitConv.mm2pt(100);
    //图片的宽度
    private static final int IMG_HEIGHT = (int) UnitConv.mm2pt(120);


    // 条形码 高
    private static final int BAR_HEIGHT = (int) UnitConv.mm2pt(20);

    // 条形码 宽
    private static final int MODULE_WIDTH = (int) UnitConv.mm2pt(70);


    private static final String STORE_PATH = "d:\\2.png";


    public void print(ZjsPrintOrderParam z) {

        //构建打印请求属性集
        //HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        //设置打印格式，如未确定类型，选择autosense
        //DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_HTML_HOST;
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;

        //查找所有的可用的打印服务
        //PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        //定位默认的打印服务
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        //显示打印对话框
        //PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);
        if (defaultService != null) {
            try {
                DocPrintJob job = defaultService.createPrintJob();//创建打印作业
                byte[] bytes = createApiCture(z);
                InputStream fis = new ByteArrayInputStream(bytes);//构造待打印的文件流
                //FileInputStream fis = new FileInputStream("C:\\Users\\atliwen\\Desktop\\新建文本文档.txt");//构造待打印的文件流
                //DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, null);
                job.print(doc, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private byte[] createApiCture(ZjsPrintOrderParam z) throws IOException {

        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 创建画板
        buildTheSketchpad(g);


        // 画A部分
        partA(g, z.getProductType(), z.getSortCode(), z.getTheAwb());

        // 画B部分
        partB(g, z.getSProvince(), z.getSCity(), z.getSArea(), z.getSAddress(), z.getSName(), z.getSMobilePhone(), z.getSPhone());

        // 画c部分
        partC(g, z.getFProvince(), z.getFCity(), z.getFArea(), z.getFAddress(), z.getFName(), z.getFMobilePhone(), z.getFPhone());

        // 画D部分

        partD(g, z.getCod(), z.getNumber(), z.getChargeableWeight(), z.getPrintUnit(), z.getPrintTime());

        // 画E部分
        partE(g, z.getCod(), z.getBarCode(), z.getMailNo(), z.getItemName());


        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", os);
        return os.toByteArray();
        //ImageIO.write(image, "PNG", new File(STORE_PATH));


    }

    /**
     * @param g        画布
     * @param cod      总代收款  9200.00
     * @param barCode  条码号 A11000225225155-999-1
     * @param mailNo   客户单号  1212151215151
     * @param itemName 商品名称  服服服服服服服服服服服服服服服服服服服服服服QWW啊啊啊啊啊啊啊啊啊啊啊啊
     */
    private void partE(Graphics2D g, String cod, String barCode, String mailNo, String itemName) {
        String 条码号 = "A11000225225155-999-1";
        g.drawString("条 码 号：" + barCode, (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(99));
        String 客户单号 = "1212151215151";
        g.drawString("客户单号：" + mailNo, (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(102));
        g.setFont(new Font("黑体", Font.PLAIN, 10));
        g.drawString("代收款：" + cod + " 元", (int) UnitConv.mm2pt(64), (int) UnitConv.mm2pt(100));

        String 商品名称 = "服服服服服服服服服服服服服服服服服服服服服服QWW啊啊啊啊啊啊啊啊啊啊啊啊";

        drawString(g, new Font("黑体", Font.PLAIN, 12), itemName, 264, 4, 108, 5);
    }

    /**
     * @param g
     * @param cod              总共代收款  39000.00
     * @param number           件数 999
     * @param chargeableWeight 计费重量 200.05
     * @param printUnit        打印单位  中国移动
     * @param printTime        打印时间 2018-05-17
     * @return
     */
    private void partD(Graphics2D g, String cod, String number, String chargeableWeight, String printUnit, String printTime) {
        g.setFont(new Font("黑体", Font.BOLD, 9));
        g.drawString("重要提示：", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(71));
        g.drawString("总代收款：", (int) UnitConv.mm2pt(57), (int) UnitConv.mm2pt(79));


        String 总代收款 = "39000.00";
        g.setFont(new Font("黑体", Font.BOLD, 18));
        g.drawString("￥" + cod + "元", (int) UnitConv.mm2pt(57), (int) UnitConv.mm2pt(86));
        g.drawString("签收人：", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(88));


        g.setFont(new Font("黑体", Font.PLAIN, 8));
        g.drawString("派送前请联系收货人，必须本人签收，他人代收要求提供代。", (int) UnitConv.mm2pt(20), (int) UnitConv.mm2pt(71));
        g.drawString("收人身份证号后六位", (int) UnitConv.mm2pt(20), (int) UnitConv.mm2pt(75));

        String 件数 = "999";
        g.drawString("件数：共 " + number + "件", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(79));

        String 计费重量 = "200.05";
        g.drawString("计费重量：" + chargeableWeight + "公斤", (int) UnitConv.mm2pt(27), (int) UnitConv.mm2pt(79));

        String 打印单位 = "中国移动";
        g.drawString("打印单位：" + printUnit, (int) UnitConv.mm2pt(68), (int) UnitConv.mm2pt(89));

        String 打印时间 = "2018-05-17";
        g.drawString("打印时间：" + printTime, (int) UnitConv.mm2pt(68), (int) UnitConv.mm2pt(93));
        // 签收下划线
        g.drawLine(START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(95), START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT + (int) UnitConv.mm2pt(95)); //下边框

    }

    /**
     * @param g           画布
     * @param province    发件人 省    北京
     * @param city        发件人 市    北京
     * @param area        发件人 区    顺义区
     * @param address     发件人 地址   顺义顺义顺义顺义空港物流园八街三号一一一一AAAAA一
     * @param name        发件人 姓名   李先生
     * @param mobilePhone 发件人 手机    19210001200
     * @param phone       发件人 电话    1230-15151-8481
     */
    private void partC(Graphics2D g, String province, String city, String area, String address, String name, String mobilePhone, String phone) {

        g.drawLine(START_WIDTH + (int) UnitConv.mm2pt(10), START_HEIGHT + (int) UnitConv.mm2pt(54), START_WIDTH + (int) UnitConv.mm2pt(10), START_HEIGHT + (int) UnitConv.mm2pt(67)); //下边框


        Font sFont = new Font("黑体", Font.PLAIN, 8);
        g.setFont(sFont);
        g.drawString("发", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(58));
        g.drawString("件", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(62));
        g.drawString("人", (int) UnitConv.mm2pt(4), (int) UnitConv.mm2pt(65));

        String 发件人地址 = "北京 北京 顺义区 顺义顺义顺义顺义空港物流园八街三号一一一一AAAAA一";
        String s = province + " " + city + " " + area + " " + address;
        drawString(g, sFont, s, 240, 12, 58, 4);


        String 发件人姓名手机电话 = "李先生 19210001200 1230-15151-8481";
        String d = name + " " + mobilePhone + " " + phone;
        g.drawString(d, (int) UnitConv.mm2pt(12), (int) UnitConv.mm2pt(66));


        // 发件人下划线
        g.drawLine(START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(67), START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT + (int) UnitConv.mm2pt(67));
    }

    /**
     * @param g           画布
     * @param province    收件人 省    河北
     * @param city        收件人 市    石家庄
     * @param area        收件人 区    无名
     * @param address     收件人 地址   天天阿里中文路为大帝滴滴aaaaaa为大帝滴滴aass大帝
     * @param name        收件人 姓名   李先生
     * @param mobilePhone 收件人 手机    19210001200
     * @param phone       收件人 电话    1230-15151-8481
     */
    private void partB(Graphics2D g, String province, String city, String area, String address, String name, String mobilePhone, String phone) {
        g.drawLine(START_WIDTH + (int) UnitConv.mm2pt(10), START_HEIGHT + (int) UnitConv.mm2pt(38), START_WIDTH + (int) UnitConv.mm2pt(10), START_HEIGHT + (int) UnitConv.mm2pt(55));

        Font addressFont = new Font("黑体", Font.PLAIN, 12);
        g.setFont(addressFont);
        g.drawString("收", (int) UnitConv.mm2pt(3), (int) UnitConv.mm2pt(43));
        g.drawString("件", (int) UnitConv.mm2pt(3), (int) UnitConv.mm2pt(49));
        g.drawString("人", (int) UnitConv.mm2pt(3), (int) UnitConv.mm2pt(53));

        String s = province + " " + city + " " + area + " " + address;

        String 收件人地址 = "河北 石家庄 天天阿里中文路为大帝滴滴aaaaaa为大帝滴滴";

        drawString(g, addressFont, s, 235, 12, 43, 4);

        String 收件人姓名手机电话 = "李先生 19210001200 1230-15151-8481";
        String d = name + " " + mobilePhone + " " + phone;
        g.drawString(d, (int) UnitConv.mm2pt(12), (int) UnitConv.mm2pt(52));

        // 收件人下划线
        g.drawLine(START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(54), START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT + (int) UnitConv.mm2pt(54));
    }

    /**
     * @param g           画布
     * @param productType 产品类型
     * @param sortCode    分拣编码  311A-CA08-C001
     * @param theAwb      运单号    A1000101111111101111101
     */
    private void partA(Graphics2D g, String productType, String sortCode, String theAwb) {
        //填写产品类型
        g.setFont(new Font("黑体", Font.BOLD, 24));
        g.drawString(productType, START_WIDTH + (int) UnitConv.mm2pt(90), START_HEIGHT + (int) UnitConv.mm2pt(9));
        //分拣编码
        Font sortCodeFont = new Font("黑体", Font.BOLD, 32);
        g.setFont(sortCodeFont);
        g.drawString(sortCode, (IMG_WIDTH - g.getFontMetrics(sortCodeFont).stringWidth(sortCode)) / 2, START_HEIGHT + (int) UnitConv.mm2pt(17));

        // 条形码
        Image image1 = getBarCode(theAwb);
        g.drawImage(image1, (IMG_WIDTH - MODULE_WIDTH) / 2, (int) UnitConv.mm2pt(18), null);
        // 条形码 下线
        g.drawLine(START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(38), START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT + (int) UnitConv.mm2pt(38));
    }

    private void buildTheSketchpad(Graphics2D g) {


        //设置背景色为白色
        g.setColor(Color.WHITE);
        //设置颜色区域大小
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        /*
         * 绘制表格 填充内容
         * */
        //表格线条的颜色
        g.setColor(Color.BLACK);

        //消除文本出现锯齿现象
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        //表格的四个边框

        // 上边框
        g.drawLine(START_WIDTH, START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT);
        //左边框
        g.drawLine(START_WIDTH, START_HEIGHT, START_WIDTH, START_HEIGHT + (int) UnitConv.mm2pt(120));
        //下边框
        g.drawLine(START_WIDTH, START_HEIGHT + (int) UnitConv.mm2pt(120), START_WIDTH + (int) UnitConv.mm2pt(99), START_HEIGHT + (int) UnitConv.mm2pt(119));
        //下边框
        g.drawLine(START_WIDTH + (int) UnitConv.mm2pt(100), START_HEIGHT, START_WIDTH + (int) UnitConv.mm2pt(99), START_HEIGHT + (int) UnitConv.mm2pt(119));

    }


    private Image getBarCode(String msg) {
        try {
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            Code128Bean bean = new Code128Bean();
            final int resolution = 150;
            bean.setModuleWidth(UnitConv.in2mm(3.0f / resolution));
            String format = "image/png";
            // 输出流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format,
                    resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            //生成条码
            bean.generateBarcode(canvas, msg);
            canvas.finish();
            InputStream i = new ByteArrayInputStream(ous.toByteArray());
            return ImageIO.read(i).getScaledInstance(MODULE_WIDTH, BAR_HEIGHT, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 文字超出限定长度自动换行
     *
     * @param g           画布
     * @param font        字体样式
     * @param text        文字
     * @param widthLength 最大长度  （多少长度后需要换行）
     * @param x           文字位置坐标  x
     * @param y           文字位置坐标 Y
     * @param yn          每次换行偏移多少pt
     */
    private void drawString(Graphics2D g, Font font, String text, int widthLength, int x, int y, int yn) {

        FontMetrics fg = g.getFontMetrics(font);
        List<String> ls = new ArrayList<>(2);
        getListText(fg, text, widthLength, ls);
        for (int i = 0; i < ls.size(); i++) {
            if (i == 0) {
                g.drawString(ls.get(i), (int) UnitConv.mm2pt(x), (int) UnitConv.mm2pt(y));
            } else {
                g.drawString(ls.get(i), (int) UnitConv.mm2pt(x), (int) UnitConv.mm2pt(y + yn));
            }
        }
    }


    /**
     * 递归 切割字符串
     * @param fg
     * @param text
     * @param widthLength
     * @param ls
     */
    private void getListText(FontMetrics fg, String text, int widthLength, List<String> ls) {
        String ba = text;
        boolean b = true;
        int i = 1;
        while (b) {
            if (fg.stringWidth(text) > widthLength) {
                text = text.substring(0, text.length() - 1);
                i++;
            } else {
                b = false;
            }
        }
        if (i != 1) {
            ls.add(ba.substring(0, ba.length() - i));
            getListText(fg, ba.substring(ba.length() - i), widthLength, ls);
        } else {
            ls.add(text);
        }
    }

}
