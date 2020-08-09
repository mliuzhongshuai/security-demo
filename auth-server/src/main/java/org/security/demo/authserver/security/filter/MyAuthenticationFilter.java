package org.security.demo.authserver.security.filter;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Liu Zhongshuai
 * @description 处理认证filter，对于客户端提交的认证请求进行处理，内置认证成功、失败的方法
 * @date 2020-08-08 10:07
 **/
public class MyAuthenticationFilter extends  UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //设置登录端点路径，security会自动处理这个路径
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        /*LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);*/
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("liuzhongshuai@itcast.cn", "liuzhongshuai123")
        );
    }


    /**
     * 成功验证后调用的方法
     * 如果验证成功，就生成token并返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //认证成功后可以通过认证信息 返回一个 token
        response.setHeader("content-type", MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("登录成功,token:"+ UUID.randomUUID().toString());


    }

    /**
     * 认证失败的方法，该方法内响应认证失败信息
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setHeader("content-type", MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

}
