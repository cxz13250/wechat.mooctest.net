import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.util.HttpRequestUtil;

import net.sf.json.JSONObject;

import org.junit.Test;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
public class HttpTest {

    @Test
    public void test1(){
        String result = HttpRequestUtil.sendPost(WitestManager.join_url,"account=18652376580&managerName=СѕЧе&groupId=1");
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
        System.out.println(result);
    }

}

