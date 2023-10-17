package com.example.demo.controller;///*
// * Ant Group
// * Copyright (c) 2004-2023 All Rights Reserved.
// */
//package com.example.myjkenins.controller;
//
//import com.alipay.dchainsaascore.common.service.facade.util.DcscDateUtils;
//import com.alipay.fc.common.lang.money.MultiCurrencyMoney;
//import com.alipay.mychain.dapp.common.facade.error.CommonErrorCodeEnum;
//import com.alipay.mychain.dapp.common.facade.error.DappBaseException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
///**
// * excel 工具类
// * @author zhongren.yy
// * @version ExcelUtil.java, v 0.1 2023年07月05日 14:27 zhongren.yy
// */
//@Slf4j
//public class ExcelUtil {
//
//    final static DataFormatter formatter = new DataFormatter();
//
//    public static String getCellContent(Cell cell) {
//        return formatter.formatCellValue(cell);
//    }
//
//    /**
//     * 获取表头每列对应的索引
//     * headerMap key是表头,value类的字段
//     */
//    public static <T> List<T> parseExcel(LinkedHashMap<String, String> headerMap, InputStream fileInputStream, Class<T> clazz) throws Exception {
//        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        XSSFRow headerRow = sheet.getRow(0);
//        validateHeader(headerRow, headerMap);
//        workbook.close();
//        List<T> dataList = getDataList(sheet,headerMap, clazz);
//        return dataList;
//    }
//
//    /**
//     * 获取表头每列对应的索引
//     */
//    private static void validateHeader(XSSFRow headerRow, Map<String, String> headerMap) {
//        List<String> headerNameList = headerMap.entrySet()
//                .stream()
//                .map(entry -> entry.getKey())
//                .collect(Collectors.toList());
//        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
//            XSSFCell cell = headerRow.getCell(i);
//            if(Objects.isNull(cell)){
//                continue;
//            }
//            String headerName = cell.getStringCellValue();
//            if (!StringUtils.equals(headerNameList.get(i),headerName)) {
//                throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "未找到表头中" + headerName + "对应的列");
//            }
//        }
//    }
//
//    /**
//     * 获取数据列表
//     */
//    private static <T> List<T> getDataList(XSSFSheet sheet, HashMap<String, String> headerMap, Class<T> clazz) throws Exception {
//        DataFormatter dataFormatter = new DataFormatter();
//        List<T> dataList = new ArrayList<>();
//
//        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//            XSSFRow dataRow = sheet.getRow(i);
//            if(Objects.isNull(dataRow)){
//                continue;
//            }
//            T t = clazz.newInstance();
//            int j = 0;
//            boolean isNotBlankByRow = false;
//
//            for (Map.Entry<String, String > entry : headerMap.entrySet()) {
//                XSSFCell cell = dataRow.getCell(j);
//                String cellValue = dataFormatter.formatCellValue(cell);
//                if (StringUtils.isNotBlank(cellValue)) {
//                    isNotBlankByRow = true;
//                }
//                Field field = clazz.getDeclaredField(entry.getValue());
//                field.setAccessible(true);
//                Class<?> fieldType = field.getType();
//                if (fieldType == String.class) {
//                    field.set(t, getStringCellValue(cell,entry.getKey()));
//                } else if ( fieldType == int.class) {
//                    field.setInt(t, getIntegerCellValue(dataFormatter,cell,entry.getKey()));
//                } else if (fieldType == Integer.class ) {
//                    field.setInt(t, getIntCellValue(dataFormatter,cell,entry.getKey()));
//                } else if (fieldType == MultiCurrencyMoney.class ) {
//                    field.set(t, getMultiCurrencyMoneyCellValue(dataFormatter,cell,entry.getKey()));
//                } else if (fieldType == Date.class ) {
//                    field.set(t, getDateCellValue(cell,entry.getKey()));
//                }
//                j++;
//            }
//            if(isNotBlankByRow){
//                dataList.add(t);
//            }
//        }
//        return dataList;
//    }
//
//    /**
//     * 获取单元格字符串值
//     */
//    private static String getStringCellValue(XSSFCell cell,String headerName) {
//        if (cell == null||StringUtils.isBlank(getCellContent(cell)) ) {
//            return null;
//        }
//        if (cell.getCellType() == CellType.STRING) {
//            return cell.getStringCellValue();
//        }
//        throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//    }
//    /**
//     * 获取单元格整数值
//     */
//    private static Integer getIntegerCellValue(DataFormatter dataFormatter ,XSSFCell cell,String headerName) {
//        if (cell == null||StringUtils.isBlank(getCellContent(cell))) {
//            return null;
//        }
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return Integer.valueOf(dataFormatter.formatCellValue(cell));
//        }
//        throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//
//    }
//    private static int getIntCellValue(DataFormatter dataFormatter ,XSSFCell cell,String headerName) {
//        if (cell == null||StringUtils.isBlank(getCellContent(cell))) {
//            return 0;
//        }
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return Integer.valueOf(dataFormatter.formatCellValue(cell));
//        }
//        throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//
//    }
//    /**
//     * 获取单元格MultiCurrencyMoney值
//     */
//    private static MultiCurrencyMoney getMultiCurrencyMoneyCellValue(DataFormatter dataFormatter ,XSSFCell cell,String headerName) {
//        if (cell == null ||StringUtils.isBlank(getCellContent(cell))) {
//            return null;
//        }
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return new MultiCurrencyMoney(dataFormatter.formatCellValue(cell));
//        }
//        throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//    }
//    /**
//     * 获取单元格MultiCurrencyMoney值
//     */
//    private static Date getDateCellValue(XSSFCell cell,String headerName) {
//        if (cell == null ||StringUtils.isBlank(getCellContent(cell))) {
//            return null;
//        }
//        if ( DateUtil.isCellDateFormatted(cell)) {
//            return  cell.getDateCellValue();
//        }
//        throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//    }
//
//    /**
//     * 根据数据列表和字段映射关系生成 Excel 文件输入流
//     *
//     * @param dataList 数据列表
//     * @param fieldMap 字段映射关系，key 是表头，value 是对象的属性名
//     * @return 生成的 Excel 文件
//     */
//    public static <T> InputStream generateExcelInputStream(List<T> dataList, LinkedHashMap<String, String> fieldMap) {
//        XSSFWorkbook xssfWorkbook = generateExcel(dataList, fieldMap);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            xssfWorkbook.write(os);
//        } catch (IOException e) {
//            throw DappBaseException.noRetry(CommonErrorCodeEnum.SYSTEM_ERROR, e.getMessage(), e);
//        }
//        byte[] content = os.toByteArray();
//        InputStream inputStream = new ByteArrayInputStream(content);
//        return inputStream;
//    }
//
//    /**
//     * 根据数据列表和字段映射关系生成 Excel 文件
//     *
//     * @param dataList 数据列表
//     * @param fieldMap 字段映射关系，key 是表头，value 是对象的属性名
//     * @return 生成的 Excel 文件
//     */
//    public static <T> XSSFWorkbook generateExcel(List<T> dataList, LinkedHashMap<String, String> fieldMap) {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet();
//        List<String> headers = new ArrayList<>(fieldMap.keySet());
//        createHeaderRow(sheet, headers);
//        createDataRows(workbook,sheet, dataList, fieldMap, headers);
//        return workbook;
//    }
//
//    /**
//     * 创建 Excel 表头行
//     *
//     * @param sheet Excel Sheet 对象
//     * @param headers 表头列表
//     */
//    private static void createHeaderRow(Sheet sheet, List<String> headers) {
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < headers.size(); i++) {
//            Cell headerCell = headerRow.createCell(i);
//            headerCell.setCellValue(headers.get(i));
//        }
//    }
//
//    /**
//     * 创建 Excel 数据行
//     *
//     * @param sheet Excel Sheet 对象
//     * @param dataList 数据列表
//     * @param fieldMap 字段映射关系，key 是表头，value 是对象的属性名
//     */
//    private static <T> void createDataRows(XSSFWorkbook workbook,Sheet sheet, List<T> dataList, Map<String, String> fieldMap,List<String> headers) {
//        AtomicInteger totalIndex = new AtomicInteger(1);
//        for (T data : dataList) {
//            Row dataRow = sheet.createRow(totalIndex.get());
//            for (int i = 0; i < fieldMap.size(); i++) {
//                String headerName = headers.get(i);
//                String fieldName = fieldMap.get(headerName);
//                Cell dataCell = dataRow.createCell(i);
//
//                try {
//                    Field field = data.getClass().getDeclaredField(fieldName);
//                    field.setAccessible(true);
//                    Object fieldValue = field.get(data);
//                    if (fieldValue instanceof String) {
//                        if(Objects.nonNull(fieldValue)){
//                            CellStyle style = workbook.createCellStyle();
//                            DataFormat df = workbook.createDataFormat();
//                            style.setDataFormat(df.getFormat("@"));
//                            dataCell.setCellStyle(style); // 将单元格设置为文本格式
//                            dataCell.setCellValue((String) fieldValue);
//                        }
//                    } else if (fieldValue instanceof Integer || fieldValue instanceof Long) {
//                        convert(workbook, dataCell);
//                        if(Objects.nonNull(fieldValue)){
//                            dataCell.setCellValue(((Number) fieldValue).doubleValue());
//                        }
//                    }  else if (fieldValue instanceof Date) {
//                        if(Objects.nonNull(fieldValue)){
//                            dataCell.setCellValue(DcscDateUtils.getStringDate((Date)fieldValue, "yyyy/MM/dd"));
//                        }
//                    } else if (fieldValue instanceof MultiCurrencyMoney) {
//                        convert(workbook, dataCell);
//                        if(Objects.nonNull(fieldValue)){
//                            dataCell.setCellValue(((MultiCurrencyMoney) fieldValue).getAmount().toString());
//                        }
//                    } else if (fieldValue != null) {
//                        //默认转换成文本
//                        CellStyle style = workbook.createCellStyle();
//                        DataFormat df = workbook.createDataFormat();
//                        style.setDataFormat(df.getFormat("@"));
//                        dataCell.setCellStyle(style); // 将单元格设置为文本格式
//                        dataCell.setCellValue(fieldValue.toString());
//                    }
//                } catch (Exception e) {
//                    log.error("设置单元格值时发生异常", e);
//                }
//            }
//            totalIndex.incrementAndGet();
//        }
//    }
//
//    private static void convert(XSSFWorkbook workbook, Cell dataCell) {
//        CellStyle style = workbook.createCellStyle();
//        DataFormat df = workbook.createDataFormat(); // 此处设置数据格式
//        style.setDataFormat(df.getFormat("0.00_ "));// 最关键的是'_ '，最后有个空格别忘了，空格是必须的
//        dataCell.setCellStyle(style);
//    }
//
//}
//
//    public static LinkedHashMap<String, String> transfer(){
//        LinkedHashMap<String, String> uploadFieldEnumMap = new LinkedHashMap<>();
//        for (PpfRepayUploadFieldEnum fieldEnum : PpfRepayUploadFieldEnum.values()) {
//            uploadFieldEnumMap.put(fieldEnum.getHeaderName(), fieldEnum.getFieldName());
//        }
//        return uploadFieldEnumMap;
//    }
//
//    /**
//     * 获取单元格字符串值
//     */
//    private static String getStringCellValue(Field field,XSSFCell cell,String headerName) {
//        String string = null;
//        if (cell == null) {
//            return null;
//        }
//        if (cell.getCellType() == CellType.STRING) {
//            string= cell.getStringCellValue();
//        }else {
//            throw DappBaseException.noRetry(CommonErrorCodeEnum.PARAM_ILLEGAL, "表头中对应的列" + headerName + "类型不符");
//        }
//
//        Annotation[] annotations = field.getAnnotations(); // 获取该字段上的所有注解
//        if(  annotations.length>0){
//            for (Annotation annotation : annotations) {
//                if (annotation instanceof CustomEnumValue) { // 判断是否为你想要的注解
//                    // 存在你想要的注解，执行相应的逻辑
//                    CustomEnumValue customEnumValue = field.getAnnotation(CustomEnumValue.class);
//                    Class<? extends Marker> enumType = customEnumValue.value();
//                    Marker[] constants = enumType.getEnumConstants();
//                    HashMap<String, String> markerMap = new HashMap<>();
//                    for (Marker marker: constants) {
//                        markerMap.put( marker.fetchDescription(),marker.fetchCode());
//                    }
//                    string = markerMap.get(string);
//                }
//            }
//        }
//        return string;
//
//    }