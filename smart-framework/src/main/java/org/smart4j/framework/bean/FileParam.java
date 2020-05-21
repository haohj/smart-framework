package org.smart4j.framework.bean;

import java.io.InputStream;

/**
 * 封装上传文件参数
 *
 * @author haohj
 * @date 2020-05-21 10:34
 */
public class FileParam {
    //表单字段名
    private String fieldName;
    //文件名
    private String fileName;
    //文件大小
    private long fileSize;
    //上传文件的context-type，可判断文件类型
    private String contextType;
    //文件字节输入流
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contextType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contextType = contextType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
