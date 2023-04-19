package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.TransUtils;

/**
 * ClassName Trans
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 19:23
 */
public class Trans {
    private static final String APP_ID = "20230418001646798";
    private static final String SECURITY_KEY = "5YFlJjr3IMLTz8Bet7QP";

    public static final TransUtils TRANS_UTILS = new TransUtils(APP_ID, SECURITY_KEY);

    public static String getTranslateRes(String src){
        String transResultStr = TRANS_UTILS.getTransResult(src, "zh", "en");
        JSONObject result = JSONObject.parseObject(transResultStr);
        JSONArray jsonArray = result.getJSONArray("trans_result");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        return jsonObject.getString("dst");
    }
}
