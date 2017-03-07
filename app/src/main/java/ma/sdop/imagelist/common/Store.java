package ma.sdop.imagelist.common;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class Store<Value> extends LinkedHashMap<String, Object> implements Comparable {
    private String compareString = null;
    private boolean ASC = true;

    private static final String STORE_TYPE = "ma.sdop.imagelist.common.Store";

    private enum Type {
        Object,
        List
    }

    private LinkedHashMap<String, String> types;

    public Store(){
        super();
        types = new LinkedHashMap<>();
    }

    public String toString(){
        StringBuffer msg = new StringBuffer();
        msg.append("{");
        String[] keys = getKeys();
        String[] values = getStringArray();

        for(int i=0;i<keys.length;i++){
            if(i!=0) msg.append(",");
            msg.append(keys[i].toString()+":"+values[i].toString());
        }
        msg.append("}");
        return msg.toString();
    }

    public Value get(String key){
        Value result = (Value) super.get(key);
        if(result == null) return null;
        return result;
    }

    public Value get(Object key){
        return get(String.valueOf(key));
    }

    public String getString(String key){
        Object result = get(key);
        if (result == null) return null;
        return (String) result;
    }

    public String getString(String key, String defaultValue){
        Object result = this.get(key);
        if (result == null) return defaultValue;
        return (String)result;
    }

    public Integer getInt(String key){
        Object result = get(key);
        if(result == null) return null;
        if(result instanceof java.lang.String) return Integer.parseInt((String)result);
        return (Integer) result;
    }

    public int getInt(String key, int defaultValue){
        Object result = get(key);
        if(result == null) return defaultValue;
        if(result instanceof java.lang.String){
            return Integer.parseInt((String) result);
        }
        return (Integer) result;
    }

    public Float getFloat(String key){
        Object result = get(key);
        if (result == null) return null;
        if(result instanceof java.lang.String) return Float.parseFloat((String) result);
        return (Float) result;
    }

    public Float getFloat(String key, float defaultValue){
        Object result = get(key);
        if(result == null) return defaultValue;
        if(result instanceof java.lang.String) return Float.parseFloat((String) result);
        return (Float) result;
    }

    public Long getLong(String key){
        Object result = get(key);
        if(result == null) return null;
        if(result instanceof java.lang.String) return Long.parseLong((String)result);
        return (Long) result;
    }

    public Boolean getBoolean(String key){
        Object result = get(key);
        if(result == null) return null;
        if(result instanceof java.lang.String) return Boolean.parseBoolean((String)result);
        return (Boolean) result;
    }

    public Boolean getBoolean(String key, boolean defaultValue){
        Object result = get(key);
        if(result == null) return defaultValue;
        if(result instanceof java.lang.String) return Boolean.parseBoolean((String)result);
        return (Boolean) result;
    }

    public void put(String keys[], Object[] values){
        if(keys.length != values.length) throw new IllegalArgumentException("Both length of keys and values are invalid.");

        for(int i=0; i<keys.length; i++) this.put(keys[i], values[i]);
    }

    public synchronized Store<Value> put(String key, Object value){
        super.put(key, value);
        types.put(key, "java.lang.Object");
        return this;
    }

    public synchronized Store<Value> put(String key, Object value, String type){
        super.put(key, value);
        types.put(key, type);
        return this;
    }

    private String[] getKeys(){
        if(this.size()==0)  return new String[0];

        String[] keys = new String[this.size()];
        this.keySet().toArray(keys);
        return keys;
    }

    public String[] getStringArray(){
        if(this.size()==0)
            return null;
        Object[] o = this.values().toArray();
        String[] values= new String[this.size()];
        for(int i=0; i<values.length; i++){
            values[i] = (String)o[i];
        }
        return values;
    }

    public synchronized Store<Value> add(String key, Object value){
        put(key, value);
        return this;
    }

    public void setASC(boolean ASC){
        this.ASC = ASC;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if(compareString==null) compareString=this.getString(this.getKeys()[0]);
        if(ASC) return this.getString(compareString).compareTo(((Store)another).getString(compareString));
        else return this.getString(compareString).compareTo(((Store)another).getString(compareString))*(-1);
    }
}
