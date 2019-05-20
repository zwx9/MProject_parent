package com.zwx.base;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zwx.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserZuulFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 通过返回值指定过滤器类型:
     *      pre：可以在请求被路由之前调用
     *      route：在路由请求时候被调用
     *      post：在route和error过滤器之后被调用
     *      error：处理请求时发生错误时被调用
     * */
    public String filterType() {
        return "pre";
    }

    //如果有多个过滤器，当前过滤器的执行顺序:  0>1>2   （定义filter的顺序，数字越小表示顺序越高，越先执行）
    public int filterOrder() {
        return 0;
    }

    //是否开启本地过滤器
    public boolean shouldFilter() {
        return true;
    }


    /**
     * 权限校验：（实际是验证是否登录，若登录即可进行操作，未登录则失败不可操作）
     *      如果已登录，则在Header中找到Authorization值，
     *      否则即未登录，校验失败。
     * */
    //注意：过滤将请求拦截后 是否放行，与返回值无关。
    //return  任意值 ，都代表 放行；
    //如果要终止请求： requestContext.setSendZuulResponse(false);
    public Object run(){
        System.out.println("userzuul过滤器！");
        //获取header
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");

        //若存在Authorization，则进行实际校验
        //若是登录（即login），则无需拦截 ; 若不是登录则进行校验
        String url = request.getRequestURL().toString();
        if (url.equals("user/login")){
            System.out.println("登录页面");
            return null;
        }

        //如果访问的是网关，则也不需要进行权限校验；网关的方法名就是“OPTIONS”
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("访问的是网关");
            return null;
        }

        //试图通过网关请求真实的服务，因此需要真正的校验
        if (authorization != null && authorization.startsWith("Bearer ")) {
            //获取token
            String token = authorization.substring(7);
            //解析token
            System.out.println(token);
            Claims claims = jwtUtil.parseJwt(token);
            if (claims != null) {
                //判断角色是否是User，如果是 则放行(return null)
                if ("user".equals(claims.get("roles"))) {
                    //符合user角色
                    System.out.println("当前为user身份");
                    //将Header信息传递到 其他具体的微服务 （header信息只能使用一次，在经过Zuul时已经被使用了。如果要传递给其他服务，必须再将它手工传递一次）
                    requestContext.addZuulRequestHeader("Authorization",authorization);
                    return null;
                }
            }
            //终止请求
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(444);//请求错误的状态码
            requestContext.setResponseBody("当前请求不是User权限！");//提示文字
            requestContext.getResponse().setContentType("text/html;charset=utf-8");

            return null;
        }


        return null;
    }
}
