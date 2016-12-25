package MyWork;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30.
 */
public class ESTest {
    public static void main(String[] args) {
        String indexName = "pdinfoetl";
        String[] types = {"men", "women", "outdoor", "sport", "children", "shoes", "underwaist"};
        String[] fields = {"color", "material", "print", "style", "collarmodel", "sleevemodel", "model"};
//        String indexName = "wkzuntreatedcolor";
//        String[] types = {"wkznocolor"};
//        String[] fields = {"colors"};
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "dev.es.dp").build();
        Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.31.7", 9300));

//        long num = 0;
//        for (String indexType : types) {
//            num += count(client, indexName, indexType);
//        }
//        System.out.println(num);


        getAllData(client, indexName, types);
        client.close();
    }

    public static void getAllData(Client client, String indexName, String[] types) {
        try {
            //把导出的结果以JSON的格式写到文件里
            BufferedWriter out = new BufferedWriter(new FileWriter("D:/Work/Statistics/estotal.log", true));
            for (String indexType : types) {
                SearchResponse response = client.prepareSearch(indexName).setTypes(indexType)
                        .setQuery(QueryBuilders.matchAllQuery()).setSize(10000).setScroll(new TimeValue(600000))
                        .setSearchType(SearchType.SCAN).execute().actionGet();//setSearchType(SearchType.Scan) 告诉ES不需要排序只要结果返回即可 setScroll(new TimeValue(600000)) 设置滚动的时间
                String scrollid = response.getScrollId();

                //每次返回数据10000条。一直循环查询直到所有的数据都查询出来
                while (true) {
                    SearchResponse response2 = client.prepareSearchScroll(scrollid).setScroll(new TimeValue(1000000))
                            .execute().actionGet();
                    SearchHits searchHit = response2.getHits();
                    //再次查询不到数据时跳出循环
                    if (searchHit.getHits().length == 0) {
                        break;
                    }
                    System.out.println("查询数量 ：" + searchHit.getHits().length);
                    for (int i = 0; i < searchHit.getHits().length; i++) {
                        String json = searchHit.getHits()[i].getSourceAsString();
                        out.write(json);
                        out.write("\r\n");
                    }
                }
            }
            out.flush();
            out.close();
            System.out.println("查询结束");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public static void getData(Client client, String[] fields, String indexName, String[] types) {
        for (String field : fields) {
            try {
                //把导出的结果以JSON的格式写到文件里
                BufferedWriter out = new BufferedWriter(new FileWriter("D:/Work/Statistics/es" + field + ".log", true));
                for (String indexType : types) {
                    SearchResponse response = client.prepareSearch(indexName).setTypes(indexType)
                            .setQuery(QueryBuilders.matchAllQuery()).setSize(10000).setScroll(new TimeValue(600000))
                            .setSearchType(SearchType.SCAN).execute().actionGet();//setSearchType(SearchType.Scan) 告诉ES不需要排序只要结果返回即可 setScroll(new TimeValue(600000)) 设置滚动的时间
                    String scrollid = response.getScrollId();

                    //每次返回数据10000条。一直循环查询直到所有的数据都查询出来
                    while (true) {
                        SearchResponse response2 = client.prepareSearchScroll(scrollid).setScroll(new TimeValue(1000000))
                                .execute().actionGet();
                        SearchHits searchHit = response2.getHits();
                        //再次查询不到数据时跳出循环
                        if (searchHit.getHits().length == 0) {
                            break;
                        }
                        System.out.println("查询数量 ：" + searchHit.getHits().length);
                        for (int i = 0; i < searchHit.getHits().length; i++) {
                            //String json = searchHit.getHits()[i].getSourceAsString();
                            Map<String, Object> map = searchHit.getHits()[i].getSource();
                            Object obj = map.get(field);
                            if (obj != null) {
                                String json = obj.toString();
                                out.write(json);
                                out.write("\r\n");
                            }
                        }
                    }
                    System.out.println("查询结束");
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error");
            }
        }
    }

    public static long count(Client client, String indexName, String indexType) {
        CountResponse countresponse = client.prepareCount(indexName)
                .setQuery(QueryBuilders.termQuery("_type", indexType))
                .execute()
                .actionGet();
        return countresponse.getCount();
    }
}
