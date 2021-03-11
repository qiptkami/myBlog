角色：普通访客、管理员



访客

- 分页查看所有博客
- 查看博客数最多的几个分类
- 查看所有分类
- 查看某个分类下的博客列表
- 查看标记博客最多的10个标签
- 查看所有标签
- 查看某个标签下的所有博客
- 根据年度时间线查看博客
- 查看最新的博客
- 关键字全局搜索博客
- 查看单个博客内容
- 对博客进行评论
- 赞赏博客
- 微信扫码阅读博客



管理员

- 用户名密码登录
- 管理博客
    - 发布博客
    - 分类
    - 打标签
    - 修改博客
    - 删除博客
    - 根据标题、分类、标签查看博客
- 管理博客分类
- 管理标签



遇到的问题:

- 页面加载的很慢 需要10多s才能加载完成

    问题本质，通过F12可以看出，访问页面时间都浪费在https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic&subset=latin（这个页面是senamtic.min.css请求的）上了，这是谷歌字体，服务器在外国，请求半天最终结果是无法访问；解决办法，将字体下载到本地，将senamtic.min.css也下载到本地（https://files-cdn.cnblogs.com/files/tekikesyo/LocalGoogleFont.zip），并进行替换，在第11行f
    速度问题已经解决了，但是F12发现有3个请求报错，分别是icons.ttf、icons.woff、icons.woff2；解决方法，将相关文件导入（文件在senamtic官网下载的压缩包里面）

    



开发细节：

- 页面冗余部分

    使用thymeleaf的th:fragmenthe和th:replace进行简化

    将冗余的标签加上th:fragment属性并命名，全部加载新的一个html文件中

    将原本的部分用th:replace替换

    > **导航栏的高亮**
    >
    > 点击某个页面的时候，加上一个属性activeUri，然后在目标页面中通过该属性的值判断哪个需要高亮

    ```html
    <nav class="ui inverted attached segment m-padded-tb-mini m-shadow-small" th:fragment="topbar">
       <div class="ui container">
            <div class="ui inverted secondary stackable menu">
                <h2 class="ui teal header item">Blog</h2>
                <a href="#" th:href="@{/index}" class="m-item item m-mobile-hide" th:class="${activeUri=='index'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="home icon"></i>首页</a>
                <a href="#" th:href="@{/types}" class="m-item item m-mobile-hide" th:class="${activeUri=='types'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="idea icon"></i>分类</a>
                <a href="#" th:href="@{/tags}" class="m-item item m-mobile-hide" th:class="${activeUri=='tags'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="tags icon"></i>标签</a>
                <a href="#" th:href="@{/archives}" class="m-item item m-mobile-hide" th:class="${activeUri=='archives'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="clone icon"></i>归档</a>
                <a href="#" th:href="@{/about}" class="m-item item m-mobile-hide" th:class="${activeUri=='about'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="info icon"></i>关于我</a>
                <div class="right m-item item m-mobile-hide">
                    <div class="ui icon input">
                        <input type="text" placeholder="Search...">
                        <i class="search icon link"></i>
                    </div>
                </div>
            </div>
        </div>
        ...
    </nav>
    ```

    ```html
    <div th:replace="commons/bar::topbar(activeUri='blog')"></div>
    ```

