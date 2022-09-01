# good-button-api
【概要】<br>
いいね！機能実装用のAPIです<br>
使用したいプロジェクト内にカウント表示箇所と、ボタンを設置するだけで使用できます<br>
<br>
【使い方】<br>
1.このプロジェクトをインポートしてください<br>
2.当プロジェクトの/static/sample.jsファイルの中身を、<br>
適用したいプロジェクトの任意のファイル内へ記述してください<br>
※適宜内容はお使いのHTMLに合わせて変更してください<br>
3.任意のデータベースに以下のテーブルをご用意ください<br>
■いいねテーブル<br>
drop table if exists iines cascade;<br>	
CREATE TABLE iines ( <br>							
id SERIAL PRIMARY KEY, <br>
article_id integer NOT NULL,<br>
count integer NOT NULL 	<br>					
);<br>
・create index index_article on iines (article_id);<br>
■いいねとユーザー情報の関連テーブル<br>
drop table if exists iine_user cascade;	<br>		
CREATE TABLE iine_user (<br> 
iine_id integer NOT NULL,<br>
user_id integer NOT NULL	<br>
);<br>
create index index_user on iine_user (user_id);<br>
<br>
【仕様】<br>
・初めていいねする場合はカウントが＋1されます<br>
・同じユーザーのいいねが2回目だった場合は-1されます

