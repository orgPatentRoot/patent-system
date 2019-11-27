package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.PatentMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentMapper patentMapper;

    /**
     * 插入专利信息
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> insertPatentSevice(Patent patent) {
        Message message = new Message();
        if (patentMapper.insertPatent(patent) != 0) {
            message.setMessage(null, 200, "新建专利成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "新建专利失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 通过专利ID修改专利信息（编辑功能）
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> updatePatentServiceByIdService(Patent patent) {
        Message message = new Message();
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "修改信息成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "修改信息失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 通过专利Id修改撰写人信息（认领功能）
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> updatePatentWriterByIdService(Patent patent) {
        Message message = new Message();
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "认领成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "认领失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 查询专利信息
     * 通过专利id查询、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询、创建人id查询、撰写人id查询、其他条件
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> selectPatentService(Patent patent) {
        Message message = new Message();
        List<Patent> list = patentMapper.selectPatent(patent);
        message.setMessage(list, 200, "查询成功！", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 指标维度查询
     * 通过指标内容、专利名称、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> selectPatentWithIndexService(Patent patent) {
        Message message = new Message();
        List<Patent> list = patentMapper.selectPatentWithIndex(patent);
        message.setMessage(list, 200, "查询成功！", true);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 审核通过
     * @param patent
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public ResponseEntity<Message> auditPass(Patent patent) {
        Message message = new Message();
        List<Patent> list = patentMapper.selectPatent(patent);
        if (list.size() < 1) {
            message.setMessage(null, 400, "审核条件不符合！", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        patent.setPatentStatusId(list.get(0).getPatentStatusId());
        if (list.get(0).getPatentSign() != 1) {
            message.setMessage(null, 400, "审核条件不符合！", false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        //审核通过将审核状态设为待审核状态0，并且将专利进度+1
        patent.setPatentSign(0);
        patent.setPatentStatusId(patent.getPatentStatusId() + 1);
        patent.setSpecialCondition("patent_sign = 1 and patent_status_id IN (0,2,3,4,5)");
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "审核通过成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "审核条件不符合！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 审核不通过
     * @param patent
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public ResponseEntity<Message> auditFailed(Patent patent) {
        Message message = new Message();
        //审核不通过将审核状态设为审核不通过状态2
        patent.setPatentSign(2);
        //设置条件:审核进度为审核中2,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 1 and patent_status_id IN (0,2,3,4,5)");
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "审核不通过成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "审核通过失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 用户提交审核功能
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> userSubmitAudit(Patent patent) {
        Message message = new Message();
        //提交审核:将审核状态设为审核中1
        patent.setPatentSign(1);
        //设置条件:审核进度为审核中0,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 0 and patent_status_id IN (0,2,3,4,5)");
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "提交审核成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 200, "提交审核失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 用户回滚功能
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> userRollBack(Patent patent) {
        Message message = new Message();
        patent.setPatentSign(0);
        //设置条件:审核进度为审核中2,专利进度为需要审核的阶段
        patent.setSpecialCondition("patent_sign = 2 and patent_status_id IN (0,2,3,4,5)");
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "回滚成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 200, "回滚失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 数据维护修改专利状态（初审状态及之后才能修改）
     * @param patent
     * @return
     */
    @Override
    public ResponseEntity<Message> updateStatusId(Patent patent) {
        Message message = new Message();
        patent.setSpecialCondition("patent_status_id IN (6,7,8,9,10,11,12)"); //设置数据维护阶段可以修改的进度
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "修改进度成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "修改进度失败！", false);
        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<Message> downloadPatent(HttpServletResponse response, Patent patent1) throws IOException {
        Message message = new Message();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        //List<Patent> patentExcle = patentService.selectTest();
        System.out.println(patent1.getPatentStatusId());
        List<Patent> patentExcle = patentMapper.selectPatent(patent1);
        if (patentExcle == null) {
            message.setMessage(null, 400, "exccel导出失败！您要导出的信息不存在", true);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        } else {
            message.setMessage(null, 200, "exccel导出成功！", true);
        }
        String fileName = "patentExcel"  + ".xls"; //设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"专利ID", "批次", "案例文号", "申请号", "申请时间", "技术联系人", "申请人", "创建人", "专利名", "审核状态", "专利状态", "发明类型", "发明人", "撰写人", "备注", "状态", "创建人名称", "撰写人名称"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (Patent patent : patentExcle) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(patent.getPatentId());
            row1.createCell(1).setCellValue(patent.getPatentBatch());
            row1.createCell(2).setCellValue(patent.getPatentCaseNum());
            row1.createCell(3).setCellValue(patent.getPatentApplyNum());
            row1.createCell(4).setCellValue(patent.getPatentApplyTime());
            row1.createCell(5).setCellValue(patent.getPatentTechnicalContact());
            row1.createCell(6).setCellValue(patent.getPatentApplyPerson());
            row1.createCell(7).setCellValue(patent.getPatentCreatePerson());
            row1.createCell(8).setCellValue(patent.getPatentName());
            row1.createCell(9).setCellValue(patent.getPatentSign());
            row1.createCell(10).setCellValue(patent.getPatentStatusId());
            row1.createCell(11).setCellValue(patent.getPatentType());
            row1.createCell(12).setCellValue(patent.getPatentInventor());
            row1.createCell(13).setCellValue(patent.getPatentWriter());
            row1.createCell(14).setCellValue(patent.getPatentRemarks());
            row1.createCell(15).setCellValue(patent.getStatusName());
            row1.createCell(16).setCellValue(patent.getCreatePersonName());
            row1.createCell(17).setCellValue(patent.getWriterName());
            rowNum++;
        }
        response.setContentType("application/x-xls;charset=utf-8");
        //response.setContentType("application/octet-stream");//设置发送到客户端的响应的内容类型，application/octet-stream（ 二进制流，不知道下载文件类型）
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")); //这样写文件名中文不会乱码
        response.flushBuffer();
        ServletOutputStream out = response.getOutputStream(); //输出流
        workbook.write(out); //输出文件
        out.close(); //关闭流，不关也能下载，不过会报错

        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }
}
