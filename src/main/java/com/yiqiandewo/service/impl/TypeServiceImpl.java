package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@CacheConfig(cacheNames = "type")  //配置缓存名字 的前缀
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /*
    首先先去查cache，按照指定的cacheNames获取，然后根据指定的key去获取，如果没有查到，就调用方法，然后将结果放入缓存
     */
    @Cacheable(key = "#id")  //缓存中的key 就是 cacheNames+key 即 type::id
    @Override
    public Type selectOne(Long id) {
        return typeMapper.selectOneById(id);
    }

    @Cacheable(key = "#name")
    @Override
    public Type selectOne(String name) {
        return typeMapper.selectOneByName(name);
    }

    /**
     * 仅仅查询所有的type
     * @return
     */
    @Cacheable() //如果没有参数；key=new SimpleKey();
    @Override
    public List<Type> selectList() {
        return typeMapper.selectList();
    }

    @Cacheable(key = "#size")
    @Override
    public List<Type> selectList(int size) {
        List<Type> types = typeMapper.selectListAndBlog();  //拿到types中 blog数量最多的size个type
        types.sort(new Comparator<Type>() {
            @Override
            public int compare(Type t1, Type t2) {
                return t2.getBlogs().size() - t1.getBlogs().size();
            }
        });

        if (size >= types.size()) {
            return types;
        }
        return types.subList(0, size);
    }


    @Cacheable() //如果有多个参数：key=new SimpleKey(params);
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
        return type;
    }

    //既调用方法，又更新缓存数据；同步更新缓存
    @CachePut(key = "#result.id")   //方法返回值不能为null
    @Override
    public Type update(Long id, Type type) {
        Type t = typeMapper.selectOneById(id);
        if (t == null) {
            return null;
        }
        typeMapper.update(type);
        return type;
    }

    /*
      @CacheEvict：缓存清除 key：指定要清除的数据 allEntries = true：指定清除这个缓存中所有的数据
      beforeInvocation = false：缓存的清除是否在方法之前执行
       默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
      beforeInvocation = true： 代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     */
    @CacheEvict(beforeInvocation = true, key = "#id")
    @Override
    public void delete(Long id) {
        typeMapper.delete(id);
    }

}
