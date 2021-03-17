package com.yiqiandewo.pojo;

import java.util.List;

//标签
public class Tag {
    private Long id;
    private String name;

    private List<Blog> blogs; //对多

    public Tag(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
