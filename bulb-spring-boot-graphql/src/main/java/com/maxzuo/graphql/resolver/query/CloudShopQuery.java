package com.maxzuo.graphql.resolver.query;

import com.maxzuo.graphql.resolver.GraphQLQueryResolver;
import com.maxzuo.graphql.vo.ShopInfoVO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 店铺查询
 * <p>
 * Created by zfh on 2019/08/19
 */
@Component
public class CloudShopQuery implements GraphQLQueryResolver {

    /**
     * 名片旗下店铺
     */
    public DataFetcher<List<ShopInfoVO>> querySubordinateShop() {
        return new DataFetcher<List<ShopInfoVO>>() {
            @Override
            public List<ShopInfoVO> get(DataFetchingEnvironment environment) throws Exception {
                // TODO: 参数
                Map<String, Object> arguments = environment.getArguments();
                System.out.println("querySubordinateShop 参数: " + arguments);

                ShopInfoVO shopInfoVO = new ShopInfoVO();
                shopInfoVO.setShopId(288);
                shopInfoVO.setShopName("shopName");
                shopInfoVO.setLogUrl("logo");
                shopInfoVO.setShopDesc("desc");
                shopInfoVO.setLongitude("110.0");
                shopInfoVO.setLatitude("230.0");

                List<ShopInfoVO> shopInfoVOList = new ArrayList<>(10);
                shopInfoVOList.add(shopInfoVO);
                return shopInfoVOList;
            }
        };
    }

    /**
     * 查询店铺详情
     */
    public DataFetcher<ShopInfoVO> queryShopInfo() {
        return new DataFetcher<ShopInfoVO>() {
            @Override
            public ShopInfoVO get(DataFetchingEnvironment environment) throws Exception {
                // TODO: 参数
                Map<String, Object> arguments = environment.getArguments();

                return null;
            }
        };
    }

}
