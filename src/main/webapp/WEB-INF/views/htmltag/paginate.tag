@   var currentPage = page.pageNum;   // 当前页
@   var totalPage = page.pages;     // 总页数
@   var totalResult = page.total; //总记录数
@	var formId = formId!'search-form'; //当前的表单id,一个页面多个分页需要定义

@if(totalResult != 0){
<div class="clearfix">
	<div style="float: left;">
		<div style="padding-top:5px;">
			<span class="badge badge-info">
				当前第 ${currentPage} 页 / ${totalPage} 页，共 ${totalResult} 条数据
			</span>
		</div>
	</div>
	<div>
			<div class="pagination no-margin pull-right clearfix">
		@		if(currentPage == 1){
						<li class="prev disabled"><a href="javascript:void(0);" onclick="return false;">首页</a></li>
						<li class="prev disabled"><a href="javascript:void(0);" onclick="return false;">上一页</a></li>
		@			}else{
						<li><a href="javascript:void(0);" onclick="paging('${formId}',1);return false;">首页</a></li>
						<li><a href="javascript:void(0);" onclick="paging('${formId}',${currentPage - 1});return false;">上一页</a></li>
		@			}
		@	      if(totalPage <= 8){
		@	       var i = 1;
		@	       while(i <= totalPage){
		@	         if(currentPage == i){
			         	<li class="active"><a href="javascript:void(0);" onclick="return false;">${currentPage}</a></li>
		@	         }else{
			         	<li><a href="javascript:void(0);" onclick="paging('${formId}',${i});return false;">${i}</a></li>         	
		@	         }
		@			i = i+1;
		@	       }
		@	      }else{
		@	      	if(currentPage <= 5){
		@	      		 var j = 1;
		@	      		 while(j<=7){
		@	         		if(currentPage==j){
			         			<li class="active"><a href="javascript:void(0);" onclick="return false;">${currentPage}</a></li>
		@	         		}else{
			         			<li><a href="javascript:void(0);" onclick="paging('${formId}',${j});return false;">${j}</a></li>
		@	         		}
		@	     			j = j+1;
		@	     		}
			     		<li><a href="javascript:void(0);">…</a></li>
		@	      	}else{
			      		<li><a href="javascript:void(0);" onclick="paging('${formId}',1);return false;">1</a></li>
						<li><a href="javascript:void(0);" onclick="paging('${formId}',2);return false;">2</a></li>
						<li><a href="javascript:void(0);" onclick="return false;">…</a></li>
						
		@					var begin = currentPage - 2;
		@					var end = currentPage + 2;
		@					if(end > totalPage){
		@						end = totalPage;
		@						begin = end - 4;
		@						if(currentPage - begin < 2){
		@							begin = begin-1;
		@						}
		@					}
		@					if(end + 1 == totalPage){
		@					 	end = totalPage;
		@					} var m = begin;
		@					while(m<=end){
		@						if(currentPage == m){
									<li class="active"><a href="javascript:void(0);" onclick="return false;">${currentPage}</a></li>
		@						}else{
									<li><a href="javascript:void(0);" onclick="paging('${formId}',${m});return false;">${m}</a></li>
		@						}
		@						m = m+1;
		@					}
		@					if(end != totalPage){
							   <li><a href="javascript:void(0);">…</a></li>
							   <li><a href="javascript:void(0);" onclick="paging('${formId}',${totalPage-1});return false;">${totalPage-1}</a></li>
							   <li><a href="javascript:void(0);" onclick="paging('${formId}',${totalPage});return false;">${totalPage}</a></li>
		@					}
		@	      		}
		
		@	      }
		@		    if(currentPage == totalPage){
						<li class="next disabled"><a href="javascript:void(0);" onclick="return false;">下一页</a></li>
						<li class="next disabled"><a href="javascript:void(0);" onclick="paging('${formId}',${totalPage});return false;">尾页</a></li>
		@			}else{
						<li><a href="javascript:void(0);" onclick="paging('${formId}',${currentPage + 1});return false;">下一页</a></li>
						<li><a href="javascript:void(0);" onclick="paging('${formId}',${totalPage});return false;">尾页</a></li>
		@			}
			</div>
		
	</div>
</div>
@}