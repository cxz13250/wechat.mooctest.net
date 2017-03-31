import com.mooctest.weixin.util.HttpRequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
public class HttpTest {

    @Test
    public void test1(){
        String result = HttpRequestUtil.sendGet("http://mooctest.net/api/wechat/getTaskInfo","account=wangjiawei0227@163.com");
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
//        jsonArray.forEach(e->{
//            System.out.println(e.get("workerId"));
//        });
//
//
//        System.out.println(result);
//        JSONObject jsonObject=JSONObject.fromObject(result);
//        JSONArray jArray=JSONArray.fromObject(jsonObject.get("data"));
//        JSONObject obj=jArray.getJSONObject(0);
//        System.out.println(obj);
    }

}

