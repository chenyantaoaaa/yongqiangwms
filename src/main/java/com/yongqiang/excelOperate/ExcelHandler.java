package com.yongqiang.excelOperate;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yantao.chen on 2019-07-12
 * 用于盘点excel汇总操作
 */
public class ExcelHandler {
    public static final Integer wareHouseId = 385;
    public static final String gysCode = "GYS1555648265062981";//系统经销虚拟供应商编码

    public static void main(String[] args) {
        Map<String,Map<String,Map<String,Object>>> sectionData = getSectionData();
        Map<String,Map<String,Object>> skuData = getSkuData();
        Map<String,Map<String,Object>> daiXiaoData = getDaiXiaoData();
        Map<String,Map<String,Object>> feeData = getFeeData();

        Map<String,Map<String,Object>> resultSectionData = new HashMap<>();
        String errorMsg = "";
        //去重
        for (Map.Entry<String, Map<String,Map<String,Object>>> entry : sectionData.entrySet()) {
            for (Map.Entry<String,Map<String,Object>> entry1: entry.getValue().entrySet()) {
                if(resultSectionData.containsKey(entry1.getKey())){
                    Map<String,Object> itemMap = entry1.getValue();
                    System.out.println(entry1.getKey()+"该条商品编码对应多条数据，实库数量正在进行累加");
                    itemMap.put(BizConstants.TITLE_PRODUCT_ACCOUNT,(Long)itemMap.get(BizConstants.TITLE_PRODUCT_ACCOUNT) + (Long)entry1.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT));
                    resultSectionData.put(entry1.getKey(),itemMap);
                }else{
                    resultSectionData.put(entry1.getKey(),entry1.getValue());
                }
            }
        }
        //系统sku对比
        for (Map.Entry<String, Map<String, Object>> entry : resultSectionData.entrySet()) {
            if(entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT) == null || StringUtils.isEmpty(entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT).toString())){
                errorMsg = errorMsg + entry.getKey() + ":该sku在盘点表格中实盘数量为空请重新检查\n";
            }
            Map<String,Object> itemMap = entry.getValue();
            if(skuData.containsKey(entry.getKey())){
                itemMap.put("系统SKU",skuData.get(entry.getKey()).get("SKU编码"));
            }else{
                itemMap.put("系统SKU","");
                errorMsg = errorMsg + entry.getKey() + ":该sku在系统中不存在请联系采购专员在系统中添加对应sku编码\n";
            }
        }
        //代销数据添加
        for (Map.Entry<String, Map<String, Object>> entry : resultSectionData.entrySet()) {
            Map<String,Object> itemMap = entry.getValue();
            if(daiXiaoData.containsKey(entry.getKey())){
                if(daiXiaoData.get(entry.getKey()).get("供应商编码") == null || StringUtils.isEmpty(daiXiaoData.get(entry.getKey()).get("供应商编码").toString())){
                    errorMsg = errorMsg + entry.getKey() + ":该sku在表格中对应的代销供应商编码是空,请检查代销数量表格\n";
                    continue;
                }
                itemMap.put("代销供应商",daiXiaoData.get(entry.getKey()).get("供应商编码"));
                itemMap.put("代销数",daiXiaoData.get(entry.getKey()).get("代销数"));
            }
        }
        //成本价格添加
        for (Map.Entry<String, Map<String, Object>> entry : resultSectionData.entrySet()) {
            Map<String,Object> itemMap = entry.getValue();
            if(feeData.containsKey(entry.getKey())){
                if(feeData.get(entry.getKey()).get("成本价") == null || StringUtils.isEmpty(feeData.get(entry.getKey()).get("成本价").toString())){
                    errorMsg = errorMsg + entry.getKey() + ":该sku在表格中对应的成本价格是空,请检查成本价格表格\n";
                    continue;
                }
                itemMap.put("成本价",feeData.get(entry.getKey()).get("成本价"));
            }else{
                errorMsg = errorMsg + entry.getKey() + ":该sku在系统中不存在成本价格请联系财务专员在系统中添加对应成本价格\n";
            }
        }
        if(StringUtils.isNotEmpty(errorMsg)){
            System.out.println(errorMsg);
            return;
        }
        //重新进行数据封装
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Map<String, Object>> writeMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : resultSectionData.entrySet()) {
            if(entry.getValue().get("代销数") == null){//无代销
                Map<String, Object> rowMap = new LinkedHashMap<>();
                rowMap.put("仓库编码",wareHouseId);
                rowMap.put("仓库名称","");
                rowMap.put("商品编码",entry.getValue().get(BizConstants.TITLE_PRODUCT_NUM));
                rowMap.put("供应商编码",gysCode);
                rowMap.put("销售模式",2);//2经销 1代销
                rowMap.put("分仓",1);//全部正常仓
                rowMap.put("商品名称","");
                rowMap.put("实际库存",entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT));
                rowMap.put("单价",BigDecimalFormat(entry.getValue().get("成本价")));
                rows.add(rowMap);
            }else{//有代销
                Map<String, Object> rowMapJing = new LinkedHashMap<>();
                Map<String, Object> rowMapDai = new LinkedHashMap<>();
                if((Long)entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT) - (Long)entry.getValue().get("代销数") > 0) {
                    rowMapJing.put("仓库编码", wareHouseId);
                    rowMapJing.put("仓库名称", "");
                    rowMapJing.put("商品编码", entry.getValue().get(BizConstants.TITLE_PRODUCT_NUM));
                    rowMapJing.put("供应商编码", gysCode);
                    rowMapJing.put("销售模式", 2);//1经销 2代销
                    rowMapJing.put("分仓", 1);//全部正常仓
                    rowMapJing.put("商品名称", "");
                    rowMapJing.put("实际库存", (Long) entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT) - (Long) entry.getValue().get("代销数"));
                    rowMapJing.put("单价", BigDecimalFormat(entry.getValue().get("成本价")));

                    rowMapDai.put("仓库编码", wareHouseId);
                    rowMapDai.put("仓库名称", "");
                    rowMapDai.put("商品编码", entry.getValue().get(BizConstants.TITLE_PRODUCT_NUM));
                    rowMapDai.put("供应商编码", entry.getValue().get("代销供应商"));
                    rowMapDai.put("销售模式", 1);//2经销 1代销
                    rowMapDai.put("分仓", 1);//全部正常仓
                    rowMapDai.put("商品名称", "");
                    rowMapDai.put("实际库存", entry.getValue().get("代销数"));
                    rowMapDai.put("单价", BigDecimalFormat(entry.getValue().get("成本价")));
                    rows.add(rowMapJing);
                    rows.add(rowMapDai);
                }else{//如果系统中代销数量大于等于实际盘点数量 则只生成代销数据
                    rowMapDai.put("仓库编码", wareHouseId);
                    rowMapDai.put("仓库名称", "");
                    rowMapDai.put("商品编码", entry.getValue().get(BizConstants.TITLE_PRODUCT_NUM));
                    rowMapDai.put("供应商编码", entry.getValue().get("代销供应商"));
                    rowMapDai.put("销售模式", 1);//2经销 1代销
                    rowMapDai.put("分仓", 1);//全部正常仓
                    rowMapDai.put("商品名称", "");
                    rowMapDai.put("实际库存", entry.getValue().get(BizConstants.TITLE_PRODUCT_ACCOUNT));
                    rowMapDai.put("单价", BigDecimalFormat(entry.getValue().get("成本价")) );
                    rows.add(rowMapDai);
                }
            }


        }
        ExcelWriter writer = ExcelUtil.getWriter("d:/盘点导入结果.xlsx");
        writer.write(rows, true);
        writer.close();
        System.out.println("导入excel已经生成 请在D盘查看");
    }


    //数据格式化s
    private static BigDecimal BigDecimalFormat(Object obj){

        return new BigDecimal(obj.toString().replace("\n","").replace("\t","").replace("\r","").replace("\\s","").trim());
    }

    /**
     * 获取所有区域盘点数据
     * 获得的数据结构为   第一层  key sheet名称   value 数据list   第二层  数据list中   key 单元格title   value 数值
     */
    public static Map<String,Map<String,Map<String,Object>>> getSectionData(){
        List<String> sheetNames = new ArrayList<>();
        Map<String,Map<String,Map<String,Object>>> sectionDataMap = new HashMap<>();
        try {
            String path = ExcelHandler.class.getClassLoader().getResource("templates/QuYuPanDian.xlsx").getPath();
            File file = new File(path);
            Workbook wb = WorkbookFactory.create(new FileInputStream(file));
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
//            int k =20;
//            for (int i = k; i < k+1; i++) {
                sheetNames.add(wb.getSheetName(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sheetNames.forEach(a -> {
            ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("templates/QuYuPanDian.xlsx"),a);
            List<Map<String,Object>> readAll = reader.readAll();
            Map<String,Map<String,Object>> skuMap = new HashMap<>();
            readAll.forEach(b -> {
                if(skuMap.containsKey(b.get(BizConstants.TITLE_PRODUCT_NUM).toString())){
                    b.put(BizConstants.TITLE_PRODUCT_ACCOUNT,(Long)b.get(BizConstants.TITLE_PRODUCT_ACCOUNT) + (Long)skuMap.get(b.get(BizConstants.TITLE_PRODUCT_NUM).toString()).get(BizConstants.TITLE_PRODUCT_ACCOUNT));
                    skuMap.put(b.get(BizConstants.TITLE_PRODUCT_NUM).toString(), b);
                }else {
                    skuMap.put(b.get(BizConstants.TITLE_PRODUCT_NUM).toString(), b);
                }
            });
            sectionDataMap.put(a,skuMap);
        });
        return sectionDataMap;
    }

    /**
     * 获取所有SKU数据
     */
    public static Map<String,Map<String,Object>> getSkuData(){
        Map<String,Map<String,Object>> resultMap = new HashMap<>();
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("templates/BIData.xlsx"),"sku总表");
        List<Map<String,Object>> readAll = reader.readAll();
        readAll.forEach(a -> resultMap.put(a.get("SKU编码").toString(),a));
        return resultMap;
    }

    /**
     * 获取所有代销数据
     */
    public static Map<String,Map<String,Object>> getDaiXiaoData(){
        Map<String,Map<String,Object>> resultMap = new HashMap<>();
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("templates/BIData.xlsx"),"代销数表");
        List<Map<String,Object>> readAll = reader.readAll();
        readAll.forEach(a -> resultMap.put(a.get("SKU编码").toString(),a));
        return resultMap;
    }
    /**
     * 获取所有成本数据
     */
    public static Map<String,Map<String,Object>> getFeeData(){
        Map<String,Map<String,Object>> resultMap = new HashMap<>();
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("templates/BIData.xlsx"),"成本价表");
        List<Map<String,Object>> readAll = reader.readAll();
        readAll.forEach(a -> resultMap.put(a.get("SKU编码").toString(),a));
        return resultMap;
    }
}
