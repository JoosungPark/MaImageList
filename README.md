## MaImageList
Open API(instagram 및 N사)를 통해 이미지 로딩 및 상세 화면을 보여준다.

## 세부 내용
#### 지원자명 : 박주성
#### 개발환경
+ OS : OS X 10.11
+ IDE : Android Studio 2.3
+ OpenSource
 + piccasso(https://github.com/square/picasso) : 이미지 로더. 
 + okhttp(https://github.com/square/okhttp) : http client.
 + photoview(https://github.com/chrisbanes/PhotoView) : zoomable image view.
 + gson(https://github.com/google/gson) : JSON to DTO deserialization.
 + Simple XML serialization(http://simple.sourceforge.net/) : XML to DTO deserialization.
 
#### 실행환경 및 방법
+ 실행환경 : Galaxy S7
+ 실행방법 : IDE를 통한 Run

#### homework 수행에 대한 회고
+ 개발 진행 과정
 + 대략 나흘에 걸쳐 간단한 앱 만들었습니다. 보다 효율적이면서 유연한 구조에 대한 설계에 대한 고민 시간이 부족한 게 안타까웠습니다.
+ 결과물.
 + UI 관련 : ReplaceBuilder를 정의함으로 Fragment 관리를 했습니다.
 + DTO 관련 : DTO 정의를 통해 JSON/XML 데이터를 동적 deserialization 가능한 구조를 만들었습니다.
 + TaskHandler 관련 : Open API에 필요한 데이터를 요청하는 Task를 관리하는 TaskHandle를 만들어 UI 독립적으로 관리하도록 했습니다.

#### 테스트 방법
![Alt text](/screenShot.png)
+ 앱을 실행하면 1번 화면이 나옵니다.디폴트 검색 API는 인스타그램입니다.
+ 2번 그림처럼 원하는 사용자의 아이디 입력 후 우상단의 돋보기 버튼을 누르면 결과가 나옵니다.
+ 검색 결과 중 보고 싶은 사진을 누르면 3번 화면으로 이동합니다.
+ 2번 그림 붉은 원 표시된 설정 아이콘을 누르면 4번 그림처럼 API 변경이 가능합니다.
+ N사 이미지 검색을 하려면 5번 화면에서 키워드를 누른 후 돋보기 버튼을 누릅니다. 이후 과정은 인스타그램 API 결과와 동일합니다.
