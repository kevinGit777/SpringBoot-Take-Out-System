package com.myProject.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;


/***
 * common class handle the result return from the server to the web client
 * 
 * @author kevin
 *
 *
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //encoded result, 1 success, 0 and other is fail

    private String msg; //error message

    private T data; // data 

    private Map map = new HashMap(); //key value pair that not bounded by type

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
