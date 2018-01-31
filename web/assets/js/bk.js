/**
 * Created by fangshilei on 18/1/30.
 */
/**
 * 全区布控
 */
function qqbkText(actionInfo,macInfo,mac,macHistoryInfo,flag,val){
    //生成 全区布控页面
    //先初始化地图
    $("#MacOrbit").text(macInfo);
    $("#Mac").text(mac);
    $("#MacHistoryInfo").text(macHistoryInfo);
    $("#dp1").remove();
    $("#dp2").remove();
    $("#macAddress").remove();
    $("#button").children().remove();
    $("#mapContainer").children().remove();
    $("#button").append('<button type="button" class="btn btn-default" onclick="");">新建</button>');
    //此处需要请求后台获取数据
    createTable();

}
function ddbk(actionInfo,macInfo,mac,macHistoryInfo,flag,val){
    //生成 全区布控页面
    //先初始化地图
    $("#MacOrbit").text(macInfo);
    $("#Mac").text(mac);
    $("#MacHistoryInfo").text(macHistoryInfo);
    $("#dp1").remove();
    $("#dp2").remove();
    $("#macAddress").remove();
    $("#button").children().remove();
    $("#mapContainer").children().remove();
    $("#button").append('<button type="button" class="btn btn-default" onclick="");">新建</button>');
}
//自定义表格
function createTable(){
    var html = '<div class="body-nest" id="tableStatic"><section id="flip-scroll"><table class="table table-bordered table-striped cf">' +
        '<thead class="cf"><tr><th>Code</th><th>Company</th><th class="numeric">Price</th></tr></thead><tbody><tr><td>AAC</td><td>AUSTRALIAN AGRICULTURAL COMPANY LIMITED.</td>' +
        '<td class="numeric">$1.38</td></tr></tbody></table></section><div id="ampagination" style="float: right;margin-right: 10px;"></div></div>';
    $("#mapContainer").append(html);
    //生成分页
    var pager = jQuery('#ampagination').pagination({
        page:5,
        totals:100,
        pageSize:10
    });
    pager.onChangePage(function(e){
        jQuery('.showlabel').text('The selected page no: '+e.page);
    });
}
