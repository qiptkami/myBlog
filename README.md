角色：普通访客、管理员



访客

- 分页查看所有博客

- 查看博客数最多的几个分类

- 查看所有分类

- 查看某个分类下的博客列表

- 根据年度时间线查看博客

- 查看最新的博客

- 关键字全局搜索博客

- 查看单个博客内容

- 对博客进行评论

- 赞赏博客

- 微信扫码阅读博客

- 增加一个常用的学习网站或者资源网站

    图床 https://sm.ms/

    ...



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



遇到的问题:

- 页面加载的很慢 需要10多s才能加载完成

    问题本质，通过F12可以看出，访问页面时间都浪费在https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic&subset=latin（这个页面是senamtic.min.css请求的）上了，这是谷歌字体，服务器在外国，请求半天最终结果是无法访问；解决办法，将字体下载到本地，将senamtic.min.css也下载到本地（https://files-cdn.cnblogs.com/files/tekikesyo/LocalGoogleFont.zip），并进行替换，在第11行f
    速度问题已经解决了，但是F12发现有3个请求报错，分别是icons.ttf、icons.woff、icons.woff2；解决方法，将相关文件导入（文件在senamtic官网下载的压缩包里面）

    



开发细节：

- mysql表的设计

    er图

    ![ER图](https://i.loli.net/2021/03/24/79hCOtSdgEPDr32.png)

    表关系：

    - blog-comment 一对多

        一篇博客下可以有多条评论

    - blog-user 多对一

        一篇博客下只能属于一个用户，但一个用户可以有多篇博客

    - blog-type 多对一

        一篇博客只能有一种类型，一种类型下可以有多篇博客

    - comment-childComment 一对多

        一条评论下可以有多个子评论

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
    
- 集成md编辑器

    使用的是editormd

    ```js
    //初始化Markdown编辑器
        var contentEditor;
        $(function () {
            contentEditor = editormd("md-content",{
                width:"100%",
                height:640,
                syncScrolling:"single",
                path:"../lib/editormd/lib/"  //路径一定要正确
            });
        });
    ```
    ```html
    <div class="field">
        <div id="md-content" style="z-index: 1 !important;"><!--将markdown文本框放到最上方以免全屏布局错乱-->
            <textarea name="content" placeholder="博客内容" style="display: none;">
                ### Disabled options
                - TeX (Based on KaTeX);
                -Emoji;
                -Task lists;
                -HTML tags decode;f
            </textarea>
        </div>
    </div>
    ```

- 异常处理

    > 在templates目录下的error目录，springboot会自动根据错误状态码找到对应的错误页面（html页面以状态码命名）

    同时配置异常处理器

    ```java
    @ControllerAdvice //拦截所有Controller的控制器
    public class ControllerExceptionHandler {
    
        private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class); //log4j
    
        @ExceptionHandler(Exception.class) //异常处理
        public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
            logger.error("Request Url : {}, exception : {}", request.getRequestURL(), e); //打印访问的url和异常信息
            ModelAndView mv = new ModelAndView();
            mv.addObject("url", request.getRequestURL());
            mv.addObject("exception", e);
            mv.setViewName("error/error");  //跳转到error.html
            return mv;
        }
    }
    ```

- 日志处理

    用spring aop对controller进行拦截

    用环绕通知

    ```java
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String url = request.getRequestURL().toString();  //访问的url
    String ip = request.getRemoteAddr();   //ip
    String classMethod = pcj.getSignature().getDeclaringTypeName() + "." + pcj.getSignature().getName();  //方法
    Object[] args = pcj.getArgs();  //参数
    RequestLog requestLog = new RequestLog(url, ip, classMethod, args);  //封装
    logger.info("Request : {}" ,requestLog);
    ```



- pageHelper的细节

    采用的是mybatis的xml方式实现分页查询

    > 在xml中写sql时不应在最后加上分号，因为pageHelper在帮我们分页时，会根据参数pageNo和pageSize给sql语句加limit，如果加上分号会报sql语法错误。



- 后端的数据校验

    采用`validation`

    首先`pom.xml`

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
        <version>2.4.3</version>
    </dependency>
    ```

    首先在要校验的字段上叫注解

    ```java
    @NotBlank(message = "分类名称不能为空")
    private String name;
    ```

    给需要校验的数据加上`@Valid`，BindingResult result是校验结果

    ```java
    @PostMapping("/types")
    public String addType(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.queryByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.addType(type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","新增失败");
        } else {
            attributes.addFlashAttribute("successMsg","新增成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }
    ```

    

    ```html
    <form action="#" method="post" th:if="*{id}==null" th:object="${type}" th:action="@{/admin/types}" class="ui form">
        <div class="required field">
            <div class="ui left labeled input">
                <label class="ui teal basic label">名称</label>
                <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
            </div>
        </div>
    
        <div class="ui error message"></div>
        <!-- 这里的*{id} 其实就相当于${type.id}，就是从对象中拿属性的值 -->
    
        <!-- 
    		前端页面接收  下面这种注释thymeleaf仍然可以识别
    		th:if里的是要验证的name域是否有错误
    		th:errors="*{name}" 会从@NotBlank中的message拿
    		如果这样写,会报错，需要在上面加一个th:object="${type}"，就是从后端拿到一个Type对象，然后th:value="*{name}"，就是拿到type里的             value值,然后注意还需要从后端传一个type对象
        -->
        <!--/*/
        <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
        <i class="close icon"></i>
        <div class="header">操作失败</div>
        <p th:errors="*{name}">提交信息不符合规则</p>
        </div>
        /*/-->
        <!--操作按钮-->
        <div class="ui right aligned container">
            <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
            <button class="ui teal submit button">提交</button>
        </div>
    </form>
    ```

    

- 新增和编辑用同一个页面

    用了两个form表单是因为使用了restful api，然后新增是post请求，修改是put请求（put请求需要配置`<input type="hidden" name="_method" value="put">`），然后不让两个表单同时生效，使用了th:if，由于修改请求会使用id将需要修改的name输入在页面上，而新增不需要，所以两者的区别就是 数据检验使用的type对象的id是否为null。

    ```html
    <form action="#" method="post" th:if="*{id}==null" th:object="${type}" th:action="@{/admin/types}" class="ui form">
        <div class="required field">
            <div class="ui left labeled input">
                <label class="ui teal basic label">名称</label>
                    <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
            </div>
         </div>
    
         <div class="ui error message"></div>
         <!--/*/
         <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
             <i class="close icon"></i>
             <div class="header">操作失败</div>
             <p th:errors="*{name}">提交信息不符合规则</p>
         </div>
         /*/-->
         <!--操作按钮-->
         <div class="ui right aligned container">
             <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
             <button class="ui teal submit button">提交</button>
         </div>
    </form>
    
    <form action="#" method="post" th:if="*{id}!=null" th:object="${type}" th:action="@{/admin/types/{id}(id=*{id})}" class="ui form">
         <input type="hidden" name="id" th:value="*{id}">
         <input type="hidden" name="_method" value="put">
         <div class="required field">
             <div class="ui left labeled input">
                 <label class="ui teal basic label">名称</label>
                 <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
             </div>
         </div>
         <div class="ui error message"></div>
         <!--/*/
         <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
             <i class="close icon"></i>
             <div class="header">操作失败</div>
             <p th:errors="*{name}">提交信息不符合规则</p>
         </div>
         /*/-->
         <!--操作按钮-->
         <div class="ui right aligned container">
             <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
             <button class="ui teal submit button">提交</button>
         </div>
    </form>
    ```




- Ajax异步请求

    表单的多项查询，查询的时候需要带查询框里的参数过去，所有直接用 ajax刷新页面的form表单里的内容
  
  ```html
  <div id="table-container">
      <!--表格-->
      <table class="ui compact teal table" th:fragment="blogList">
          ...
          <tfoot>
              <tr>
                  <th colspan="7">
                      <div class="ui mini pagination menu" th:if="${pageInfo.pages}>1">
                          <a onclick="page(this)" th:attr="data-page=${pageInfo.pageNum-1}" class="item" th:unless="${pageInfo.isFirstPage}">上一页</a>
                          <a onclick="page(this)" th:attr="data-page=${pageInfo.pageNum+1}" class="item" th:unless="${pageInfo.isLastPage}">下一页</a>
                      </div>
                      <a href="#" th:href="@{/admin/blogs/input}" class="ui mini right floated teal basic button">新增</a>
                  </th>
              </tr>
          </tfoot>
      </table>
      ...
  </div>
  ```
  
  js
  
  ```javascript
  function page(obj) {
      $("[name='page']").val($(obj).attr("data-page")); //点击上一页 下一页 也是使用ajax 同时传递参数page
      loadData();
  }
  
  $("#search-btn").click(function () {
      $("[name='page']").val(0);
      loadData();
  });
  
  function loadData() {
      $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search", {
          title : $("[name='title']").val(),
          typeId : $("[name='type']").val(),//typeId会封装进controller中的与它参数名一致的参数 name='type' 然后找到对应name的value值
          recommend : $("[name='recommend']").prop('checked'),
          page : $("[name='page']").val(),
      });
  }
  ```
  
  controller
  
  ```java
  @PostMapping("/blogs/search")
  public String search(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                       @RequestParam(name = "size", required = true, defaultValue = "3") Integer size,
                       Model model, String title, Long typeId, boolean recommend) {
      PageInfo<Blog> pageInfo = blogService.queryConditional(page, size, title, typeId, recommend);
      model.addAttribute("pageInfo", pageInfo);
      return "admin/blogs :: blogList";  //这里只刷新页面中 fragment 里的内容
  }
  ```
  
  