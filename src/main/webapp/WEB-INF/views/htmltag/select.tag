@var id = id!"chosen-select";
@var name = name!;
@var width = width!"100%";
@var class = class!;
<select class="chosen-select ${class}" name="${name}" id="${id}">
	${tagBody!}
</select>
<script>
$(function(){
	$("#${id}").chosen({width: "${width}",search_contains: true}); 
});
</script>