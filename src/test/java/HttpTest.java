import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.entity.JoinGroup;
import com.mooctest.weixin.entity.JoinResult;
import com.mooctest.weixin.entity.TaskInfo;
import com.mooctest.weixin.json.JSONObject;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Quiz;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.HttpRequestUtil;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
public class HttpTest {

    @Test
    public void test1(){
    	/*List<Group> list=new ArrayList<Group>();
		String param="managerId="+725;
		String result=HttpRequestUtil.sendGet(WitestManager.group_url2, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("groups"));
		Group group=new Group();
		JSONObject obj=new JSONObject();
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			System.out.println(obj);
			group=(Group)JSONObject.toBean(obj, Group.class);
			list.add(group);
		}*/
    	Quiz quiz=Managers.quizManager.getQuiz("133");
    	JSONObject jsonObject=new JSONObject(quiz.getContent());
    	System.out.println(jsonObject.length());
    }

}

