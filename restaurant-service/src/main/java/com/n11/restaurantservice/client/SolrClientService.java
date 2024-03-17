package com.n11.restaurantservice.client;

import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author BeytullahBilek
 */
@Service
@SuppressWarnings("deprecation")
@RequiredArgsConstructor
public class SolrClientService {
    @Value("http://localhost:8983/solr/restaurants")
    private String solrUrl;

    private final RestaurantMapper restaurantMapper;
    public List<RestaurantResponse>solrQuery(String userLocation){
        try (HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build()){

            final Map<String ,String> queryParamMap =new HashMap<>();

            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "fq={!geofilt pt=" + userLocation + " sfield=location d=10}");
            queryParamMap.put("start", "0");
            queryParamMap.put("rows", "3");
            queryParamMap.put("sort", "sum(mul(div(averageScore,5),7),mul(div(sub(10,geodist(" + userLocation + ",location)),10),3)) desc");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);
            QueryResponse queryResponse=solrClient.query(queryParams);
            SolrDocumentList results = queryResponse.getResults();

            List<Restaurant>restaurantList = new ArrayList<>();

            for(SolrDocument solrDocument : results) {
                Restaurant restaurant =new Restaurant();

                  restaurant.setId(solrDocument.get("id").toString());
                  restaurant.setName(solrDocument.get("name").toString());
                  restaurant.setAverageScore((Double) solrDocument.get("averageScore"));
                  restaurant.setLocation(solrDocument.get("location").toString());
                  restaurantList.add(restaurant);

            }
            return restaurantMapper.convertToRestaurantResponseList(restaurantList);

        } catch (SolrServerException | java.io.IOException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
