package com.yiqiandewo.pojo;

import java.util.Date;
import java.util.List;

public class Blog {
    private Long id;
    private String title;  //标题
    private String content;  //内容
    private String firstPicture;  //首图
    private String flag; //原创 转载 ...
    private Integer views;  //浏览次数
    private boolean appreciation;  //赞赏开启
    private boolean shareStatement; //转载开启
    private boolean commentAble; //评论开启
    private boolean published;  //是否发布
    private boolean recommend; //是否推荐
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    /*
    新增一个blog时， 如果type和tag不存在，需停先新增type和tag
     */
    private Type type; //对一
    private List<Tag> tags; //对多

    private User user; //对一

    private List<Comment> comments; //对多

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public Integer getViews() {
        return views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public boolean isCommentAble() {
        return commentAble;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public void setCommentAble(boolean commentAble) {
        this.commentAble = commentAble;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentAble=" + commentAble +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
