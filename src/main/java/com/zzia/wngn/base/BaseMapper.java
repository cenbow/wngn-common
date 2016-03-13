package com.zzia.wngn.base;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    /**
     * 向数据库插入数据
     * 
     * @param map
     * @return
     */
    public int insertByMap(Map<String, Object> map);

    /**
     * 更新数据
     * 
     * @param map
     * @return
     */
    public int updateByMap(Map<String, Object> map);

    /**
     * 删除数据
     * 
     * @param map
     * @return
     */
    public int deleteByMap(Map<String, Object> map);

    /**
     * 批量删除数据
     * 
     * @param list
     * @return
     */
    public int deleteBatch(List<String> list);

    /**
     * 通过ID删除数据
     * 
     * @param id
     * @return
     */
    public int deleteById(String id);

    /**
     * 向数据库插入数据
     * 
     * @param t
     * @return
     */
    public int insert(T t);

    /**
     * 更新数据
     * 
     * @param t
     * @return
     */
    public int update(T t);

    /**
     * 删除数据
     * 
     * @param t
     * @return
     */
    public int delete(T t);

    /**
     * 查询数据，如果匹配多个返回第一个
     * 
     * @param t
     * @return
     */
    public T select(T t);

    /**
     * 通过ID查询数据
     * 
     * @param id
     * @return
     */
    public T selectById(String id);

    /**
     * 查询数据，如果匹配多个返回第一个
     * 
     * @param map
     * @return
     */
    public T selectByMap(Map<String, Object> map);

    /**
     * 查询数据列表
     * 
     * @param map
     * @return
     */
    public List<T> selectListByMap(Map<String, Object> map);

    /**
     * 查询数据总数
     * 
     * @param map
     * @return
     */
    public int selectListCountByMap(Map<String, Object> map);

}
