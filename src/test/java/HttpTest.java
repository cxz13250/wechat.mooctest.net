import com.mooctest.weixin.entity.JoinGroup;
import com.mooctest.weixin.entity.JoinResult;
import com.mooctest.weixin.entity.TaskInfo;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.HttpRequestUtil;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
public class HttpTest {

    @Test
    public void test1(){
//    	JoinGroup joinGroup=new JoinGroup();
//    	joinGroup.setAccount("18652376580");
//    	joinGroup.setGroupId(Long.parseLong("12"));
//    	joinGroup.setManagerName("증합");
//    	String param=JSONSerializer.toJSON(joinGroup).toString();
//    	System.out.println(param);
//        String result = HttpRequestUtil.post(WitestManager.join_url,param);
//        System.out.println(result);
//        JSONObject jsonObject = JSONObject.fromObject(result);
////    	JSONObject jsonObject=CommonUtil.httpsRequest(WitestManager.join_url, "POST", param);
////    	System.out.println(jsonObject);
//        boolean success=jsonObject.getBoolean("success");
//    	try {
//			List<TaskInfo> list=WitestManager.getTaskInfo("18652376580");
//			for(TaskInfo taskInfo:list){
//				System.out.print(taskInfo.getTaskName()+" ");
//				System.out.println(taskInfo.getPassword());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    	JoinResult jr=WitestManager.joinGroup("18652376580", "12", "증합");
    	System.out.println(jr.getMessage());
    }

}

