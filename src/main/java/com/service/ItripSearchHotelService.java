package com.service;

import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotelVO;

import java.util.List;

public interface ItripSearchHotelService {
    /*酒店搜索*/
    public Page<ItripHotelVO> SearchItripHotelPage(SearchHotelVO vo, Integer PageNo, Integer Pagesize)throws Exception;
    /*热门城市酒店搜索*/
    public List<ItripHotelVO> SearchItripHotelListByHotCity(Integer CityId, Integer pageSize);
}
