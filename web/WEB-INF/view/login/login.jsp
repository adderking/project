<%--
  Created by IntelliJ IDEA.
  User: fangshilei
  Date: 18/1/23
  Time: 下午2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include  file="basePath.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="utf-8">
  <title>WIFI视频联动系统</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- Le styles -->
  <script type="text/javascript" src="<%=path%>/assets/js/jquery.min.js?version=<%=System.currentTimeMillis()%>"></script>
  <script type="text/javascript" src="<%=path%>/assets/js/jquery.js?version=<%=System.currentTimeMillis()%>"></script>
  <!--  <link rel="stylesheet" href="assets/css/style.css"> -->
  <link rel="stylesheet" href="<%=path%>/assets/css/loader-style.css?version=<%=System.currentTimeMillis()%>">
  <link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css?version=<%=System.currentTimeMillis()%>">
  <link rel="stylesheet" href="<%=path%>/assets/css/signin.css?version=<%=System.currentTimeMillis()%>">






  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
  <![endif]-->
  <!-- Fav and touch icons -->
  <link rel="shortcut icon" href="<%=path%>/assets/ico/minus.png?version=<%=System.currentTimeMillis()%>">
</head>

<body>
<!-- Preloader -->
<div id="preloader">
  <div id="status">&nbsp;</div>
</div>

<div class="container">



  <div class="form_center" id="login-wrapper">
    <div class="row">
      <div class="col-md-4 col-md-offset-4">
        <div id="logo-login">
          <h1>WI-FI视频联动管理系统
            <span>v1.0</span>
          </h1>
        </div>
      </div>

    </div>

    <div class="row">
      <div class="col-md-4 col-md-offset-4">
        <div class="account-box">
          <%--<form role="form" action="index.html">--%>
            <div class="form-group">
              <!--a href="#" class="pull-right label-forgot">Forgot email?</a-->
              <label for="userName">用户名</label>
              <input type="text" id="userName" class="form-control">
            </div>
            <div class="form-group">
              <!--a href="#" class="pull-right label-forgot">Forgot password?</a-->
              <label for="password">密码</label>
              <input type="password" id="password" class="form-control">
            </div>
            <div class="checkbox pull-left">
              <label>
                <input type="checkbox">记住用户名</label>
            </div>
            <button class="btn btn btn-primary pull-right" type="submit" onclick="login();">
              登 录
            </button>
          <%--</form>--%>
          <!--div class="or-box">

              <center><span class="text-center login-with">Login or <b>Sign Up</b></span></center>
              <div class="row">
                  <div class="col-md-6 row-block">
                      <a href="index.html" class="btn btn-facebook btn-block">
                          <span class="entypo-facebook space-icon"></span>Facebook</a>
                  </div>
                  <div class="col-md-6 row-block">
                      <a href="index.html" class="btn btn-twitter btn-block">
                          <span class="entypo-twitter space-icon"></span>Twitter</a>

                  </div>

              </div>
              <div style="margin-top:25px" class="row">
                  <div class="col-md-6 row-block">
                      <a href="index.html" class="btn btn-google btn-block"><span class="entypo-gplus space-icon"></span>Google +</a>
                  </div>
                  <div class="col-md-6 row-block">
                      <a href="index.html" class="btn btn-instagram btn-block"><span class="entypo-instagrem space-icon"></span>Instagram</a>
                  </div>

              </div>
          </div>
          <hr>
          <div class="row-block">
              <div class="row">
                  <div class="col-md-12 row-block">
                      <a href="index.html" class="btn btn-primary btn-block">Create New Account</a>
                  </div>
              </div>
          </div-->
          <div class="row-block">
            <div class="row">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <p>&nbsp;</p>
  <div style="text-align:center;margin:0 auto;">
    <h6 style="color:#fff;"><%--<br />--%>
      北京赛思信安技术股份有限公司 版权所有 京ICP备11004889号</h6>
  </div>

</div>



<!--  END OF PAPER WRAP -->




<!-- MAIN EFFECT -->
<script type="text/javascript" src="<%=path%>/assets/js/preloader.js?version=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=path%>/assets/js/bootstrap.js?version=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=path%>/assets/js/app.js?version=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=path%>/assets/js/load.js?version=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=path%>/assets/js/main.js?version=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript">
  //登录调用方法
  function login(){
    var userName = $("#userName").val();
    var password = $("#password").val();
    $.ajax({
      type:"post",
      url:"<%=basePath%>/mvc/loginAction",
      //contentType:"application/json;charset=utf-8",
      data:{userName:userName,passWord:password},
      dataType:"json",
      success:function(data){
        var data = $.parseJSON(data);
        if(data.result=="success"){
          //登录验证成功
          window.location.href="<%=basePath%>/mvc/home";
        }else{
          //登录验证失败
          alert("登录失败");
        }
      },
      error:function(data){
        alert("登录失败");
      }
    });
  }
</script>




</body>

</html>

