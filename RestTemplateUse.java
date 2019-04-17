package com.hierway.resm.repository;

import com.hierway.resm.common.HWResult;
import com.hierway.resm.common.JsonUtils;
import com.hierway.resm.dao.innerdao.virtualconf.PowerUnitStatusMapper;
import com.hierway.resm.domain.virtualconf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类封装了Service层对dao层的调用过程
 *
 * @author sunyiwei
 */
@Component
public class PowerUnitStatusApiAccess {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PowerUnitStatusMapper powerUnitStatusMapper;

    /**
     * @param
     * @param json
     * @return
     */
    public HWResult createPowerUnitStatus( String json) {
        CreateInfoDTO createInfoDTO = JsonUtils.jsonToPojo(json,CreateInfoDTO.class);
        //new
        logger.debug("createInfoDto:{}",createInfoDTO);
        Integer tableId = createInfoDTO.getTableId();
        logger.debug("tableId:{}",tableId);
        List<PowerUnitStatus> list = createInfoDTO.getList();
        logger.debug("list:{}",list);

        try{
            powerUnitStatusMapper.createPowerUnitStatusByPowerUnit(tableId,list);
        }catch (Exception e){
            return HWResult.build(12010001,"创建失败");
        }
        return HWResult.build(10000100,"创建成功");
        //new
        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        String jsonResult = restTemplate.postForObject(url, entity, String.class);
        if (jsonResult == null) {
            return null;
        }
        HWResult result = HWResult.format(jsonResult);
        return result;*/
        //old
    }


    public HWResult deletePowerUnitStatusByTime( String json, Integer puId, Long startTime) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        try{
            int row = 0;
            for(Integer tableId:list){
                row = powerUnitStatusMapper.deletePowerUnitStatusByTime(tableId,puId,startTime);
            }

        }catch (Exception e){
            return  HWResult.build(12010001,"删除失败");
        }
        return HWResult.build(10000100,"删除成功");
        //new
        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url + "?puId=" + puId + "&startTime=" + startTime, HttpMethod.DELETE, entity, String.class);
        if (exchange == null) {
            return null;
        }
        String resultJson = exchange.getBody();
        HWResult result = HWResult.format(resultJson);
        return result;*/
        //old
    }


    public HWResult getPeriodPowerUnitStatus( String json, Integer puId, Long startTime, Long endTime) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        List<PowerUnitStatus> totalList = new ArrayList<>();
        try {
            for(Integer tableId:list){
                List<PowerUnitStatus> lists = powerUnitStatusMapper.getPeriodPowerUnitStatus(tableId,puId,startTime,endTime);
                if(lists != null && lists.size()>0){
                    for(PowerUnitStatus element:lists){
                        totalList.add(element);
                    }
                }
            }
        }catch (Exception e){
            return HWResult.build(12010001,"查询失败");
        }
        return HWResult.build(10000100,"查询成功",totalList);
        //new

        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url + "?puId=" + puId + "&startTime=" + startTime + "&endTime=" + endTime, HttpMethod.POST, entity, String.class);
        if (exchange == null) {
            return null;
        }
        String resultJson = exchange.getBody();
        HWResult result = HWResult.formatToList(resultJson, PowerUnitStatus.class);
        return result;*/
        //old
    }

    public PowerUnitStatus getSinglePowerUnitStatus(Integer puId, Long startTime, Integer tableId) {
        //new
        try {
            PowerUnitStatus powerUnitStatus = powerUnitStatusMapper.getSinglePowerUnitStatus(tableId,puId,startTime);
            return powerUnitStatus;
        }catch (Exception e){
            return null;
        }
        //new
        //old
        /*HttpEntity<String> entity = restTemplate.getForEntity(url + "?puId=" + puId + "&startTime=" + startTime + "&tableId=" + tableId, String.class);
        String body = entity.getBody();
        PowerUnitStatus powerUnitStatus = JsonUtils.jsonToPojo(body, PowerUnitStatus.class);
        return powerUnitStatus;*/
        //old

    }

    public HWResult getPowerUnitStatusByStartTime( String json, Integer puId, Long startTime) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        List<PowerUnitStatus> totalList = new ArrayList<>();
        try {
            for(Integer tableId:list){
                List<PowerUnitStatus> lists = powerUnitStatusMapper.getPowerUnitStatusByStartTime(tableId,puId,startTime);
                for(PowerUnitStatus element:lists){
                    totalList.add(element);
                }
            }
        }catch (Exception e){
            return HWResult.build(12010001,"查询失败");
        }
        return HWResult.build(10000100,"查询成功",totalList);
        //new
        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url + "?puId=" + puId + "&startTime=" + startTime, HttpMethod.POST, entity, String.class);
        if (exchange == null) {
            return null;
        }
        String resultJson = exchange.getBody();
        HWResult result = HWResult.formatToList(resultJson, PowerUnitStatus.class);
        return result;*/
        //old
    }


    public HWResult updateSingleTokenInfo( String json, Integer puId, Long startTime) {
        TokenInfoVO tokenInfoVO = JsonUtils.jsonToPojo(json,TokenInfoVO.class);
        //new
        logger.debug("tokenInfoVo:{}",tokenInfoVO);

        Integer tableId = tokenInfoVO.getTableId();
        logger.debug("tokenInfoVoRemain:{}",tokenInfoVO.getRemainCapacity());
        try {
            int row = powerUnitStatusMapper.updateSingleTokenInfo(tableId,puId,startTime,tokenInfoVO);
        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new

        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url + "?puId=" + puId + "&startTime=" + startTime, HttpMethod.PUT, entity, String.class);
        if (exchange == null) {
            return null;
        }
        String resultJson = exchange.getBody();
        HWResult result = HWResult.format(resultJson);
        return result;*/
        //old
    }

    public HWResult updatePeriodControlProductionRate( Integer puId, String json) {
        TableIdAndControlProductionRateModifyDataDTO tableIdAndControlProductionRateModifyDataDTO
                = JsonUtils.jsonToPojo(json,TableIdAndControlProductionRateModifyDataDTO.class);
        //new
        List<Integer> tableIdList = tableIdAndControlProductionRateModifyDataDTO.getTableIdList();
        List<ControlProductionRateModifyDTO> list =tableIdAndControlProductionRateModifyDataDTO.getControlProductionRateModifyDTOList();
        try {
            Integer row = 0;
            for(Integer tableId:tableIdList){
                row = powerUnitStatusMapper.updatePeriodControlProductionRate(tableId,puId,list);
            }
        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new

        //old
        /*HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity entity = new HttpEntity(json, headers);
        ResponseEntity<HWResult> exchange = restTemplate.exchange(url + "?puId=" + puId, HttpMethod.PUT, entity, HWResult.class, puId);
        if (exchange == null) {
            return null;
        }
        HWResult result = exchange.getBody();
        return result;*/
        //olc
    }

    public HWResult updatePeriodMinProductionRate( String json, Integer puId, Long startTime, Long endTime, Integer minProductionRate) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        try{
            int row=0;
            for(Integer tableId:list){
                row = powerUnitStatusMapper.updatePeriodMinProductionRate(tableId,puId,startTime,endTime,minProductionRate);
            }

        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new
        //olc
        /*return exchange(url + "?puId=" + puId + "&startTime=" + startTime + "&endTime=" + endTime + "&minProductionRate=" + minProductionRate, json);*/
        //old
    }

    public HWResult updatePeriodMaxProductionRate(String json, Integer puId, Long startTime, Long endTime, Integer maxProductionRate) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        try{
            int row;
            for(Integer tableId:list){
                row = powerUnitStatusMapper.updatePeriodMaxProductionRate(tableId,puId,startTime,endTime,maxProductionRate);
            }
        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new
        //old
        /*return exchange(url + "?puId=" + puId + "&startTime=" + startTime + "&endTime=" + endTime + "&maxProductionRate=" + maxProductionRate, json);*/
        //old
    }


    public HWResult updatePeriodOperationalState(String json, Integer puId, Long startTime, Long endTime, Byte operationalState) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        try{
            int row=0;
            for(Integer tableId:list){
                row = powerUnitStatusMapper.updatePeriodOperationalState(tableId, puId, startTime, endTime, operationalState);
            }

        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new
        //old
       /* return exchange(url + "?puId=" + puId + "&startTime=" + startTime + "&endTime=" + endTime + "&operationalState=" + operationalState, json);*/
       //old
    }

    public HWResult updatePeriodAdminState( String json, Integer puId, Long startTime, Long endTime, Byte adminState) {
        List<Integer> list = JsonUtils.jsonToList(json,Integer.class);
        //new
        try{
            int row=0;
            for(Integer tableId:list){
                row = powerUnitStatusMapper.updatePeriodAdminState(tableId, puId, startTime, endTime, adminState);
            }

        }catch (Exception e){
            return HWResult.build(12010001,"修改失败");
        }
        return HWResult.build(10000100,"修改成功");
        //new
        //old
        /*return exchange(url + "?puId=" + puId + "&startTime=" + startTime + "&endTime=" + endTime + "&adminState=" + adminState, json);*/
        //old
    }

    /**
     * todo delete
     * @param tableIndex
     * @return
     */
    public int truncatePowerUnitStatus(Integer tableIndex){
        int i = powerUnitStatusMapper.truncatePowerUnitStatus(tableIndex);
        return i;
    }


    private HWResult exchange(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        if (exchange == null) {
            return null;
        }
        String resultJson = exchange.getBody();
        HWResult result = HWResult.format(resultJson);
        return result;
    }
}
