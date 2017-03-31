import com.mooctest.weixin.manager.WitestManager;
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
        String result = HttpRequestUtil.sendGet(WitestManager.taskgrade_url,"account=18652376580");
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
        System.out.println(jsonArray.getJSONObject(0));
    }

}

