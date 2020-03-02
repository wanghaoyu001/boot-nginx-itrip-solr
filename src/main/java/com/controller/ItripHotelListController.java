package com.controller;

import com.po.Dto;
import com.service.ItripSearchHotelService;
import com.util.DtoUtil;
import com.util.EmptyUtil;
import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotCityVO;
import com.util.vo.SearchHotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value="/api/hotellist")
public class ItripHotelListController {
    @Autowired
    private ItripSearchHotelService itripSearchHotelService;

    public ItripSearchHotelService getItripSearchHotelService() {
        return itripSearchHotelService;
    }

    public void setItripSearchHotelService(ItripSearchHotelService itripSearchHotelService) {
        this.itripSearchHotelService = itripSearchHotelService;
    }

    /*酒店查询*/
    @RequestMapping(value="/searchItripHotelPage")
    public Dto searchItripHotelPage(HttpServletRequest request, HttpServletResponse response,@RequestBody SearchHotelVO vo){
        System.out.println("酒店搜索模块");
        Page page=null;//分页结果集
        //传入参数的判空判null（目的地）
        if (EmptyUtil.isEmpty(vo)||EmptyUtil.isEmpty(vo.getDestination())){
            return DtoUtil.returnFail("目的地不能为空","20002");
        }
        try {
            //调用solr搜索业务层，获取酒店分页数据
            page=itripSearchHotelService.SearchItripHotelPage(vo,vo.getPageNo(),vo.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，获取数据失败","20001");
        }
        //指定条件没有酒店
        if(page.getRows().size()==0){
            System.out.println("指定条件酒店为空");
            return DtoUtil.returnFail("指定条件酒店为空","20004");
        }
        return DtoUtil.returnDataSuccess(page);
    }
    //热门城市酒店查询
    @RequestMapping(value = "searchItripHotelListByHotCity")
    private Dto searchItripHotelListByHotCity(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchHotCityVO vo){
        System.out.println("酒店查询模块");
        if (EmptyUtil.isEmpty(vo)||EmptyUtil.isEmpty(vo.getCityId())){
            return DtoUtil.returnFail("城市ID不能为空", "20004");
        }
        List<ItripHotelVO> hotelVOList=itripSearchHotelService.SearchItripHotelListByHotCity(vo.getCityId(), vo.getCount());
        if (hotelVOList.size()==0){
            return DtoUtil.returnFail("城市酒店为空", "20004");
        }
        return DtoUtil.returnDataSuccess(hotelVOList);
    }
}
