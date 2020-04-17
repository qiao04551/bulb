package com.maxzuo.excel;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;

/**
 * 高级版：POI克隆Excel模板，转换成HTML；不使用临时文件，通过ByteArrayInputStream将输出流转换为字节，不读取磁盘，提高效率。
 * <pre>
 *  扩展：将Excel文件上传至OSS，获取文件存储路径
 * </pre>
 * Created by zfh on 2019/05/09
 */
public class POIReadExcelAdvancedExample {

    public static void main(String[] args) {
        URL templateResource = POIReadExcelExample.class.getResource("template/applyTableTemplate.xls");
        try {
            // 通过模板克隆Excel，填充数据
            byte[] dataArray = cloneExcelFile(templateResource.getFile());

            // 将Excel转换成Html
            String htmlFilePath = "F:\\bulb\\bulb-demo\\applyTableTemplate.html";
            convertExcelToHtml(new ByteArrayInputStream(dataArray), htmlFilePath);

            // 扩展存储文件到本地
            String excelFilePath = "F:\\bulb\\bulb-demo\\applyTableTemplate.xls";
            saveFileToLocal(new ByteArrayInputStream(dataArray), excelFilePath);

            // 扩展：上传文件至OSS
            uploadFileToOSS(new ByteArrayInputStream(dataArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 克隆模板，填充模板数据，获取最终的文件（通过字节数组，将输出流转换成输入流）
     */
    private static byte[] cloneExcelFile(String filePath) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        HSSFSheet sheet = wb.cloneSheet(0);
        wb.setSheetName(0, "Sheet1");

        // 填充数据
        replaceCellValue(sheet);

        // 克隆的Excel，使用字节数组存储
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 移除workbook中的模板sheet
        wb.removeSheetAt(0);
        wb.write(baos);
        baos.flush();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * 替换单元格
     */
    private static void replaceCellValue(HSSFSheet sheet) {
        // 获取mock的填充数据
        POIReadExcelExample.ApplyTableExcelVO excelVO = mockFillData();
        sheet.getRow(1).getCell(1).setCellValue(excelVO.getCompany());
        sheet.getRow(1).getCell(2).setCellValue(excelVO.getFillTableTime());
        sheet.getRow(1).getCell(5).setCellValue(excelVO.getApplicant());

        // 活动类型（插入符号）
        String activityType;
        if (Integer.valueOf(1).equals(excelVO.getActivityType())) {
            activityType = (char) 0x2611 + " 小组活动 " + (char) 0x2610 + " 跨组活动";
        } else {
            activityType = (char) 0x2610 + " 小组活动 " + (char) 0x2611 + " 跨组活动";
        }
        sheet.getRow(2).getCell(1).setCellValue(activityType);
        sheet.getRow(3).getCell(1).setCellValue(excelVO.getActivitySubject());
        sheet.getRow(4).getCell(1).setCellValue(excelVO.getActivityGoal());
        sheet.getRow(5).getCell(1).setCellValue(excelVO.getActivityTime());
        sheet.getRow(5).getCell(3).setCellValue(excelVO.getActivityPlace());
        sheet.getRow(6).getCell(1).setCellValue(excelVO.getPersonnel());
        sheet.getRow(7).getCell(1).setCellValue(excelVO.getActivityItems());
        sheet.getRow(8).getCell(1).setCellValue(excelVO.getActivityIntroduced());
        sheet.getRow(9).getCell(1).setCellValue(excelVO.getTotalBudget());
        sheet.getRow(9).getCell(3).setCellValue(excelVO.getReceiver());
        sheet.getRow(10).getCell(1).setCellValue(excelVO.getActivityHead());
        sheet.getRow(10).getCell(3).setCellValue(excelVO.getManager());
        sheet.getRow(10).getCell(5).setCellValue(excelVO.getFinancial());
    }

    /**
     * 模拟填充数据
     */
    private static POIReadExcelExample.ApplyTableExcelVO mockFillData() {
        POIReadExcelExample.ApplyTableExcelVO excelVO = new POIReadExcelExample.ApplyTableExcelVO();
        excelVO.setCompany("");
        excelVO.setFillTableTime("2019年5月9日");
        excelVO.setApplicant("dazuo");
        excelVO.setActivityType(1);
        excelVO.setActivitySubject("winner winner clicken dinner");
        excelVO.setActivityGoal("clicken dinner");
        excelVO.setActivityTime("2019-5-9");
        excelVO.setActivityPlace("");
        excelVO.setPersonnel("dazuo、wang");
        excelVO.setActivityItems("ipad、MAC");
        excelVO.setActivityIntroduced("一起happy");
        excelVO.setTotalBudget("100000000000.00");
        excelVO.setReceiver("dazuo");
        excelVO.setActivityHead("dazuo");
        excelVO.setManager("dazuo");
        excelVO.setFinancial("wang");
        return excelVO;
    }

    /**
     * 将Excel转html
     * @param htmlFilePath  生成的HTML文件路径
     */
    private static void convertExcelToHtml (InputStream is, String htmlFilePath) throws Exception {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 不显示顶部Sheet标题
        hssfWorkbook.setSheetName(0, " ");
        ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        converter.setOutputColumnHeaders(false);
        converter.setOutputRowNumbers(false);
        converter.processWorkbook(hssfWorkbook);

        StringWriter  writer = new StringWriter();
        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(new DOMSource(converter.getDocument()), new StreamResult(writer));

        FileOutputStream out = new FileOutputStream(htmlFilePath);
        out.write(writer.toString().getBytes("UTF-8"));
        out.flush();
        hssfWorkbook.close();
    }

    /**
     * 扩展：保存文件到本地
     */
    private static void saveFileToLocal (InputStream is, String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(filePath));
            byte[] carrier = new byte[1024];
            int count;
            while ((count = is.read(carrier)) != -1) {
                fos.write(carrier, 0 ,count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扩展：上传文件至OSS服务器
     */
    private static void uploadFileToOSS (InputStream is) {
        String accessKeyId = "";
        String accessKeySecret = "";
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String endpointSave = "https://oss-cn-hangzhou.aliyuncs.com";

        // 阿里云上创建
        String bucketName = "";
        // 文件目录
        String fileDirectory = "fans-activity-table/";
        // 文件名
        String fileName = "20170000-applyTable.xls";

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("application/vnd.ms-excel");
        client.putObject(bucketName, fileDirectory + fileName, is, meta);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件路径：" + endpointSave + "/" + bucketName + "/" + fileDirectory + fileName);
    }
}
