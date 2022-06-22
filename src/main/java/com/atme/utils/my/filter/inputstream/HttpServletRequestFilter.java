package com.atme.utils.my.filter.inputstream;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


//参考文章：http://dditblog.com/itshare_529.html
//
//        代码需要获取入参里面URL后面的参数，还有POST请求里面BODY的数据；
//
//        1、直接获取URL后面的数据
//
//        直接使用 request.getParameter("appKey"); 就可以完成
//
//        2、获取BODY里面的JSON数据
//
//        无法直接获取request，直接获取getInputStream()里面入参情况，遍历的时候会提示stream close()的错误；
//
//        造成原因：
//
//        request在Spring里面已经获取了一次，流只能读一次、 读了就没有了、为了后面的代码还能够取得流；
//        -----------------------------------
//        解决request.getInputStream()只能读取一次的问题


/***
 * HttpServletRequest 过滤器
 * 解决: request.getInputStream()只能读取一次的问题
 * 目标: 流可重复读
 */

@WebFilter(urlPatterns = {"/volunteer/dim/list"})
//@WebFilter和@Configuration同时存在时会初始化两次filter 将会拦截所有的路径
//@Configuration
public class HttpServletRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中
        // 在chain.doFiler方法中传递新的request对象
        if(null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /***
     * HttpServletRequest 包装器
     * 解决: request.getInputStream()只能读取一次的问题
     * 目标: 流可重复读
     */
    public class RequestWrapper extends HttpServletRequestWrapper {

        /**
         * 请求体
         */
        private String mBody;

        public RequestWrapper(HttpServletRequest request) {
            super(request);
            // 将body数据存储起来
            mBody = getBody(request);
        }

        /**
         * 获取请求体
         * @param request 请求
         * @return 请求体
         */
        private String getBody(HttpServletRequest request) {

            return HttpContextUtils.getBodyString(request);
        }

        /**
         * 获取请求体
         * @return 请求体
         */
        public String getBody() {
            return mBody;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            // 创建字节数组输入流
            final ByteArrayInputStream bais = new ByteArrayInputStream(mBody.getBytes(StandardCharsets.UTF_8));

            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }

    }
}


