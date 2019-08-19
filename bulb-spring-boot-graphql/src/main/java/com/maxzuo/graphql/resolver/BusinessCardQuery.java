package com.maxzuo.graphql.resolver;

import com.maxzuo.graphql.vo.BrowsingHistoryCardVO;
import com.maxzuo.graphql.vo.BusinessCardFavoritesVO;
import com.maxzuo.graphql.vo.BusinessCardVO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 名片查询
 * <p>
 * Created by zfh on 2019/08/19
 */
@Component
public class BusinessCardQuery implements GraphQLQueryResolver {

    /**
     * 名片基本信息
     */
    public DataFetcher<BusinessCardVO> queryBusinessCard () {
        return new DataFetcher<BusinessCardVO>() {
            @Override
            public BusinessCardVO get(DataFetchingEnvironment environment) throws Exception {
                // TODO: 参数
                System.out.println("queryBusinessCard 参数: " + environment.getArguments());

                BusinessCardVO businessCardVO = new BusinessCardVO();
                businessCardVO.setCardId(1);
                businessCardVO.setName("dazuo");
                businessCardVO.setSignature("signature");
                businessCardVO.setPosition("java");
                businessCardVO.setCompany("dongsha");
                businessCardVO.setOtherJobs("[]");
                businessCardVO.setTradeId(1);
                businessCardVO.setTradeName("美业");
                businessCardVO.setBusiness("business");
                businessCardVO.setMobile("111");
                businessCardVO.setEmail("@qq");
                businessCardVO.setQq("qq");
                businessCardVO.setWechat("wechat");
                businessCardVO.setProvince("省");
                businessCardVO.setCity("市");
                businessCardVO.setArea("区");
                businessCardVO.setAddress("address");
                businessCardVO.setLongitude("110.0");
                businessCardVO.setLatitude("2300");
                return businessCardVO;
            }
        };
    }

    /**
     * 查询浏览名片历史
     */
    public DataFetcher<List<BrowsingHistoryCardVO>> querybrowsingHistoryCard () {
        return new DataFetcher<List<BrowsingHistoryCardVO>>() {
            @Override
            public List<BrowsingHistoryCardVO> get(DataFetchingEnvironment environment) throws Exception {
                return null;
            }
        };
    }

    /**
     * 查询名片收藏夹
     */
    public DataFetcher<List<BusinessCardFavoritesVO>> queryBusinessCardFavorites () {
        return new DataFetcher<List<BusinessCardFavoritesVO>>() {
            @Override
            public List<BusinessCardFavoritesVO> get(DataFetchingEnvironment environment) throws Exception {
                return null;
            }
        };
    }

    /**
     * 查询发现页
     */
    public DataFetcher queryDiscoverCard() {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
                return null;
            }
        };
    }
}
