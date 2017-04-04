## MaImageList
MaImageList show image list and concrete image that user searched and tabbed.<br>
It use open api provided by instagram and naver.
+ Instagram : https://www.instagram.com/%s/media/?max_id=%s
+ Naver : https://openapi.naver.com/v1/search/image.xml 
 
#### How you can test,
![Alt text](/screenShot.png)
+ This app is a just simple app using image open api from instagram or naver.
+ Default open api is instagram. you can choose another one by select setting button like #4 image.
+ And you can choose json deserialization options likewise below code block.<br>
getOperator return 'concrete DTO' and getOperator return appropriate concreted HttpOperator. 
```
    WebWrapper webWrapper = new WebWrapper(activity, WebConfig.HOST_INSTAGRAM)
            .setUri(WebConfig.INSTAGRAM.getApi(instagramParameter.getUserId(), instagramParameter.getMaxId()))
            .setOperator(getOperator());

    taskHandler = new TaskHandler.Builder<>(activity, getResultType())
                    .setWebWrapper(webWrapper)
            .setParameter(instagramParameter)
            .setOnCompletedListener(onCompletedListener)
            .build();

    taskHandler.execute();
``` 

#### Get Image Sequence Diagram
![Alt text](/getImage.png)
+ these diagram are deprecated. I would update up to date ASAP. 

#### Development environment
+ OpenSource
 + piccasso(https://github.com/square/picasso) : Image loader
 + okhttp(https://github.com/square/okhttp) : http client
 + photoview(https://github.com/chrisbanes/PhotoView) : zoomable image view.
 + gson(https://github.com/google/gson) : JSON to DTO deserialization.
 + jackson(https://github.com/FasterXML/jackson-databind/) : JSON to DTO deserialization.
 + Simple XML serialization(http://simple.sourceforge.net/) : XML to DTO deserialization.

#### Improvement
I will update this app with MVVM pattern, Retrofit and RxAndroid.<br>
For more information, view [Apply android mvvm with rxjava and retrofit](https://github.com/JoosungPark/MaImageList/issues/9) issue. 