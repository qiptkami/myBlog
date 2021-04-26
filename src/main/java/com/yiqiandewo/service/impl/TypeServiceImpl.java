package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import com.yiqiandewo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Type selectOne(Long id) {
        String key = "type_id:" + id;
        //首先查缓存
        Type type = (Type) redisUtils.get(key);

        if (type == null) {
            type = typeMapper.selectOneById(id);
            redisUtils.set(key, type);
        }

        return type;
    }

    @Override
    public Type selectOne(String name) {
        return typeMapper.selectOneByName(name);
    }

    /**
     * 仅仅查询出所有的type
     * @return
     */
    @Override
    public List<Type> selectList() {
        String pattern = "type_id*";
        List<Type> types = new ArrayList<>();
        Set<String> keys = redisUtils.keys(pattern);

        if (keys == null) {
            types = typeMapper.selectList();
            for (Type type : types) {
                redisUtils.set("type_id:" + type.getId(), type);
            }
        } else {
            for (String key : keys) {
                types.add((Type) redisUtils.get(key));
            }
        }

        return types;
    }

    /**
     * 拿到types中 blog数量最多的size个type  排序由redis的zset完成
     * 缓存可以采用zset  每个type的blog size作为score type id作为
     * @param size
     * @return
     */
    @Override
    public Map<Type, Integer> selectList(int size) {
        String key = "type_blogs";
        Map<Type, Integer> map = new LinkedHashMap<>();
        List<Type> types;
        boolean exists = redisUtils.exists(key);
        if (!exists) {
            types = typeMapper.selectListAndBlog();  //未排序
            for (Type type : types) {
                redisUtils.zAdd(key, type.getId(), (double) type.getBlogs().size());  //放入zset中
            }
        }

        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtils.zRevRangeWithScores(key, 0, -1);

        if (size >= typedTuples.size()) {
            size = typedTuples.size();
        }

        int i = 0;
        for (ZSetOperations.TypedTuple<Object> typedTuple: typedTuples) {
            if (i >= size) {
                break;
            }
            Object typeId =  typedTuple.getValue();
            Integer score = typedTuple.getScore().intValue(); //数量
            Type type = (Type) redisUtils.get("type_id:" + typeId);
            map.put(type, score);
            i++;
        }

        return map;
    }

    @Override
    public PageInfo<Type> selectList(int page, int size) {
        PageHelper.startPage(page, size);
        List<Type> types = typeMapper.selectList();
        return new PageInfo<>(types);
    }

    @Override
    public Type insert(Type type) {
        //首先判断数据库中有无这个数据
        Type t = typeMapper.selectOneByName(type.getName());
        if (t != null) {
            return null;
        }
        typeMapper.insert(type);
        //放入缓存
        redisUtils.set("type_id:" + type.getId(), type);
        return type;
    }

    @Override
    public Type update(Long id, Type type) {
        Type t = typeMapper.selectOneById(id);
        if (t == null) {
            return null;
        }
        typeMapper.update(type);
        //更新缓存
        redisUtils.set("type_id:" + type.getId(), type);
        return type;
    }

    @Override
    public void delete(Long id) {
        boolean flag = false;  //是否可以删除
        //首先需要先判断是否有博客是这个类型
        Map<Type, Integer> map = this.selectList(10000000);

        for (Type type : map.keySet()) {
            if (type.getId().equals(id)) { //Long类型比较大小用equals
                if (map.get(type) == 0) {
                    flag = true;
                }
            }
        }

        if (flag) {
            //如果没有，才能删除
            typeMapper.delete(id);
            redisUtils.del("type_id:" + id);
        } else {
            throw new RuntimeException("该类型下还有所属博客！！！");
        }

    }

}
