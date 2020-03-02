package com.service.Impl;

import com.mapper.BaseQuery;
import com.service.ItripSearchHotelService;
import com.util.EmptyUtil;
import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;



import java.util.List;
@Service
public class ItripSearchHotelServiceImpl implements ItripSearchHotelService {
    private BaseQuery<ItripHotelVO> itripHotelVOBaseQuery=new BaseQuery<>("http://127.0.0.1:8080/solr/hotel");
    @Override
    public Page<ItripHotelVO> SearchItripHotelPage(SearchHotelVO vo, Integer PageNo, Integer Pagesize) throws  Exception {
       //查询所有
        SolrQuery query=new SolrQuery("*:*");
        //条件拼接字符串和标识符
        StringBuffer tempquery=new StringBuffer();
        int tempFlag=0;
        //传入条件不为空
        if(EmptyUtil.isNotEmpty(vo)){
            //目的地不为空
            if(EmptyUtil.isNotEmpty(vo.getDestination())){
                tempquery.append("destination:"+vo.getDestination());
                tempFlag=1;
            }
            //酒店级别不为空
            if(EmptyUtil.isNotEmpty(vo.getHotelLevel())){
              query.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
                //tempquery.append("hotelLevel:"+vo.getHotelLevel());
            }
            //最高价格minPrive酒店最低<=vo.getMaxPrice()传入最大
            if(EmptyUtil.isNotEmpty(vo.getMaxPrice())){
                query.addFilterQuery("minPrice:"+"[* TO "+vo.getMaxPrice()+"]");
            }
            //最低价格vo.getMinPrice()传入最低>maxPrive酒店最高
            if(EmptyUtil.isNotEmpty(vo.getMinPrice())){
               query.addFilterQuery("maxPrice:"+"["+vo.getMinPrice()+" TO *]");
            }
            //按照指定字段升序
            if (EmptyUtil.isNotEmpty(vo.getAscSort())){
                query.addSort(vo.getAscSort(),SolrQuery.ORDER.asc);
            }
            //按照指定字段降序
            if (EmptyUtil.isNotEmpty(vo.getDescSort())){
                query.addSort(vo.getDescSort(),SolrQuery.ORDER.desc);
            }
            //关键字不为空
            if(EmptyUtil.isNotEmpty(vo.getKeywords())){
                if(tempFlag==1){
                    tempquery.append(" AND "+"keyword:"+vo.getKeywords());
                }else{
                    tempquery.append("keyword:"+vo.getKeywords());
                }

            }
        }
        //条件结束，判断是否有条件
        if(EmptyUtil.isNotEmpty(tempquery.toString())){
            System.out.println("酒店目的地查询条件字符串："+tempquery.toString());
            query.setQuery(tempquery.toString());
        }
        System.out.println("solr查询语句："+query.toString());
        Page<ItripHotelVO> page=itripHotelVOBaseQuery.queryPage(query,PageNo,Pagesize,ItripHotelVO.class);
        return page;
    }

    @Override
    public List<ItripHotelVO> SearchItripHotelListByHotCity(Integer CityId, Integer pageSize) {
        //查询所有
        SolrQuery query=new SolrQuery("*:*");
        //城市ID不为空
        if (EmptyUtil.isNotEmpty(CityId)){
            query.addFilterQuery("cityId:"+CityId);
        }else {
            return null;
        }
        System.out.println("solr查询语句："+query.toString());
        List<ItripHotelVO> hotelVolist= null;
        try {
            hotelVolist = itripHotelVOBaseQuery.queryList(query, pageSize, ItripHotelVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotelVolist;
    }
}
