package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController
{
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) throws IOException
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        // 打印user信息
        System.out.println(githubUser.getName());

        if (githubUser.getId() != null)
        {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
        }

        // 重定向首页
        // return "redirect:/";
        // 解决重定向后uri参数携带JSESSIONID
        response.sendRedirect("/");
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request) throws IOException
    {
        // 退出登录 移除Session和Cookie
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // 重定向首页
        response.sendRedirect("/");
        return null;
    }
}
