rowIndex = 0;

function hideSelects(visibility){
    selects = document.getElementsByTagName('select');
    for(i = 0; i < selects.length; i++) {
        selects[i].style.visibility = visibility;
    }
}

function allSelect(id){
    var ms	=	id?document.getElementById(id):document;
    var	colInputs = ms.getElementsByTagName("input");
    for	(var i=0; i < colInputs.length; i++)
    {
        colInputs[i].checked= true;
    }
}
function allUnSelect(id){
    var ms	=	id?document.getElementById(id):document;
    var	colInputs = ms.getElementsByTagName("input");
    for	(var i=0; i < colInputs.length; i++)
    {
        colInputs[i].checked= false;
    }
}

function InverSelect(id){
    var ms = id?document.getElementById(id):document;
    var	colInputs = ms.getElementsByTagName("input");
    for	(var i=0; i<colInputs.length; i++) {
        colInputs[i].checked= !colInputs[i].checked;
    }
}

function show(){
    if (document.getElementById('menu').style.display!='none') {
        document.getElementById('menu').style.display='none';
        document.getElementById('main').className = 'full';
    } else {
        document.getElementById('menu').style.display='inline';
        document.getElementById('main').className = 'main';
    }
}

function CheckAll(strSection) {
    var i;
    var	colInputs = document.getElementById(strSection).getElementsByTagName("input");
    for	(i=1; i < colInputs.length; i++) {
        colInputs[i].checked=colInputs[0].checked;
    }
}

function add(id){
    if(id) {
        location.href  = URL+"/add/id/"+id;
    } else {
        location.href  = URL+"/add/";
    }
}

function edit(id){
    var keyValue;
    if(id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValue();
    }

    if(!keyValue) {
        alert('请选择编辑项！');
        return false;
    }
    location.href =  URL+"/edit/id/"+keyValue;
}

function sort(id) {
    var keyValue;
    keyValue = getSelectCheckboxValues();
    location.href = URL+"/index/sort/sortId/"+keyValue;
}


function sortBy(field,sort) {
    location.href = URL+"/index/_order/"+field+"/_sort/"+sort;
}

function recycle(id) {
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValue();
    }
    if (!keyValue) {
        alert('请选择要还原的项目�?');
        return false;
    }
    location.href = URL+"/recycle/id/"+keyValue;
}


function child(id){
    location.href = URL+"/index/pid/"+id;
}
function action(id){
    location.href = URL+"/action/groupId/"+id;
}

function access(id){
    location.href= URL+"/access/id/"+id;
}
function app(id){
    location.href = URL+"/app/groupId/"+id;
}

function module(id){
    location.href = URL+"/module/groupId/"+id;
}

function user(id){
    location.href = URL+"/user/id/"+id;
}


var selectRowIndex = Array();
function del(id){
    var keyValue;
    if (id)
    {
        keyValue = id;
    }else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue)
    {
        alert('请选择删除项！');
        return false;
    }

    if (window.confirm('确实要删除选择项吗�?'))
    {
        location.href =  URL+"/delete/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}


function exportMember(id){
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue) {
        alert('请选导出除项！');
        return false;
    }

    if (window.confirm('确实要导出选择项吗�?')) {
        location.href =  APP + "/Excel/exportMember/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}

function exportOrder(id){
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue) {
        alert('请选导出除项！');
        return false;
    }

    if (window.confirm('确实要导出选择项吗�?')) {
        location.href =  APP + "/Excel/exportOrder/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}

function exportInvoice(id){
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue) {
        alert('请选导出除项！');
        return false;
    }

    if (window.confirm('确实要导出选择项吗�?')) {
        location.href =  APP + "/Excel/exportInvoice/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}

function exportAccount(id){
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue) {
        alert('请选导出除项！');
        return false;
    }

    if (window.confirm('确实要导出选择项吗�?')) {
        location.href =  APP + "/Excel/exportAccount/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}

function exportMemberList(id){
    var keyValue;
    if (id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue) {
        alert('请选导出除项！');
        return false;
    }

    if (window.confirm('确实要导出选择项吗�?')) {
        location.href =  APP + "/Other/sendList/id/"+keyValue;
        //ThinkAjax.send(URL+"/delete/","id="+keyValue+'&ajax=1',doDelete);
    }
}
function foreverdel(id){
    var keyValue;
    if(id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValues();
    }
    if(!keyValue) {
        alert('请选择删除项！');
        return false;
    }

    if (window.confirm('确实要永久删除选择项吗�?')) {
        location.href =  URL+"/foreverdelete/id/"+keyValue;
        //ThinkAjax.send(URL+"/foreverdelete/","id="+keyValue+'&ajax=1',doDelete);
    }
}
function getTableRowIndex(obj){
    selectRowIndex[0] =obj.parentElement.parentElement.rowIndex;/*当前行对�?*/
}

function doDelete(data,status){
    if (status==1)
    {
        var Table = $('checkList');
        var len	=	selectRowIndex.length;
        for (var i=len-1;i>=0;i-- )
        {
            //删除表格�?
            Table.deleteRow(selectRowIndex[i]);
        }
        selectRowIndex = Array();
    }

}


function delAttach(id,showId){
    var keyValue;
    if (id)
    {
        keyValue = id;
    }else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue)
    {
        alert('请选择删除项！');
        return false;
    }

    if (window.confirm('确实要删除选择项吗�?'))
    {
        $('result').style.display = 'block';
        ThinkAjax.send(URL+"/delAttach/","id="+keyValue+'&_AJAX_SUBMIT_=1');
        if (showId != undefined)
        {
            $(showId).innerHTML = '';
        }
    }
}

function clearData(){
    if (window.confirm('确实要清空全部数据吗�?'))
    {
        location.href = URL+"/clear/";
    }
}

function takeback(id){
    var keyValue;
    if (id)
    {
        keyValue = id;
    }else {
        keyValue = getSelectCheckboxValues();
    }
    if (!keyValue)
    {
        alert('请选择回收项！');
        return false;
    }

    if (window.confirm('确实要回收选择项吗�?'))
    {
        location.href = URL+"/takeback/id/"+keyValue;
    }
}


function getSelectCheckboxValue(){
    var obj = document.getElementsByName('key');
    var result ='';
    for (var i=0;i<obj.length;i++)
    {
        if (obj[i].checked==true)
            return obj[i].value;

    }
    return false;
}

function getSelectCheckboxValues(){
    var obj = document.getElementsByName('key');
    var result ='';
    var j= 0;
    for (var i=0;i<obj.length;i++)
    {
        if (obj[i].checked==true){
            selectRowIndex[j] = i+1;
            result += obj[i].value+",";
            j++;
        }
    }
    return result.substring(0, result.length-1);
}

function searchItem(item){
    for(i=0;i<selectSource.length;i++)
        if (selectSource[i].text.indexOf(item)!=-1)
        {selectSource[i].selected = true;break;}
}

function addItem(){
    for(i=0;i<selectSource.length;i++)
        if(selectSource[i].selected){
            selectTarget.add( new Option(selectSource[i].text,selectSource[i].value));
        }
    for(i=0;i<selectTarget.length;i++)
        for(j=0;j<selectSource.length;j++)
            if(selectSource[j].text==selectTarget[i].text)
                selectSource[j]=null;
}

function delItem(){
    for(i=0;i<selectTarget.length;i++)
        if(selectTarget[i].selected){
            selectSource.add(new Option(selectTarget[i].text,selectTarget[i].value));

        }
    for(i=0;i<selectSource.length;i++)
        for(j=0;j<selectTarget.length;j++)
            if(selectTarget[j].text==selectSource[i].text) selectTarget[j]=null;
}

function delAllItem(){
    for(i=0;i<selectTarget.length;i++){
        selectSource.add(new Option(selectTarget[i].text,selectTarget[i].value));

    }
    selectTarget.length=0;
}
function addAllItem(){
    for(i=0;i<selectSource.length;i++){
        selectTarget.add(new Option(selectSource[i].text,selectSource[i].value));

    }
    selectSource.length=0;
}

function getReturnValue(){
    for(i=0;i<selectTarget.length;i++){
        selectTarget[i].selected = true;
    }
}


function editProduct(id){
    var keyValue;
    if(id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValue();
    }
    if (!keyValue) {
        alert('请选择编辑项！');
        return false;
    }
    location.href =  URL+"/editProduct/id/"+keyValue;
}


function editCategory(id){
    var keyValue;
    if(id) {
        keyValue = id;
    } else {
        keyValue = getSelectCheckboxValue();
    }
    if(!keyValue) {
        alert('请选择编辑项！');
        return false;
    }
    location.href =  URL+"/editCategory/id/"+keyValue;
}

/**
 * 删除
 * @param url
 * @returns {boolean}
 */
function deletes(url){
    //获取全部选中的checkbox
    var checks=$("input[name=key]:checked");
    var ids="";
    if(checks.length<1){
        alert("请选择一个删除!");
        return false;
    }else{
        checks.each(function(){
            ids=$(this).val()+","+ids;
        });

        location.href=url+ids;
    }
}



/**
 * 批量启动
 * @param url
 * @returns {boolean}
 */
function enabledQ(url){
    //获取全部选中的checkbox
    var checks=$("input[name=key]:checked");
    var ids="";
    if(checks.length<1){
        alert("请选择一个进行启用!");
        return false;
    }else{
        checks.each(function(){
            ids=$(this).val()+","+ids;
        });

        location.href=url+ids;
    }
}




$(function(){
    $(".table tr").hover(function(){
        $(this).css("backgroundColor","#fefcee");
    },function(){
        $(this).removeAttr("style");
    })
});

var zTreeObj;
var setting = {							//设置
    data : {
        simpleData : {
            enable: true,				//允许设置数据编辑格式
            idKey: "id",				//treeNodes数据的主键列名是id
            pIdKey: "parentId",				//设置treeNodes的父id的列明为pId
            rootPId: 0				//默认的顶级id的值是0
        }
    },
    check:{								//这个属性来控制是多选还是单选,单选貌似只能支持级联选中
        enable: false,
        chkStyle: "checkbox",			//多选
        chkboxType: { "Y": "s", "N": "s" }	//y表示选中的结果,n表示取消的结果,后面如果跟p表示只影响自己,s表示影响子节点和自己
    },
    callback:{
        //treeId,可以获取数据的内容,treeNode不知道是什么啊
        beforeClick:function(event, treeId, treeNode){
            //if(treeId.isParent){
                location.href = location.pathname+"?type=djj&category="+treeId.id
            //}
        }
    }
};

function setTree(){
    $.ajax({
        async : false,			//这个是关闭多线程,因为post方法没有等待取回数据就执行了前台,所以总是不显示
        cache : true,
        type : 'POST',
        dataType : "json",
        url : "../task/goodstype/tree",
        success : function(html) {
            zTreeObj = $.fn.zTree.init($("#tree"),setting,html);//id,设置,数据,$.fn.zTree包下
            tmp_nodeId = $("#category").val()
            node = zTreeObj.getNodeByParam('id',tmp_nodeId)
            if(node != null){
                zTreeObj.selectNode(node);
            }
            console.log(tmp_nodeId)
        }
    });
    //设置

}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}



//获取返回值
function returnExecute(url,param){
    var result = null;
    $.ajax({
        type: 'post',
        url:url,
        async:false,
        data: param,
        dataType: 'json',
        error: function () {
            // alert('Error loading XML document'+url);
        },
        success: function (map) {
            result = map;
        }
    });
    return result;
}

//添加下拉框
function buildChilds(name,url,param,obj){
    var _this = $(obj);
    if(obj==null){
        _this = $("select[name='"+name+"']");
    }
    //移除子节点Select
    $.each($("select[name='"+name+"']"),function(index,item){
        if(index>_this.index()) {
            $(this).remove();
        }
    });

    var data = returnExecute(url,param);
    if(data!=null && data.length>0){
        var opts_str = "<select name='"+name+"' class='addselect'>";
        $.each(data,function(index,item){
            opts_str = opts_str+"<option value='"+item.id+"'>"+item.name+"</option>";
        });
        opts_str = opts_str+"</select>";
        _this.after(opts_str);
    }
}
//编辑下拉框
function editChild(ids,name,url,param){
    var ids = ids.split(',');
    $("select[name='"+name+"']").eq(0).val(ids[0]);
    for(var i=1;i<ids;i++){
        var options = returnExecute(url,param+'&child_id='+ids[i]);
        if(options!=null && options.length>0){
            var opts = "<select name='"+name+"' class='addselect'>";
            $.each(options,function(index,item){
                if(item.id==ids[i]){
                    opts = opts+"<option value='"+item.id+"' selected>"+item.name+"</option>";
                }else{
                    opts = opts+"<option value='"+item.id+"'>"+item.name+"</option>";
                }
            });
            opts = opts+"</select>";
            $("select[name='"+name+"']").eq(i-1).after(options);
        }
    }
}


//创建下拉框
function buildSelect(name,url,param,valued){
    var select_str ="<select name="+name+" class='addselect'>";
    $.each(returnExecute(url,param),function(index,item){
        if(item.id==valued){
            select_str = select_str+"<option value='"+item.id+"' selected>"+item.name+"</option>";
        }else{
            select_str = select_str+"<option value='"+item.id+"' >"+item.name+"</option>";
        }
    });
    select_str = select_str+"</select>";
    return select_str;
}








