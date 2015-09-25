@var order = order!'';
@var class = class!'width-100 '; //样式
@var width = width!'350px'; //弹窗宽度
@var height = height!'400px'; //弹窗的高度
@var modelId = modelId!'parentId'; //隐藏要提交的id (实体属性)
@var modelName = modelName!''; //要提交的name (实体属性)
@var modelIdValue = modelIdValue!'0'; //id初始值value 
@var modelNameValue = modelNameValue!''; //name初始值value
@var id = id!('treeselectid'+order); //隐藏要添加的input的id名称,页面多个时候需要指定
@var nameId = nameId!('treeselectname'+order);  //名称
@var url = ctxPath+"/"+url!''; //树数据url,必填,从管理路径之后添起，如menu/tree
@var pIdKey = pIdKey!'parentId'; //父级的model中属性名字
@var selectIds = selectIds!''; //默认选择节点
@var curId = curId!'-1'; //当前节点的id,如要验证不能选择当前节点需要填写,多个时必须填写，详见office-save页面
@var isCheck = isCheck!''; //是否验证不为空
@var style = style!'';
@var checked = checked!'false'; //是都显示复选框
@var isLayer = isLayer!'true'; //是否为弹窗
@var treeSelectId = treeSelectId!'tree'; //树id,即是树对象名称
@var rootNodeName = rootNodeName!""; //虚拟顶级节点名称
@var chkboxType = chkboxType!""; //1不选择父节点

@if(isLayer == "true"){
<div class="clearfix ${class} input-group" style="${style}">
	<input class="form-control search-query" type="text" readonly
	id="${nameId}" name="${modelName}" value="${modelNameValue}"
	@if(!isEmpty(isCheck)){
		datatype="*"  nullmsg=${isCheck}
	@}
	>
	<span class="input-group-btn">
		<span class="btn btn-purple btn-sm" type="button" style="height: 34px;"
		data-mode="page" data-url="${ctxPath}/tag/treeselect"
		data-data="{url:'${url}',id:'${id}',nameId:'${nameId}',pIdKey:'${pIdKey}',selectIds:'${selectIds}',curId:'${curId}',checked:'${checked}',rootNodeName:'${rootNodeName}',isLayer:'${isLayer}'}" 
		data-title="请选择" data-width="${width!}" data-height="${height}">
			<i class="ace-icon fa fa-search bigger-110"></i>查找
		</span>
	</span>
	<input type="hidden" name="${modelId}" id="${id}" value="${modelIdValue}"/>
</div>
${tagBody!}
@}else{
	${tagBody!}
	<script>
		$.ajax({
			url:"${ctxPath}/tag/treeselect",
			type:"post",
			data:{url:'${url}',id:'${id}',nameId:'${nameId}',pIdKey:'${pIdKey}',
				selectIds:'${selectIds}',curId:'${curId}',checked:'${checked}',isLayer:"false",
				treeSelectId:'${treeSelectId}',rootNodeName:'${rootNodeName}',chkboxType:'${chkboxType}'},
			success:function(data){
				$("#${treeSelectId}").html(data);
			}
		});
	</script>
@}
