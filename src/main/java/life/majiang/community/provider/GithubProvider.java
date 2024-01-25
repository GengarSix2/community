package life.majiang.community.provider;


import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider
{
    /*
    p12利用accesstoken进行用户信息获取的方法现在不适用了，应该改成下面这段代码，
    即不需要再url里直接加入token，而应该再响应头中加入下面的话
    Request request = new Request.Builder()
    .url("https://api.github.com/user")
    .header("Authorization","token "+accessToken)
    .build();
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO)
    {
        MediaType mediaType = MediaType.get("application/json");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute())
        {
            assert response.body() != null;
            String responseBody = response.body().string();
            String tokenStr = responseBody.split("&")[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public GithubUser getUser(String accessToken) throws IOException
    {
        OkHttpClient client = new OkHttpClient();

//        Request request = new Request.Builder()
//                .url(url)
//                .build();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute())
        {
            assert response.body() != null;
            String responseBody = response.body().string();
            // String类型的JSON对象解析为Java对象
            GithubUser githubUser = JSON.parseObject(responseBody, GithubUser.class);
            return githubUser;
        }
    }
}

