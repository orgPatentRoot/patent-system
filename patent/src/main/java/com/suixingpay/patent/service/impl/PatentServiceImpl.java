package com.suixingpay.patent.service.impl;

import com.suixingpay.patent.mapper.PatentMapper;
import com.suixingpay.patent.pojo.Message;
import com.suixingpay.patent.pojo.Patent;
import com.suixingpay.patent.service.PatentService;
import org.apache.poi.hssf.usermodel.*;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
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
        //统一前端时间小时
        if(patent.getPatentApplyTime() != null) {
            Date date = patent.getPatentApplyTime();
            date.setHours(8);
            patent.setPatentApplyTime(date);
        }
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
        //统一前端时间小时
        if(patent.getPatentApplyTime() != null) {
            Date date = patent.getPatentApplyTime();
            date.setHours(8);
            patent.setPatentApplyTime(date);
        }
        Message message = new Message();
        if (patentMapper.updatePatent(patent) != 0) {
            message.setMessage(null, 200, "修改信息成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 200, "非待审核状态，不允许修改！", false);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
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
    public List<Patent> selectPatentService(Patent patent) {
        //统一前端时间小时
        if(patent.getPatentApplyTime()!=null) {
            Date date = patent.getPatentApplyTime();
            date.setHours(8);
            patent.setPatentApplyTime(date);
        }
        return patentMapper.selectPatent(patent);
    }

    /**
     * 指标维度查询
     * 通过指标内容、专利名称、案件文号查询、申请号查询、申请日期查询、进度查询、发明人姓名查询
     * @param patent
     * @return
     */
    @Override
    public List<Patent> selectPatentWithIndexService(Patent patent) {
        //统一前端时间小时
        if(patent.getPatentApplyTime()!=null) {
            Date date = patent.getPatentApplyTime();
            date.setHours(8);
            patent.setPatentApplyTime(date);
        }
        return patentMapper.selectPatentWithIndex(patent);
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
     * 驳回
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
            message.setMessage(null, 200, "驳回成功！", true);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        message.setMessage(null, 400, "驳回失败！", false);
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

    /**
     * 查询导出文件
     * @param response
     * @throws IOException
     */
    @Override
    public void exportDeviceModelMsg(HttpServletResponse response,String fileName, List<Patent> list)
            throws IOException {
        try {
            List<Patent> patentExcle = list; //获取列表数据
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel/" + fileName + ".xls"); //文档路径
            //列表数据将存储到指定的excel文件路径，这个路径是在项目编译之后的target目录下
            String temp = System.getProperty("user.dir");
            String path = temp + "\\public\\" + fileName + ".xls";
            System.out.println("++++++++++++++++++++++++++++++++++++");
            System.out.println(path);
            FileOutputStream out = new FileOutputStream(path);
            //这里的context是jxls框架上的context内容
            org.jxls.common.Context context = new org.jxls.common.Context();
            //将列表参数放入context中
            context.putVar("patentExcle", patentExcle);
            //将List<Patent>列表数据按照模板文件中的格式生成到patent.xls文件中
            JxlsHelper.getInstance().processTemplate(in, out, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
