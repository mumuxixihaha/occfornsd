package com.yonyou.occ.fee.web;

import com.yonyou.occ.fee.service.CustomerApplyToBillService;
import com.yonyou.occ.fee.service.CustomerBillService;
import com.yonyou.occ.fee.service.RebateStlToBillService;
import com.yonyou.occ.fee.service.dto.CustomerApplyDto;
import com.yonyou.occ.fee.service.dto.CustomerBillDto;
import com.yonyou.occ.fee.service.dto.PreHeadTableDto;
import com.yonyou.occ.rebate.service.dto.RebateSettlementDto;
import com.yonyou.ocm.common.web.ApproveController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import java.util.List;

/**
 * 客户费用单的控制器
 *
 * @author zhengwbc
 * @date 2019-03-19 11:09:08
 */
@RestController
@RequestMapping(value = "/fee/customer-bills")
@Generated(value = "com.yonyou.occ.util.codegenerator.CodeGenerator")
public class CustomerBillController extends ApproveController<CustomerBillDto, CustomerBillService> {

    @Autowired
    private CustomerApplyToBillService customerApplyToBillService;

    @Autowired
    private RebateStlToBillService rebateStlToBillService;
    /**
     * 获取单个订单详细信息
     */
    @GetMapping("/detail/{id}")
    @Transactional
    public CustomerBillDto getOrderDetail(@PathVariable("id") String id) {
        CustomerBillDto billDto = service.findOne(id);
        return billDto;
    }

    /**
     * 冲应收 获取红字中转应收单
     **/
    @GetMapping("/deficitHeadTabel/{id}")
    @Transactional
    public PreHeadTableDto getDeficitHeadTabel(@PathVariable("id") String id) {
        return service.getDeficitHeadTabel(id);
    }

    /**
     * 新增应收单保存
     **/
    @PostMapping("/deficitReceBill")
    @Transactional
    public void saveReceBill(@RequestBody PreHeadTableDto preDto) {
        service.saveReceBill(preDto);
        return;
    }
    @PostMapping("/changeApplyToBill")
    public List<CustomerBillDto> changeApplyToBill(@RequestBody List<CustomerApplyDto> applyDtoList) {
        return customerApplyToBillService.transfer(applyDtoList);
    }

    @PostMapping("/changeRebateStlToBill")
    public List<CustomerBillDto> changeRebateStlToBill(@RequestBody List<RebateSettlementDto> rebateSettlementDtoList) {
        return rebateStlToBillService.transfer(rebateSettlementDtoList);
    }

    @ApiOperation("关闭")
    @PostMapping("/close")
    public void close(@RequestParam("ids") String ids){
        String[] idArr = ids.split(",");
        service.close(idArr);
    }

    @ApiOperation("打开")
    @PostMapping("/open")
    public void open(@RequestParam("ids") String ids){
        String[] idArr = ids.split(",");
        service.open(idArr);
    }
}
