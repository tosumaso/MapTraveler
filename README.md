# ポートフォリオ 

## 概要
* アプリ名:　[MapTraveler](http://maptraveler.site:8887/my-portfolio)

* 機能 
  * googlemapにピンを刺したところに掲示板を作成し、旅の記録をブログのように残すことができる。
  * 他のユーザーはgooglemapから好きな掲示板を選択してコメントやいいねを残すことができる。

* アピールポイント
  * googlemapに紐づいた記事の投稿が可能なため、使いやすいUIを実現できた。
  * Junit5,Mockito,Spring MVC Testを使った機能テストを行った。

## 使用技術
* HTML
* CSS
* Bootstrap
* javascript
* Java 11
* spring-boot 2.5.5
* Junit
* Mockito
* MySQL 5.6.47
* Apache
* Tomcat
* AWS
  * VPC
  * EC2
  * RDS
  * Route53
* Git

## 機能詳細
* ユーザーログイン機能
* ユーザー登録機能
* アプリの使い方紹介
* googlemap
  * ピン立て
  * googlemapapiの設定
* 投稿機能
  * 画像
  * テキスト
  * コメント
* いいね機能
* コメントのwebsocket通信
* 投稿の検索機能
* マイページ
  * 自分の投稿一覧
  * 自分のコメント一覧
  * 自分のいいね一覧

## テスト
* ユーザーログイン、登録機能(ApplicationUserServiceTest.java)
* ユーザーからリクエストを受け取り指定したレスポンスを返すまでの一連の流れ(MapTravelerController.java)
* 以下のビジネスロジック
  * コメント一覧取得機能(CommentServiceImplTest.java)
  * いいねの登録、削除機能(FavouriteServiceImplTest.java)
  * googlemapに表示される掲示板一覧取得機能(MapServiceImplTest.java)
  * 記事の投稿、検索、遷移、マイページ取得機能(PostServiceImplTest.java)

## 実現できなかったこと:対策
1. モダンなwebデザイン: レスポンシブを意識したwebサイトのデザインを学習中。既存のwebサイトを模写してフロントエンド技術になれる。
2. 乱雑、同じ記述が書かれたコード: 定期的に見直しリファクタリング箇所を探す。ビジネスロジックはService層に、同じ記述はAOPに退避させることを意識する。

## 今後について
spring-bootを使った開発も続ける一方、アプリ開発時に苦労したフロントエンドの学習も行いたいと考える。
入社後は企業レベルでの開発と個人アプリでの開発を比較し、自分に足りない技術を都度習得していきたい。
