$(document).ready(function(){	

function loadData(){
	$("#listGarmentstyle").dataTable().fnDestroy();
	$('#listGarmentstyle tbody').empty();	
		$.ajax({
			dataType: "json",
			type: 'GET',
			data: {},
			url: getAbsolutePath() +  "garmentstyle/list",
			contentType: "application/json",
			success: function(data){
				if(data.list.length==0){
//					alert("Table have no data.");
				}
				var i=1;
				$.each(data.list,function(key,value){
					var tmp='';
					if(value.garmentstylereferpriceModelList.length>0){
						tmp+='<table border="0">';
						$.each(value.garmentstylereferpriceModelList,function(key,value1){
							if(value1.referprice!= null)
								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
						});
						tmp+='</table>';
					}
					
					
					$('<tr>').append(
							$('<td>').text(i++),
							$('<td>').text(value.displayName),
							$('<td>').text(value.customerShortname),
							$('<td>').text(value.factoryShortname),
							$('<td>').text(value.garmentkindcode),
							$('<td>').html(value.referprice==null?tmp:value.referprice),
							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
									+'<option value="Options" disabled selected>Options</option>'
									+'<option value="AccessoryList">Accessory List</option>'
									+'<option value="Copy">Copy</option>'
									+'<option value="Edit">Edit</option>'
									+'<option value="Delete">Delete</option></select>')
					).appendTo('#listGarmentstyle');
				});
				action();
				
				$('#listGarmentstyle').DataTable( {
					"pagingType": "full_numbers"
			    } );
			},
			error: function(){
				alert("Can not get data!");
			}
		});
	};
	
	//handle edit, delete, accList, copy
//	function action(){	
//		//When click edit
//		$('.btnEdit').on('click', function (e) {	
//			var garmentstylecode= $(this).data('id');
//			
//			$.ajax({
//	    		dataType: "json",
//				type: 'GET',
//				data:{},
//				contentType: "application/json",
//				url: "/Chori/garmentstyle/detail/"+garmentstylecode,
//				success: function(data){
//					if(data.status== "ok"){
//						$("#editGarmentstyleDialog").find("#customercode").val(data.currentgarmentstyle.customercode);
//						$("#editGarmentstyleDialog").find("#factorycode").val(data.currentgarmentstyle.factorycode);
//						$("#editGarmentstyleDialog").find("#garmentstylecode").val(data.currentgarmentstyle.garmentstylecode);
//						$("#editGarmentstyleDialog").find("#description").val(data.currentgarmentstyle.description);
//						$("#editGarmentstyleDialog").find("#garmentkindcode").val(data.currentgarmentstyle.garmentkindcode);
//						$("#editGarmentstyleDialog").find("#referprice").val(data.currentgarmentstyle.referprice);
//						
//						//show image part
//						//img1 part
//						if((data.currentgarmentstyle.imgurl1==null)||(data.currentgarmentstyle.imgurl1.trim()=='')){
//							//do nothing
//						}else{
//							$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 1: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'" /></span>');//////////
//						}
//						//img2 part
//						if((data.currentgarmentstyle.imgurl2==null)||(data.currentgarmentstyle.imgurl2.trim()=='')){
//							//do nothing
//						}else{
//							$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 2: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'" /></span>');//////////
//						}
//						//img3 part
//						if((data.currentgarmentstyle.imgurl3==null)||(data.currentgarmentstyle.imgurl3.trim()=='')){
//							//do nothing
//						}else{
//							$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 3: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'" /></span>');//////////
//						}
//						//img4 part
//						if((data.currentgarmentstyle.imgurl4==null)||(data.currentgarmentstyle.imgurl4.trim()=='')){
//							//do nothing
//						}else{
//							$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 4: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'" /></span>');//////////
//						}
//						//img5 part
//						if((data.currentgarmentstyle.imgurl5==null)||(data.currentgarmentstyle.imgurl5.trim()=='')){
//							//do nothing
//						}else{
//							$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 5: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'" /></span>');//////////
//						}
//						
//					}else{
//						alert('This alert should never show!');
//					}
//				},
//				error: function(){
//					alert('Cant get detail');
//				}
//	    	});
//			
//			$("#editGarmentstyleDialog").dialog({
//				modal: true,
//				show:{
//					effect:"blind",
//					duration: 500
//				},
//				title: "Edit Garment Style",
//				height: 600,
//				width: 700,
//				buttons:{
//					"Cancel": function(){
//						$("#editGarmentstyleDialog").dialog("close");
//						$("#editGarmentstyleDialog").find('#5images').html('');
//					}
//				},
//				close: function(){
//					$("#editGarmentstyleDialog").find('#5images').html('');
//				}
//			});
//			
//		});
//		
//
//		//handle function for delete
//		$('.btnDelete').on('click', function (e) {	
//			var garmentstylecode= $(this).data('id');
//			$("#deleteGarmentstyleDialog").find("#messageContent").text('Are you sure you want to delete garment style "' + garmentstylecode+'"?');
//			$("#deleteGarmentstyleDialog").dialog({
//				modal: true,
//				show:{
//					effect:"slide",
//					duration: 500
//				},
//				title: "Delete Confirm",
//				height: 300,
//				width: 400,
//				buttons:{
//					"Yes": function(){
//						$.ajax({
//				    		dataType: "json",
//							type: 'POST',
//							data:{},
//							contentType: "application/json",
//							url: "/Chori/garmentstyle/delete/"+garmentstylecode,
//							success: function(data){
//								callMessageDialog("Message", "Delete successfully!");
//								$("#deleteGarmentstyleDialog").dialog("close");
//								$("#listGarmentstyle").dataTable().fnDestroy();
//								$('#listGarmentstyle tbody').empty();						
//								loadData();
//							},
//							error: function(){
//								//alert('Cant delete garment style!');
//								callMessageDialog("Warning Message", 'Cant delete this garment style!');
//								$("#deleteGarmentstyleDialog").dialog("close");
//							}
//				    	});
//					},
//					"No": function(){
//						$("#deleteGarmentstyleDialog").dialog("close");
//					}
//				}
//			});
//		});
//		
//		//handle function for copy
//		$('.btnCopy').on('click', function () {	
//			var oldGarmentstyleCode= $(this).data('id');
//			$("#copyGarmentstyleDialog").find("#txtOldGarmentstyleCode").val(oldGarmentstyleCode);
//			$("#copyGarmentstyleDialog").dialog({
//				modal: true,
//				show:{
//					effect:"slide",
//					duration: 500
//				},
//				title: "Copy Garment Style",
//				height: 300,
//				width: 400,
//				buttons:{
//					"Save": function(){
//						
//						var garmentstylecode= $("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val();
//						
//						///TEST
//						//n???u x??a tr???ng th?? b???t
//						if(garmentstylecode.trim() === '' || garmentstylecode == null){
//							//hi???n lu??n dialog th??ng b??o ra
//							callMessageDialog("Warning Message", "Please enter garment style code!");
//							return;
//						}
//						//ki???m tra tr?????ng h???p c?? space ??? ?????u, cu???i
//						if(garmentstylecode.trim().length!=garmentstylecode.length){
//							callMessageDialog("Warning Message", "You can't enter space at prefix or suffix!");
//							return;
//						}
//						//tr?????ng h???p over range:
//						if(garmentstylecode.length>50){
//							callMessageDialog("Warning Message", "Field's length is 50, your input is overrange!");
//							return;
//						}
//						
//						//tr?????ng h???p isExisted
//						$.ajax({
//							dataType: "json",
//							type: 'GET',
//							data: {},
//							contentType: "application/json",
//							url: "/Chori/garmentstyle/isExist/"+garmentstylecode,
//							success: function(data){
//								if(data.isExisted){
//									callMessageDialog("Warning Message", "Garment style code is existed, please choose different one!");
//								}else if(data.isExisted== false){
//									//d??? li???u ???? valid h???t
//									$.ajax({
//							    		dataType: "json",
//										type: 'POST',
//										data:JSON.stringify({
//											garmentstylecode: garmentstylecode
//										}),
//										contentType: "application/json",
//										url: "/Chori/garmentstyle/copy/"+oldGarmentstyleCode,
//										success: function(data){
//											if(data.copyStatus==true&&data.status=="ok"){
//												callMessageDialog("Message", "Copy Garment Style Successfully!");
//												$("#copyGarmentstyleDialog").dialog("close");
//												$("#listGarmentstyle").dataTable().fnDestroy();
//												$('#listGarmentstyle tbody').empty();						
//												loadData();
//											}
//										},
//										error: function(){
//											callMessageDialog("Warning Message", 'Can\'t Copy This Garment Style!');
//											$("#copyGarmentstyleDialog").dialog("close");
//										}
//							    	});
//									//end d??? li???u ???? valid h???t
//								}
//							},error: function(){
//								//tr?????ng h???p x??a h???t input
//								callMessageDialog("Warning Message", "Please enter garment style code!");
//							}
//						});
//						///TEST
//						
////						//d??? li???u ???? valid h???t
////						$.ajax({
////				    		dataType: "json",
////							type: 'POST',
////							data:JSON.stringify({
////								garmentstylecode: garmentstylecode
////							}),
////							contentType: "application/json",
////							url: "/Chori/garmentstyle/copy/"+oldGarmentstyleCode,
////							success: function(data){
////								if(data.copyStatus==true&&data.status=="ok"){
////									callMessageDialog("Message", "Copy Garment Style Successfully!");
////									$("#copyGarmentstyleDialog").dialog("close");
////									$("#listGarmentstyle").dataTable().fnDestroy();
////									$('#listGarmentstyle tbody').empty();						
////									loadData();
////								}
////							},
////							error: function(){
////								callMessageDialog("Warning Message", 'Can\'t Copy This Garment Style!');
////								$("#copyGarmentstyleDialog").dialog("close");
////							}
////				    	});
////						//end d??? li???u ???? valid h???t
//					},
//					"Cancel": function(){
//						$("#copyGarmentstyleDialog").dialog("close");
//						//reset l???i css
//						$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text('');
//						$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "white");
//						//x??a d??? li???u ???? nh???p
//						$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val('');
//					}
//				},
//				close: function(){
//					//reset l???i css
//					$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text('');
//					$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "white");
//					//x??a d??? li???u ???? nh???p
//					$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val('');
//				}
//			});
//		});
//		
//		//handle function for accessory list
//		$('.btnAccessoryList').on('click', function () {	
////			var garmentstyleCode= $(this).data('id');
//			var garmentstylecode= $(this).data('id');
//			
//			$.ajax({
//	    		dataType: "json",
//				type: 'GET',
//				data:{},
//				contentType: "application/json",
//				url: "/Chori/garmentstyle/detail/"+garmentstylecode,
//				success: function(data){
//					if(data.status== "ok"){
//						$("#configAccessoryDialog").find("#customercode").val(data.currentgarmentstyle.customercode);
//						$("#configAccessoryDialog").find("#factorycode").val(data.currentgarmentstyle.factorycode);
//						$("#configAccessoryDialog").find("#garmentstylecode").val(data.currentgarmentstyle.garmentstylecode);
//						$("#configAccessoryDialog").find("#description").val(data.currentgarmentstyle.description);
//						$("#configAccessoryDialog").find("#garmentkindcode").val(data.currentgarmentstyle.garmentkindcode);
//						$("#configAccessoryDialog").find("#referprice").val(data.currentgarmentstyle.referprice);
//						
//						//show image part
//						//img1 part
//						if((data.currentgarmentstyle.imgurl1==null)||(data.currentgarmentstyle.imgurl1.trim()=='')){
//							//do nothing
//						}else{
//							$("#configAccessoryDialog").find('#5images').append('<span>Picture 1: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'" /></span>');//////////
//						}
//						//img2 part
//						if((data.currentgarmentstyle.imgurl2==null)||(data.currentgarmentstyle.imgurl2.trim()=='')){
//							//do nothing
//						}else{
//							$("#configAccessoryDialog").find('#5images').append('<span>Picture 2: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'" /></span>');//////////
//						}
//						//img3 part
//						if((data.currentgarmentstyle.imgurl3==null)||(data.currentgarmentstyle.imgurl3.trim()=='')){
//							//do nothing
//						}else{
//							$("#configAccessoryDialog").find('#5images').append('<span>Picture 3: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'" /></span>');//////////
//						}
//						//img4 part
//						if((data.currentgarmentstyle.imgurl4==null)||(data.currentgarmentstyle.imgurl4.trim()=='')){
//							//do nothing
//						}else{
//							$("#configAccessoryDialog").find('#5images').append('<span>Picture 4: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'" /></span>');//////////
//						}
//						//img5 part
//						if((data.currentgarmentstyle.imgurl5==null)||(data.currentgarmentstyle.imgurl5.trim()=='')){
//							//do nothing
//						}else{
//							$("#configAccessoryDialog").find('#5images').append('<span>Picture 5: <img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'" /></span>');//////////
//						}
//						
//						//load d??? li???u v??o ddl
//						loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
//						
//						//h ko load last table n???a, load dll trc
//						//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
//					}else{
//						alert('This alert should never show!');
//					}
//				},
//				error: function(){
//					alert('Cant get detail');
//				}
//	    	});
//			
//			//load accessory list into table
////			$("#listAccessoryForGarment").dataTable().fnDestroy();
////			$('#listAccessoryForGarment tbody').empty();
//			
//			$.ajax({
//				dataType: "json",
//				type: 'GET',
//				data: {},
//				url: "/Chori/garmentstyle/listAccessoryForGarment/"+garmentstylecode,
//				contentType: "application/json",
//				success: function(data){
//					if(data.listAccessoryForGarmentstyle.length==0){
//						alert("Table have no data.");
//					}
//					var i=1;
//					$.each(data.listAccessoryForGarmentstyle,function(key,value){
//						$('<tr>').append(
//								$('<td>').text(i++),
//								$('<td>').html(value.assignedForGarment==true?'<a class="hplEdit" data-id="'+value.accessorycode+'">'+value.name+'</a>':value.name),
//								$('<td>').text(value.mode),
//								$('<td>').html(value.assignedForGarment==true?'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'" checked/>':'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'">')
//						).appendTo('#listAccessoryForGarment');
//					});
//					actionToggle();
//					
//					$('#listAccessoryForGarment').DataTable( {
//						"pagingType": "full_numbers"
//				    } );
//				},
//				error: function(){
//					alert("Can not get data!");
//				}
//			});
//			//end load accessory list into table
//			
//			//from here show dialog
//			$("#configAccessoryDialog").dialog({
//				modal: true,
//				show:{
//					effect:"slide",
//					duration: 500
//				},
//				title: "Config Accessory For Garment Style",
//				height: 600,
//				width: 700,
//				buttons:{
//					"Close": function(){
//						$("#configAccessoryDialog").dialog("close");
//						$("#configAccessoryDialog").find('#5images').html('');
//						
//						$("#listAccessoryForGarment").dataTable().fnDestroy();
//						$('#listAccessoryForGarment tbody').empty();
//						
//						//x??a b???ng cu???i
////						$("#listGarmentStyleAccessoryDetailMain").dataTable().fnDestroy();
////						$('#listGarmentStyleAccessoryDetailMain tbody').empty();
//						$("#lastTable").html('');
//					}
//				},
//				close: function(){
//					$("#configAccessoryDialog").find('#5images').html('');
//					
//					$("#listAccessoryForGarment").dataTable().fnDestroy();
//					$('#listAccessoryForGarment tbody').empty();
//					
//					//x??a b???ng cu???i
////					$("#listGarmentStyleAccessoryDetailMain").dataTable().fnDestroy();
////					$('#listGarmentStyleAccessoryDetailMain tbody').empty();
//					$("#lastTable").html('');
//				}
//			});
//		});
//	};
	
	function action(){	
		$('.selectOption').on('change', function (e) {
			var optionSelected = $(this).find("option:selected");
		    var valueSelected  = optionSelected.val();
		    
		    var garmentstylecode= $(this).data('id');//this is RoleID of each record.
		    //alert('You choose '+roleIdSeleted+', and the option is: '+ valueSelected);
		    $(".selectOption").val("Options");
		    
		    
		    //If user choose edit option
		    if(valueSelected=="Edit"){
		    	
		    	editOption(garmentstylecode);
				
				$("#editGarmentstyleDialog").dialog({
					modal: true,
					show:{
						effect:"blind",
						duration: 500
					},
					title: "Edit Garment Style",
					height: 550,
					width: 700,
					buttons:{
						"Cancel": function(){
							$("#editGarmentstyleDialog").dialog("close");
							$("#editGarmentstyleDialog").find('#5images').html('');
						}
					},
					close: function(){
						$("#editGarmentstyleDialog").find('#5images').html('');
					}
				});
		    };
		    
		    //if user choose delete option
		    if(valueSelected=="Delete"){
		    	
		    	$.ajax({
		    		dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						garmentstylecode: garmentstylecode
					}),
					contentType: "application/json",
					url: getAbsolutePath() +  "garmentstyle/detail",
					success: function(data1){
						//
						var displayName = data1.currentgarmentstyle.displayName;
						
						$("#deleteGarmentstyleDialog").find("#messageContent").text('Are you sure you want to delete garment style "' + displayName +'"?');
						$("#deleteGarmentstyleDialog").dialog({
							modal: true,
							show:{
								effect:"slide",
								duration: 500
							},
							title: "Delete Confirm",
							height: 300,
							width: 400,
							buttons:{
								"Yes": function(){
									$.ajax({
							    		dataType: "json",
							    		type: 'POST',
										data: JSON.stringify({
											garmentstylecode: garmentstylecode
										}),
										contentType: "application/json",
										url: getAbsolutePath() +  "garmentstyle/delete",
										success: function(data){
											callMessageDialog("Message", "Delete garment style \""+displayName+"\" successfully!");
											$("#deleteGarmentstyleDialog").dialog("close");
											$("#listGarmentstyle").dataTable().fnDestroy();
											$('#listGarmentstyle tbody').empty();						
											loadDataByCustomerAndFactory($("#ddlGlobalCustomer").val(), $("#ddlGlobalFactory").val());
										},
										error: function(){
											//alert('Cant delete garment style!');
											callMessageDialog("Warning Message", 'Can\'t delete garment style "'+displayName+'"!');
											$("#deleteGarmentstyleDialog").dialog("close");
										}
							    	});
								},
								"No": function(){
									$("#deleteGarmentstyleDialog").dialog("close");
								}
							}
						});
						//
					},
					error: function(){
						
					}
				});
		    	
		    	
//		    	$("#deleteGarmentstyleDialog").find("#messageContent").text('Are you sure you want to delete garment style "' + garmentstylecode+'"?');
//				$("#deleteGarmentstyleDialog").dialog({
//					modal: true,
//					show:{
//						effect:"slide",
//						duration: 500
//					},
//					title: "Delete Confirm",
//					height: 300,
//					width: 400,
//					buttons:{
//						"Yes": function(){
//							$.ajax({
//					    		dataType: "json",
//					    		type: 'POST',
//								data: JSON.stringify({
//									garmentstylecode: garmentstylecode
//								}),
//								contentType: "application/json",
//								url: getAbsolutePath() +  "garmentstyle/delete",
//								success: function(data){
//									callMessageDialog("Message", "Delete garment style \""+garmentstylecode+"\" successfully!");
//									$("#deleteGarmentstyleDialog").dialog("close");
//									$("#listGarmentstyle").dataTable().fnDestroy();
//									$('#listGarmentstyle tbody').empty();						
//									loadData();
//								},
//								error: function(){
//									//alert('Cant delete garment style!');
//									callMessageDialog("Warning Message", 'Can\'t delete garment style "'+garmentstylecode+'"!');
//									$("#deleteGarmentstyleDialog").dialog("close");
//								}
//					    	});
//						},
//						"No": function(){
//							$("#deleteGarmentstyleDialog").dialog("close");
//						}
//					}
//				});
		    };
		    //end if user choose delete option
		    
		  //if user choose Copy option
		    if(valueSelected=="Copy"){
		    	var garmentstylecode= $(this).data('id');
				$("#copyGarmentstyleDialog").find("#txtOldGarmentstyleCode").val(garmentstylecode);
				//g???i ajax find by id ????? l???y display name
				$.ajax({
		    		dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						garmentstylecode: garmentstylecode
					}),
					contentType: "application/json",
					url: getAbsolutePath() +  "garmentstyle/detail",
					success: function(data){
						if(data.status== "ok"){
							$("#copyGarmentstyleDialog").find("#displayName").val(data.currentgarmentstyle.displayName);
							//g??n gi?? tr??? cho factory code
							$("#copyGarmentstyleDialog").find("#customercode").val(data.currentgarmentstyle.customercode);
						}
					},
					error: function(){
						alert('Cant get detail');
					}
		    	});
				//end g???i ajax find by id ????? l???y display name
				
				
				$("#copyGarmentstyleDialog").dialog({
					modal: true,
					show:{
						effect:"slide",
						duration: 500
					},
					title: "Copy Garment Style",
					height: 300,
					width: 400,
					buttons:{
						"Save": function(){
//							alert($("#copyGarmentstyleDialog").find("#customercode").val());
							var newgarmentstylecode= $("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val();
							
							///TEST
							//n???u x??a tr???ng th?? b???t
							if(newgarmentstylecode.trim() === '' || newgarmentstylecode == null){
								//hi???n lu??n dialog th??ng b??o ra
								callMessageDialog("Warning Message", "Please enter garment style code!");
								return;
							}
							//ki???m tra tr?????ng h???p c?? space ??? ?????u, cu???i
							if(newgarmentstylecode.trim().length!=newgarmentstylecode.length){
								callMessageDialog("Warning Message", "You can't enter space at prefix or suffix!");
								return;
							}
							//tr?????ng h???p over range:
							if(newgarmentstylecode.length>50){
								callMessageDialog("Warning Message", "Field's length is 50, your input is overrange!");
								return;
							}
							
							//tr?????ng h???p isExisted
							$.ajax({
								dataType: "json",
								type: 'POST',
								data: JSON.stringify({
									garmentstylecode: newgarmentstylecode,
									customercode: $("#copyGarmentstyleDialog").find("#customercode").val()
								}),
								contentType: "application/json",
								url: getAbsolutePath() +  "garmentstyle/isExist",
								success: function(data){
									if(data.isExisted){
										callMessageDialog("Warning Message", "Garment style code is existed, please choose different one!");
									}else if(data.isExisted== false){
										//d??? li???u ???? valid h???t
										$.ajax({
								    		dataType: "json",
											type: 'POST',
											data:JSON.stringify({
												garmentstylecode: newgarmentstylecode,
												oldGarmentstyleCode: garmentstylecode,//g???i code c?? v??? model
												customercode: $("#copyGarmentstyleDialog").find("#customercode").val()
											}),
											contentType: "application/json",
											url: getAbsolutePath() +  "garmentstyle/copy",
											success: function(data){
												if(data.copyStatus==true&&data.status=="ok"){
													callMessageDialog("Message", "Copy Garment Style Successfully!");
													$("#copyGarmentstyleDialog").dialog("close");
													$("#listGarmentstyle").dataTable().fnDestroy();
													$('#listGarmentstyle tbody').empty();						
													loadDataByCustomerAndFactory($("#ddlGlobalCustomer").val(), $("#ddlGlobalFactory").val());
												}
											},
											error: function(){
												callMessageDialog("Warning Message", 'Can\'t Copy This Garment Style!');
												$("#copyGarmentstyleDialog").dialog("close");
											}
								    	});
										//end d??? li???u ???? valid h???t
									}
								},error: function(){
									//tr?????ng h???p x??a h???t input
									callMessageDialog("Warning Message", "Please enter garment style code!");
								}
							});
						},
						"Cancel": function(){
							$("#copyGarmentstyleDialog").dialog("close");
							//reset l???i css
							$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text('');
							$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "white");
							//x??a d??? li???u ???? nh???p
							$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val('');
						}
					},
					close: function(){
						//reset l???i css
						$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text('');
						$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "white");
						//x??a d??? li???u ???? nh???p
						$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").val('');
					}
				});
		    };
		    //end if user choose delete option
		    
		  //if user choose Copy option
		    if(valueSelected=="AccessoryList"){
		    	$.ajax({
		    		dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						garmentstylecode: garmentstylecode
					}),
					contentType: "application/json",
					url: getAbsolutePath() +  "garmentstyle/detail",
					success: function(data){
						if(data.status== "ok"){
							$("#configAccessoryDialog").find("#customercode").val(data.currentgarmentstyle.customercode);
							$("#configAccessoryDialog").find("#factorycode").val(data.currentgarmentstyle.factorycode);
							$("#configAccessoryDialog").find("#garmentstylecode").val(data.currentgarmentstyle.garmentstylecode);
							$("#configAccessoryDialog").find("#description").val(data.currentgarmentstyle.description);
							$("#configAccessoryDialog").find("#garmentkindcode").val(data.currentgarmentstyle.garmentkindcode);
							$("#configAccessoryDialog").find("#referprice").val(data.currentgarmentstyle.referprice);
							
							$("#configAccessoryDialog").find("#sewingguide").val(data.currentgarmentstyle.sewingguide);
							$("#configAccessoryDialog").find("#packingguide").val(data.currentgarmentstyle.packingguide);
							
							$("#configAccessoryDialog").find("#garmentstylecode2").val(data.currentgarmentstyle.garmentstylecode);
							$("#configAccessoryDialog").find("#displayName").val(data.currentgarmentstyle.displayName);
							
							//show image part
							//img1 part
							if((data.currentgarmentstyle.imgurl1==null)||(data.currentgarmentstyle.imgurl1.trim()=='')){
								//do nothing
							}else{
								$("#configAccessoryDialog").find('#5images').append('<span>Picture 1: <a class="fancybox"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'" alt=""></a></span>');//////////
							}
							//img2 part
							if((data.currentgarmentstyle.imgurl2==null)||(data.currentgarmentstyle.imgurl2.trim()=='')){
								//do nothing
							}else{
								$("#configAccessoryDialog").find('#5images').append('<span>Picture 2: <a class="fancybox"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'" alt=""></a></span>');//////////
							}
							//img3 part
							if((data.currentgarmentstyle.imgurl3==null)||(data.currentgarmentstyle.imgurl3.trim()=='')){
								//do nothing
							}else{
								$("#configAccessoryDialog").find('#5images').append('<span>Picture 3: <a class="fancybox"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'" alt=""></a></span>');//////////
							}
							//img4 part
							if((data.currentgarmentstyle.imgurl4==null)||(data.currentgarmentstyle.imgurl4.trim()=='')){
								//do nothing
							}else{
								$("#configAccessoryDialog").find('#5images').append('<span>Picture 4: <a class="fancybox"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'" alt=""></a></span>');//////////
							}
							//img5 part
							if((data.currentgarmentstyle.imgurl5==null)||(data.currentgarmentstyle.imgurl5.trim()=='')){
								//do nothing
							}else{
								$("#configAccessoryDialog").find('#5images').append('<span>Picture 5: <a class="fancybox"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'" alt=""></a></span>');//////////
							}
							
							//load d??? li???u v??o ddl
							loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
							
							//h ko load last table n???a, load dll trc
							//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
							
						}else{
							alert('This alert should never show!');
						}
					},
					error: function(){
						alert('Cant get detail');
					}
		    	});
				
				//load accessory list into table
//				$("#listAccessoryForGarment").dataTable().fnDestroy();
//				$('#listAccessoryForGarment tbody').empty();
				
				$.ajax({
					dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						garmentstylecode: garmentstylecode
					}),
					url: getAbsolutePath() +  "garmentstyle/listAccessoryForGarment",
					contentType: "application/json",
					success: function(data){
						if(data.listAccessoryForGarmentstyle.length==0){
//							alert("Table have no data.");
						}
						var i=1;
						$.each(data.listAccessoryForGarmentstyle,function(key,value){
							$('<tr>').append(
									$('<td>').text(i++),
									$('<td>').html(value.assignedForGarment==true?'<a class="hplEdit" data-id="'+value.accessorycode+'">'+value.name+'</a>':value.name),
									$('<td>').text(value.mode),
									$('<td>').html(value.assignedForGarment==true?'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'" checked/>':'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'">')
							).appendTo('#listAccessoryForGarment');
						});
						actionToggle();
						
						$('#listAccessoryForGarment').DataTable( {
							"pagingType": "full_numbers"
					    } );
					},
					error: function(){
						alert("Can not get data!");
					}
				});
				//end load accessory list into table
				
				//from here show dialog
				$("#configAccessoryDialog").dialog({
					modal: true,
					show:{
						effect:"slide",
						duration: 500
					},
					title: "Config Accessory For Garment Style",
					height: 550,
					width: 700,
					buttons:{
						"Close": function(){
							$("#configAccessoryDialog").dialog("close");
							$("#configAccessoryDialog").find('#5images').html('');
							
							$("#listAccessoryForGarment").dataTable().fnDestroy();
							$('#listAccessoryForGarment tbody').empty();
							
							//x??a b???ng cu???i
//							$("#listGarmentStyleAccessoryDetailMain").dataTable().fnDestroy();
//							$('#listGarmentStyleAccessoryDetailMain tbody').empty();
							$("#lastTable").html('');
						}
					},
					close: function(){
						$("#configAccessoryDialog").find('#5images').html('');
						
						$("#listAccessoryForGarment").dataTable().fnDestroy();
						$('#listAccessoryForGarment tbody').empty();
						
						//x??a b???ng cu???i
//						$("#listGarmentStyleAccessoryDetailMain").dataTable().fnDestroy();
//						$('#listGarmentStyleAccessoryDetailMain tbody').empty();
						$("#lastTable").html('');
					}
				});
		    };
		    //end if user choose delete option
		});
	};
	
	/**
	 * h??m x??? l?? option Edit
	 */
	function editOption(garmentstylecode){
		
		$("#editGarmentstyleDialog").find('#5images').html('');
		//b???t ?????u ajax findbyid
		$.ajax({
    		dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/detail",
			success: function(data){
				if(data.status== "ok"){
					//enable l???i refer price
					$("#editGarmentstyleDialog").find("#referprice").prop("disabled", false);
					
					$("#editGarmentstyleDialog").find("#customercode").val(data.currentgarmentstyle.customercode);
					$("#editGarmentstyleDialog").find("#factorycode").val(data.currentgarmentstyle.factorycode);
					$("#editGarmentstyleDialog").find("#garmentstylecode").val(data.currentgarmentstyle.garmentstylecode);
					//display name
					$("#editGarmentstyleDialog").find("#displayName").val(data.currentgarmentstyle.displayName);
					$("#editGarmentstyleDialog").find("#description").val(data.currentgarmentstyle.description);
					$("#editGarmentstyleDialog").find("#garmentkindcode").val(data.currentgarmentstyle.garmentkindcode);
					$("#editGarmentstyleDialog").find("#referprice").val(data.currentgarmentstyle.referprice);
					$("#editGarmentstyleDialog").find("#sewingguide").val(data.currentgarmentstyle.sewingguide);
					$("#editGarmentstyleDialog").find("#packingguide").val(data.currentgarmentstyle.packingguide);
					$("#editGarmentstyleDialog").find("#ddlPriceUnit").val(data.currentgarmentstyle.currencycode);
					
					//show image part
					//img1 part
					if((data.currentgarmentstyle.imgurl1==null)||(data.currentgarmentstyle.imgurl1.trim()=='')){
						//do nothing
					}else{
						$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 1: <a class="fancybox" rel="'+ garmentstylecode +'" href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl1+'" alt=""></a> <button class="btn btn-danger btnDeleteImg" data-id="'+data.currentgarmentstyle.imgurl1+'">Delete Picture 1</button> </span><br/>');//////////
					}
					//img2 part
					if((data.currentgarmentstyle.imgurl2==null)||(data.currentgarmentstyle.imgurl2.trim()=='')){
						//do nothing
					}else{
						$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 2: <a class="fancybox" rel="'+ garmentstylecode +'"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'" ><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl2+'" alt=""></a> <button class="btn btn-danger btnDeleteImg" data-id="'+data.currentgarmentstyle.imgurl2+'">Delete Picture 2</button> </span><br/>');//////////
					}
					//img3 part
					if((data.currentgarmentstyle.imgurl3==null)||(data.currentgarmentstyle.imgurl3.trim()=='')){
						//do nothing
					}else{
						$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 3: <a class="fancybox" rel="'+ garmentstylecode +'"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'" ><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl3+'" alt=""></a> <button class="btn btn-danger btnDeleteImg" data-id="'+data.currentgarmentstyle.imgurl3+'">Delete Picture 3</button> </span><br/>');//////////
					}
					//img4 part
					if((data.currentgarmentstyle.imgurl4==null)||(data.currentgarmentstyle.imgurl4.trim()=='')){
						//do nothing
					}else{
						$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 4: <a class="fancybox" rel="'+ garmentstylecode +'"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'" ><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl4+'" alt=""></a> <button class="btn btn-danger btnDeleteImg" data-id="'+data.currentgarmentstyle.imgurl4+'">Delete Picture 4</button> </span><br/>');//////////
					}
					//img5 part
					if((data.currentgarmentstyle.imgurl5==null)||(data.currentgarmentstyle.imgurl5.trim()=='')){
						//do nothing
					}else{
						$("#editGarmentstyleDialog").find('#5images').append('<span>Picture 5: <a class="fancybox" rel="'+ garmentstylecode +'"  href="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'" ><img height="100" width="100" src="choriGarmentStyleImage/'+data.currentgarmentstyle.imgurl5+'" alt=""></a> <button class="btn btn-danger btnDeleteImg" data-id="'+data.currentgarmentstyle.imgurl5+'">Delete Picture 5</button> </span>');//////////
					}
					
					//ph???n g??n s??? ki???n n??t delete image
					$(".btnDeleteImg").click(function(e){
						e.preventDefault();
						
						var imgUrlDelete = $(this).data('id');
						
						$("#deleteImageDialog").find("#messageContent").text("Are you sure to delete this image?");
						
						//m??? dialog
						$("#deleteImageDialog").dialog({
							modal: true,
							show:{
								effect:"blind",
								duration: 500
							},
							title: "Delete Confirm",
							height: 300,
							width: 300,
							buttons:{
								"OK": function(){
									//
									$.ajax({
										dataType: "json",
										type: 'POST',
										data: JSON.stringify({
											garmentstylecode: $("#editGarmentstyleDialog").find("#garmentstylecode").val(),
											imgUrlDelete: imgUrlDelete
										}),
										contentType: "application/json",
										url: getAbsolutePath() +  "garmentstyle/deleteImage",//ch??a fix
										success: function(data){
											if(data.deleteImageStatus==true){
												callMessageDialog("Message", 'Delete image successfully!');
												$("#deleteImageDialog").dialog("close");
												editOption(garmentstylecode);
												$("#listGarmentstyle").dataTable().fnDestroy();
												$('#listGarmentstyle tbody').empty();						
												loadDataByCustomerAndFactory($("#ddlGlobalCustomer").val(), $("#ddlGlobalFactory").val());
											}
										},
										error: function(){
											alert("L???i cmnr");
										}
									});
									//
								},
								"Cancel": function(){
									$(this).dialog("close");
								}
							},
							close: function(){
								
							}
						});
						//end m??? dialog
					});
					//end ph???n g??n s??? ki???n n??t delete image
					enableDisableCustomerFactory(garmentstylecode);
					
					/**
					 * Test
					 */
//					alert(data.currentgarmentstyle.referprice);
					//n???u refer price kh??c null, th?? xu???t price
					if(data.currentgarmentstyle.referprice!=null){
						//ph???n append m???y c??i textbox by dozen
						$("#editGarmentstyleDialog").find("#priceBySizeGroupCover").html('');
						$("#editGarmentstyleDialog").find("#priceBySizeGroupCover").append(
								'<table id="tblPriceBySizeGroupEdit"></table>'
						);
						
						$.ajax({
							dataType: "json",
							type: 'GET',
							data: {},
							url: getAbsolutePath() +  "type/list",
							contentType: "application/json",
							success: function(data){
								$.each(data.list,function(key,value){
									$('<tr>').append(
											$('<td>').text(value.typeCode),
											$('<td>').html('<input type="number" step="any" maxlength="12" min="0" max="999999999999" class="priceBySizeGroupClassEdit quantity1" id="'+value.typeCode+'" >')
									).appendTo('#tblPriceBySizeGroupEdit');
//									$("#addGarmentstyleDialog").find("#priceBySizeGroupCover").append(
//											value.typeCode+': <input type="text" class="priceBySizeGroupClass" id="'+value.typeCode+'" ><br>'
//									);
								});
								
								//ph???n default check pcs
								$("#editGarmentstyleDialog").find("#radPcs").prop("checked", true);
								
								//disable c??c ph???n textbox dozen
								$('.priceBySizeGroupClassEdit').each(function(i, obj) {
									$(this).attr("disabled", "disabled");
								});
								
								maxlengthForNumber();
							},
							error: function(){
								alert("Can not get data!");
							}
						});
						//end ph???n append m???y c??i textbox by dozen
					}else{
						//ph???n append m???y c??i textbox by dozen
						$("#editGarmentstyleDialog").find("#priceBySizeGroupCover").html('');
						$("#editGarmentstyleDialog").find("#priceBySizeGroupCover").append(
								'<table id="tblPriceBySizeGroupEdit"></table>'
						);
						
						$.each(data.listGarmentstylereferprice,function(key,value){
							$('<tr>').append(
									$('<td>').text(value.typecode),
									$('<td>').html('<input type="number" step="any" maxlength="12" min="0" max="999999999999" class="priceBySizeGroupClassEdit quantity1" id="'+value.typecode+'" value="'+value.referprice+'" >')
							).appendTo('#tblPriceBySizeGroupEdit');
						});
						
						//ph???n default check pcs
						$("#editGarmentstyleDialog").find("#radPcs").prop("checked", false);
						$("#editGarmentstyleDialog").find("#radDozen").prop("checked", true);
						
						$("#editGarmentstyleDialog").find("#referprice").prop("disabled", true);
						
						maxlengthForNumber();
					}
					/**
					 * End Test
					 */
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Cant get detail');
			}
    	});
    	//end g???i ajax find by id
	}
	
	function showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstyleCode){
//		$("#listGarmentStyleAccessoryDetailMain").dataTable().fnDestroy();
//		$('#listGarmentStyleAccessoryDetailMain tbody').empty();
		
		///test
		$("#lastTable").html('');
		$("#lastTable").append('<table class="table table-striped table-bordered display responsive"'+
					'id="listGarmentStyleAccessoryDetailMain">'+
					'<thead>'+
						'<tr>'+
							'<th>No</th>'+
							'<th>Accessory Name</th>'+
							'<th>Size</th>'+
							'<th>Used Value</th>'+
							'<th>Type</th>'+
						'</tr>'+
					'</thead>'+
				'</table>');
		///
		
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstyleCode
			}),
			url: getAbsolutePath() +  "garmentstyle/listGarmentstyleaccessorydetailByGarmentStyleName",
			contentType: "application/json",
			success: function(data){
				///TEST
				var i=0;
				var preAccessoryName ="";
				$.each(data.lstGarmentstyleaccessorydetailByGarmentStyleName,function(key,value){
					$('<tr>').append(
							$('<td>').text(preAccessoryName.localeCompare(value.accessoryName)==0?i:++i),
							$('<td>').text(value.accessoryName),
							$('<td>').text(value.sizename),
							$('<td>').text(value.usedvalue),
							$('<td>').text(value.typecode)
					).appendTo('#listGarmentStyleAccessoryDetailMain');
					preAccessoryName = value.accessoryName;
				});
				///
				$('#listGarmentStyleAccessoryDetailMain').DataTable( {
					"sPaginationType": "full_numbers",
					paging: true,
//					"aaData": data.lstGarmentstyleaccessorydetailByGarmentStyleName,
//					"order": [[ 0, "asc" ]],
			        rowsGroup: [0,1,4]
//			        "aoColumns": [
//						{
//							"data": "accessoryName",
//							sDefaultContent: ""
//						},
//						{
//							"data": "sizename",
//							sDefaultContent: ""
//						},
//						{
//							"data": "usedvalue",
//							sDefaultContent: ""
//						},
//						{
//							"data": "typecode",
//							sDefaultContent: ""
//						}
//			        ]
			    } );
			},
			error: function(){
				alert("Can not get data!");
			}
		});
	};
	
	/**
	 * This function handle check box accessory check
	 */
	function actionToggle(){
		$('.chkAssign').on('click', function () {
//			alert("click");
			if($(this).is(':checked')){
				//get garmentStyleCode, accessoryCode and customerCode
				var garmentstyleCode = $("#configAccessoryDialog").find('#garmentstylecode').val();
				var accessoryCode = $(this).data('id');
				var customerCode = $("#configAccessoryDialog").find('#customercode').val();
				
				var garmentKindCode= $("#configAccessoryDialog").find('#garmentkindcode').val();
				var displayName = $("#configAccessoryDialog").find('#displayName').val();
				
				//assign value for garment name
				$("#addGarmentstyleaccessorydetailDialog").find('#garmentstylecode').val(garmentstyleCode);
				$("#addGarmentstyleaccessorydetailDialog").find('#displayName').val(displayName);
				//g??n gi?? tr??? cho customerCode
				$("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val(customerCode);
				//assign value for accessory name:
				$.ajax({
					dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						accessorycode: accessoryCode,
					}),
					url: getAbsolutePath() +  "accessory/detail",
					contentType: "application/json",
					success: function(data){
						//g???i ajax ????? l???y accessory name qua accessoryCode, v?? hi???n th??? th?? show ra name
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryName').val(data.accessory.name);
						//l???y code lu??n nh??ng add v?? textbox hidden
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryCode').val(data.accessory.accessorycode);/////
					},
					error: function(){
						alert("Can not get data!");
					}
				});
				//end assign value for accessory name:
				
				loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				//test
//				$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
//				$('#listGarmentstyleaccessorydetailAdd tbody').empty();
				
				getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstyleCode, accessoryCode);
				
				//ki???m tra xem en hay disable n??t add
				enOrDisableBtnAddNewGarmentstyleaccessorydetail(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				$("#addGarmentstyleaccessorydetailDialog").dialog({
					modal: true,
					show:{
						effect:"blind",
						duration: 500
					},
					title: "Add Garment Style Accessory Detail",
					height: 550,
					width: 700,
					buttons:{
						
					},
					close: function(){
						$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
						$('#listGarmentstyleaccessorydetailAdd tbody').empty();
						//load l???i list accessory
						loadListAccessoryByGarmentStyle(garmentstyleCode);
					}
				});
			}
		});
		
		//function handle when click on hyperlink
		$('.hplEdit').on('click', function () {
				//get garmentStyleCode, accessoryCode and customerCode
				var garmentstyleCode = $("#configAccessoryDialog").find('#garmentstylecode').val();
				var accessoryCode = $(this).data('id');
				var customerCode = $("#configAccessoryDialog").find('#customercode').val();
				
				var garmentKindCode = $("#configAccessoryDialog").find('#garmentkindcode').val();
				var displayName = $("#configAccessoryDialog").find('#displayName').val();
				
				//assign value for garment name
				$("#addGarmentstyleaccessorydetailDialog").find('#garmentstylecode').val(garmentstyleCode);
				$("#addGarmentstyleaccessorydetailDialog").find('#displayName').val(displayName);
				//g??n gi?? tr??? cho customerCode
				$("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val(customerCode);
				//assign value for accessory name:
				$.ajax({
					dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						accessorycode: accessoryCode,
					}),
					url: getAbsolutePath() +  "accessory/detail",
					contentType: "application/json",
					success: function(data){
						//g???i ajax ????? l???y accessory name qua accessoryCode, v?? hi???n th??? th?? show ra name
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryName').val(data.accessory.name);
						//l???y code lu??n nh??ng add v?? textbox hidden
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryCode').val(data.accessory.accessorycode);/////
					},
					error: function(){
						alert("Can not get data!");
					}
				});
				//end assign value for accessory name:
				
				loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				//test
				$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
				$('#listGarmentstyleaccessorydetailAdd tbody').empty();
				
				getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstyleCode, accessoryCode);
				
				//ki???m tra xem en hay disable n??t add
				enOrDisableBtnAddNewGarmentstyleaccessorydetail(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				$("#addGarmentstyleaccessorydetailDialog").dialog({
					modal: true,
					show:{
						effect:"blind",
						duration: 500
					},
					title: "Add Garment Style Accessory Detail",
					height: 550,
					width: 700,
					buttons:{
						
					},
					close: function(){
						$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
						$('#listGarmentstyleaccessorydetailAdd tbody').empty();
						//load l???i list accessory
						loadListAccessoryByGarmentStyle(garmentstyleCode);
					}
				});
		});
	};
	
	function loadListAccessoryByGarmentStyle(garmentstylecode){
		//load accessory list into table
		$("#listAccessoryForGarment").dataTable().fnDestroy();
		$('#listAccessoryForGarment tbody').empty();
		
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode
			}),
			url: getAbsolutePath() +  "garmentstyle/listAccessoryForGarment",
			contentType: "application/json",
			success: function(data){
				if(data.listAccessoryForGarmentstyle.length==0){
//					alert("Table have no data.");
				}
				var i=1;
				$.each(data.listAccessoryForGarmentstyle,function(key,value){
					$('<tr>').append(
							$('<td>').text(i++),
							$('<td>').html(value.assignedForGarment==true?'<a class="hplEdit" data-id="'+value.accessorycode+'">'+value.name+'</a>':value.name),
							$('<td>').text(value.mode),
							$('<td>').html(value.assignedForGarment==true?'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'" checked/>':'<input type="checkbox" class="btn btn-danger chkAssign" data-id="'+value.accessorycode+'">')
					).appendTo('#listAccessoryForGarment');
				});
				actionToggle();
				
				$('#listAccessoryForGarment').DataTable( {
					"pagingType": "full_numbers"
			    } );
			},
			error: function(){
				alert("Can not get data!");
			}
		});
		//end load accessory list into table
	}
	
	/**
	 * This function load data for ddlSize
	 * H??m x??? l?? add c??c option v??o ddlSize khi hi???n th??? dialog add m???i GarmentStyleAccessoryDetail
	 */
	function loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstyleCode, accessoryCode, customerCode, garmentKindCode){
		$('#addGarmentstyleaccessorydetailDialog').find('#ddlSize').html('');
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstyleCode,
				accessoryCode: accessoryCode,
				customercode: customerCode,
				garmentkindcode: garmentKindCode
			}),
			url: getAbsolutePath() +  "garmentstyle/listSizeByGarmentAccessoryCustomer",
			contentType: "application/json",
			success: function(data){
				//?????u ti??n add y??u c???u ch???n 1 size v??o
				$('#addGarmentstyleaccessorydetailDialog').find('#ddlSize').append('<option value="-1" disabled selected>--Please Select Size--');
				//sau ???? l???p qua v?? add t???ng size v??o
				$.each( data.lstSizeByGarmentAccessoryCustomer, function( key, value ) {
                    $('#addGarmentstyleaccessorydetailDialog').find('#ddlSize').append(value.assignedForGarmentAccessory==false?'<option value="'+value.sizecode+'">'+value.sizename:'<option value="'+value.sizecode+'" disabled>'+value.sizename);
                });
			},
			error: function(){
				alert("Can not get data!");
			}
		});
	};
	
	/**
	 * H??m load d??? li???u v??o b???ng c???a dialog "Add Garment Style Accessory Detail"
	 * load theo: vs garmentStyle v?? accessory th?? l???y ra c??c garmentStyleAccessoryDetail ???? assign
	 */
	function getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstyleCode, accessoryCode){
		
		$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
		$('#listGarmentstyleaccessorydetailAdd tbody').empty();
		
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstyleCode,
				accessoryCode: accessoryCode
			}),
			url: getAbsolutePath() +  "garmentstyle/listGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryName",
			contentType: "application/json",
			success: function(data){
				///
				if(data.lstGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryName.length==0){
					//alert("Table have no data.");
				}
				var i=1;
				$.each(data.lstGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryName,function(key,value){
					$('<tr>').append(
							$('<td>').text(i++),
							$('<td>').text(value.sizename),
							$('<td>').text(value.typecode),
							$('<td>').text(value.usedvalue),
//							$('<td>').html('<button class="btn btn-primary btnEditGarmentstyleaccessorydetail" data-id="'+value.garmentstyleaccessorydetailcode+'">Edit</button>'),
//							$('<td>').html('<button class="btn btn-danger btnDeleteGarmentstyleaccessorydetail" data-id="'+value.garmentstyleaccessorydetailcode+'">Delete</button>')
							$('<td>').html('<select class="selectpicker selectOptionForListGarmentstyleaccessorydetail" data-id="'+value.garmentstyleaccessorydetailcode+'">'
									+'<option value="Options" disabled selected>Options</option>'
									+'<option value="Edit">Edit</option>'
									+'<option value="Delete">Delete</option></select>')
					).appendTo('#listGarmentstyleaccessorydetailAdd');
				});
				actionForListGarmentstyleaccessorydetail();
				
				$('#listGarmentstyleaccessorydetailAdd').DataTable( {
					"pagingType": "full_numbers"
			    } );
				///
			},
			error: function(){
				alert("Can not get data!");
			}
		});
	};
	
	/**
	 * H??m handle n??t edit, delete cho b???ng garmentstyleaccessorydetail
	 */
//	function actionForListGarmentstyleaccessorydetail(){
//		/**
//		 * H??m handle n??t edit
//		 */
//		$('.btnEditGarmentstyleaccessorydetail').on('click', function () {
//			var garmentstyleaccessorydetailcode= $(this).data('id');
//			//g??n id ????? l???y ??c garmentstyleaccessorydetailcode cho vi???c edit
//			$("#editGarmentstyleaccessorydetailDialog").find("#garmentstyleaccessorydetailcode").val(garmentstyleaccessorydetailcode);
//			//l???y garmentStyle v?? accessory ????? load l???i b???ng d?????i
//			var garmentstylecode = $("#addGarmentstyleaccessorydetailDialog").find("#garmentstylecode").val();
//			var accessorycode = $("#addGarmentstyleaccessorydetailDialog").find("#accessoryCode").val();
//			
//			$("#editGarmentstyleaccessorydetailDialog").dialog({
//				modal: true,
//				show:{
//					effect:"blind",
//					duration: 500
//				},
//				title: "Edit Garment Style Accessory Dialog",
//				height: 300,
//				width: 300,
//				buttons:{
//					"Update": function(){
//						//validate nh???p newUsedValue
//						var newUsedvalue= $("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val();
//						//n???u ch??a nh???p used value th?? b??o
//						if(newUsedvalue.trim() == '' || newUsedvalue == null){
//							callMessageDialog("Warning Message", "Please enter new used value!");
//							return;
//						}
//						
//						//n???u used value<=0 th?? b??o
//						if(newUsedvalue<=0){
//							callMessageDialog("Warning Message", "Used value must be greater than 0!");
//							return;
//						}
//						//end validate nh???p newUsedValue
//						
//						//n???u ???? ok r th?? g???i ajax
//						//l???y id v?? used value ????a v??o
//						var Garmentstyleaccessorydetail = {
//								garmentstyleaccessorydetailcode: garmentstyleaccessorydetailcode,
//								usedvalue: newUsedvalue	
//						};
//						///G???i ajax ????? edit
//						$.ajax({
//							dataType: "json",
//							type: 'POST',
//							data: JSON.stringify(Garmentstyleaccessorydetail),
//							url: "/Chori/garmentstyleaccessorydetail/edit",
//							contentType: "application/json",
//							success: function(data){
//								if(data.editStatus==true){
//									//Add hi???n th??? dialog
//									callMessageDialog("Message", "Edit garment style accessory detail successfully!");
//									//load l???i b???ng b??n d?????i
//									getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstylecode, accessorycode);
//									//????ng l???i dialog
//									$("#editGarmentstyleaccessorydetailDialog").dialog("close");
//									//load b???ng ngo??i
//									//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
//									//x??a b???ng ngo??i, load l???i dll
//									$("#lastTable").html('');
//									//loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
//									var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
//									reloadLastTableAfterAddEditDelete(accessorygroupcode);
//									$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
//								}
//							},
//							error: function(){
//								alert("Can not get data!");
//							}
//						});
//						///
//					},
//					"Cancel": function(){
//						$(this).dialog("close");
//						//x??a gi?? tr??? used value ???? nh???p
//						$("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val('');
//					}
//				},
//				close: function(){
//					//x??a gi?? tr??? used value ???? nh???p
//					$("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val('');
//				}
//			});
//		});
//		
//		//handle function for delete Garment style accessory detail
//		$('.btnDeleteGarmentstyleaccessorydetail').on('click', function () {	
//			var garmentstyleaccessorydetailcode= $(this).data('id');
//			$("#deleteGarmentstyleaccessorydetailDialog").find("#messageContent").text('Are you sure you want to delete this garment style accessory detail?');
//			$("#deleteGarmentstyleaccessorydetailDialog").dialog({
//				modal: true,
//				show:{
//					effect:"slide",
//					duration: 500
//				},
//				title: "Delete Confirm",
//				height: 300,
//				width: 400,
//				buttons:{
//					"Yes": function(){
//						$.ajax({
//				    		dataType: "json",
//							type: 'POST',
//							data:{},
//							contentType: "application/json",
//							url: "/Chori/garmentstyleaccessorydetail/delete/"+garmentstyleaccessorydetailcode,
//							success: function(data){
//								callMessageDialog("Message", "Delete successfully!");
//								//????ng confirm dialog
//								$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
//								//load l???i b???ng b??n d?????i
//								//l???y garmentStyle v?? accessory ????? load l???i b???ng d?????i
//								var garmentstylecode = $("#addGarmentstyleaccessorydetailDialog").find("#garmentstylecode").val();
//								var accessorycode = $("#addGarmentstyleaccessorydetailDialog").find("#accessoryCode").val();
//								var customercode = $("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val();
//								var garmentKindCode = $("#configAccessoryDialog").find('#garmentkindcode').val();
//								getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstylecode, accessorycode);
//								//load l???i ddlSize
//								loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstylecode, accessorycode, customercode, garmentKindCode);
//								//enable l???i n??t add
//								$('#addGarmentstyleaccessorydetailDialog').find("#btnAddNewGarmentstyleaccessorydetail").prop('disabled', false);
//								//load b???ng ngo??i (b??? sung sau).
//								//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
//								//x??a b???ng ngo??i, load l???i dll
//								$("#lastTable").html('');
//								//loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
//								var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
//								reloadLastTableAfterAddEditDelete(accessorygroupcode);
//								$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
//							},
//							error: function(){
//								//alert('Cant delete garment style!');
//								callMessageDialog("Warning Message", 'Cant delete this garment style accessory detail!');
//								$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
//							}
//				    	});
//					},
//					"No": function(){
//						$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
//					}
//				}
//			});
//		});
//	};
	
	function actionForListGarmentstyleaccessorydetail(){	
		$('.selectOptionForListGarmentstyleaccessorydetail').on('change', function (e) {
			var optionSelected = $(this).find("option:selected");
		    var valueSelected  = optionSelected.val();
		    
		    var garmentstyleaccessorydetailcode= $(this).data('id');//this is RoleID of each record.
		    //alert('You choose '+roleIdSeleted+', and the option is: '+ valueSelected);
		    $(".selectOption").val("Options");
		    
		    
		    //If user choose edit option
		    if(valueSelected=="Edit"){
		    	
				//g??n id ????? l???y ??c garmentstyleaccessorydetailcode cho vi???c edit
				$("#editGarmentstyleaccessorydetailDialog").find("#garmentstyleaccessorydetailcode").val(garmentstyleaccessorydetailcode);
				//l???y garmentStyle v?? accessory ????? load l???i b???ng d?????i
				var garmentstylecode = $("#addGarmentstyleaccessorydetailDialog").find("#garmentstylecode").val();
				var accessorycode = $("#addGarmentstyleaccessorydetailDialog").find("#accessoryCode").val();
				
				$("#editGarmentstyleaccessorydetailDialog").dialog({
					modal: true,
					show:{
						effect:"blind",
						duration: 500
					},
					title: "Edit Garment Style Accessory Dialog",
					height: 300,
					width: 300,
					buttons:{
						"Update": function(){
							//validate nh???p newUsedValue
							var newUsedvalue= $("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val();
							//n???u ch??a nh???p used value th?? b??o
							if(newUsedvalue.trim() == '' || newUsedvalue == null){
								callMessageDialog("Warning Message", "Please enter new used value!");
								return;
							}
							
							//n???u used value<=0 th?? b??o
							if(newUsedvalue<=0){
								callMessageDialog("Warning Message", "Used value must be greater than 0!");
								return;
							}
							//end validate nh???p newUsedValue
							
							//n???u ???? ok r th?? g???i ajax
							//l???y id v?? used value ????a v??o
							var Garmentstyleaccessorydetail = {
									garmentstyleaccessorydetailcode: garmentstyleaccessorydetailcode,
									usedvalue: newUsedvalue	
							};
							///G???i ajax ????? edit
							$.ajax({
								dataType: "json",
								type: 'POST',
								data: JSON.stringify(Garmentstyleaccessorydetail),
								url: getAbsolutePath() +  "garmentstyleaccessorydetail/edit",
								contentType: "application/json",
								success: function(data){
									if(data.editStatus==true){
										//Add hi???n th??? dialog
										callMessageDialog("Message", "Edit garment style accessory detail successfully!");
										//load l???i b???ng b??n d?????i
										getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstylecode, accessorycode);
										//????ng l???i dialog
										$("#editGarmentstyleaccessorydetailDialog").dialog("close");
										//load b???ng ngo??i
										//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
										//x??a b???ng ngo??i, load l???i dll
										$("#lastTable").html('');
										//loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
										var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
										reloadLastTableAfterAddEditDelete(accessorygroupcode);
										$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
									}
								},
								error: function(){
									alert("Can not get data!");
								}
							});
							///
						},
						"Cancel": function(){
							$(this).dialog("close");
							//x??a gi?? tr??? used value ???? nh???p
							$("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val('');
						}
					},
					close: function(){
						//x??a gi?? tr??? used value ???? nh???p
						$("#editGarmentstyleaccessorydetailDialog").find("#newUsedValue").val('');
					}
				});
		    };
		    //end if user choose edit option
		    
		  //If user choose delete option
		    if(valueSelected=="Delete"){
		    	$("#deleteGarmentstyleaccessorydetailDialog").find("#messageContent").text('Are you sure you want to delete this garment style accessory detail?');
				$("#deleteGarmentstyleaccessorydetailDialog").dialog({
					modal: true,
					show:{
						effect:"slide",
						duration: 500
					},
					title: "Delete Confirm",
					height: 300,
					width: 400,
					buttons:{
						"Yes": function(){
							$.ajax({
					    		dataType: "json",
					    		type: 'POST',
								data: JSON.stringify({
									garmentstyleaccessorydetailcode: garmentstyleaccessorydetailcode,
								}),
								contentType: "application/json",
								url: getAbsolutePath() +  "garmentstyleaccessorydetail/delete",
								success: function(data){
									callMessageDialog("Message", "Delete successfully!");
									//????ng confirm dialog
									$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
									//load l???i b???ng b??n d?????i
									//l???y garmentStyle v?? accessory ????? load l???i b???ng d?????i
									var garmentstylecode = $("#addGarmentstyleaccessorydetailDialog").find("#garmentstylecode").val();
									var accessorycode = $("#addGarmentstyleaccessorydetailDialog").find("#accessoryCode").val();
									var customercode = $("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val();
									var garmentKindCode = $("#configAccessoryDialog").find('#garmentkindcode').val();
									getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstylecode, accessorycode);
									//load l???i ddlSize
									loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstylecode, accessorycode, customercode, garmentKindCode);
									//enable l???i n??t add
									$('#addGarmentstyleaccessorydetailDialog').find("#btnAddNewGarmentstyleaccessorydetail").prop('disabled', false);
									//load b???ng ngo??i (b??? sung sau).
									//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
									//x??a b???ng ngo??i, load l???i dll
									$("#lastTable").html('');
									//loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
									var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
									reloadLastTableAfterAddEditDelete(accessorygroupcode);
									$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
								},
								error: function(){
									//alert('Cant delete garment style!');
									callMessageDialog("Warning Message", 'Cant delete this garment style accessory detail!');
									$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
								}
					    	});
						},
						"No": function(){
							$("#deleteGarmentstyleaccessorydetailDialog").dialog("close");
						}
					}
				});
		    };
		    //end if user choose delete option
		});
	};
	
	/**
	 * This function enable or disable btnAddNewGarmentstyleaccessorydetail
	 * H??m enable hay disable n??t AddNewGarmentstyleaccessorydetail ????? tr??nh b??? tr??ng size (???????c g???i sau khi add, load dialog)
	 */
	function enOrDisableBtnAddNewGarmentstyleaccessorydetail(garmentstylecode, accessorycode, customercode, garmentKindCode){
		//g???i ajax xem k???t qu??? l?? true hay false
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode,
				accessoryCode: accessorycode,
				customercode: customercode,
				garmentkindcode: garmentKindCode
			}),
			url: getAbsolutePath() +  "garmentstyleaccessorydetail/isAllSizeAlreadyAssigned",
			contentType: "application/json",
			success: function(data){
				//Tr?????ng h???p ???? assign h???t r th?? disable n??t add.
				if(data.isAllSizeAlreadyAssigned==true){
					$('#addGarmentstyleaccessorydetailDialog').find("#btnAddNewGarmentstyleaccessorydetail").prop('disabled', true);
				}else if(data.isAllSizeAlreadyAssigned==false){
					$('#addGarmentstyleaccessorydetailDialog').find("#btnAddNewGarmentstyleaccessorydetail").prop('disabled', false);
				}
			},
			error: function(){
				alert("problem at 915");
			}
		});
	}
	
	/**
	 * This function handle add new Garmentstyleaccessorydetail
	 * H??m x??? l?? add m???i 1 garmentStyleAccessoryDetail
	 */
	$('#addGarmentstyleaccessorydetailDialog').find("#btnAddNewGarmentstyleaccessorydetail").on('click', function (e) {
		//alert("OK");
		var garmentstylecode = $("#addGarmentstyleaccessorydetailDialog").find("#garmentstylecode").val();
		var accessorycode = $("#addGarmentstyleaccessorydetailDialog").find("#accessoryCode").val();
		var sizecode= $("#addGarmentstyleaccessorydetailDialog").find("#ddlSize").val();
		var garmentKindCode = $("#configAccessoryDialog").find('#garmentkindcode').val();
		
//		var garmentKindCode = $("#configAccessoryDialog").find("#garmentkindcode").val();
		
		//n???u ko ch???n m?? b???m add th?? return lu??n
		if(sizecode==-1){
			callMessageDialog("Warning Message", "Please select a size!");
			return;
		}
		
		//usedValue
		var usedvalue= $("#addGarmentstyleaccessorydetailDialog").find("#usedValue").val();
		//n???u ch??a nh???p used value th?? b??o
		if(usedvalue.trim() == '' || usedvalue == null){
			callMessageDialog("Warning Message", "Please enter used value!");
			return;
		}
		
		//n???u used value<=0 th?? b??o
		if(usedvalue<=0){
			callMessageDialog("Warning Message", "Used value must be greater than 0!");
			return;
		}
		
		var customercode=$("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val();
		
		var Garmentstyleaccessorydetail = {
				garmentstylecode: garmentstylecode,
				accessorycode: accessorycode,
				sizecode: sizecode,
				usedvalue: usedvalue	
		};
		
		///G???i ajax ????? add
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify(Garmentstyleaccessorydetail),
			url: getAbsolutePath() +  "garmentstyleaccessorydetail/add",
			contentType: "application/json",
			success: function(data){
				if(data.addStatus==true){
					//Add hi???n th??? dialog
					callMessageDialog("Message", "Add new garment style accessory detail successfully!");
					//load l???i b???ng b??n d?????i
					getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstylecode, accessorycode);
					//load b???ng b??n ngo??i (ch??a l??m)
					//showGarmentStyleAccessoryDetailWhenClickAccessoryList(garmentstylecode);
					//x??a b???ng ngo??i, load l???i dll
					$("#lastTable").html('');
					//loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
					var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
					reloadLastTableAfterAddEditDelete(accessorygroupcode);
					$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
					//load l???i ddlSize
					loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstylecode, accessorycode, customercode, garmentKindCode);
					//ki???m tra xem x??i h???t size ch??a ????? en- hay disable n??t add
					enOrDisableBtnAddNewGarmentstyleaccessorydetail(garmentstylecode, accessorycode, customercode, garmentKindCode);
					//clear used value ???? nh???p
					$("#addGarmentstyleaccessorydetailDialog").find("#usedValue").val('');
				}
			},
			error: function(){
				alert("Can not get data!");
			}
		});
		///
	});
	
	/**
	 * This function handle add new garment style
	 * H??m x??? l?? show dialog khi nh???n v??o "Add New Garment Style", h??m ch??? show dialog do c?? upload h??nh
	 */
	$('#btnAddNewGarmentstyle').on('click', function (e) {
		
		$("#addGarmentstyleDialog").find("#garmentstylecode").val('');
		
		$("#listGarmentstyle,#addGarmentstyleDialog").find("#errorGarmentstylecode").text("");
		$("#listGarmentstyle,#addGarmentstyleDialog").find("#garmentstylecode").css("border-color", "");	
		
		//validate l??c m??? dialog
		validateWhenOpenAddGarmentStyleDialog();
		
		/**
		 * Test
		 */
		//ph???n append m???y c??i textbox by dozen
		$("#addGarmentstyleDialog").find("#priceBySizeGroupCover").html('');
		$("#addGarmentstyleDialog").find("#priceBySizeGroupCover").append(
				'<table id="tblPriceBySizeGroup"></table>'
		);
		
		$.ajax({
			dataType: "json",
			type: 'GET',
			data: {},
			url: getAbsolutePath() +  "type/list",
			contentType: "application/json",
			success: function(data){
				$.each(data.list,function(key,value){
					$('<tr>').append(
							$('<td>').text(value.typeCode),
							$('<td>').html('<input type="number" step="any" maxlength="12" min="0" max="999999999999" class="priceBySizeGroupClass quantity1" id="'+value.typeCode+'" >')
					).appendTo('#tblPriceBySizeGroup');
//					$("#addGarmentstyleDialog").find("#priceBySizeGroupCover").append(
//							value.typeCode+': <input type="text" class="priceBySizeGroupClass" id="'+value.typeCode+'" ><br>'
//					);
				});
				
				//ph???n default check pcs
				$("#addGarmentstyleDialog").find("#radPcs").prop("checked", true);
				
				//disable c??c ph???n textbox dozen
				$('.priceBySizeGroupClass').each(function(i, obj) {
					$(this).attr("disabled", "disabled");
				});
				
				maxlengthForNumber();
			},
			error: function(){
				alert("Can not get data!");
			}
		});
		//end ph???n append m???y c??i textbox by dozen
		/**
		 * End test
		 */
		
		$("#addGarmentstyleDialog").dialog({
			modal: true,
			show:{
				effect:"blind",
				duration: 500
			},
			title: "Add New Garment Style",
			height: 550,
			width: 700,
			buttons:{
				"Cancel": function(){
					$("#addGarmentstyleDialog").dialog("close");
					//clear when close
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is valid!");
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "green");
					$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "white");
				}
			},
			close: function(){
				//clear when close
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is valid!");
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "green");
				$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "white");
			}
		});
	});

	/**
	 * This function is used to load data into all dropdownList of add new garment style
	 * H??m d??ng ????? load data v??o t???t c??? dropdownlist khi add m???i garment style.
	 */
    function loadDropDownGarmentKind(){
    	//load data into kind ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentkind/list",
			success: function(data){
				if(data.status == "ok"){
					$.each( data.list, function( key, value ) {
                        $('#editGarmentstyleDialog').find('#garmentkindcode').append($('<option>', {
                            value: value.garmentkindcode,
                            text: value.garmentkindcode
                        }));
                        
                        $('#addGarmentstyleDialog').find('#garmentkindcode').append($('<option>', {
                            value: value.garmentkindcode,
                            text: value.garmentkindcode
                        }));
                    });
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    	
    	//load data into customer ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "customer/list",
			success: function(data){
				if(data.status == "ok"){
					$.each( data.list, function( key, value ) {
						//if status is active, append to ddl
						if(value.status=="Active"){
	                        $('#addGarmentstyleDialog').find('#customercode').append($('<option>', {
	                            value: value.customercode,
	                            text: value.customercode
	                        }));
						}
						
//						//if status is active, append to ddl global
//						if(value.status=="Active"){
//	                        $('#ddlGlobalCustomer').append($('<option>', {
//	                            value: value.customercode,
//	                            text: value.customercode
//	                        }));
//						}
						
						//m???c ????ch c?? th??? xem nh???ng garment m?? c?? customer in-active
						$('#editGarmentstyleDialog').find('#customercode').append($('<option>', {
                            value: value.customercode,
                            text: value.customercode
                        }));
//						$('#editGarmentstyleDialog').find('#customercode').append(value.status=="Active"?'<option value="'+value.customercode+'">'+value.customercode:'<option value="'+value.customercode+'" disabled="disabled">'+value.customercode);
                    });
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    	
    	//load data into factory ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "factory/list",
			success: function(data){
				if(data.status == "ok"){
					$.each( data.list, function( key, value ) {
						if(value.status=="Active"){
	                        $('#addGarmentstyleDialog').find('#factorycode').append($('<option>', {
	                            value: value.factorycode,
	                            text: value.shortname
	                        }));
						}
						
						$('#editGarmentstyleDialog').find('#factorycode').append($('<option>', {
                            value: value.factorycode,
                            text: value.shortname
                        }));
//						$('#editGarmentstyleDialog').find('#factorycode').append(value.status=="Active"?'<option value="'+value.factorycode+'">'+value.shortname:'<option value="'+value.factorycode+'" disabled="disabled">'+value.shortname);
                    });
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    	
    	//load data into unit ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "currency/list",
			success: function(data){
				if(data.status == "ok"){
					$.each( data.list, function( key, value ) {
                        $('#editGarmentstyleDialog').find('#ddlPriceUnit').append($('<option>', {
                            value: value.currencycode,
                            text: value.currencycode
                        }));
                        
                        $('#addGarmentstyleDialog').find('#ddlPriceUnit').append($('<option>', {
                            value: value.currencycode,
                            text: value.currencycode
                        }));
                    });
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    };
    
    /**
     * This function is used to load list accessory group into ddlAccessoryGroup
     * H??m ???????c d??ng ????? load list accessory group v??o ddlAccessoryGroup
     */
    function loadListAccessoryGroupByGarmentStyleName(garmentstylecode){
    	$('#configAccessoryDialog').find('#ddlAccessoryGroup').html('');
    	$.ajax({
    		dataType: "json",
    		type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/getListAccessoryGroupByGarmentStyleName",
			success: function(data){
				if(data.status == "ok"){
					$('#configAccessoryDialog').find('#ddlAccessoryGroup').append('<option disabled selected>--Please Select--');
					$.each( data.lstAccessoryGroupByGarmentStyleName, function( key, value ) {
                        $('#configAccessoryDialog').find('#ddlAccessoryGroup').append($('<option>', {
                            value: value.accessorygroupCode,
                            text: value.accessorygroupCode
                        }));
                    });
					$('#configAccessoryDialog').find('#ddlAccessoryGroup').append('<option value="All">ALL');
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    };
    
    /**
     * Onchange function for ddlAccessoryGroup
     * H??m onchange cho ddlAccessoryGroup
     */
    $('#configAccessoryDialog').find('#ddlAccessoryGroup').on('change', function () {
    	var accessorygroupcode  = $(this).find("option:selected").val();
    	///reload table
		$("#lastTable").html('');
		$("#lastTable").append('<table class="table table-striped table-bordered display responsive"'+
					'id="listGarmentStyleAccessoryDetailMain">'+
					'<thead>'+
						'<tr>'+
							'<th>No</th>'+
							'<th>Accessory Name</th>'+
							'<th>Size</th>'+
							'<th>Used Value</th>'+
							'<th>Type</th>'+
						'</tr>'+
					'</thead>'+
				'</table>');
		///end reload table
		var garmentstylecode=$("#configAccessoryDialog").find("#garmentstylecode").val();
		//load data into table
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode,
				accessoryGroupCode: accessorygroupcode
			}),
			url: getAbsolutePath() +  "garmentstyle/listGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryGroupName",
			contentType: "application/json",
			success: function(data){
				///TEST
				var i=0;
				var preAccessoryName ="";
				$.each(data.lstGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryGroupName,function(key,value){
					$('<tr>').append(
							$('<td>').text(preAccessoryName.localeCompare(value.accessoryName)==0?i:++i),
//							$('<td>').text(value.accessoryName),
							$('<td>').html('<a class="hplEdit2" data-id="'+value.accessorycode+'">'+value.accessoryName+'</a>'),
							$('<td>').text(value.sizename),
							$('<td>').text(value.usedvalue),
							$('<td>').text(value.typecode)
					).appendTo('#listGarmentStyleAccessoryDetailMain');
					preAccessoryName = value.accessoryName;
				});
				///
				actionToggle2();
				$('#listGarmentStyleAccessoryDetailMain').DataTable( {
					"sPaginationType": "full_numbers",
					paging: true,
			        rowsGroup: [0,1,4]
			    } );
				
			},
			error: function(){
				alert("Can not get data!");
			}
		});
    });
    
    function actionToggle2(){
		//function handle when click on hyperlink
		$('.hplEdit2').on('click', function () {
			
				var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
//				alert(accessorygroupcode);
			
				//get garmentStyleCode, accessoryCode and customerCode
				var garmentstyleCode = $("#configAccessoryDialog").find('#garmentstylecode').val();
				var accessoryCode = $(this).data('id');
				var customerCode = $("#configAccessoryDialog").find('#customercode').val();
				
				var garmentKindCode= $("#configAccessoryDialog").find('#garmentkindcode').val();
				var displayName = $("#configAccessoryDialog").find('#displayName').val();
				
				//assign value for garment name
				$("#addGarmentstyleaccessorydetailDialog").find('#garmentstylecode').val(garmentstyleCode);
				$("#addGarmentstyleaccessorydetailDialog").find('#displayName').val(displayName);
				//g??n gi?? tr??? cho customerCode
				$("#addGarmentstyleaccessorydetailDialog").find('#customerCode').val(customerCode);
				//assign value for accessory name:
				$.ajax({
					dataType: "json",
					type: 'POST',
					data: JSON.stringify({
						accessorycode: accessoryCode,
					}),
					url: getAbsolutePath() +  "accessory/detail",
					contentType: "application/json",
					success: function(data){
						//g???i ajax ????? l???y accessory name qua accessoryCode, v?? hi???n th??? th?? show ra name
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryName').val(data.accessory.name);
						//l???y code lu??n nh??ng add v?? textbox hidden
						$("#addGarmentstyleaccessorydetailDialog").find('#accessoryCode').val(data.accessory.accessorycode);/////
					},
					error: function(){
						alert("Can not get data!");
					}
				});
				//end assign value for accessory name:
				
				loadDdlSizeForGarmentstyleaccessorydetailDialog(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				//test
				$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
				$('#listGarmentstyleaccessorydetailAdd tbody').empty();
				
				getListGarmentstyleaccessorydetailIntoListGarmentstyleaccessorydetailAdd(garmentstyleCode, accessoryCode);
				
				//ki???m tra xem en hay disable n??t add
				enOrDisableBtnAddNewGarmentstyleaccessorydetail(garmentstyleCode, accessoryCode, customerCode, garmentKindCode);
				
				$("#addGarmentstyleaccessorydetailDialog").dialog({
					modal: true,
					show:{
						effect:"blind",
						duration: 500
					},
					title: "Add Garment Style Accessory Detail",
					height: 550,
					width: 700,
					buttons:{
						
					},
					close: function(){
						$("#listGarmentstyleaccessorydetailAdd").dataTable().fnDestroy();
						$('#listGarmentstyleaccessorydetailAdd tbody').empty();
						//load l???i list accessory
						loadListAccessoryByGarmentStyle(garmentstyleCode);
//						var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
						reloadLastTableAfterAddEditDelete(accessorygroupcode);
						$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(accessorygroupcode);
					}
				});
		});
	};
    
	/**
	 * Reload table after add, edit, delete
	 */
	function reloadLastTableAfterAddEditDelete(accessorygroupcode){
//		var accessorygroupcode  = $('#configAccessoryDialog').find('#ddlAccessoryGroup').find("option:selected").val();
		//test
		var agc = accessorygroupcode;
		var garmentstylecode=$("#configAccessoryDialog").find("#garmentstylecode").val();
		//reload ddl
		loadListAccessoryGroupByGarmentStyleName(garmentstylecode);
//		$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(agc);
		///reload table
		$("#lastTable").html('');
		$("#lastTable").append('<table class="table table-striped table-bordered display responsive"'+
					'id="listGarmentStyleAccessoryDetailMain">'+
					'<thead>'+
						'<tr>'+
							'<th>No</th>'+
							'<th>Accessory Name</th>'+
							'<th>Size</th>'+
							'<th>Used Value</th>'+
							'<th>Type</th>'+
						'</tr>'+
					'</thead>'+
				'</table>');
		///end reload table
		//load data into table
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode,
				accessoryGroupCode: agc
			}),
			url: getAbsolutePath() +  "garmentstyle/listGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryGroupName",
			contentType: "application/json",
			success: function(data){
				///TEST
				var i=0;
				var preAccessoryName ="";
				$.each(data.lstGarmentstyleaccessorydetailByGarmentStyleNameAndAccessoryGroupName,function(key,value){
					$('<tr>').append(
							$('<td>').text(preAccessoryName.localeCompare(value.accessoryName)==0?i:++i),
							$('<td>').html('<a class="hplEdit2" data-id="'+value.accessorycode+'">'+value.accessoryName+'</a>'),
							$('<td>').text(value.sizename),
							$('<td>').text(value.usedvalue),
							$('<td>').text(value.typecode)
					).appendTo('#listGarmentStyleAccessoryDetailMain');
					preAccessoryName = value.accessoryName;
				});
				///
				actionToggle2();
				$('#listGarmentStyleAccessoryDetailMain').DataTable( {
					"sPaginationType": "full_numbers",
					paging: true,
			        rowsGroup: [0,1,4]
			    } );
				
			},
			error: function(){
				alert("Can not get data!");
			}
		});
//		$('#configAccessoryDialog').find('#ddlAccessoryGroup').val(agc);
	};
	
    /**
	 * This function is used to call and set params for message dialog.
	 */
	function callMessageDialog(title, messageContent){
		$("#messageDialog").find("#messageContent").text(messageContent);
		$("#messageDialog").dialog({
			modal: true,
			show:{
				effect:"blind",
				duration: 500
			},
			title: title,
			height: 200,
			width: 350,
			hide: {
				effect: "explode",
				duration: 500
			},
			buttons:{
				"OK": function(){
					$("#messageDialog").dialog("close");
				}
			}
		});
	}
	
	/**
	 * This function allow to validate when open add dialog
	 * H??m validate l??c m???i m??? dialog add garment l??n
	 */
	function validateWhenOpenAddGarmentStyleDialog(){
		var garmentstylecode = $("#addGarmentstyleDialog").find("#garmentstylecode").val();
		if(garmentstylecode.trim() === '' || garmentstylecode == null){
			$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
		}
	};
	
	//do not allow input " character 
	$("#addGarmentstyleDialog").find("#garmentstylecode").keydown(function(e) {
		if(e.keyCode==222) return false;
	});
	
	/**
	 * This function is used to validate when user enter garment style name
	 * H??m validate tr?????ng garment name khi add
	 */
	$("#addGarmentstyleDialog").find("#garmentstylecode").on('change keyup paste',function(){
		var garmentstylecode = $(this).val();
		//n???u x??a tr???ng th?? b???t
		if(garmentstylecode.trim() === '' || garmentstylecode == null){
			//css th??ng b??o ra
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Please enter garment style code!");
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		//ki???m tra tr?????ng h???p c?? space ??? ?????u, cu???i
		if(garmentstylecode.trim().length!=garmentstylecode.length){
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("You can't enter space at prefix or suffix!");
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		//tr?????ng h???p over range:
		if(garmentstylecode.length>50){
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Field's length is 50, your input is overrange!");
			$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		
		//tr?????ng h???p isExisted
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode,
				customercode: $("#addGarmentstyleDialog").find("#customercode").val()
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/isExist",
			success: function(data){
				if(data.isExisted){
					//css th??ng b??o ra
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is existed, please choose different one!");
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
					$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "red");
					//disable n??t add
					$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
				}else if(data.isExisted== false){
					//css th??ng b??o ra
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is valid!");
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "green");
					$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "white");
					//disable n??t add
					$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', false);
					
					//n???u x??a tr???ng th?? b???t
					if(garmentstylecode.trim() === '' || garmentstylecode == null){
						//css th??ng b??o ra
						$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Please enter garment style code!");
						$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
						$(this).css("background-color", "red");
						//disable n??t add
						$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
						return;
					}
				}
			},error: function(){
				//tr?????ng h???p x??a h???t input
				//css th??ng b??o ra
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Please enter garment style code!");
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
				$(this).css("background-color", "red");
				//disable n??t add
				$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			}
		});
	});
	
	//h??m onchange cho ddl customer add dialog (is exist)
	$('#addGarmentstyleDialog').find("#customercode").on('change', function (e) {
		var garmentstylecode = $("#addGarmentstyleDialog").find("#garmentstylecode").val();
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: $("#addGarmentstyleDialog").find("#garmentstylecode").val(),
				customercode: $("#addGarmentstyleDialog").find("#customercode").val()
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/isExist",
			success: function(data){
				if(data.isExisted){
					//css th??ng b??o ra
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is existed, please choose different one!");
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
					$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "red");
					//disable n??t add
					$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
				}else if(data.isExisted== false){
					//css th??ng b??o ra
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Garment style code is valid!");
					$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "green");
					$("#addGarmentstyleDialog").find("#garmentstylecode").css("background-color", "white");
					//disable n??t add
					$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', false);
					
					//n???u x??a tr???ng th?? b???t
					if(garmentstylecode.trim() === '' || garmentstylecode == null){
						//css th??ng b??o ra
						$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Please enter garment style code!");
						$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
						$(this).css("background-color", "red");
						//disable n??t add
						$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
						return;
					}
				}
			},error: function(){
				//tr?????ng h???p x??a h???t input
				//css th??ng b??o ra
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").text("Please enter garment style code!");
				$("#addGarmentstyleDialog").find("#errorGarmentstylecode").css("color", "red");
				$(this).css("background-color", "red");
				//disable n??t add
				$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			}
		});
	});
	
	/**
	 * Validate range for description
	 * Validate onkey up cho description textBox add
	 */
	$("#addGarmentstyleDialog").find("#description").on('change keyup paste',function(){
		var description = $(this).val();
		//ch??? validate over range
		if(description.length>500){
			$("#addGarmentstyleDialog").find("#errorDescription").text("Field's length is 500, your input is overrange!");
			$("#addGarmentstyleDialog").find("#errorDescription").css("color", "red");
			$(this).css("background-color", "red");
			return;
		}
		
		$("#addGarmentstyleDialog").find("#errorDescription").text("Valid input!");
		$("#addGarmentstyleDialog").find("#errorDescription").css("color", "green");
		$(this).css("background-color", "white");
	});
	
	/**
	 * End ph???n validate add, edit
	 */
	
	/**
	 * Validate ph???n copy
	 */
	$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").on('change keyup paste',function(){
		var garmentstylecode = $(this).val();
		//n???u x??a tr???ng th?? b???t
		if(garmentstylecode.trim() === '' || garmentstylecode == null){
			//css th??ng b??o ra
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Please enter garment style code!");
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			//$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		//ki???m tra tr?????ng h???p c?? space ??? ?????u, cu???i
		if(garmentstylecode.trim().length!=garmentstylecode.length){
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("You can't enter space at prefix or suffix!");
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			//$("#copyGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		//tr?????ng h???p over range:
		if(garmentstylecode.length>50){
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Field's length is 50, your input is overrange!");
			$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
			$(this).css("background-color", "red");
			//disable n??t add
			//$("#copyGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			return;
		}
		
		//tr?????ng h???p isExisted
		$.ajax({
			dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode,
				customercode: $("#copyGarmentstyleDialog").find("#customercode").val()
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/isExist",
			success: function(data){
				if(data.isExisted){
					//css th??ng b??o ra
					$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Garment style code is existed, please choose different one!");
					$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
					$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "red");
					//disable n??t add
					//$("#copyGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
				}else if(data.isExisted== false){
					//css th??ng b??o ra
					$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Garment style code is valid!");
					$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "green");
					$("#copyGarmentstyleDialog").find("#txtNewGarmentstyleCode").css("background-color", "white");
					//disable n??t add
					//$("#copyGarmentstyleDialog").find("#btnAdd").prop('disabled', false);
					//n???u x??a tr???ng th?? b???t
					if(garmentstylecode.trim() === '' || garmentstylecode == null){
						//css th??ng b??o ra
						$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Please enter garment style code!");
						$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
						$(this).css("background-color", "red");
						//disable n??t add
						//$("#addGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
						return;
					}
				}
			},error: function(){
				//tr?????ng h???p x??a h???t input
				//css th??ng b??o ra
				$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").text("Please enter garment style code!");
				$("#copyGarmentstyleDialog").find("#errorTxtNewGarmentstyleCode").css("color", "red");
				$(this).css("background-color", "red");
				//disable n??t add
				//$("#copyGarmentstyleDialog").find("#btnAdd").prop('disabled', true);
			}
		});
	});
	
	//load data into 2 global ddl customer and factory
	function loadDataintoCustomerAndFactoryGlobal(){
		//load data into customer ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "customer/list",
			success: function(data){
				if(data.status == "ok"){
					$('#ddlGlobalCustomer').append('<option selected="selected" disabled="disabled" value="selectRequest">-- Please Select --');
					$.each( data.list, function( key, value ) {
						//if status is active, append to ddl global
						if(value.status=="Active"){
	                        $('#ddlGlobalCustomer').append($('<option>', {
	                            value: value.customercode,
	                            text: value.customercode
	                        }));
						}
                    });
					$('#ddlGlobalCustomer').append('<option value="All">All');
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    	//
    	
    	//load data into factory ddl
    	$.ajax({
    		dataType: "json",
			type: 'GET',
			data:{},
			contentType: "application/json",
			url: getAbsolutePath() +  "factory/list",
			success: function(data){
				if(data.status == "ok"){
					$('#ddlGlobalFactory').append('<option selected="selected" disabled="disabled" value="selectRequest">-- Please Select --');
					$.each( data.list, function( key, value ) {
						if(value.status=="Active"){
	                        $('#ddlGlobalFactory').append($('<option>', {
	                            value: value.factorycode,
	                            text: value.shortname
	                        }));
						}
                    });
					$('#ddlGlobalFactory').append('<option value="All">All');
				}else{
					alert('This alert should never show!');
				}
			},
			error: function(){
				alert('Error!');
			}
    	});
    	//
	}
	
	//on change customer ddl
	$('#ddlGlobalCustomer').on('change', function (e) {
		var optionSelected = $(this).find("option:selected");
	    var customercode  = optionSelected.val();
	    var factoryCode = $('#ddlGlobalFactory').val();

	    if(customercode=="All"&&factoryCode=="All"){
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					var tmp='';
	    					if(value.garmentstylereferpriceModelList.length>0){
	    						tmp+='<table border="0">';
	    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
	    							if(value1.referprice!= null)
	    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
	    						});
	    						tmp+='</table>';
	    					}
	    					
	    					$('<tr>').append(
	    							$('<td>').text(i++),
	    							$('<td>').text(value.displayName),
	    							$('<td>').text(value.customerShortname),
	    							$('<td>').text(value.factoryShortname),
	    							$('<td>').text(value.garmentkindcode),
	    							$('<td>').html(value.referprice==null?tmp:value.referprice),
	    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
	    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
	    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
	    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
	    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
	    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
	    									+'<option value="Options" disabled selected>Options</option>'
	    									+'<option value="AccessoryList">Accessory List</option>'
	    									+'<option value="Copy">Copy</option>'
	    									+'<option value="Edit">Edit</option>'
	    									+'<option value="Delete">Delete</option></select>')
	    					).appendTo('#listGarmentstyle');
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    else if(customercode=="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    //case 3
	    else if(customerCode!="All"&&factoryCode=="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	    
	  //case 4
	    else if(customerCode!="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode&&value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	});
	
	//on change factory ddl
	$('#ddlGlobalFactory').on('change', function (e) {
		var optionSelected = $(this).find("option:selected");
	    var customercode  = $('#ddlGlobalCustomer').val();
	    var factoryCode = optionSelected.val();

	    if(customercode=="All"&&factoryCode=="All"){
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					var tmp='';
	    					if(value.garmentstylereferpriceModelList.length>0){
	    						tmp+='<table border="0">';
	    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
	    							if(value1.referprice!= null)
	    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
	    						});
	    						tmp+='</table>';
	    					}
    						
	    					$('<tr>').append(
	    							$('<td>').text(i++),
	    							$('<td>').text(value.displayName),
	    							$('<td>').text(value.customerShortname),
	    							$('<td>').text(value.factoryShortname),
	    							$('<td>').text(value.garmentkindcode),
	    							$('<td>').html(value.referprice==null?tmp:value.referprice),
	    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
	    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
	    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
	    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
	    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
	    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
	    									+'<option value="Options" disabled selected>Options</option>'
	    									+'<option value="AccessoryList">Accessory List</option>'
	    									+'<option value="Copy">Copy</option>'
	    									+'<option value="Edit">Edit</option>'
	    									+'<option value="Delete">Delete</option></select>')
	    					).appendTo('#listGarmentstyle');
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    else if(customercode=="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    //case 3
	    else if(customerCode!="All"&&factoryCode=="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	    
	  //case 4
	    else if(customerCode!="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode&&value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	});
	
	/**
	 * load data by customer and factory ddl
	 */
	function loadDataByCustomerAndFactory(customercode, factoryCode) {
//		var optionSelected = $(this).find("option:selected");
//	    var customercode  = $('#ddlGlobalCustomer').val();
//	    var factoryCode = optionSelected.val();

	    if(customercode=="All"&&factoryCode=="All"){
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					var tmp='';
	    					if(value.garmentstylereferpriceModelList.length>0){
	    						tmp+='<table border="0">';
	    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
	    							if(value1.referprice!= null)
	    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
	    						});
	    						tmp+='</table>';
	    					}
    						
	    					$('<tr>').append(
	    							$('<td>').text(i++),
	    							$('<td>').text(value.displayName),
	    							$('<td>').text(value.customerShortname),
	    							$('<td>').text(value.factoryShortname),
	    							$('<td>').text(value.garmentkindcode),
	    							$('<td>').html(value.referprice==null?tmp:value.referprice),
	    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
	    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
	    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
	    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
	    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
	    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
	    									+'<option value="Options" disabled selected>Options</option>'
	    									+'<option value="AccessoryList">Accessory List</option>'
	    									+'<option value="Copy">Copy</option>'
	    									+'<option value="Edit">Edit</option>'
	    									+'<option value="Delete">Delete</option></select>')
	    					).appendTo('#listGarmentstyle');
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    else if(customercode=="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    }
	    
	    //case 3
	    else if(customerCode!="All"&&factoryCode=="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	    
	  //case 4
	    else if(customerCode!="All"&&factoryCode!="All"){
	    	//load theo factory
	    	$("#listGarmentstyle").dataTable().fnDestroy();
	    	$('#listGarmentstyle tbody').empty();	
	    		$.ajax({
	    			dataType: "json",
	    			type: 'GET',
	    			data: {},
	    			url: getAbsolutePath() +  "garmentstyle/list",
	    			contentType: "application/json",
	    			success: function(data){
	    				if(data.list.length==0){
//	    					alert("Table have no data.");
	    				}
	    				var i=1;
	    				$.each(data.list,function(key,value){
	    					if(value.customercode==customercode&&value.factorycode==factoryCode){
	    						var tmp='';
		    					if(value.garmentstylereferpriceModelList.length>0){
		    						tmp+='<table border="0">';
		    						$.each(value.garmentstylereferpriceModelList,function(key,value1){
		    							if(value1.referprice!= null)
		    								tmp+='<tr><td>'+value1.typecode+'</td><td>'+value1.referprice+'</td>'+'</tr>';
		    						});
		    						tmp+='</table>';
		    					}
	    						
	    						
	    						$('<tr>').append(
		    							$('<td>').text(i++),
		    							$('<td>').text(value.displayName),
		    							$('<td>').text(value.customerShortname),
		    							$('<td>').text(value.factoryShortname),
		    							$('<td>').text(value.garmentkindcode),
		    							$('<td>').html(value.referprice==null?tmp:value.referprice),
		    							$('<td>').html((value.imgurl1==null?'':'<a class="fancybox" rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl1+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl1+'" alt=""></a>')
		    									+(value.imgurl2==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl2+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl2+'" alt=""></a>')
		    									+(value.imgurl3==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl3+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl3+'" alt=""></a>')
		    									+(value.imgurl4==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl4+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl4+'" alt=""></a>')
		    									+(value.imgurl5==null?'':'<a class="fancybox" hidden rel="'+ value.garmentstylecode +'"  href="choriGarmentStyleImage/'+value.imgurl5+'"><img height="100" width="100" src="choriGarmentStyleImage/'+value.imgurl5+'" alt=""></a>')),
		    							$('<td>').html('<select class="selectpicker selectOption" data-id="'+value.garmentstylecode+'">'
		    									+'<option value="Options" disabled selected>Options</option>'
		    									+'<option value="AccessoryList">Accessory List</option>'
		    									+'<option value="Copy">Copy</option>'
		    									+'<option value="Edit">Edit</option>'
		    									+'<option value="Delete">Delete</option></select>')
		    					).appendTo('#listGarmentstyle');
	    					}
	    				});
	    				action();
	    				
	    				$('#listGarmentstyle').DataTable( {
	    					"pagingType": "full_numbers"
	    			    } );
	    			},
	    			error: function(){
	    				alert("Can not get data!");
	    			}
	    		});
	    	}
	};
	/**
	 * End load data by customer and factory ddl
	 */
	
	/**
	 * This function allow-not 
	 */
	function enableDisableCustomerFactory(garmentstylecode){
		$.ajax({
    		dataType: "json",
			type: 'POST',
			data: JSON.stringify({
				garmentstylecode: garmentstylecode
			}),
			contentType: "application/json",
			url: getAbsolutePath() +  "garmentstyle/isHasGarmentStyleAccessoryDetail",
			success: function(data){
				//n???u ????ng th?? disable factory v?? customer ddl
				if(data.isHasGarmentStyleAccessoryDetail){
					$("#editGarmentstyleDialog").find("#customercode").prop('disabled', true);
					$("#editGarmentstyleDialog").find("#factorycode").prop('disabled', true);
					$("#editGarmentstyleDialog").find("#garmentkindcode").prop('disabled', true);
				}else if(!data.isHasGarmentStyleAccessoryDetail){
//					$("#editGarmentstyleDialog").find("#customercode").prop('disabled', false);
//					$("#editGarmentstyleDialog").find("#factorycode").prop('disabled', false);
//					$("#editGarmentstyleDialog").find("#garmentkindcode").prop('disabled', false);
					//t???m fix h???t l?? ko edit ??c
					$("#editGarmentstyleDialog").find("#customercode").prop('disabled', true);
					$("#editGarmentstyleDialog").find("#factorycode").prop('disabled', true);
					$("#editGarmentstyleDialog").find("#garmentkindcode").prop('disabled', true);
				}
			},
			error: function(){
				alert('2431!');
			}
    	});
	}
	
	/**
	 * -- H??m ki???m tra add --
	 */
	function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	
	function checkIfAddSuccessfully(){
		//message for add part
		if(getUrlParameter('addResultStatus')==='Success'){
			callMessageDialog("Message", "Add new garment style successfully!");
		}
		if(getUrlParameter('addResultStatus')==='Failed'){
			callMessageDialog("Warning Message", "Add new garment style failed!");
		}
		//message for edit part
		if(getUrlParameter('editResultStatus')==='Success'){
			callMessageDialog("Message", "Edit garment style successfully!");
		}
		if(getUrlParameter('editResultStatus')==='Failed'){
			callMessageDialog("Warning Message", "Edit garment style failed!");
		}
	};
	/**
	 * -- End h??m ki???m tra add --
	 */
	
	/**
	 * handle khi nh???n add ????? save garment v??o database
	 */
	$('#btnAdd').click(function (e) {
	    e.preventDefault();

	    //tr?????ng h???p radio pcs ??c check
	    if($("#addGarmentstyleDialog").find("#radPcs").is(':checked')){
		    var lstGarmentstylereferpriceModel = [];
		    
		    //g???i v??? controller
		    $.ajax({
	    		dataType: "json",
	    		type: 'POST',
				data: JSON.stringify(lstGarmentstylereferpriceModel),
				contentType: "application/json",
				url: getAbsolutePath() +  "garmentstyle/getLstGarmentstylereferpriceWhenAdd/pcs",
				success: function(data){
					//n???u save th??nh c??ng th?? submit form
					if(data.saveStatus=="ok"){
						$("#addGarmentstyleForm").submit();
					}
				},
				error: function(){
					
				}
	    	});
		    //end g???i v??? controller
	    }else if($("#addGarmentstyleDialog").find("#radDozen").is(':checked')){
		    var lstGarmentstylereferpriceModel = [];
		    
		    $('.priceBySizeGroupClass').each(function(i, obj) {
		    	lstGarmentstylereferpriceModel.push({
		    		typecode: $(this).attr('id'),
		    		referprice: $(this).val()
		    	});
			});
		    
		    console.log(lstGarmentstylereferpriceModel);
		    
		    //g???i v??? controller
		    $.ajax({
	    		dataType: "json",
	    		type: 'POST',
				data: JSON.stringify(lstGarmentstylereferpriceModel),
				contentType: "application/json",
				url: getAbsolutePath() +  "garmentstyle/getLstGarmentstylereferpriceWhenAdd/dozen",
				success: function(data){
					//n???u save th??nh c??ng th?? submit form
					if(data.saveStatus=="ok"){
						$("#addGarmentstyleForm").submit();
					}
				},
				error: function(){
					
				}
	    	});
		    //end g???i v??? controller
	    }
	});
	
	/**
	 * Khi click v?? radio pcs th??
	 * - enable textbox pcs
	 * - disable c??c textbox dozen
	 */
	$("#addGarmentstyleDialog").find("#radPcs").click(function(){
		$("#addGarmentstyleDialog").find("#referprice").prop("disabled", false);
		//disable c??c ph???n textbox dozen
		$('.priceBySizeGroupClass').each(function(i, obj) {
			$(this).attr("disabled", "disabled");
		});
	});
	//--------
	$("#editGarmentstyleDialog").find("#radPcs").click(function(){
		$("#editGarmentstyleDialog").find("#referprice").prop("disabled", false);
		//disable c??c ph???n textbox dozen
		$('.priceBySizeGroupClassEdit').each(function(i, obj) {
			$(this).attr("disabled", "disabled");
		});
	});
	
	/**
	 * Khi click v?? radio dozen th??
	 * - enable textbox pcs
	 * - disable c??c textbox dozen
	 */
	$("#addGarmentstyleDialog").find("#radDozen").click(function(){
		$("#addGarmentstyleDialog").find("#referprice").prop("disabled", true);
		//disable c??c ph???n textbox dozen
		$('.priceBySizeGroupClass').each(function(i, obj) {
			$(this).prop("disabled", false);
		});
	});
	//-------
	$("#editGarmentstyleDialog").find("#radDozen").click(function(){
		$("#editGarmentstyleDialog").find("#referprice").prop("disabled", true);
		//disable c??c ph???n textbox dozen
		$('.priceBySizeGroupClassEdit').each(function(i, obj) {
			$(this).prop("disabled", false);
		});
	});
	/**
	 * --------------------------------------------
	 */
	/**
	 * handle khi nh???n add ????? save garment v??o database
	 */
	$('#btnEdit').click(function (e) {
	    e.preventDefault();

	    //tr?????ng h???p radio pcs ??c check
	    if($("#editGarmentstyleDialog").find("#radPcs").is(':checked')){
		    var lstGarmentstylereferpriceModel = [];
		    
		    //g???i v??? controller
		    $.ajax({
	    		dataType: "json",
	    		type: 'POST',
				data: JSON.stringify(lstGarmentstylereferpriceModel),
				contentType: "application/json",
				url: getAbsolutePath() +  "garmentstyle/getLstGarmentstylereferpriceWhenEdit/pcs",
				success: function(data){
					//n???u save th??nh c??ng th?? submit form
					if(data.saveStatus=="ok"){
						$("#editGarmentstyleForm").submit();
					}
				},
				error: function(){
					
				}
	    	});
		    //end g???i v??? controller
	    }else if($("#editGarmentstyleDialog").find("#radDozen").is(':checked')){
		    var lstGarmentstylereferpriceModel = [];
		    
		    $('.priceBySizeGroupClassEdit').each(function(i, obj) {
		    	lstGarmentstylereferpriceModel.push({
		    		typecode: $(this).attr('id'),
		    		referprice: $(this).val()
		    	});
			});
		    
		    console.log(lstGarmentstylereferpriceModel);
		    
		    //g???i v??? controller
		    $.ajax({
	    		dataType: "json",
	    		type: 'POST',
				data: JSON.stringify(lstGarmentstylereferpriceModel),
				contentType: "application/json",
				url: getAbsolutePath() +  "garmentstyle/getLstGarmentstylereferpriceWhenEdit/dozen",
				success: function(data){
					//n???u save th??nh c??ng th?? submit form
					if(data.saveStatus=="ok"){
						$("#editGarmentstyleForm").submit();
					}
				},
				error: function(){
					
				}
	    	});
		    //end g???i v??? controller
	    }
	});
	
	/**
	 * H??m max length cho number
	 */
	var inputQuantity = [];
	
//    function maxlengthForNumber() {
      $(".quantity").each(function(i) {
        inputQuantity[i]=this.defaultValue;
         $(this).data("idx",i); // save this field's index to access later
      });
      $(".quantity").on("keyup", function (e) {
        var $field = $(this),
            val=this.value,
            $thisIndex=parseInt($field.data("idx"),10); // retrieve the index
//        window.console && console.log($field.is(":invalid"));
          //  $field.is(":invalid") is for Safari, it must be the last to not error in IE8
        if (this.validity && this.validity.badInput || isNaN(val) || $field.is(":invalid") ) {
            this.value = inputQuantity[$thisIndex];
            return;
        } 
        if (val.length > Number($field.attr("maxlength"))) {
          val=val.slice(0, 5);
          $field.val(val);
        }
        inputQuantity[$thisIndex]=val;
      });      
//    };
      
     //maxlength cho dozen
    function maxlengthForNumber() {
    	var inputQuantity1 = [];
    	
      $(".quantity1").each(function(i) {
    	  inputQuantity1[i]=this.defaultValue;
         $(this).data("idx",i); // save this field's index to access later
      });
      $(".quantity1").on("keyup", function (e) {
        var $field = $(this),
            val=this.value,
            $thisIndex=parseInt($field.data("idx"),10); // retrieve the index
//        window.console && console.log($field.is(":invalid"));
          //  $field.is(":invalid") is for Safari, it must be the last to not error in IE8
        if (this.validity && this.validity.badInput || isNaN(val) || $field.is(":invalid") ) {
            this.value = inputQuantity1[$thisIndex];
            return;
        } 
        if (val.length > Number($field.attr("maxlength"))) {
          val=val.slice(0, 5);
          $field.val(val);
        }
        inputQuantity1[$thisIndex]=val;
      });      
    };
	/**
	 * End H??m max length cho number
	 */
	
	$(".fancybox").fancybox({
		helpers	: {
//			title	: {
//				type: 'outside'
//			},
			thumbs	: {
				width	: 50,
				height	: 50
			}
		}
	});
	
	loadDataintoCustomerAndFactoryGlobal();
	loadDropDownGarmentKind();
	checkIfAddSuccessfully();
//	loadData();
//	maxlengthForNumber();
});