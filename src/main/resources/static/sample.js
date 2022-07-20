'use strict';

$(function() {
	window.addEventListener('load', function(){
		$.ajax({
			url: 'http://localhost:8082/api/iine/show',
			dataType: 'json',
			data: {
				
				/*引数の指定を変更したい場合はこちらを修正してください*/
				articleId: $('#article_id').val(),
			},
			async: true
		}).done(function(data) {
			console.log(JSON.stringify(data));
			
			/*HTML側でのタグを変更したい場合はこちらを修正してください*/
			$('#count').text(data.count);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert('正しい結果を得られませんでした')
			console.log('XMLHttpRequest:' + XMLHttpRequest.status);
			console.log('textStatus:' + textStatus);
			console.log('errorThrown:' + errorThrown.message);
		});
	});
	
	$(document).on('click', '#iine', function() {
		$.ajax({
			url: 'http://localhost:8082/api/iine',
			dataType: 'json',
			data: {
				
				/*引数の指定を変更したい場合はこちらを修正してください*/
				articleId: $('#article_id').val(),
			},
			async: true
		}).done(function(data) {
			console.log(JSON.stringify(data));
			
			/*HTML側でのタグを変更したい場合はこちらを修正してください*/
			$('#count').text(data.count);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert('正しい結果を得られませんでした')
			console.log('XMLHttpRequest:' + XMLHttpRequest.status);
			console.log('textStatus:' + textStatus);
			console.log('errorThrown:' + errorThrown.message);
		});
	});
});