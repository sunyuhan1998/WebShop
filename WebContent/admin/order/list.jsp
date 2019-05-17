<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript">
			function showDetail(oid){
				var $val = $("#but"+oid).val();
				if($val == "订单详情"){
					// ajax 显示图片,名称,单价,数量
					$("#div"+oid).append("<img width='60' height='65' src='${pageContext.request.contextPath}/products/1/c_0028.jpg'>&nbsp;xxxx&nbsp;998<br/>");
					
					$("#but"+oid).val("关闭");
				}else{
					$("#div"+oid).html("");
					$("#but"+oid).val("订单详情");
				}
			}
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="5%">
										序号
									</td>
									<td align="center" width="20%">
										订单编号
									</td>
									<td align="center" width="5%">
										订单金额
									</td>
									<td align="center" width="5%">
										收货人
									</td>
									<td align="center" width="5%">
										订单状态
									</td>
									<td align="center" width="60%">
										订单详情
									</td>
								</tr>
								<c:forEach items="${allOrders }" var="order" varStatus="i">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${i.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="20%">
												${order.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${order.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
												${order.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="5%">
													<c:if test="${order.state==1 }">未付款</c:if>
													<c:if test="${order.state==2 }"><a href="${pageContext.request.contextPath}/AdminOrderServlet?method=updateOrderByOid&oid=${order.oid}">点此发货</a></c:if>
													<c:if test="${order.state==3 }">已发货</c:if>
													<c:if test="${order.state==4 }">订单完成</c:if>
											</td>
											<td align="center" style="HEIGHT: 22px;width: 60%">
												<input type="button" value="展开订单" id="${order.oid}" class="myClass"/>
												<table border="1" width = "100%" >
												</table>
											</td>
										</tr>
										</c:forEach>
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		$(function () {
			$(".myclass").click(function () {
				//向服务器发起ajax请求，将当前订单id发送到服务器
				var id = this.id;
				//获取当前按钮文字
				var txt = this.value;
				var $tb = $(this).next();//当前按钮的下一个元素，即table
				if (txt=="展开订单") {
					var url = "/WebShop/AdminOrderServlet";
					var obj = {"method":"findOrderByOidWithAjax","id":id};
					$.post(url,obj,function(data){
						$tb.html("");
						var th = "<tr><th width='4%'>商品</th><th width='2%'>名称</th><th width='10%'>单价</th><th width='4%'>数量</th>";
						$tb.append(th);
						//遍历收到的json
						$.each(data,function (i,obj) {
							var td="<tr><td width='4%''><img src='/WebShop/"+obj.product.pimage+"' width='50px'/></td><td width='22%'>"+obj.product.pname+"</td><td width='10%'>￥"+obj.product.shop_price+"元</td><td width='4%'>"+obj.quantity+"</td></tr>";
							$tb.append(td);
						});
					},"json");
					this.value="收起订单"
				}else {
					//清空内容
					$tb.html("");
					this.value="展开订单"
				}
			});
		});
	</script>
</HTML>

