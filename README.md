## MaImageList
Open API(instagram 및 N사)를 통해 이미지 로딩 및 상세 화면을 보여준다.

## 세부 내용
#### 지원자명 : 박주성
##### 개발환경
+ OS : OS X 10.11
+ IDE : Android Studio 2.3
+ OpenSource
 + piccasso(https://github.com/square/picasso) : 이미지 로더. 
 + okhttp(https://github.com/square/okhttp) : http client.
 + photoview(https://github.com/chrisbanes/PhotoView) : zoomable image view.
 + gson(https://github.com/google/gson) : JSON to DTO deserialization.
 + Simple XML serialization(http://simple.sourceforge.net/) : XML to DTO deserialization.
 
#### 실행환경 및 방법
+ 실행환경 : Galaxy S7.
+ 실행방법 : IDE를 통한 Run.

#### homework 수행에 대한 회고
+ 개발 진행 과정
 + 대략 나흘에 걸쳐 간단한 앱 만들었습니다. 보다 효율적이면서 유연한 구조에 대한 설계에 대한 고민 시간이 부족한 게 안타까웠습니다.
+ 결과물.
 + UI 관련 : ReplaceBuilder를 정의함으로 Fragment 관리를 했습니다.
 + DTO 관련 : DTO 정의를 통해 JSON/XML 데이터를 동적 deserialization 가능한 구조를 만들었습니다.
 + TaskHandler 관련 : Open API에 필요한 데이터를 요청하는 Task를 관리하는 TaskHandle를 만들어 UI 독립적으로 관리하도록 했습니다.

#### 테스트 방법
