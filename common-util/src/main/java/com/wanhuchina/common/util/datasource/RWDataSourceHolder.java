package com.wanhuchina.common.util.datasource;

/**
 * <p>Copyright© 2013-2016 AutoChina International Ltd. All rights reserved.</p>
 */
public class RWDataSourceHolder {
    public static final ThreadLocal<String> holder=new ThreadLocal<>();

    public static String getDataSource(){
        return holder.get();
    }

    public static void setDataSource(String dataSourceName){
        holder.set(dataSourceName);
    }
    public static void ClearDataSource(){
        holder.remove();
    }
}
