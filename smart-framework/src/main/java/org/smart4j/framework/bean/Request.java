package org.smart4j.framework.bean;

import java.util.Objects;

/**
 * 封装请求信息
 */
public class Request {
    //请求方法
    private String requestMethod;
    //请求路径
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return Objects.equals(requestMethod, request.requestMethod) &&
                Objects.equals(requestPath, request.requestPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, requestPath);
    }
}
