package com.bishe.bishe.es;

import com.bishe.bishe.model.esmodel.EsBackInfo;
import com.bishe.bishe.model.esmodel.EsWarc;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.DeleteIndex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class EsDaoImpl implements EsDao {
    static protected final Log log = LogFactory.getLog(EsDaoImpl.class.getName());
    //约定返回的map，有total总条数，还有warclist两个key
    private static JestClient client;
    static{
        try{
            client = MyClient.getClient();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    /**
     *
     * @param type ：当前删除document名称
     * @return
     */
    public JestResult deleteIndex(String type) {
        DeleteIndex deleteIndex = new DeleteIndex.Builder(type).build();
        JestResult result = null ;
        try {
            result = client.execute(deleteIndex);
            log.info("deleteIndex == " + result.getJsonString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result ;
    }

    /**
     *
     * @param script
     * @param index ：文档在哪存放
     * @param type ： 文档表示的对象类别
     * @param id ：文档唯一标识
     * @return
     */
    public JestResult updateDocument(String script, String index, String type, String id) {
        Update update = new Update.Builder(script).index(index).type(type).id(id).build();
        JestResult result = null ;
        try {
            result = client.execute(update);
            log.info("updateDocument == " + result.getJsonString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result ;
    }

    /**
     *
     * @param index ：文档在哪存放
     * @param type ： 文档表示的对象类别
     * @param id ：文档唯一标识
     * @return
     */
    public Boolean deleteDocument(String index, String type, String id) {
        Delete delete = new Delete.Builder(id).index(index).type(type).build();
        JestResult result = null ;
        Double successful = null;
        try {
            result = client.execute(delete);
            JsonObject jsonObject = result.getJsonObject();
            Gson gson = new Gson();
            Map tempmap = gson.fromJson(jsonObject.getAsJsonObject("_shards"),Map.class);
            successful = (Double) tempmap.get("successful");
            log.info("deleteDocument == " + result.getJsonString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return successful!=(Double) 0.0;
    }

    /**
     *
     * @param index
     * @param type
     * @param params
     * @return
     */
    public JestResult deleteDocumentByQuery(String index, String type, String params) {
        DeleteByQuery db = new DeleteByQuery.Builder(params)
                .addIndex(index)
                .addType(type)
                .build();

        JestResult result = null ;
        try {
            result = client.execute(db);
            log.info("deleteDocument == " + result.getJsonString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param index ：文档在哪存放
     * @param type ： 文档表示的对象类别
     * @param id ：文档唯一标识
     * @return map-took,total,warc
     */
    public Map getDocument(String index, String type, String id) {
        Get get = new Get.Builder(index, id).type(type).build();
        Map map = new HashMap();
        JestResult result = null ;
        try {
            result = client.execute(get);
            JsonObject jsonObject = result.getJsonObject();
            Gson gson = new Gson();
            Map tempmap = gson.fromJson(jsonObject,Map.class);
            map = (Map) tempmap.get("_source");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     *
     * @param index
     * @return map-String or list<warc>
     */
    public Map searchAll(String index,Integer size,Integer page) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(size).from(size*(page-1));
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(index)
                .build();
        SearchResult result = null ;
        List<?> hits = null ;
        Map map = new HashMap();
        try {
            result = client.execute(search);
            System.out.println("本次查询共查到："+result.getTotal()+"个关键字！");
            log.info("本次查询共查到："+result.getTotal()+"个关键字！");
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
            List<EsWarc> warcList = this.suggestForTransferHits(hitsobject);
            long took = jsonObject.get("took").getAsLong();
            long total = hitsobject.get("total").getAsLong();

            map.put("took",took);
            map.put("total",total);
            map.put("warcList",warcList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map ;
    }

    /**
     *
     * @param keyWord ：搜索关键字
     * @param fields
     * @return
     */
    public Map createSearch(String keyWord, String index,Integer size,Integer page, String... fields) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(size).from(size*(page-1));
            searchSourceBuilder.query(QueryBuilders.queryStringQuery(keyWord));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).build();
        SearchResult result = null ;
        List<?> hits = null ;
        Map map = new HashMap();
        try {
            result = client.execute(search);
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
            Long total = hitsobject.get("total").getAsLong();
            List<EsWarc> warcList = this.suggestForTransferHits(hitsobject);
            map.put("total",total);
            map.put("warcList",warcList);
            System.out.println("本次查询共查到："+result.getTotal()+"个结果！");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 用于把hits内容转换成Eswarc格式列表
     * @param hitsjson
     * @return
     */
    public List<EsWarc> suggestForTransferHits(JsonObject hitsjson){

        List<EsBackInfo> allInfoList = new LinkedList<EsBackInfo>();//专门装返回的所有数据,备用
        List<EsWarc> warcList = new LinkedList<EsWarc>();//专门装warc部分数据
        Gson gson = new Gson();
        Map tempmap = gson.fromJson(hitsjson,Map.class);
        //Gson解析
        List<Map> hitsList = (List<Map>) tempmap.get("hits");
        for (Map map:hitsList){
            EsBackInfo esBackInfo = new EsBackInfo();
            EsWarc esWarc = new EsWarc();
            esBackInfo.setId((String) map.get("_id"));
            esBackInfo.setIndex((String) map.get("_index"));
            esBackInfo.setType((String) map.get("_type"));
            esBackInfo.setScore((Double) map.get("_score"));
            esBackInfo.setSource((Map<String, Object>) map.get("_source"));

            allInfoList.add(esBackInfo);

            Map<String,Object > esmap = esBackInfo.getSource();
            esWarc.setMimeType((String)esmap.get("mimeType"));
            esWarc.setHeaderFields((String)esmap.get("headerFields"));
            esWarc.setHeaderWarcType((String)esmap.get("headerWarcType"));
            esWarc.setContent((String)esmap.get("content"));
            esWarc.setWarcUrl((String)esmap.get("warcUrl"));
            esWarc.setUpdateTime((String)esmap.get("updateTime"));
            esWarc.setAddTime((String)esmap.get("addTime"));
            esWarc.setResponseUrl((String)esmap.get("responseUrl"));
            if (esmap.get("create_at")!=null)
                esWarc.setCreate_at(Math.round((Double) esmap.get("create_at")));
            if (esmap.get("update_at")!=null)
                esWarc.setUpdate_at(Math.round((Double) esmap.get("update_at")));
            esWarc.setId(esBackInfo.getId());
            warcList.add(esWarc);
        }

        return warcList;
    }

    /**
     * 重载方法，搜索除了时间外的关键字
     * @param keyWord
     * @param index
     * @param field(除了时间外)
     * @return
     */
    public Map createSearch(String keyWord, String index, String field,Integer size,Integer page) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(size).from(size*(page-1));
        String querystr = "*" + keyWord + "*";
        searchSourceBuilder.query(QueryBuilders.wildcardQuery(field,querystr));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).build();
        SearchResult result = null ;
        List<?> hits = null ;
        Map map = new HashMap();
        try {
            result = client.execute(search);
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
            Long total = hitsobject.get("total").getAsLong();
            List<EsWarc> warcList = this.suggestForTransferHits(hitsobject);
            map.put("total",total);
            map.put("warcList",warcList);
            System.out.println("本次查询共查到："+result.getTotal()+"个结果！");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
     * 重载搜索，搜索时间段
     * @param index
     * @param field
     * @param form
     * @param to
     * @return
     */
    public Map createSearch(String index, String field,Long form,Long to,Integer size,Integer page) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(size).from(size*(page-1));
        searchSourceBuilder.query(QueryBuilders.rangeQuery(field).from(form).to(to));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).build();
        SearchResult result = null ;
        List<?> hits = null ;
        Map map = new HashMap();
        try {
            result = client.execute(search);
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
            Long total = hitsobject.get("total").getAsLong();
            List<EsWarc> warcList = this.suggestForTransferHits(hitsobject);
            map.put("total",total);
            map.put("warcList",warcList);
            System.out.println("本次查询共查到："+result.getTotal()+"个结果！");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        EsDao esDao = new EsDaoImpl();
        String index = "warcs";
        String type = "warc";
        String keyword = "test";
        System.out.println();
        /*for (SearchResult.Hit<EsWarc,Void> hit:result) {
            System.out.println(hit);
        }
        System.out.println(result);*/
    }



}
